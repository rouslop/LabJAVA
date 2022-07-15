package com.mantel.api.service;


import com.mantel.api.model.Contenido;
import com.mantel.api.model.GeneradorContenido;


import java.util.List;
public interface GeneradorContenidoService {
    public void agregarGeneradorContenido(GeneradorContenido generadorContenido);

    public boolean existeGCPorEmail(String email);
    public GeneradorContenido obtenerGeneradorContenido(long id);
    public GeneradorContenido obtenerGCPorEmail(String email);
    public boolean checkCredenciales(long id, String email, String contrasenia);
    public GeneradorContenido editarGC(GeneradorContenido gc);
    public List<GeneradorContenido> obtenerGeneradores();
    public boolean eliminarGenerador(String email);

    public List<Contenido> listarContenidos(String email);
//    public boolean eliminarContenidoDeLista(Long idC);
}
