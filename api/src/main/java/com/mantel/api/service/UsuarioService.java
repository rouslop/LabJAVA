package com.mantel.api.service;

import com.mantel.api.model.Usuario;

import java.util.List;

public interface UsuarioService {

    public void agregarUsuario(Usuario usuario);
    public void eliminarUsuario(long usuarioId);
    public List<Usuario> obtenerUsuarios();
    public Usuario obtenerUsuario(long id);
    public Usuario editarUsuario(Usuario usuario);
}
