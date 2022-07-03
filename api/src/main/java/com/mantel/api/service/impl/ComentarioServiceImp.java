package com.mantel.api.service.impl;


import com.mantel.api.model.Comentario;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.CategoriaService;
import com.mantel.api.service.ComentarioService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ComentarioServiceImp implements ComentarioService  {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void agregarComentario(Comentario comentario){
        em.persist(comentario);
    }

    public void eliminarComentario(long id){
        Comentario comentario = em.find(Comentario.class, id);
        em.remove(comentario);
    }
}
