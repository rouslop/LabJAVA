package com.mantel.api.service.impl;

import com.mantel.api.model.Login;
import com.mantel.api.model.TipoUsuario;
import com.mantel.api.service.SistemaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class SistemaServiceImpl implements SistemaService {

//    private String emailActual = "";
//
//    public String getEmailActual() {
//        return emailActual;
//    }
//
//    public void setEmailActual(String emailActual) {
//        this.emailActual = emailActual;
//    }

    @PersistenceContext
    private EntityManager em;

    public Login agregarLogin(Login login) {
        em.persist(login);
        return login;
    }

    public boolean existeLogin(String email) {

        Login login = em.find(Login.class, email);
        if (login != null)
            return true;
        else
            return false;

    }

    public Login obtenerLogin(String email){
        Login login = em.find(Login.class, email);
        if (login != null)
            return login;
        else
            return null;
    }
}




