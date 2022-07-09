package com.mantel.api.service.impl;
import com.mantel.api.model.Usuario;
import com.mantel.api.model.Visualizacion;
import com.mantel.api.service.UsuarioService;
import com.mantel.api.service.VisualizacionService;
import org.springframework.stereotype.Service;

import javax.persistence.*;
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


    public Visualizacion obtenerVisualizacion(long idUsu, long idContenido) {
        Visualizacion v = null;

        TypedQuery<Visualizacion> query = em.createQuery("SELECT v FROM Visualizacion v WHERE v.contenidoId.id = :idCon AND v.usuarioId.id = :idUsu", Visualizacion.class);
        try {
            if ((query.setParameter("idCon", idContenido).setParameter("idUsu", idUsu).getSingleResult()) != null) {
                Visualizacion vi = query.setParameter("idCon", idContenido).setParameter("idUsu", idUsu).getSingleResult();
                v = vi;
            } else {
                return null;
            }

        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }

        return v;
    }








}
