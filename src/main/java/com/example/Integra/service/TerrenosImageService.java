package com.example.Integra.service;

import com.example.Integra.models.TerrenosImage;
import com.example.Integra.models.ViviendaImage;
import com.example.Integra.repositorio.RepositorioImageTerrenos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TerrenosImageService {
    private final RepositorioImageTerrenos repositorioImageTerrenos;
    public List<TerrenosImage> listVivienda() {
        return repositorioImageTerrenos.findAll();
    }

    public void guardarVivienda(TerrenosImage terrenosImage) {
        repositorioImageTerrenos.save(terrenosImage);
        log.info("Guardo nueva imagen");
    }

    public void eliminoVivienda(TerrenosImage terrenosImage) {
        repositorioImageTerrenos.delete(terrenosImage);
        log.info("Eliminado imagen");
    }


    public TerrenosImage getById(Long id){
        var viv = repositorioImageTerrenos.getById(id);
        return viv;
    }


    public List<TerrenosImage> listTerrenosImagebyId(Long id){
        List<TerrenosImage> listTerrenos = repositorioImageTerrenos.findAll();
        listTerrenos = listTerrenos.stream().filter(viv -> viv.getTerrenos().getId().equals(id)).collect(Collectors.toList());
        return listTerrenos;
    }

    public TerrenosImage getbyname(String name){
        List<TerrenosImage> listTerrenos = repositorioImageTerrenos.findAll();
        TerrenosImage foundImage = listTerrenos.stream()
                .filter(img -> img.getNameImage().equals(name))
                .findFirst() // Find the first matching image
                .orElse(null);
        return foundImage;
    }
}
