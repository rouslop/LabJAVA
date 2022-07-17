package com.mantel.api.service;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;

import java.util.List;

public interface UsuarioService {

    public void agregarUsuario(Usuario usuario);
    public void eliminarUsuario(long usuarioId);
    public List<Usuario> obtenerUsuarios();
    public Usuario obtenerUsuario(long id);
    public Usuario obtenerUsuarioPorEmail(String email);
    public boolean existeUsuarioPorEmail(String email);
    public Usuario editarUsuario(Usuario usuario);
    public boolean bloquearUsuario(String email);
    public boolean desbloquearUsuario(String email);
    public boolean eliminadoLogico(String email);
    public boolean checkCredenciales(long id, String email, String contrasenia);
    public List<Contenido> listarFavoritos(long idU);
    public List<Contenido> listarRelacionadosFavoritos(long idU);
    public void agregarContenidoAfavoritos(Contenido c , long id);
    public void eliminarContenidoDeFavoritos(Contenido c, long id);
    public List<Contenido> listarRecomendados(long idUsu);
}
