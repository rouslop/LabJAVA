package com.mantel.api.service.impl;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.CategoriaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarCategoria(Categoria cat){
        em.persist(cat);
    }

    @Override
    public List<Categoria> listaCategoria() {

        Query query = em.createQuery("SELECT c FROM Categoria c ", Categoria.class);
        List<Categoria> lista = query.getResultList();
        return lista;
    }

    public Categoria editarCategoria(Categoria categoria){
        return em.merge(categoria);
    }

    public void eliminarCategoria(long id){
        Categoria c = em.find(Categoria.class, id);
        em.remove(c);
    }

    public Categoria obtenerCategoria(long id){
        Categoria c = em.find(Categoria.class, id);
        return c;
    }





}
