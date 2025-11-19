package edu.utsa.cs3443.questlog.model;

public class GameEntry {
    private final String id;
    private final String userId;
    private String title;
    private LocalDate releaseDate;
    private LocalDate startDate;
    private LocalDate completionDate;
    private Game platform;
    private Tag status;        // BACKLOG, PLAYING, COMPLETED, DROPPED, etc.
    private int rating;           // 0–5 hearts
    private String notes;
    private String imagePath;     // local file path or URL
}

