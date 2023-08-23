package com.example.Integra.models.enumeration;

public enum Coloborador {
    Propiedad ("Propiedad"),
    Coloborador("Coloborador");
    private final String displayName;

    Coloborador(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
