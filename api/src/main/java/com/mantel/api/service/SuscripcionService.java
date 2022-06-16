package com.mantel.api.service;

import com.mantel.api.model.Suscripcion;
import com.mantel.api.model.Usuario;

import java.util.List;

public interface SuscripcionService {

    public void agregarSuscripcion(Suscripcion suscripcion);
    public void eliminarSuscripcion(long suscripcionId);
    public List<Suscripcion> obtenerSuscripciones();
    public Suscripcion obtenerSuscripcion(long id);
    public Suscripcion editarSuscripcion(Suscripcion suscripcion);
}
