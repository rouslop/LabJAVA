package com.mantel.api.service.impl;

import com.mantel.api.model.Login;
import com.mantel.api.model.TipoUsuario;
import com.mantel.api.service.SistemaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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

    public void agregarLogin(Login login) {
        em.persist(login);
    }

    public boolean chequearLogin(String email, String contrasenia) {
        boolean credencialesCorrectas = false;

//        boolean existeLogin = existeLogin(email);
//        if (existeLogin) {
//            Login login = em.find(Login.class, email);
//            if (login.getContrasenia() == contrasenia) {
//                credencialesCorrectas = true;
//            }
//
//        }
//        else{
//            Login l = new Login();
//            l.setTipoUsuario(TipoUsuario.CLIENTE);
//            l.setEmail(email);
//            agregarLogin();
//        }
        return credencialesCorrectas;
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




