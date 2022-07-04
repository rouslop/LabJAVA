package com.mantel.api.service;

import com.mantel.api.model.Visualizacion;



import java.util.List;
public interface VisualizacionService {
    public void agregarVisualizacion(Visualizacion v);
    public List<Visualizacion> obtenerVisualizaciones();


}
