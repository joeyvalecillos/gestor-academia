package com.tuempresa.gestoracademia.repository;

import com.tuempresa.gestoracademia.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}