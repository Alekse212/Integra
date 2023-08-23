package com.example.Integra.repositorio;

import com.example.Integra.models.Local;
import com.example.Integra.models.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioLocal extends JpaRepository<Local,Long> {
}
