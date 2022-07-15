package com.mantel.api.service.impl;

import com.mantel.api.model.Suscripcion;
import com.mantel.api.service.SuscripcionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarSuscripcion(Suscripcion suscripcion){
        em.persist(suscripcion);
    }

    @Override
    public void eliminarSuscripcion(long suscripcionId){
        Suscripcion suscripcion = em.find(Suscripcion.class, suscripcionId);
        em.remove(suscripcion);
    }

    @Override
    public List<Suscripcion> obtenerSuscripciones() {

        Query query = em.createQuery("SELECT s FROM Suscripcion s", Suscripcion.class);
        List<Suscripcion> lista = query.getResultList();
        return lista;
    }

    @Override
    public Suscripcion obtenerSuscripcion(long id) {
        Suscripcion suscripcion = em.find(Suscripcion.class, id);
        return suscripcion;
    }

    @Override
    public Suscripcion editarSuscripcion(Suscripcion suscripcion) {
        return em.merge(suscripcion);
    }

}
