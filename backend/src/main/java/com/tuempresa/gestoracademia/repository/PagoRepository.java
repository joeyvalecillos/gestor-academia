package com.tuempresa.gestoracademia.repository;

import com.tuempresa.gestoracademia.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByAlumnoIdAlumno(Long idAlumno);

    @Query("SELECT SUM(p.importe) FROM Pago p WHERE p.alumno.idAlumno = :idAlumno")
    BigDecimal obtenerTotalPagadoPorAlumno(@Param("idAlumno") Long idAlumno);
}