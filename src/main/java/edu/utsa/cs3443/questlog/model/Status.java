package edu.utsa.cs3443.questlog.model;

public enum Status {
    BACKLOG("Backlog"),
    PLAYING("Playing"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    DROPPED("Dropped");

    private final String display;

    Status(String display) { this.display = display; }

    @Override
    public String toString() { return display; }
}

