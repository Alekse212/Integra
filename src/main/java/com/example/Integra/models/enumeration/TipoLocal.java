package com.example.Integra.models.enumeration;

public enum TipoLocal {
    Nave("Nave"),
    Local("Local");

    private final String displayName;

    TipoLocal(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
