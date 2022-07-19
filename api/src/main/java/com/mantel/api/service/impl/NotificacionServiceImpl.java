package com.mantel.api.service.impl;

import com.mantel.api.model.Notificacion;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.NotificacionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {


    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarNotificacion(Notificacion notificacion) {
        em.persist(notificacion);
    }

    public List<Notificacion> listarNotificaciones(String email){
        Query query = em.createQuery("SELECT n FROM Notificacion n WHERE n.emailReceptor= :email", Notificacion.class);
        List<Notificacion> lista = (List<Notificacion>) query.setParameter("email", email).getResultList();
        return lista;
    }



}
