package com.example.Integra.config;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        http
                .csrf().disable()
                .authorizeRequests().requestMatchers("/private/**", "/static/private/**").hasAnyRole("MANAGER", "ADMIN")
                .and()
                .authorizeRequests()
                .requestMatchers("/", "/public/**", "/static/public/**", "/robots.txt", "/api/v1/auth/**").permitAll()
                .and()
                .authorizeRequests().requestMatchers("/private/shutdown").hasRole( "ADMIN")
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwAuthFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }



}
