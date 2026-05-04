package com.tuempresa.gestoracademia.repository;

import com.tuempresa.gestoracademia.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
	
    List<Matricula> findByAlumnoIdAlumno(Long idAlumno);
}

