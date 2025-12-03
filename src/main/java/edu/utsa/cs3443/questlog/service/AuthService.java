package edu.utsa.cs3443.questlog.service;

import edu.utsa.cs3443.questlog.model.User;
import edu.utsa.cs3443.questlog.repository.CsvUserRepository;
import edu.utsa.cs3443.questlog.repository.UserRepository;
import edu.utsa.cs3443.questlog.util.DataPaths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AuthService {

    private static final AuthService INSTANCE =
            new AuthService(new CsvUserRepository(DataPaths.USERS_PATH));

    private final UserRepository repository;
    private final ObservableList<User> users = FXCollections.observableArrayList();

    private User currentUser;

    private AuthService(UserRepository repository) {
        this.repository = repository;
        try {
            List<User> loaded = repository.loadAll();
            users.addAll(loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuthService getInstance() {
        return INSTANCE;
    }

    public ObservableList<User> getAllUsers() {
        return users;
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }


     // Try to log in with username + password. If successful, sets currentUser and returns it.
    public Optional<User> login(String username, String password) {
        Optional<User> match = users.stream()
                .filter(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst();

        match.ifPresent(u -> currentUser = u);
        return match;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // prevents duplicate usernames
    public void register(User user) {
        boolean exists = users.stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername()));

        if (exists) {
            throw new IllegalArgumentException("Username already exists!");
        }

        users.add(user);
        persist();
    }

    private void persist() {
        try {
            repository.saveAll(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
