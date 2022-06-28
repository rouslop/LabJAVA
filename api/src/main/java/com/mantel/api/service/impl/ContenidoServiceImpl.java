package com.mantel.api.service.impl;

import com.mantel.api.model.Contenido;
import com.mantel.api.service.ContenidoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ContenidoServiceImpl implements ContenidoService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarContenido(Contenido contenido){
        em.persist(contenido);
    }

    @Override
    public void eliminarContenido(long contenidoId) {
        Contenido contenido = em.find(Contenido.class, contenidoId);
        em.remove(contenido);
    }

    @Override
    public List<Contenido> obtenerContenidos(int limit, int offset) {
        Query query = em.createQuery("SELECT c FROM Contenido c", Contenido.class);
        Query q = em.createNativeQuery("SELECT * FROM contenido LIMIT :limit OFFSET :offset");
        q.setParameter("limit", limit);
        q.setParameter("offset", offset);
        List<Contenido> lista = query.getResultList();
        return lista;
    }

    @Override
    public Contenido obtenerContenido(long id) {
        Contenido contenido = em.find(Contenido.class, id);
        return contenido;
    }

    @Override
    public Contenido editarContenido(Contenido contenido) {
        return em.merge(contenido);
    }

    @Override
    public void agregarCategoria (long idContenido, long idCategoria){
        Query query = em.createQuery("INSERT INTO contenidos_categoria (contenido_id, categoria_i) VALUES (idContenido, idCategoria)");

    }

}
