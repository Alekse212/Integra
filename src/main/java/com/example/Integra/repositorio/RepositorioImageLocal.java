package com.example.Integra.repositorio;

import com.example.Integra.models.LocalImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioImageLocal extends JpaRepository<LocalImage, Long> {

}
