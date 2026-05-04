package com.tuempresa.gestoracademia.repository;

import com.tuempresa.gestoracademia.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}