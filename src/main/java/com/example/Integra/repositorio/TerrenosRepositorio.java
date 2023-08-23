package com.example.Integra.repositorio;


import com.example.Integra.models.Terrenos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrenosRepositorio extends JpaRepository<Terrenos, Long> {
        List<Terrenos> findByTitle(String title);
        }
