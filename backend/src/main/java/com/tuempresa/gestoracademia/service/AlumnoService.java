package com.tuempresa.gestoracademia.service;

import com.tuempresa.gestoracademia.model.Alumno;
import com.tuempresa.gestoracademia.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de negocio para la entidad Alumno.
 * Aquí centralizamos la lógica relacionada con alumnos para no meterla en el Controller.
 */
@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    // Inyección por constructor (recomendado)
    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    /**
     * Devuelve todos los alumnos almacenados en la base de datos.
     */
    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    /**
     * Busca un alumno por su ID.
     * Si no existe, lanza una excepción para indicar "no encontrado".
     */
    public Alumno obtenerPorId(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado (id=" + id + ")"));
    }

    /**
     * Guarda un alumno (sirve tanto para crear como para actualizar).
     * Si idAlumno es null → crea.
     * Si idAlumno tiene valor → actualiza (si existe).
     */
    public Alumno guardar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    /**
     * Elimina un alumno por ID.
     * Si no existe, lanza excepción para que quede claro el motivo del fallo.
     */
    public void borrar(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new RuntimeException("No se puede borrar: alumno no encontrado (id=" + id + ")");
        }
        alumnoRepository.deleteById(id);
    }
}