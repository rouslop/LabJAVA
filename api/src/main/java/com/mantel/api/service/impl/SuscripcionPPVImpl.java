package com.mantel.api.service.impl;


import com.mantel.api.model.SuscripcionPerPayView;
import com.mantel.api.service.SuscripcionPPVService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class SuscripcionPPVImpl implements SuscripcionPPVService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void agregarSuscripcionPPV(SuscripcionPerPayView suscripcionPerPayView) {
        em.persist(suscripcionPerPayView);
    }

    @Override
    public SuscripcionPerPayView obtenerSuscripcionPPV(long id) {
        SuscripcionPerPayView suscripcionPerPayView = em.find(SuscripcionPerPayView.class, id);
        return suscripcionPerPayView;
    }
}
