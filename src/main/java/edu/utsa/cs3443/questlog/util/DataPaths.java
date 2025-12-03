package edu.utsa.cs3443.questlog.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class DataPaths {

    // This creates: C:\Users\Aaron\.questlog\entries.csv
    public static final Path ENTRIES_PATH = Paths.get(
            System.getProperty("user.home"),
            ".questlog",
            "entries.csv"
    );

    // C:\Users\<name>\.questlog\users.csv
    public static final Path USERS_PATH = Paths.get(
            System.getProperty("user.home"),
            ".questlog",
            "users.csv"
    );

    private DataPaths() {
        // Prevent instantiation
    }
}
