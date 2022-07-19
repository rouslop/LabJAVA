package com.mantel.api.service.impl;


import com.mantel.api.model.*;
import com.mantel.api.service.GananciaService;
import com.mantel.api.service.SuscripcionPPVService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SuscripcionPPVImpl implements SuscripcionPPVService {

    @PersistenceContext
    private EntityManager em;

    private GananciaService gananciaService;

    public SuscripcionPPVImpl(GananciaService gananciaService){
        super();
        this.gananciaService = gananciaService;
    }

    @Override
    public void agregarSuscripcionPPV(SuscripcionPerPayView suscripcionPerPayView) {
        em.persist(suscripcionPerPayView);
        actualizarGananciaGC(suscripcionPerPayView.getContenidoId());
    }

    @Override
    public SuscripcionPerPayView obtenerSuscripcionPPV(long id) {
        SuscripcionPerPayView suscripcionPerPayView = em.find(SuscripcionPerPayView.class, id);
        return suscripcionPerPayView;
    }

    public List<SuscripcionPerPayView> obtenerSuscripcionesPPV() {

        Query query = em.createQuery("SELECT s FROM SuscripcionPerPayView s", SuscripcionPerPayView.class);
        List<SuscripcionPerPayView> lista = query.getResultList();
        return lista;
    }

    public void actualizarGananciaGC(Contenido  contenido){
        float montoTotal = 0;
        GeneradorContenido gc = contenido.getGeneradorContenidoid();
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();

        List<SuscripcionPerPayView> suscripcionesppv = obtenerSuscripcionesPPV();
        for (SuscripcionPerPayView s : suscripcionesppv){
            if (s.getContenidoId().getGeneradorContenidoid().equals(gc)){
                if (s.getFechaSuscripcion().getMonthValue() == mesActual){
                    montoTotal = montoTotal + s.getMonto();
                }
            }
        }

        float gananciaMensual = (10 * montoTotal) / 100;

        // chequea que no haya un registro de ese mes con ese gc
        if (!(gananciaService.existeRegistroMesConGC(gc, mesActual))){
            Ganancia ga = new Ganancia();
            ga.setGananciaMensual(0);
            ga.setIdGC(gc.getId());
            ga.setMes(mesActual);
            ga.setGananciaMensualPPV(gananciaMensual);
            gananciaService.guardarGanancia(ga);
        }else {
            Ganancia g = gananciaService.obtenerGanancia(gc, mesActual);
            g.setGananciaMensualPPV(gananciaMensual);
            gananciaService.editarGanancia(g);
        }

    }







}
