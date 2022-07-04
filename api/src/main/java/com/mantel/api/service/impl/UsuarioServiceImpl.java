package com.mantel.api.service.impl;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarUsuario(Usuario usuario){
        em.persist(usuario);
    }

    @Override
    public void eliminarUsuario(long usuarioId){
        Usuario usuario = em.find(Usuario.class, usuarioId);
        em.remove(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {

        Query query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return (List<Usuario>) query.getResultList();
    }

    @Override
    public Usuario obtenerUsuario(long id) {
        Usuario usuario = em.find(Usuario.class, id);
        return usuario;
    }

    @Override
    public boolean existeUsuarioPorEmail(String email) {
        boolean existeUsu = false;
        //chequea en la tabla de usuarios que no exista
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        try {
            Usuario usu = query.setParameter("email", email).getSingleResult();
            if(usu != null) existeUsu=true;
        }catch (NoResultException nre){
         //Ignore this because as per your logic this is ok!
        }

        //chequea en la tabla de generadores de contenido que no exista
        TypedQuery<GeneradorContenido> queryDos = em.createQuery("SELECT g FROM GeneradorContenido g WHERE g.email = :email1", GeneradorContenido.class);
        try {
            GeneradorContenido gc = queryDos.setParameter("email1", email).getSingleResult();
            if (gc != null) existeUsu=true;
        }catch (NoResultException nre){
            //Ignore this because as per your logic this is ok!
        }

        return existeUsu;
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        Usuario u = null;

        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        try {
            if( (query.setParameter("email", email).getSingleResult()) != null){
                Usuario usu = query.setParameter("email", email).getSingleResult();
                u = usu;
            }else{
                return null;
            }

        }catch (NoResultException nre){
            //Ignore this because as per your logic this is ok!
        }

        return u;
    }
    @Override
    public Usuario editarUsuario(Usuario usuario) {
        return em.merge(usuario);
    }

    @Override
    public boolean bloquearUsuario(String email) {
        Query q = this.em.createQuery("SELECT u FROM Usuario u WHERE u.email=:email AND u.tipoUsuario='CLIENTE'");
        q.setParameter("email",email);
        Usuario u = (Usuario) q.getSingleResult();
        if(u!=null){
            u.setBloqueado(true);
            this.em.merge(u);
        }
        return false;
    }

    @Override
    public boolean desbloquearUsuario(String email) {
        Query q = this.em.createQuery("SELECT u FROM Usuario u WHERE u.email=:email AND u.tipoUsuario='CLIENTE'");
        q.setParameter("email",email);
        Usuario u = (Usuario) q.getSingleResult();
        if(u!=null){
            u.setBloqueado(false);
            this.em.merge(u);
        }
        return false;
    }

    @Override
    public boolean eliminadoLogico(String email) {
        Query q = this.em.createQuery("SELECT u FROM Usuario u WHERE u.email =:email");
        q.setParameter("email",email);
        Usuario u = (Usuario) q.getSingleResult();
        if(u!=null){
            u.setActivo(false);
            this.em.merge(u);
            return true;
        }
        return false;
    }

    public boolean checkCredenciales(long id, String email, String contrasenia){
        Usuario usuario = em.find(Usuario.class, id);
        if ((usuario.getEmail().equals(email)) && (usuario.getContrasenia().equals(contrasenia))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean rankearContenido(String email, Long idContenido) {
        return false;
    }

    @Override
    public void agregarContenidoAfavoritos(Contenido c, long id) {
        Usuario usuario = em.find(Usuario.class, id);
        Set<Contenido> favoritos = usuario.getFavoritos();
        favoritos.add(c);
        em.merge(usuario);
    }

    @Override
    public void eliminarContenidoDeFavoritos(Contenido c, long id) {
        Usuario usuario = em.find(Usuario.class, id);
        Set<Contenido> favoritos = usuario.getFavoritos();
        favoritos.remove(c);
        em.merge(usuario);
    }


}
