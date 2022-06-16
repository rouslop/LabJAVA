package com.mantel.api.service;

import com.mantel.api.model.Persona;
import com.mantel.api.model.Usuario;

import java.util.List;

public interface PersonaService {

    public void agregarPersona(Persona persona);
    public void eliminarPersona(long personaId);
    public List<Persona> obtenerPersonas();
    public Persona obtenerPersona(long id);
    public Persona editarPersona(Persona persona);
}
