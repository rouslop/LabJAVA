package com.mantel.api.service;

import com.mantel.api.model.Login;

public interface SistemaService {

    public Login agregarLogin(Login login);
    public boolean existeLogin(String email);
    public Login obtenerLogin(String email);
}
