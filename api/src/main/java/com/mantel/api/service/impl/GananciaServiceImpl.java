package com.mantel.api.service.impl;

import com.mantel.api.model.Ganancia;
import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.service.GananciaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GananciaServiceImpl implements GananciaService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Ganancia guardarGanancia(Ganancia ganancia) {
        em.persist(ganancia);
        return ganancia;
    }

    // chequea si existe un registro para ese gc y mes
    public boolean existeRegistroMesConGC (GeneradorContenido generadorContenido, int mes){
        boolean existe = false;
        Query query = em.createQuery("SELECT g FROM Ganancia g", Ganancia.class);
        List<Ganancia> lista = query.getResultList();

        for (Ganancia ganancia : lista){
            if (ganancia.getIdGC() == generadorContenido.getId() && ganancia.getMes() == mes) {
                existe = true;
                return existe;
            }
        }
        return existe;
    }

    public void editarGanancia(Ganancia ganancia){
        em.merge(ganancia);
    }

    @Override
    public Ganancia obtenerGanancia(GeneradorContenido gc, int mes) {
        Query query = em.createQuery("SELECT g FROM Ganancia g", Ganancia.class);
        List<Ganancia> lista = query.getResultList();

        for (Ganancia ganancia : lista){
            if (ganancia.getIdGC() == gc.getId() && ganancia.getMes() == mes) {
                return ganancia;
            }
        }

        return null;
    }

    @Override
    public List<Ganancia> obtenerGananciasGC(long idGC) {

        Query q = em.createQuery("SELECT g FROM Ganancia g WHERE g.idGC= :idGC", Ganancia.class);
        List<Ganancia> gananciasGC = q.setParameter("idGC", idGC).getResultList();

        return gananciasGC;
    }


}
