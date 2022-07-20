package com.mantel.api.service;

import com.mantel.api.model.*;

import java.util.List;

public interface ContenidoService {
    void agregarContenido(Contenido contenido);

    public void eliminarContenido(long contenidoId);

    public Json obtenerContenidos(int limit, int offset);

    public Contenido obtenerContenido(long id);

    public Contenido editarContenido(Contenido usuario);

    public boolean agregarCategoria(long idContenido, long idCategoria);

    public boolean eliminarCategoria(long idContenido, long idCategoria);

    public List<Categoria> listarCategorias(long idContenido);

    public List<Contenido> buscarContenidos(String nombre);

    public void agregarComentario(Contenido contenido, Comentario comentario);

    public List<Contenido> listarRelacionados(long idContenido);

    public List<Contenido> listaContenidos();

    public List<Contenido> listarPorCategoria(long id);

    public List<Contenido> listarPorTipo(String t);
    public List<Contenido> listarPorTipoCategoria(String t, long idCat);

    public List<Contenido> listarContenidosGenerador(GeneradorContenido idGC);

    public TipoContenido devolverTipo(long id);
    public boolean esPayPerView(long id);
    public boolean agregarPersona(long idC, long idP);
    public boolean eliminarPersona(long idC, long idP);
    public List<Persona> listarPersonas(long idC);
    public boolean marcarContenido(long i);
    public boolean DesmarcarContenido(long i);
    public boolean esFavorito(long idC, long idU);
    public boolean estaValorado(long idC, long idU);
    public Integer estaPagoGc(long idCont, long idUser);
    public Integer estaPagoPV(long idCont, long idUser);
    public Integer estaPago(long idCont, long idUser);

    public List<Contenido> listarmarcados(GeneradorContenido i);

    public List<Contenido> listarsinmarcar(GeneradorContenido i);
}
