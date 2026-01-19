package com.example.inscripciones.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient; // Ahora funcionará
import org.springframework.web.server.ResponseStatusException;

import com.example.inscripciones.models.dto.EstudianteDto;
import com.example.inscripciones.models.dto.CursoDto;
import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.repositories.InscripcionRepository;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private WebClient estudianteWebClient;

    @Autowired
    private WebClient cursoWebClient;

    public List<Inscripcion> obtenerTodas() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion registrarInscripcion(AgregarInscripcion nuevo) {
        // 1. Validar Estudiante (Puerto 5001)
        EstudianteDto estudiante = null;
        try {
            estudiante = estudianteWebClient.get()
                .uri("/estudiante/{id}", nuevo.getId_estudiante())
                .retrieve()
                .bodyToMono(EstudianteDto.class)
                .block();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al conectar con Servicio de Estudiantes: " + e.getMessage());
        }

        if (estudiante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no existe.");
        }

        // 2. Validar Curso (Puerto 5002)
        CursoDto curso = null;
        try {
            curso = cursoWebClient.get()
                .uri("/curso/{id}", nuevo.getId_curso())
                .retrieve()
                .bodyToMono(CursoDto.class)
                .block();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al conectar con Servicio de Cursos: " + e.getMessage());
        }

        if (curso == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no existe.");
        }

        // 3. Si ambos existen, guardar la inscripción
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId_estudiante(nuevo.getId_estudiante());
        inscripcion.setId_curso(nuevo.getId_curso());
        
        return inscripcionRepository.save(inscripcion);
    }

    public String eliminar(int id) {
        if (inscripcionRepository.existsById(id)) {
            inscripcionRepository.deleteById(id);
            return "Inscripción eliminada correctamente.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada.");
        }
    }
}