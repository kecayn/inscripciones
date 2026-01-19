package com.example.inscripciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI inscripcionesOpenApi() {
        return new OpenAPI()
        .info(new Info()
        .title("Servicio de Inscripciones")
        .version("1.0")
        .description("Microservicio para gestionar inscripciones de estudiantes en cursos")
        .contact(new Contact()
            .name("Tu Nombre")
            .email("tu-correo@ejemplo.com")));
    }
}