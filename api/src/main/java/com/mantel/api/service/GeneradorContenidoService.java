package com.mantel.api.service;


import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Usuario;


import java.util.List;
public interface GeneradorContenidoService {
    public void agregarGeneradorContenido(GeneradorContenido generadorContenido);

    public boolean existeGCPorEmail(String email);
    public GeneradorContenido obtenerGeneradorContenido(long id);
    public GeneradorContenido obtenerGCPorEmail(String email);
    public boolean checkCredenciales(long id, String email, String contrasenia);
}
