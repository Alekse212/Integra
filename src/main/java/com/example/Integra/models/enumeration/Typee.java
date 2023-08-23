package com.example.Integra.models.enumeration;

public enum Typee {
    Piso("Piso"),
    Atico("Atico"),
    Duplex("Duplex"),
    Casa("Casa"),
    Chalet("Chalet");

    private final String displayName;

    Typee(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
