package com.example.Integra.service;

import com.example.Integra.config.JwtService;
import com.example.Integra.models.User;
import com.example.Integra.repositorio.UserRepositorio;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    private final JwtService jwtService;
    private final UserRepositorio userRepository;

    public UserService(JwtService jwtService, UserRepositorio userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public User getUserFromToken(String token) {

        String username = jwtService.extractUsername(token);

        return userRepository.getByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
    public User findFromEmail (String email){
        return userRepository.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
