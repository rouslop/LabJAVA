package com.mantel.api.service;

import com.mantel.api.model.Contenido;

import java.util.List;

public interface AdminService {
    public List<Contenido> reportesContenidosActivos();
    public List<Contenido> reportesContenidosDisponibles();
    public List<Contenido> reportesContenidosVistos();

    public boolean aprobarContenido(long id);
    public List<Contenido> listarContenidosParaAprobar();

    public boolean bloquearContenido(long id);

    public boolean desbloquearContenido(long id);
}
