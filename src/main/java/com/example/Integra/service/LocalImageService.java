package com.example.Integra.service;

import com.example.Integra.models.LocalImage;
import com.example.Integra.repositorio.RepositorioImageLocal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocalImageService {
    private final RepositorioImageLocal repositorioImageLocal;
    public List<LocalImage> listlocal() {
        return repositorioImageLocal.findAll();
    }

    public void guardarLocal(LocalImage localImage) {
        repositorioImageLocal.save(localImage);
        log.info("Guardo nueva imagen");
    }

    public void eliminoVivienda(LocalImage localImage) {
        repositorioImageLocal.delete(localImage);
        log.info("Eliminado imagen");
    }


    public LocalImage getById(Long id){
        var viv = repositorioImageLocal.getById(id);
        return viv;
    }

    public List<LocalImage> listLocalImagebyId(Long id){
        List<LocalImage> listLocal = repositorioImageLocal.findAll();
        listLocal = listLocal.stream().filter(viv -> viv.getLocal().getId().equals(id)).collect(Collectors.toList());
        return listLocal;
    }

    public LocalImage getbyname(String name){
        List<LocalImage> listLocal = repositorioImageLocal.findAll();
        LocalImage foundImage = listLocal.stream()
                .filter(img -> img.getNameImage().equals(name))
                .findFirst() // Find the first matching image
                .orElse(null);
        return foundImage;
    }


}
