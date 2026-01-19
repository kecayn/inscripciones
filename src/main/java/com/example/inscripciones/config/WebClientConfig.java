package com.example.inscripciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient estudianteWebClient() {
        // Suponiendo que el microservicio de usuarios corre en el puerto 5001
        return WebClient.builder().baseUrl("http://127.0.0.1:5001").build();
    }

    @Bean
    public WebClient cursoWebClient() {
        // Suponiendo que el microservicio de cursos corre en el puerto 5002
        return WebClient.builder().baseUrl("http://127.0.0.1:5002").build();
    }
}