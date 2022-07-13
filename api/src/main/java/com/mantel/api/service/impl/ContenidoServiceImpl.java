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
            if(co.getCategorias().contains(c)){
                res.add(co);
            }
        }
        return res;
    }

    @Override
    public List<Contenido> listarPorTipo(TipoContenido t) {
        return null;
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

    public List<Contenido> listarContenidosGenerador(long idGC){
        List<Contenido> listaContenidos = this.listaContenidos();
        List<Contenido> listaRET = new ArrayList<>();
        for (Contenido con : listaContenidos){
         if(con.getGeneradorContenidoid().getId()==idGC){
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
    public boolean estaPagoGc(long idCont, long idUser){
        Contenido c = this.em.find(Contenido.class,idCont);
        Usuario u = this.em.find(Usuario.class,idUser);
        if((c!=null)&&(u!=null)){
            GeneradorContenido gc = c.getGeneradorContenidoid();
            Query q = this.em.createQuery("SELECT s FROM Suscripcion s WHERE s.usuarioId=:user AND s.generadorContenidoid=:gc");
            q.setParameter("user",u);
            q.setParameter("gc",gc);
            Suscripcion sc = (Suscripcion) q.getResultList().get(0);
            //chequear la fecha
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fecha = LocalDateTime.parse(sc.getFechaVencimiento());
            if((sc!=null)&&(fecha.isBefore(ahora))){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean estaPagoPV(long idCont, long idUser) {
        Usuario u = this.em.find(Usuario.class,idUser);
        Contenido c = this.em.find(Contenido.class,idCont);
        if((u!=null)&&(c!=null)) {
            Query q = this.em.createQuery("SELECT sp FROM SuscripcionPerPayView sp WHERE sp.contenidoId=:cont AND sp.usuarioId=:user");
            q.setParameter("user", u);
            q.setParameter("cont", c);
            SuscripcionPerPayView sc = (SuscripcionPerPayView) q.getResultList().get(0);
            if(sc!=null){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public boolean estaPago(long idCont, long idUser) {
        if(this.esPayPerView(idCont)){
            return this.estaPagoPV(idCont,idUser);
        }
        else{
            return this.estaPagoGc(idCont,idUser);
        }
    }


}
