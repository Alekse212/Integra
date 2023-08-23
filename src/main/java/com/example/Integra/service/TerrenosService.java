package com.example.Integra.service;



import com.example.Integra.models.Terrenos;
import com.example.Integra.repositorio.TerrenosRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TerrenosService {
    private final TerrenosRepositorio TerrenosRepository;

    public List<Terrenos> listTerrenos() {
        return TerrenosRepository.findAll();
    }

    public void guardarTerrenos(Terrenos Terrenos) {
        TerrenosRepository.save(Terrenos);
        log.info("Guardo nueva casa. Titulo: {}", Terrenos.getTitle());
    }

    public void eliminoTerrenos(Terrenos Terrenos) {
        TerrenosRepository.delete(Terrenos);
        log.info("Eliminado terreno. Titulo: {}", Terrenos.getTitle());
    }

    public void modifyTerrenos(Terrenos terrenos) {
        var terrenosold = TerrenosRepository.getById(terrenos.getId());
        eliminoTerrenos(terrenosold);
        guardarTerrenos(terrenos);
    }
    public Terrenos getById(Long id){
        var ter = TerrenosRepository.getById(id);
        return ter;
    }
}