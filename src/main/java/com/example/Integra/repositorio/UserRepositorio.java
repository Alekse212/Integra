package com.example.Integra.repositorio;

import com.example.Integra.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositorio extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);
}
