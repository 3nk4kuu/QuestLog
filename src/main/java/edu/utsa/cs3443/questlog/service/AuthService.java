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

    private static final AuthService INSTANCE = new AuthService(new CsvUserRepository(DataPaths.USERS_PATH));

    private final UserRepository repository;
    private final ObservableList<User> users = FXCollections.observableArrayList();

    private final EntryService entryService = EntryService.getInstance();
    private User currentUser;
    private int nextUserIndex = 1;

    private AuthService(UserRepository repository) {
        this.repository = repository;
        try {
            List<User> loaded = repository.loadAll();
            users.addAll(loaded);

            // Initialize nextUserIndex from existing users
            nextUserIndex = loaded.stream()
                    .map(User::getUserId)
                    .map(id -> id.replaceAll("\\D", "")) // remove letters
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0) + 1;
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

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
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
            throw new IllegalArgumentException("Username already in use.");
        }

        boolean emailExists = users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email already in use.");
        }

        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(generateUserId());
        }

        users.add(user);
        persist();
    }

    public void updateEmail(String newEmail) {
        if (currentUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }

        boolean emailExists = users.stream()
                .anyMatch(u -> !u.getUsername().equals(currentUser.getUsername())
                        && u.getEmail().equalsIgnoreCase(newEmail));

        if (emailExists) {
            throw new IllegalArgumentException("Email already in use.");
        }

        currentUser.setEmail(newEmail);
        persist();
    }

    public void updatePassword(String newPassword) {
        if (currentUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        currentUser.setPassword(newPassword);
        persist();
    }


    public void deleteCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        String userIdToDelete = currentUser.getUserId();
        entryService.deleteEntriesForUser(userIdToDelete);
        users.remove(currentUser);
        currentUser = null;
        persist();
    }

    private String generateUserId() {
        return String.format("u%03d", nextUserIndex++);
    }

    private void persist() {
        try {
            repository.saveAll(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
