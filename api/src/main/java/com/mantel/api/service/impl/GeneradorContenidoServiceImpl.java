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


//        TypedQuery<Contenido> query = em.createQuery("SELECT c FROM Contenido c WHERE c.generadorContenidoid.id = :idgc", Contenido.class);
//        try {
//            List<Contenido> contenidos = query.setParameter("idgc", id).getResultList();
//            if(contenidos != null){
//                generadorContenido.setContenido(contenidos);
//            }
//        }catch (NoResultException nre){
//            //Ignore this because as per your logic this is ok!
//        }

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

//    public void agregarContenidoAlista(Contenido contenido){
//        GeneradorContenido gc = contenido.getGeneradorContenidoid();
//        List<Contenido> contenidos = gc.getContenido();
//        contenidos.add(contenido);
//        editarGC(gc);
//
//    }

    public GeneradorContenido editarGC(GeneradorContenido gc) {
        return em.merge(gc);
    }

    public void eliminarContenidoDeLista(Contenido c){
        GeneradorContenido gc = c.getGeneradorContenidoid();
        List<Contenido> contenidos = gc.getContenido();
        contenidos.remove(c);
        editarGC(gc);
    }

    public List<GeneradorContenido> obtenerGeneradores(){
        Query query = em.createQuery("SELECT g FROM GeneradorContenido g", GeneradorContenido.class);
        List<GeneradorContenido> lista = query.getResultList();
        return lista;
    }
}
