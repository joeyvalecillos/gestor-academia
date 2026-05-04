package com.tuempresa.gestoracademia.service;

import com.tuempresa.gestoracademia.model.Usuario;
import com.tuempresa.gestoracademia.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de negocio para la entidad Usuario.
 * En una versión futura aquí se podría incluir autenticación, hashing de contraseñas y roles.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Devuelve todos los usuarios.
     */
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Devuelve un usuario por ID o lanza excepción si no existe.
     */
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id=" + id + ")"));
    }

    /**
     * Guarda un usuario (crear o actualizar).
     * Nota: passwordHash debería venir ya en formato hash en un sistema real.
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por ID.
     */
    public void borrar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede borrar: usuario no encontrado (id=" + id + ")");
        }
        usuarioRepository.deleteById(id);
    }
}