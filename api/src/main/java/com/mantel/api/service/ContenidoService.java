package com.mantel.api.service;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;

import java.util.List;

public interface ContenidoService {
    void agregarContenido(Contenido contenido);
    public void eliminarContenido(long contenidoId);
    public List<Contenido> obtenerContenidos();
    public Contenido obtenerContenido(long id);
    public Contenido editarContenido(Contenido usuario);

}