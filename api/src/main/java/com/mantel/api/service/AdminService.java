package com.mantel.api.service;

import com.mantel.api.model.Contenido;

import java.util.List;

public interface AdminService {
    public List<Contenido> reportesContenidosActivos();
    public List<Contenido> reportesContenidosDisponibles();
    public List<Contenido> reportesContenidosVistos();
}
