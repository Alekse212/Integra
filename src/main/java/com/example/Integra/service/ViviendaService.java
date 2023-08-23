package com.example.Integra.service;


import com.example.Integra.models.Vivienda;
import com.example.Integra.repositorio.ViviendaRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViviendaService {
    private final ViviendaRepositorio viviendaRepository;

    public List<Vivienda> listVivienda() {
        return viviendaRepository.findAll();
    }

    public void guardarVivienda(Vivienda vivienda) {
        viviendaRepository.save(vivienda);
        log.info("Guardo nueva casa. Titulo: {}", vivienda.getTitle());
    }

    public void eliminoVivienda(Vivienda vivienda) {
        viviendaRepository.delete(vivienda);
        log.info("Eliminado casa. Titulo: {}", vivienda.getTitle());
    }

    public void modifyVivienda(Vivienda vivienda) {
        var viviendaold = viviendaRepository.getById(vivienda.getId());
                eliminoVivienda(viviendaold);
                guardarVivienda(vivienda);

    }
    public Vivienda getById(Long id){
        var viv = viviendaRepository.getById(id);
        return viv;
    }
}