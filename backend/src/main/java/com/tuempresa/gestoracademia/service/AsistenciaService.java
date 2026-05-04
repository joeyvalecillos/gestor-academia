package com.tuempresa.gestoracademia.service;

import com.tuempresa.gestoracademia.model.Asistencia;
import com.tuempresa.gestoracademia.repository.AsistenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de negocio para la entidad Asistencia.
 * Centraliza la lógica relacionada con el control de asistencias.
 */
@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    // Inyección por constructor
    public AsistenciaService(AsistenciaRepository asistenciaRepository) {
        this.asistenciaRepository = asistenciaRepository;
    }

    /**
     * Devuelve todas las asistencias registradas.
     */
    public List<Asistencia> obtenerTodas() {
        return asistenciaRepository.findAll();
    }

    /**
     * Busca una asistencia por su ID.
     * Lanza excepción si no existe.
     */
    public Asistencia obtenerPorId(Long id) {
        return asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada (id=" + id + ")"));
    }

    /**
     * Guarda un registro de asistencia.
     * Sirve tanto para crear como actualizar.
     */
    public Asistencia guardar(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    /**
     * Elimina un registro de asistencia por ID.
     */
    public void borrar(Long id) {
        if (!asistenciaRepository.existsById(id)) {
            throw new RuntimeException("No se puede borrar: asistencia no encontrada (id=" + id + ")");
        }
        asistenciaRepository.deleteById(id);
    }
}