package com.example.Integra.models;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class ViviendaRequest {
    private Vivienda vivienda;
    private List<MultipartFile> imageFiles;


    public ViviendaRequest() {
    }

    public ViviendaRequest(Vivienda vivienda, List<MultipartFile> imageFiles) {
        this.vivienda = vivienda;
        this.imageFiles = imageFiles;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    public List<MultipartFile> getImageFiles() {
        return imageFiles;
    }
}
