package com.mantel.api.service.impl;


import com.mantel.api.model.*;
import com.mantel.api.service.GananciaService;
import com.mantel.api.service.GeneradorContenidoService;
import com.mantel.api.service.RankService;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GeneradorContenidoServiceImpl implements GeneradorContenidoService{
    @PersistenceContext
    private EntityManager em;

    private GananciaService gananciaService;

    public GeneradorContenidoServiceImpl (GananciaService gananciaService){
        super();
        this.gananciaService = gananciaService;
    }

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
            else{ return null;}
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

    public double obtenerRank(long idC) {
        Query q = this.em.createQuery("SELECT r FROM Rank r WHERE r.idContenido =:idc");
        q.setParameter("idc",idC);
        List<Rank> valoraciones = q.getResultList();
        if(valoraciones.isEmpty()){
            return 0;
        }
        int total = 0;
        for (Rank r: valoraciones){
            total = total+r.getPuntaje();
        }
        double res = total/valoraciones.size();
        return res;
    }

    @Override
    public List<DtReporte> obtenerReportes(long id) {
        GeneradorContenido gc = this.obtenerGeneradorContenido(id);
        if(gc!=null){
           List<Contenido> contenidos = this.listarContenidosTotales(gc.getEmail());
           List<DtReporte> res = new ArrayList<>();
           for(Contenido c: contenidos){
               Query q = this.em.createQuery("SELECT v FROM Visualizacion v WHERE v.contenidoId=:cont");
               q.setParameter("cont",c);
               Query q1 = this.em.createQuery("SELECT n FROM Notificacion n WHERE n.idContenido=:con");
               q1.setParameter("con",c.getId());
               Query q2 = this.em.createQuery("SELECT c FROM Comentario c WHERE c.contenidoid=:co");
               q2.setParameter("co",c);
               DtReporte aux = new DtReporte();
               aux.setContenido(c);
               aux.setVisualizaciones(q.getResultList().size());
               aux.setPuntaje(this.obtenerRank(c.getId()));
               aux.setComentarios(q2.getResultList().size());
               aux.setCompartidos(q1.getResultList().size());
               res.add(aux);
           }
           return res;
        }
        return null;
    }

    @Override
    public List<Contenido> listarContenidos(String email) {
        List<Contenido> contenidos = this.em.createQuery("SELECT c FROM Contenido c").getResultList();
        if(!contenidos.isEmpty()){
            List<Contenido> res = new ArrayList<>();
            Contenido c;
            Query q = this.em.createQuery("SELECT gc FROM GeneradorContenido gc WHERE gc.email=:em");
            q.setParameter("em",email);
            GeneradorContenido gc = (GeneradorContenido) q.getResultList().get(0);
            for(int i=0; i< contenidos.size(); i++){
                c = contenidos.get(i);
                if((c.getGeneradorContenidoid()==gc)&&(c.isActivo())){
                    res.add(c);
                }
            }
            return res;
        }
        else{
            return null;
        }


    }

    @Override
    public List<Contenido> listarContenidosTotales(String email) {
        List<Contenido> contenidos = this.em.createQuery("SELECT c FROM Contenido c").getResultList();
        if(!contenidos.isEmpty()){
            List<Contenido> res = new ArrayList<>();
            Contenido c;
            Query q = this.em.createQuery("SELECT gc FROM GeneradorContenido gc WHERE gc.email=:em");
            q.setParameter("em",email);
            GeneradorContenido gc = (GeneradorContenido) q.getResultList().get(0);
            for(int i=0; i< contenidos.size(); i++){
                c = contenidos.get(i);
                if(c.getGeneradorContenidoid()==gc){
                    res.add(c);
                }
            }
            return res;
        }
        else{
            return null;
        }
    }

    public List<GeneradorContenido> obtenerGeneradores(){
        Query query = em.createQuery("SELECT g FROM GeneradorContenido g", GeneradorContenido.class);
        List<GeneradorContenido> lista = query.getResultList();
        return lista;
    }


}
