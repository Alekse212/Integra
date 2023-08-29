package com.example.Integra.service;

import com.example.Integra.models.Vivienda;
import com.example.Integra.models.ViviendaImage;
import com.example.Integra.repositorio.RepositorioImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VivendaImageService {
    private final RepositorioImage repositorioImage;
    public List<ViviendaImage> listVivienda() {
        return repositorioImage.findAll();
    }

    public void guardarVivienda(ViviendaImage viviendaImage) {
        repositorioImage.save(viviendaImage);
        log.info("Guardo nueva imagen");
    }

    public void eliminoVivienda(ViviendaImage viviendaImage) {
        repositorioImage.delete(viviendaImage);
        log.info("Eliminado imagen");
    }


    public ViviendaImage getById(Long id){
        var viv = repositorioImage.getById(id);
        return viv;
    }

    public List<ViviendaImage> listViviendaImagebyId(Long id){
        List<ViviendaImage> listVivienda = repositorioImage.findAll();
        listVivienda = listVivienda.stream().filter(viv -> viv.getVivienda().getId().equals(id)).collect(Collectors.toList());
        return listVivienda;
    }

    public ViviendaImage getbyname(String name){
        List<ViviendaImage> listVivienda = repositorioImage.findAll();
        ViviendaImage foundImage = listVivienda.stream()
                .filter(img -> img.getNameImage().equals(name))
                .findFirst() // Find the first matching image
                .orElse(null);
        return foundImage;
    }

}
