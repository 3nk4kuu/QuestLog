package edu.utsa.cs3443.questlog.model;

import java.time.LocalDate;

public class GameEntry {

    private String id;
    private String title;
    private Platform platform;
    private Status status;
    private LocalDate startDate;
    private LocalDate completionDate;
    private LocalDate releaseDate;
    private String notes;

    public GameEntry(String id) {
        this.id = id;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public LocalDate getReleaseDate() { return releaseDate;
    }

    public String getNotes() {
        return notes;
    }

    // --- Setters ---
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
