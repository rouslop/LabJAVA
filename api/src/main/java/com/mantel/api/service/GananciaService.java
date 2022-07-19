package com.mantel.api.service;

import com.mantel.api.model.Ganancia;
import com.mantel.api.model.GeneradorContenido;

public interface GananciaService {

    public Ganancia guardarGanancia(Ganancia ganancia);
    public boolean existeRegistroMesConGC (GeneradorContenido generadorContenido, int mes);

    public void editarGanancia(Ganancia ganancia);

    public Ganancia obtenerGanancia(GeneradorContenido gc, int mes);

}
