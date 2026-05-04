package com.tuempresa.gestoracademia.controller;

import com.tuempresa.gestoracademia.model.Alumno;
import com.tuempresa.gestoracademia.service.AlumnoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin // permite pruebas desde frontend si luego lo usas
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // GET → listar todos
    @GetMapping
    public List<Alumno> listar() {
        return alumnoService.obtenerTodos();
    }

    // GET → buscar por id
    @GetMapping("/{id}")
    public Alumno obtener(@PathVariable Long id) {
        return alumnoService.obtenerPorId(id);
    }

    // POST → crear alumno
    @PostMapping
    public Alumno crear(@RequestBody Alumno alumno) {
        return alumnoService.guardar(alumno);
    }

    // DELETE → borrar alumno
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alumnoService.borrar(id);
    }
}