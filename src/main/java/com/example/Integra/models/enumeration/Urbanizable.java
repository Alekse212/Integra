package com.example.Integra.models.enumeration;

public enum Urbanizable {
    Urbanizable("Urbanizable"),
    NoUrbanizable("NoUrbanizable");

    private final String displayName;

    Urbanizable(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
