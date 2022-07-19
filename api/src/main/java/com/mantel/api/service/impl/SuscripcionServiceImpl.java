package com.mantel.api.service.impl;

import com.mantel.api.model.Ganancia;
import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Suscripcion;
import com.mantel.api.service.GananciaService;
import com.mantel.api.service.SuscripcionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional

public class SuscripcionServiceImpl implements SuscripcionService {

    @PersistenceContext
    private EntityManager em;

    private GananciaService gananciaService;

    public SuscripcionServiceImpl (GananciaService gananciaService){
        super();
        this.gananciaService = gananciaService;
    }

    @Override
    public void agregarSuscripcion(Suscripcion suscripcion){
        em.persist(suscripcion);
        actualizarGananciaGC(suscripcion.getGeneradorContenidoid());
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

    public void actualizarGananciaGC(GeneradorContenido gc){
        float montoTotal = 0;

        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();

        List<Suscripcion> suscripciones = obtenerSuscripciones();
        for (Suscripcion s : suscripciones){
            if (s.getGeneradorContenidoid().equals(gc)){
                if (s.getFechaSuscripcion().getMonthValue() == mesActual){
                    montoTotal = montoTotal + s.getMonto();
                }
            }
        }

        float gananciaMensual = (10 * montoTotal) / 100;  // el 10% es la ganancia a setear para el gc

        // chequea que no haya un registro de ese mes con ese gc
        if (!(gananciaService.existeRegistroMesConGC(gc, mesActual))){
            Ganancia ga = new Ganancia();
            ga.setGananciaMensual(gananciaMensual);
            ga.setIdGC(gc.getId());
            ga.setMes(mesActual);
            ga.setGananciaMensualPPV(0);
            gananciaService.guardarGanancia(ga);
        }else {
            Ganancia g = gananciaService.obtenerGanancia(gc, mesActual);
            g.setGananciaMensual(gananciaMensual);
            gananciaService.editarGanancia(g);
        }

    }






}
