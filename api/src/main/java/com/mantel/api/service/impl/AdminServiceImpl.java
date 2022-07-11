package com.mantel.api.service.impl;

import com.mantel.api.model.Contenido;
import com.mantel.api.service.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contenido> reportesContenidosActivos() {
        Query q = this.em.createQuery("SELECT c FROM Contenido c WHERE c.activo= true");
        return q.getResultList();
    }

    @Override
    public List<Contenido> reportesContenidosDisponibles() {
        Query q = this.em.createQuery("SELECT c FROM Contenido c WHERE c.activo= false");
        return q.getResultList();
    }

    @Override
    public List<Contenido> reportesContenidosVistos() {
        Query q = this.em.createNativeQuery("SELECT * FROM contenidos WHERE id IN (SELECT contenido_id FROM visualizaciones)");
        return q.getResultList();
    }

    @Override
    @Transactional
    public boolean aprobarContenido(long id) {
        Contenido c = this.em.find(Contenido.class,id);
        if(c!=null){
            c.setActivo(true);
            this.em.merge(c);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<Contenido> listarContenidosParaAprobar() {
        Query q = this.em.createQuery("SELECT c FROM Contenido c WHERE c.activo= false");
        return q.getResultList();
    }
}
