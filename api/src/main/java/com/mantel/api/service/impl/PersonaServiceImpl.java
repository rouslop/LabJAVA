package com.mantel.api.service.impl;

import com.mantel.api.model.Persona;
import com.mantel.api.service.PersonaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonaServiceImpl implements PersonaService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarPersona(Persona persona) {
        em.persist(persona);
    }

    @Override
    public void eliminarPersona(long personaId) {
        Persona persona = em.find(Persona.class, personaId);
        em.remove(persona);
    }

    @Override
    public List<Persona> obtenerPersonas() {
        Query query = em.createQuery("SELECT p FROM Persona p", Persona.class);
        List<Persona> lista = query.getResultList();
        return lista;
    }

    @Override
    public Persona obtenerPersona(long id) {
        Persona persona = em.find(Persona.class, id);
        return persona;
    }

    @Override
    public Persona editarPersona(Persona persona) {
        return em.merge(persona);
    }
}
