package com.mantel.api.service;

import com.mantel.api.model.Notificacion;

import java.util.List;

public interface NotificacionService {
    public void agregarNotificacion(Notificacion notificacion);
    public List<Notificacion> listarNotificaciones(String email);
}
