package com.tuempresa.gestoracademia.service;

import com.tuempresa.gestoracademia.model.Curso;
import com.tuempresa.gestoracademia.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado (id=" + id + ")"));
    }

    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void borrar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("No se puede borrar: curso no encontrado (id=" + id + ")");
        }
        cursoRepository.deleteById(id);
    }
}