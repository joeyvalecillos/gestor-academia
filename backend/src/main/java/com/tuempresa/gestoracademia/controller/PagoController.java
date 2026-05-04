package com.tuempresa.gestoracademia.controller;

import com.tuempresa.gestoracademia.dto.PagoRequest;
import com.tuempresa.gestoracademia.dto.PagoResponse;
import com.tuempresa.gestoracademia.model.Pago;
import com.tuempresa.gestoracademia.service.PagoService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoResponse> listar() {
        return pagoService.obtenerTodos()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @GetMapping("/alumno/{id}")
    public List<PagoResponse> obtenerPorAlumno(@PathVariable Long id) {
        return pagoService.obtenerPorAlumno(id)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }
    
    @GetMapping("/alumno/{id}/total")
    public BigDecimal obtenerTotalPagadoPorAlumno(@PathVariable Long id) {
        return pagoService.obtenerTotalPagadoPorAlumno(id);
    }
    
    @GetMapping("/alumno/{id}/pendiente")
    public BigDecimal obtenerTotalPendiente(@PathVariable Long id) {
        return pagoService.obtenerTotalPendientePorAlumno(id);
    }

    @PostMapping
    public PagoResponse crear(@RequestBody PagoRequest request) {
        Pago pago = pagoService.crearPago(request);
        return convertirAResponse(pago);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        pagoService.borrar(id);
    }

    private PagoResponse convertirAResponse(Pago pago) {
        PagoResponse response = new PagoResponse();

        response.setIdPago(pago.getIdPago());
        response.setAlumnoId(pago.getAlumno().getIdAlumno());
        response.setNombreAlumno(pago.getAlumno().getNombre());

        if (pago.getCurso() != null) {
            response.setCursoId(pago.getCurso().getIdCurso());
            response.setNombreCurso(pago.getCurso().getNombre());
        }

        response.setFecha(pago.getFecha());
        response.setImporte(pago.getImporte());
        response.setMetodo(pago.getMetodo());
        response.setNota(pago.getNota());

        return response;
    }
}