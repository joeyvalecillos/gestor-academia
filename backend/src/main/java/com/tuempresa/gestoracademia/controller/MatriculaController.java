package com.tuempresa.gestoracademia.controller;

import com.tuempresa.gestoracademia.dto.MatriculaRequest;
import com.tuempresa.gestoracademia.dto.CursoResponse;
import com.tuempresa.gestoracademia.model.Matricula;
import com.tuempresa.gestoracademia.service.MatriculaService;
import com.tuempresa.gestoracademia.dto.MatriculaResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public List<MatriculaResponse> listar() {
        return matriculaService.obtenerTodas().stream().map(m -> {
            MatriculaResponse r = new MatriculaResponse();
            r.setIdMatricula(m.getIdMatricula());
            r.setAlumnoId(m.getAlumno().getIdAlumno());
            r.setNombreAlumno(m.getAlumno().getNombre());
            r.setCursoId(m.getCurso().getIdCurso());
            r.setNombreCurso(m.getCurso().getNombre());
            r.setFechaMatricula(m.getFechaMatricula());
            r.setEstado(m.getEstado());
            return r;
        }).toList();
    }

    @GetMapping("/{id}")
    public MatriculaResponse obtener(@PathVariable Long id) {
        Matricula m = matriculaService.obtenerPorId(id);

        MatriculaResponse r = new MatriculaResponse();
        r.setIdMatricula(m.getIdMatricula());
        r.setAlumnoId(m.getAlumno().getIdAlumno());
        r.setNombreAlumno(m.getAlumno().getNombre());
        r.setCursoId(m.getCurso().getIdCurso());
        r.setNombreCurso(m.getCurso().getNombre());
        r.setFechaMatricula(m.getFechaMatricula());
        r.setEstado(m.getEstado());

        return r;
    }
    @GetMapping("/alumno/{id}")
    public List<MatriculaResponse> obtenerPorAlumno(@PathVariable Long id) {
        return matriculaService.obtenerPorAlumno(id)
                .stream()
                .map(m -> {
                    MatriculaResponse r = new MatriculaResponse();
                    r.setIdMatricula(m.getIdMatricula());
                    r.setAlumnoId(m.getAlumno().getIdAlumno());
                    r.setNombreAlumno(m.getAlumno().getNombre());
                    r.setCursoId(m.getCurso().getIdCurso());
                    r.setNombreCurso(m.getCurso().getNombre());
                    r.setFechaMatricula(m.getFechaMatricula());
                    r.setEstado(m.getEstado());
                    return r;
                })
                .toList();
    }
    
    @GetMapping("/alumno/{id}/cursos")
    public List<CursoResponse> obtenerCursosDeAlumno(@PathVariable Long id) {
        return matriculaService.obtenerCursosPorAlumno(id);
    }
    
    @PostMapping
    public MatriculaResponse crear(@RequestBody MatriculaRequest request) {
        Matricula matricula = matriculaService.crearMatricula(request);

        MatriculaResponse response = new MatriculaResponse();
        response.setIdMatricula(matricula.getIdMatricula());
        response.setAlumnoId(matricula.getAlumno().getIdAlumno());
        response.setNombreAlumno(matricula.getAlumno().getNombre());
        response.setCursoId(matricula.getCurso().getIdCurso());
        response.setNombreCurso(matricula.getCurso().getNombre());
        response.setFechaMatricula(matricula.getFechaMatricula());
        response.setEstado(matricula.getEstado());

        return response;
    }
    
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        matriculaService.borrar(id);
    }
}