package com.example.Integra.models.enumeration;

public enum Orientacion {
    Norte("Norte"),
    Sur("Sur"),
    Este("Este"),
    Oeste("Oeste"),
    Noroeste("Noroeste"),
    Noreste("Noreste"),
    Suroeste("Suroeste"),
    Sureste("Sureste");

    private final String displayName;

    Orientacion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
