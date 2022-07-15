package com.mantel.api.service.impl;


import com.mantel.api.model.Comentario;

import com.mantel.api.model.ComentarioIndividual;
import com.mantel.api.service.ComentarioService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public Comentario obtenerComentario(long id){
        Comentario comentario = em.find(Comentario.class, id);
        return comentario;
    }

    public Comentario editarComentario(Comentario comentario){
        return em.merge(comentario);
    }

    public List<Comentario> listarComentariosContenido(long idContenido){
        List<Comentario> listaResultado = new ArrayList<>();
        Query query = em.createQuery("SELECT c FROM Comentario c", Comentario.class);
        List<Comentario> comentarios = query.getResultList();

        for(Comentario c : comentarios){
            if(c.getContenidoid().getId() == idContenido){
                listaResultado.add(c);
            }
        }

        return listaResultado;
    }

    public void agregarComentarioIndividual(ComentarioIndividual ci){
        em.persist(ci);

    }

    public List<ComentarioIndividual> listarMensajesEntreUsuarios(Long idUsu1, Long idUsu2){
        List<ComentarioIndividual> mensajes = new ArrayList<>();

        Query q = em.createQuery("SELECT m FROM ComentarioIndividual m WHERE (m.idUsu1 = :idUsu1 OR m.idUsu1 =: idUsu2) AND (m.idUsu2 = :idUsu2 OR m.idUsu2 = :idUsu1) ORDER BY m.id", ComentarioIndividual.class);
        mensajes = q.setParameter("idUsu1", idUsu1).setParameter("idUsu2", idUsu2).getResultList();
        return mensajes;
    }


}
