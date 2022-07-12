package com.mantel.api.service;

import com.mantel.api.model.*;

import java.util.List;

public interface ContenidoService {
    void agregarContenido(Contenido contenido);

    public void eliminarContenido(long contenidoId);

    public Json obtenerContenidos(int limit, int offset);

    public Contenido obtenerContenido(long id);

    public Contenido editarContenido(Contenido usuario);

    public void agregarCategoria(long idContenido, long idCategoria);

    public void agregarComentario(Contenido contenido, Comentario comentario);

    public List<Contenido> listarRelacionados(long idContenido);

    public List<Contenido> listaContenidos();

    public List<Contenido> listarPorCategoria(long id);

    public List<Contenido> listarPorTipo(TipoContenido t);

    public List<Contenido> listarContenidosGenerador(long idGC);

    public TipoContenido devolverTipo(long id);
    public boolean esPayPerView(long id);

    public boolean estaPagoGc(long idCont, long idUser);
    public boolean estaPagoPV(long idCont, long idUser);
    public boolean estaPago(long idCont, long idUser);
}
