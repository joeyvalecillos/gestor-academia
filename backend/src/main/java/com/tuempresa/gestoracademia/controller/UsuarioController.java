package com.tuempresa.gestoracademia.controller;

import com.tuempresa.gestoracademia.model.Usuario;
import com.tuempresa.gestoracademia.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios del sistema.
 * Permite administrar credenciales y roles.
 */
@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    // Servicio encargado de la lógica de negocio de usuarios
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * GET /usuarios
     * Devuelve la lista de usuarios registrados.
     */
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.obtenerTodos();
    }

    /**
     * GET /usuarios/{id}
     * Obtiene un usuario por su ID.
     */
    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    /**
     * POST /usuarios
     * Crea un nuevo usuario.
     */
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    /**
     * DELETE /usuarios/{id}
     * Elimina un usuario del sistema.
     */
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        usuarioService.borrar(id);
    }
}