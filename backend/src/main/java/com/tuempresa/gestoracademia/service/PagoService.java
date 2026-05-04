package com.tuempresa.gestoracademia.service;

import com.tuempresa.gestoracademia.dto.PagoRequest;
import com.tuempresa.gestoracademia.model.Alumno;
import com.tuempresa.gestoracademia.model.Curso;
import com.tuempresa.gestoracademia.model.Pago;
import com.tuempresa.gestoracademia.repository.AlumnoRepository;
import com.tuempresa.gestoracademia.repository.CursoRepository;
import com.tuempresa.gestoracademia.repository.PagoRepository;
import com.tuempresa.gestoracademia.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoService {
	private final PagoRepository pagoRepository;
	private final AlumnoRepository alumnoRepository;
	private final CursoRepository cursoRepository;
	private final MatriculaRepository matriculaRepository;

	public PagoService(PagoRepository pagoRepository,
	                   AlumnoRepository alumnoRepository,
	                   CursoRepository cursoRepository,
	                   MatriculaRepository matriculaRepository) {
	    this.pagoRepository = pagoRepository;
	    this.alumnoRepository = alumnoRepository;
	    this.cursoRepository = cursoRepository;
	    this.matriculaRepository = matriculaRepository;
	}
    
    public BigDecimal obtenerTotalPendientePorAlumno(Long alumnoId) {

        BigDecimal totalPagado = pagoRepository.obtenerTotalPagadoPorAlumno(alumnoId);
        if (totalPagado == null) totalPagado = BigDecimal.ZERO;

        BigDecimal totalCursos = matriculaRepository.findByAlumnoIdAlumno(alumnoId)
                .stream()
                .map(m -> m.getCurso().getPrecioAnual() != null 
                        ? m.getCurso().getPrecioAnual() 
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCursos.subtract(totalPagado);
    }
    
    public BigDecimal obtenerTotalPagadoPorAlumno(Long alumnoId) {
    	BigDecimal total = pagoRepository.obtenerTotalPagadoPorAlumno(alumnoId);

        return total != null ? total : BigDecimal.ZERO;
    }

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public List<Pago> obtenerPorAlumno(Long alumnoId) {
        return pagoRepository.findByAlumnoIdAlumno(alumnoId);
    }

    public Pago crearPago(PagoRequest request) {
        Alumno alumno = alumnoRepository.findById(request.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado (id=" + request.getAlumnoId() + ")"));

        Curso curso = null;
        if (request.getCursoId() != null) {
            curso = cursoRepository.findById(request.getCursoId())
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado (id=" + request.getCursoId() + ")"));
        }

        Pago pago = new Pago();
        pago.setAlumno(alumno);
        pago.setCurso(curso);
        pago.setFecha(request.getFecha() != null ? request.getFecha() : LocalDate.now());
        pago.setImporte(request.getImporte());
        pago.setMetodo(request.getMetodo());
        pago.setNota(request.getNota());

        return pagoRepository.save(pago);
    }

    public void borrar(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("No se puede borrar: pago no encontrado (id=" + id + ")");
        }
        pagoRepository.deleteById(id);
    }
}