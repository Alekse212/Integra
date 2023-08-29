package com.example.Integra.models.enumeration;

public enum Plantas {
    Bajo("Bajo"),
    Intermedios("Intermedios"),
    Ultima_planta("Ultima planta");

    private final String displayName;

    Plantas(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
