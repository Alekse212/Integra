package com.example.Integra.models.enumeration;

public enum Extras {
    Balcon ("Balcón"),
    Piscina("Piscina"),
    VistaAlMar("Vista al Mar"),

    Terraza("Terraza"),
    Garaje("Garaje"),
    Luminoso("Luminoso"),

    Calefaccion("Сalefaccion"),
    Jardin("Jardin"),
    CalefaccionDeAgua("Calefacción de agua"),
    RadianteSuelos("Rodeantes Suelos"),
    Chimenea("Chimenea"),
    VistaAlCuidad("Vista al Cuidad");



    private final String displayName;

    Extras(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
