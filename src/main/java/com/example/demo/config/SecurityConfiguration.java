package com.example.demo.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disabilita CSRF per le API stateless
                .csrf(csrf -> csrf.disable())

                // Configurazione delle autorizzazioni per le rotte
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/auth/**").permitAll() // Permetti l'accesso pubblico a queste rotte
                                .anyRequest().authenticated() // Richiedi autenticazione per tutte le altre richieste
                )

                // Configurazione della gestione della sessione
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Indica che la sessione è stateless
                )

                // Aggiungi il provider di autenticazione personalizzato
                .authenticationProvider(authenticationProvider)

                // Aggiungi il filtro JWT prima del filtro di autenticazione standard di Spring Security
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();  // Costruisce e restituisce la configurazione finale
    }
}
