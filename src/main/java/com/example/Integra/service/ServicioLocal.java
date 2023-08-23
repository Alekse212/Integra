package com.example.Integra.service;

import com.example.Integra.models.Local;
import com.example.Integra.models.Vivienda;
import com.example.Integra.repositorio.RepositorioLocal;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServicioLocal {
    private final RepositorioLocal repositorioLocal;

    public List<Local> listLocal() {
        return repositorioLocal.findAll();
    }

    public void guardarLocal(Local local) {
        repositorioLocal.save(local);
        log.info("Guardo nueva local. Titulo: {}", local.getTitulo());
    }

    public void eliminoLocal(Local local) {
        repositorioLocal.delete(local);
        log.info("Eliminado local. Titulo: {}", local.getTitulo());
    }

    public void modifyLocal(Local local) {
        var localold = getById(local.getId());
        eliminoLocal(localold);
        guardarLocal(local);

    }
    public Local getById(Long id){
        var loc = repositorioLocal.getById(id);
        return loc;
    }
}