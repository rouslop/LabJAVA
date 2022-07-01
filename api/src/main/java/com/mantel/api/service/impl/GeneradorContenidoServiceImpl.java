package com.mantel.api.service.impl;


import com.mantel.api.model.Contenido;
import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.GeneradorContenidoService;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class GeneradorContenidoServiceImpl implements GeneradorContenidoService{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarGeneradorContenido(GeneradorContenido g){
        em.persist(g);
    }

    @Override
    public GeneradorContenido obtenerGeneradorContenido(long id) {
        GeneradorContenido generadorContenido = em.find(GeneradorContenido.class, id);

        return generadorContenido;
    }

    @Override
    public GeneradorContenido obtenerGCPorEmail(String email) {
        GeneradorContenido generadorContenido = null;

        TypedQuery<GeneradorContenido> query = em.createQuery("SELECT g FROM GeneradorContenido g WHERE g.email = :email1", GeneradorContenido.class);
        try {
            GeneradorContenido gc = query.setParameter("email1", email).getSingleResult();
            if (gc != null){
                generadorContenido=gc;
            }
        }catch (NoResultException nre){
            //Ignore this because as per your logic this is ok!
        }
        return generadorContenido;
    }

    public boolean existeGCPorEmail(String email){
        boolean existeGC = false;

        //chequea en la tabla de generadores de contenido si existe
        TypedQuery<GeneradorContenido> queryDos = em.createQuery("SELECT g FROM GeneradorContenido g WHERE g.email = :email1", GeneradorContenido.class);
        try {
            GeneradorContenido gc = queryDos.setParameter("email1", email).getSingleResult();
            if (gc != null) existeGC=true;
        }catch (NoResultException nre){
            //Ignore this because as per your logic this is ok!
        }


        //chequea en la tabla de usuarios que no exista
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        try {
            Usuario usu = query.setParameter("email", email).getSingleResult();
            if(usu != null) existeGC=true;
        }catch (NoResultException nre){
            //Ignore this because as per your logic this is ok!
        }


        return existeGC;
    }

    public boolean checkCredenciales(long id, String email, String contrasenia){

        GeneradorContenido gc = em.find(GeneradorContenido.class, id);
        if (gc.getEmail().equals(email)  && gc.getContrasenia().equals(contrasenia) ){
            return true;
        }else{
            return false;
        }
    }

    public GeneradorContenido editarGC(GeneradorContenido gc) {
        return em.merge(gc);
    }

//    public boolean eliminarContenidoDeLista(Long idC){
//        Contenido c = this.em.find(Contenido.class,idC);
//        GeneradorContenido gc = c.getGeneradorContenidoid();
//        List<Contenido> contenidos = gc.getContenido();
//        contenidos.remove(c);
//        if(editarGC(gc)!=null){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    public boolean eliminarGenerador(String email){
        Query q = this.em.createQuery("SELECT gc FROM GeneradorContenido gc WHERE gc.email=:email");
        q.setParameter("email",email);
        GeneradorContenido gc = (GeneradorContenido) q.getSingleResult();
        if(gc!=null){
            this.em.remove(gc);
            return true;
        }
        else{
            return false;
        }
    }

    public List<GeneradorContenido> obtenerGeneradores(){
        Query query = em.createQuery("SELECT g FROM GeneradorContenido g", GeneradorContenido.class);
        List<GeneradorContenido> lista = query.getResultList();
        return lista;
    }

}
