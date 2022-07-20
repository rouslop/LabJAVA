package com.mantel.api.service;

import com.mantel.api.model.Ganancia;
import com.mantel.api.model.GeneradorContenido;

import java.util.List;

public interface GananciaService {

    public Ganancia guardarGanancia(Ganancia ganancia);
    public boolean existeRegistroMesConGC (GeneradorContenido generadorContenido, int mes);

    public void editarGanancia(Ganancia ganancia);

    public Ganancia obtenerGanancia(GeneradorContenido gc, int mes);

    public List<Ganancia> obtenerGananciasGC(long idGC);

}
