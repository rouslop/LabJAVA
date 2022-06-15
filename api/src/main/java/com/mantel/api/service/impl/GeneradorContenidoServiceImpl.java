package com.mantel.api.service.impl;


import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.service.GeneradorContenidoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class GeneradorContenidoServiceImpl implements GeneradorContenidoService{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarGeneradorContenido(GeneradorContenido g){

        em.persist(g);
    }

}
