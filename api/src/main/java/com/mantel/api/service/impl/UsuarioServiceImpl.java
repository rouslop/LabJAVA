package com.mantel.api.service.impl;

import com.mantel.api.model.Usuario;
import com.mantel.api.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public Usuario editarUsuario(Usuario usuario) {
        return em.merge(usuario);
    }


}
