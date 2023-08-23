package com.example.Integra.models.enumeration;

public enum Estado {
    ObraNueva("ObraNueva"),
    BuenEstado("BuenEstado"),
    AReformar("AReformar");


    private final String displayName;

    Estado(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
