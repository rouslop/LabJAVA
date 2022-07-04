package com.mantel.api.service.impl;
import com.mantel.api.model.Usuario;
import com.mantel.api.model.Visualizacion;
import com.mantel.api.service.UsuarioService;
import com.mantel.api.service.VisualizacionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class VisualizacionServiceImpl implements VisualizacionService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarVisualizacion(Visualizacion v){
        em.persist(v);
    }

    @Override
    public List<Visualizacion> obtenerVisualizaciones() {
        Query query = em.createQuery("SELECT v FROM Visualizacion  v",Visualizacion.class);
        return (List<Visualizacion>) query.getResultList();
    }


}
