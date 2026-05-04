package com.tuempresa.gestoracademia.repository;

import com.tuempresa.gestoracademia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}