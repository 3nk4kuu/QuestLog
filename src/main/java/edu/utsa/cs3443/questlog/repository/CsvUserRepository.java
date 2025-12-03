package edu.utsa.cs3443.questlog.repository;

import edu.utsa.cs3443.questlog.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUserRepository implements UserRepository{
    private final Path file;

    public CsvUserRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<User> loadAll() throws IOException {
        List<User> users = new ArrayList<>();

        if (!Files.exists(file)) {
            // create parent dirs + empty file with header
            Files.createDirectories(file.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                w.write("username,email,password");
                w.newLine();
            }
            return users;
        }

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // keep empty fields
                if (parts.length < 3) continue;

                String username = unescape(parts[0]);
                String email    = unescape(parts[1]);
                String password = unescape(parts[2]);

                users.add(new User(username, email, password));
            }
        }

        return users;
    }

    @Override
    public void saveAll(List<User> users) throws IOException {
        Files.createDirectories(file.getParent());
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            w.write("username,email,password");
            w.newLine();
            for (User u : users) {
                w.write(String.join(",",
                        escape(u.getUsername()),
                        escape(u.getEmail()),
                        escape(u.getPassword())
                ));
                w.newLine();
            }
        }
    }

    // basic escaping so commas/newlines don't break CSV
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace(",", "\\,");
    }

    private String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\n", "\n")
                .replace("\\,", ",")
                .replace("\\\\", "\\");
    }
}
