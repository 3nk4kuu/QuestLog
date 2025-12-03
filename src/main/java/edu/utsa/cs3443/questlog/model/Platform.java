package edu.utsa.cs3443.questlog.model;

public enum Platform {
    PC("PC", "PC"),
    PLAYSTATION("PlayStation", "PS"),
    XBOX("Xbox", "XB"),
    SWITCH("Nintendo Switch", "NS"),
    OTHER("Other", "Other");

    private final String displayName;
    private final String shortName;

    Platform(String displayName, String shortName) {
        this.displayName = displayName;
        this.shortName = shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getShortCode() {
        return shortName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

