package com.mantel.api.service;

import com.mantel.api.model.Visualizacion;


import java.util.List;
public interface VisualizacionService {
    public void agregarVisualizacion(Visualizacion v);
    public List<Visualizacion> obtenerVisualizaciones();
    public Visualizacion obtenerVisualizacion(long idUsu, long idContenido);
    public void agregarTiempoVisualizacion(long idUsu, long idContenido, float tiempoVisualizacion);



}
