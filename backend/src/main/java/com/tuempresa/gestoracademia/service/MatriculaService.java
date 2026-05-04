package com.tuempresa.gestoracademia.service;

import com.tuempresa.gestoracademia.dto.MatriculaRequest;
import com.tuempresa.gestoracademia.model.Alumno;
import com.tuempresa.gestoracademia.model.Curso;
import com.tuempresa.gestoracademia.model.Matricula;
import com.tuempresa.gestoracademia.repository.AlumnoRepository;
import com.tuempresa.gestoracademia.repository.CursoRepository;
import com.tuempresa.gestoracademia.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import com.tuempresa.gestoracademia.dto.CursoResponse;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlumnoRepository alumnoRepository,
                            CursoRepository cursoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Matricula> obtenerTodas() {
        return matriculaRepository.findAll();
    }
    
    public List<Matricula> obtenerPorAlumno(Long alumnoId) {
        return matriculaRepository.findByAlumnoIdAlumno(alumnoId);
    }
    
    public List<CursoResponse> obtenerCursosPorAlumno(Long alumnoId) {
        return matriculaRepository.findByAlumnoIdAlumno(alumnoId)
                .stream()
                .map(m -> {
                    CursoResponse c = new CursoResponse();
                    c.setIdCurso(m.getCurso().getIdCurso());
                    c.setNombre(m.getCurso().getNombre());
                    c.setNivel(m.getCurso().getNivel());
                    return c;
                })
                .toList();
    }

    public Matricula obtenerPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada (id=" + id + ")"));
    }

    public Matricula crearMatricula(MatriculaRequest request) {
        Alumno alumno = alumnoRepository.findById(request.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado (id=" + request.getAlumnoId() + ")"));

        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado (id=" + request.getCursoId() + ")"));

        Matricula matricula = new Matricula();
        matricula.setAlumno(alumno);
        matricula.setCurso(curso);
        matricula.setFechaMatricula(
                request.getFechaMatricula() != null ? request.getFechaMatricula() : LocalDate.now()
        );
        matricula.setEstado(
                request.getEstado() != null ? request.getEstado() : "ACTIVA"
        );

        return matriculaRepository.save(matricula);
    }

    public void borrar(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new RuntimeException("No se puede borrar: matrícula no encontrada (id=" + id + ")");
        }
        matriculaRepository.deleteById(id);
    }
}