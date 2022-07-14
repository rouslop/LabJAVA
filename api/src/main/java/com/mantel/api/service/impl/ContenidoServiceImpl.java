package com.mantel.api.service.impl;

import com.mantel.api.model.*;
import com.mantel.api.service.ContenidoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalTime.now;

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
        Query q = em.createNativeQuery("SELECT * FROM contenidos WHERE activo = true LIMIT :limit OFFSET :offset");
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
        Query query = em.createQuery("SELECT c FROM Contenido c WHERE c.activo=true", Contenido.class);
        return (List<Contenido>) query.getResultList();
    }

    @Override
    public List<Contenido> listarPorCategoria(long id) {
        Categoria c = this.em.find(Categoria.class,id);
        if(c==null){ //si la categoria no existe
            return null;
        }
        List<Contenido> contenidos = this.em.createQuery("SELECT c FROM Contenido c").getResultList();
        List<Contenido> res = new ArrayList<>();
        if(contenidos.isEmpty()){ //si no hay contenidos
            return null;
        }
        Contenido co;
        for(int i=0; i< contenidos.size();i++){
            co = contenidos.get(i);
            if((co.getCategorias().contains(c))&&(co.isActivo())){
                res.add(co);
            }
        }
        return res;
    }

    @Override
    public List<Contenido> listarPorTipo(String t) {
        List<Contenido> contenidos = this.em.createQuery("SELECT c FROM Contenido c").getResultList();
        List<Contenido> res = new ArrayList<>();
        if(!contenidos.isEmpty()) {
            for (Contenido con : contenidos) {
                if ((con.getTipoContenido().toString().equals(t))&&(con.isActivo())) {
                    res.add(con);
                }
            }
        }
        return res;
    }

    @Override
    public List<Contenido> listarPorTipoCategoria(String t, long idCat) {
        List<Contenido> contenidos = this.em.createQuery("SELECT c FROM Contenido c").getResultList();
        List<Contenido> res = new ArrayList<>();
        if(!contenidos.isEmpty()){
            Categoria c = this.em.find(Categoria.class,idCat);
            for (Contenido con: contenidos) {
                if((con.isActivo()&&(con.getTipoContenido().toString().equals(t))&&(con.getCategorias().contains(c)))){
                    res.add(con);
                }
            }
        }
        return res;
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
                        listaResultado.add(con);
                    }
                }
            }
        }

        List<Persona> elenco = contenido.getPersona(); // Elenco contenido principal

        for(Persona persona : elenco){
            for (Contenido con : listaContenidos){
                for(Persona p : con.getPersona()){
                    if (p.getNombre().equals(persona.getNombre())){
                        listaResultado.add(con);
                    }
                }
            }
        }

        //remuevo los duplicados
        Set<Contenido> set = new HashSet<>(listaResultado);
        listaResultado.clear();
        listaResultado.addAll(set);

        listaResultado.remove(contenido); // quito el contenido al q estamos buscando sus relacionados

        return listaResultado;

    }

    public List<Contenido> listarContenidosGenerador(GeneradorContenido idGC){
        List<Contenido> listaContenidos = this.listaContenidos();
        List<Contenido> listaRET = new ArrayList<>();
        for (Contenido con : listaContenidos){
         if(con.getGeneradorContenidoid().getId()==idGC.getId()){
             listaRET.add(con);
         }
        }
        return listaRET;
    }

    @Override
    public TipoContenido devolverTipo(long id) {
        Contenido c = this.em.find(Contenido.class,id);
        if(c!=null) {
            return c.getTipoContenido();
        }
        else return null;
    }

    @Override
    public boolean esPayPerView(long id) {
        Contenido c = this.em.find(Contenido.class,id);
        if(c!=null){
            if((c.getPrecio()>0)&&(c.isActivo())){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public Integer estaPagoGc(long idCont, long idUser){
        Contenido c = this.em.find(Contenido.class,idCont);
        Usuario u = this.em.find(Usuario.class,idUser);
        if((c!=null)&&(u!=null)){
            if(c.getGeneradorContenidoid()==null){
                return -1;
            }
            Query q = this.em.createQuery("SELECT s FROM Suscripcion s WHERE s.usuarioId=:user AND s.generadorContenidoid=:gc");
            q.setParameter("user",u);
            q.setParameter("gc",c.getGeneradorContenidoid());
            if(!q.getResultList().isEmpty()) {
                Suscripcion sc = (Suscripcion) q.getResultList().get(0);
                //chequear la fecha
                if (sc != null) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//                LocalDateTime ahora = LocalDateTime.now();
//                ahora.format(formatter);
//                LocalDateTime fecha = LocalDateTime.parse(sc.getFechaVencimiento(),formatter);
//                if ((fecha.isBefore(ahora))) {
//                    return 1;
//                } else {
//                    return 0;
//                }
                    return 1;
                }
            }
            else {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public Integer estaPagoPV(long idCont, long idUser) {
        Usuario u = this.em.find(Usuario.class,idUser);
        Contenido c = this.em.find(Contenido.class,idCont);
        if((u!=null)&&(c!=null)) {
            Query q = this.em.createQuery("SELECT sp FROM SuscripcionPerPayView sp WHERE sp.contenidoId=:cont AND sp.usuarioId=:user");
            q.setParameter("user", u);
            q.setParameter("cont", c);
            if(!q.getResultList().isEmpty()) {
                SuscripcionPerPayView sc = (SuscripcionPerPayView) q.getResultList().get(0);
                if (sc != null) {
                    return 1;
                } else {
                    return 0;
                }
            }
            else {
                return 0;
            }
        }
        else {
            return -1;
        }
    }

    @Override
    public boolean estaPago(long idCont, long idUser) {
        if(this.esPayPerView(idCont)){
            if(this.estaPagoPV(idCont,idUser)==1){
                return true;
            }
            else{
                System.out.println(this.estaPagoPV(idCont,idUser));
                return false;
            }
        }
        else{
            if(this.estaPagoGc(idCont,idUser)==1){
                return true;
            }
            else {
                return false;
            }
        }
    }

}
