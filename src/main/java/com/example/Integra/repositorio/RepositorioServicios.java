package com.example.Integra.repositorio;

import com.example.Integra.models.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioServicios  extends JpaRepository<Servicios,Long> {
}
