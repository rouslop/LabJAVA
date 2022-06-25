package com.mantel.api.service.impl;

import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

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
        List<Usuario> lista = query.getResultList();
        return lista;
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

    @Override
    public Usuario editarUsuario(Usuario usuario) {
        return em.merge(usuario);
    }

    

}
