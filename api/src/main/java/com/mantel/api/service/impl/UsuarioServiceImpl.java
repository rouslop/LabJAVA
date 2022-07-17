package com.mantel.api.service.impl;

import com.mantel.api.model.*;
import com.mantel.api.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
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

        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.activo=true", Usuario.class);
        return (List<Usuario>) query.getResultList();
    }

    @Override
    public List<Usuario> listarUsuariosBloqueados(){
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.bloqueado=true", Usuario.class);
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

    public Usuario obtenerUsuarioPorEmail(String email) {//devuelve los activos solamente
        Usuario u = null;

        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.activo=true", Usuario.class);
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

      if(this.existeUsuarioPorEmail(email)){
          Query q  = this.em.createQuery("SELECT u FROM Usuario u WHERE u.email =: email");
          q.setParameter("email",email);
          Usuario u = (Usuario) q.getSingleResult();
          if(u!=null){
              u.setBloqueado(true);
              u.setActivo(false);
              this.em.merge(u);
          }
          return true;
      }
      else{
          return false;
      }


    }

    @Override
    public boolean desbloquearUsuario(String email) {
        Query q = this.em.createQuery("SELECT u FROM Usuario u WHERE u.email=:email");
        q.setParameter("email",email);
        Usuario u = (Usuario) q.getSingleResult();
        if(u!=null){
            u.setBloqueado(false);
            u.setActivo(true);
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
    public List<Contenido> listarFavoritos(long idU) {
        Usuario u = this.em.find(Usuario.class, idU);
        if((u!=null)&&(u.isActivo())){
            return u.getFavoritos();
        }
        else {
            return null;
        }
    }

    @Override
    public List<Contenido> listarRelacionadosFavoritos(long idU) {
        Usuario u = this.em.find(Usuario.class,idU);
        if((u!=null)&&(u.isActivo())){
            List<Contenido> favs = u.getFavoritos();//lista de favoritos
            List<Contenido> res = new ArrayList<>();
            List<Categoria> categorias = new ArrayList<>();//categorias de todos los favoritos
            for(Contenido con: favs){ //para cada contenido fav se trae las categorias y las filtra para que no se repitan
                for(Categoria cat: con.getCategorias()){
                    if(!categorias.contains(cat)){
                        categorias.add(cat);
                    }
                }
            }
            //me tengo que traer todos los contenidos de cada categoria
            List<Contenido> cont = this.em.createQuery("SELECT c FROM Contenido c").getResultList();//todos los contenidos del sistema
            for(Categoria cat: categorias){
                for(Contenido con: cont){
                    if((con.getCategorias().contains(cat))&&(!favs.contains(con))){//si en la lista de categorias del contenido tengo esa categoria y no esta en mis favoritos, lo agrego
                        res.add(con);
                    }
                }
            }
            return res;
        }
        return null;
    }

    @Override
    public void agregarContenidoAfavoritos(Contenido c, long id) {
        Usuario usuario = em.find(Usuario.class, id);
        List<Contenido> favoritos = usuario.getFavoritos();
        favoritos.add(c);
        em.merge(usuario);
    }

    @Override
    public void eliminarContenidoDeFavoritos(Contenido c, long id) {
        Usuario usuario = em.find(Usuario.class, id);
        List<Contenido> favoritos = usuario.getFavoritos();
        favoritos.remove(c);
        em.merge(usuario);
    }

    @Override
    public List<Contenido> listarRecomendados(long idUsu) {

        List<Contenido> listaResultado = new ArrayList<>();
        // basado en sus historicos visualizados

        Query query = em.createQuery("SELECT v FROM Visualizacion v WHERE v.usuarioId.id = :idUsu", Visualizacion.class);
        try {
            List<Visualizacion> visualizaciones = query.setParameter("idUsu", idUsu).getResultList();
            for (Visualizacion v : visualizaciones){
                listaResultado.add(v.getContenidoId());
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }

        // basado en sus favoritos

        Usuario usuario = this.obtenerUsuario(idUsu);
        List<Contenido> favoritos = usuario.getFavoritos();
        List<String> nomCategoriasContenido = new ArrayList<>();
        for (Contenido co : favoritos){
            List<Categoria> catsContenido = co.getCategorias();
            for (Categoria cate : catsContenido){
                nomCategoriasContenido.add(cate.getNombre());
            }
        }

        // quito categorias repetidas de nomCategoriasContenido
        Set<String> set = new HashSet<>(nomCategoriasContenido);
        nomCategoriasContenido.clear();
        nomCategoriasContenido.addAll(set);

        Query query2 = em.createQuery("SELECT c FROM Contenido c", Contenido.class);
        List<Contenido> listaContenidos = (List<Contenido>) query2.getResultList();

        // Recorro todos los contenidos y chequeo que sus categorias coincidan con las de sus favoritos
        for (Contenido con : listaContenidos){
            List<Categoria> catsContenido = con.getCategorias();
            for (Categoria cate : catsContenido){
                for (String nomCat : nomCategoriasContenido){
                    if (cate.getNombre().equals(nomCat)){
                        listaResultado.add(con);
                    }
                }
            }
        }

        // recorro todos los contenidos para filtrar solo los destacados
        for (Contenido conte : listaContenidos){
            if (conte.isDestacado()){
                listaResultado.add(conte);
            }
        }

        // quito contenidos repetidos de la lista resultado
        Set<Contenido> set2 = new HashSet<>(listaResultado);
        listaResultado.clear();
        listaResultado.addAll(set2);

        return listaResultado;
    }


}
