package com.example.Integra.repositorio;


import com.example.Integra.models.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViviendaRepositorio extends JpaRepository<Vivienda, Long> {
        List<Vivienda> findByTitle(String title);
        }
