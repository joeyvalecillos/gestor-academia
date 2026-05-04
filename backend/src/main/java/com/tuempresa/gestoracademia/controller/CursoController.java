package com.tuempresa.gestoracademia.controller;

import com.tuempresa.gestoracademia.model.Curso;
import com.tuempresa.gestoracademia.service.CursoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Curso obtener(@PathVariable Long id) {
        return cursoService.obtenerPorId(id);
    }

    @PostMapping
    public Curso crear(@RequestBody Curso curso) {
        return cursoService.guardar(curso);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        cursoService.borrar(id);
    }
}