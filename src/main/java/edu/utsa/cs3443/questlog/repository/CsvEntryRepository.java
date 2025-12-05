package edu.utsa.cs3443.questlog.repository;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Platform;
import edu.utsa.cs3443.questlog.model.Status;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvEntryRepository implements EntryRepository {

    private final Path file;
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    public CsvEntryRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<GameEntry> loadAll() throws IOException {
        List<GameEntry> entries = new ArrayList<>();

        if (!Files.exists(file)) {
            // create parent dirs + empty file with header
            Files.createDirectories(file.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                w.write("id,userId,title,platform,status,releaseDate,startDate,completionDate,notes,rating,coverImage");
                w.newLine();
            }
            return entries;
        }

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // keep empty fields
                if (parts.length < 8) continue;

                GameEntry e = new GameEntry(parts[0]);
                e.setUserId(parts[1]);
                e.setTitle(unescape(parts[2]));
                e.setPlatform(parsePlatform(parts[3]));
                e.setStatus(parseStatus(parts[4]));
                e.setReleaseDate(parseDate(parts[5]));
                e.setStartDate(parseDate(parts[6]));
                e.setCompletionDate(parseDate(parts[7]));

                // rating is optional (for backward compatibility)
                int rating = 0;
                if (parts.length >= 10 && !parts[9].isBlank()) {
                    try {
                        rating = Integer.parseInt(parts[9].trim());
                    } catch (NumberFormatException ex) {
                        rating = 0;
                    }
                }
                e.setRating(rating);
                
                String coverImagePath = null;
                    if (parts.length >= 11) {
                        coverImagePath = unescape(parts[10]);
                    }
                    e.setCoverImagePath(coverImagePath);

                    entries.add(e);
            }
        }

        return entries;
    }

    @Override
    public void saveAll(List<GameEntry> entries) throws IOException {
        Files.createDirectories(file.getParent());
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            w.write("id,userId,title,platform,status,releaseDate,startDate,completionDate,notes,rating,coverImage");            w.newLine();
            for (GameEntry e : entries) {
                w.write(String.join(",",
                        safe(e.getId()),
                        safe(e.getUserId()),
                        escape(e.getTitle()),
                        e.getPlatform() != null ? e.getPlatform().name() : "",
                        e.getStatus() != null ? e.getStatus().name() : "",
                        formatDate(e.getReleaseDate()),
                        formatDate(e.getStartDate()),
                        formatDate(e.getCompletionDate()),
                        escape(e.getNotes()),
                        String.valueOf(e.getRating()),
                        escape(e.getCoverImagePath())
                ));
                w.newLine();
            }
        }
    }

    private Platform parsePlatform(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Platform.valueOf(s);
        } catch (IllegalArgumentException ex) {
            return Platform.OTHER;
        }
    }

    private Status parseStatus(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Status.valueOf(s);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private LocalDate parseDate(String s) {
        if (s == null || s.isBlank()) return null;
        return LocalDate.parse(s, fmt);
    }

    private String formatDate(LocalDate d) {
        return d == null ? "" : fmt.format(d);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    // super-basic escaping so commas/newlines don’t break CSV
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
