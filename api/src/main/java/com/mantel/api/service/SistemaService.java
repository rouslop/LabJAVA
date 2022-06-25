package com.mantel.api.service;

import com.mantel.api.model.Login;

public interface SistemaService {

    public void agregarLogin(Login login);
    public boolean chequearLogin(String email, String contrasenia);
    public boolean existeLogin(String email);
    public Login obtenerLogin(String email);
}
