package com.tuempresa.gestoracademia.controller;

import com.tuempresa.gestoracademia.model.Asistencia;
import com.tuempresa.gestoracademia.service.AsistenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asistencias")
@CrossOrigin
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    // GET → listar todas las asistencias
    @GetMapping
    public List<Asistencia> listar() {
        return asistenciaService.obtenerTodas();
    }

    // GET → buscar por id
    @GetMapping("/{id}")
    public Asistencia obtener(@PathVariable Long id) {
        return asistenciaService.obtenerPorId(id);
    }

    // POST → crear asistencia
    @PostMapping
    public Asistencia crear(@RequestBody Asistencia asistencia) {
        return asistenciaService.guardar(asistencia);
    }

    // DELETE → borrar asistencia
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        asistenciaService.borrar(id);
    }
}