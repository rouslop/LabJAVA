package com.mantel.api.service.impl;

import com.mantel.api.model.*;
import com.mantel.api.service.ContenidoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ContenidoServiceImpl implements ContenidoService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void agregarContenido(Contenido contenido){
        em.persist(contenido);
    }

    @Override
    public void eliminarContenido(long contenidoId) {
        Contenido contenido = em.find(Contenido.class, contenidoId);
        em.remove(contenido);
    }

    @Override
    public Json obtenerContenidos(int limit, int offset) {
        Query query = em.createQuery("SELECT c FROM Contenido c", Contenido.class);
        Query q = em.createNativeQuery("SELECT * FROM contenidos LIMIT :limit OFFSET :offset");
        q.setParameter("limit", limit);
        q.setParameter("offset", offset);
        Hashtable<String,Integer> info = new Hashtable<>();
        info.put("cantidad",query.getResultList().size());
        info.put("limit", limit);
        return new Json(q.getResultList(),info);
    }

    @Override
    public Contenido obtenerContenido(long id) {
        Contenido contenido = em.find(Contenido.class, id);
        return contenido;
    }

    @Override
    public Contenido editarContenido(Contenido contenido) {
        return em.merge(contenido);
    }

    @Override
    public void agregarCategoria (long idContenido, long idCategoria){
        //Query query = em.createQuery("INSERT INTO contenidos_categoria (contenido_id, categoria_i) VALUES (idContenido, idCategoria)");

    }

    public void agregarComentario(Contenido contenido, Comentario comentario){
        List<Comentario> comentarios = contenido.getComentario();
        comentarios.add(comentario);
        this.editarContenido(contenido);
    }

    public List<Contenido> listaContenidos(){
        Query query = em.createQuery("SELECT c FROM Contenido c", Contenido.class);
        return (List<Contenido>) query.getResultList();
    }

    public List<Contenido> listarRelacionados(long idContenido){

        List<Contenido> listaContenidos = this.listaContenidos();
        List<Contenido> listaResultado = new ArrayList<>();

        Contenido contenido = this.obtenerContenido(idContenido);
        List<Categoria> categoriasContenido = contenido.getCategorias();

        for (Categoria categoria : categoriasContenido){ // Categorias contenido principal
            for (Contenido con : listaContenidos){  // Todos los contenidos persistidos
                for(Categoria ca : con.getCategorias()){
                    if ( ca.getNombre() == categoria.getNombre() ){
                        if(con.equals(contenido) == false){
                            listaResultado.add(con); //si no es el contenido del que estoy buscando los relacionados
                        }
                    }
                }
            }
        }

        List<Persona> elenco = contenido.getPersona(); // Elenco contenido principal
        System.out.println("elenco");
        System.out.println(elenco);

        for(Persona persona : elenco){
            for (Contenido con : listaContenidos){
                for(Persona p : con.getPersona()){
                    if (p.getNombre() == persona.getNombre()){
                        System.out.println("son iguales las personas");
                        listaResultado.add(con);

//                        if(listaResultado.indexOf(con) == -1){
//                            listaResultado.add(con);
//                        }
                    }
                }
            }
        }

        return listaResultado;

    }




}
