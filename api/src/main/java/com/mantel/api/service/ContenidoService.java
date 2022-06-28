package com.mantel.api.service;

import com.mantel.api.model.Contenido;

import com.mantel.api.model.Categoria;

import java.util.List;

public interface ContenidoService {
    void agregarContenido(Contenido contenido);
    public void eliminarContenido(long contenidoId);
    public List<Contenido> obtenerContenidos(int limit, int offset);
    public Contenido obtenerContenido(long id);
    public Contenido editarContenido(Contenido usuario);

    public void agregarCategoria (long idContenido, long idCategoria);


}
