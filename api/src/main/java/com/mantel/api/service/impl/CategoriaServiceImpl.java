package com.mantel.api.service.impl;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.CategoriaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarCategoria(Categoria cat){
        em.persist(cat);
    }

}
