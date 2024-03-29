package com.mantel.api.service.impl;

import com.mantel.api.model.*;
import com.mantel.api.service.ContenidoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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
    @Transactional
    public boolean marcarContenido(long i){
        Contenido c = this.em.find(Contenido.class,i);
        if(c!=null){
            c.setDestacado(true);
            this.em.merge(c);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean DesmarcarContenido(long i){
        Contenido c = this.em.find(Contenido.class,i);
        if(c!=null){
            c.setDestacado(false);
            this.em.merge(c);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean esFavorito(long idC, long idU) {
        Contenido c = this.em.find(Contenido.class,idC);
        Usuario u = this.em.find(Usuario.class,idU);
        if((c!=null)&&(u!=null)&&(u.isActivo())&&(!c.isBloqueado())){ //verifica que exista el contenido y no este bloqueado, tambien que el usuario exista y no este bloqueado
            if(u.getFavoritos().contains(c)){
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
    public boolean estaValorado(long idC, long idU) {
        Query q = this.em.createQuery("SELECT r FROM Rank r WHERE r.idUsuario=:user AND r.idContenido=:cont");
        q.setParameter("user",idU);
        q.setParameter("cont",idC);
        if(q.getResultList().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean agregarCategoria (long idContenido, long idCategoria){
        Contenido cont = this.em.find(Contenido.class,idContenido);
        if(cont!=null){
            Categoria cat = this.em.find(Categoria.class,idCategoria);
            if(cat!=null){
                List<Categoria> cats = cont.getCategorias();
                if(cats.contains(cat)){//chequea que la categoria no se repita
                    return false;
                }
                cats.add(cat);
                cont.setCategorias(cats);
                this.em.merge(cont);
                if(this.em.find(Contenido.class,idContenido).getCategorias().contains(cat)){
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
        else {
            return false;
        }

    }

    @Override
    public boolean eliminarCategoria(long idContenido, long idCategoria) {
        Contenido cont = this.em.find(Contenido.class,idContenido);
        if(cont!=null){
            Categoria cat = this.em.find(Categoria.class,idCategoria);
            if(cat!=null){
                List<Categoria> cats = cont.getCategorias();
                if(!cats.contains(cat)){
                    return false;
                }
                cats.remove(cat);
                cont.setCategorias(cats);
                this.em.merge(cont);
                if(!this.em.find(Contenido.class,idContenido).getCategorias().contains(cat)){
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
        else {
            return false;
        }
    }

    @Override
    public List<Categoria> listarCategorias(long idContenido) {
        Contenido c = this.em.find(Contenido.class,idContenido);
        if((c!=null)&&(c.isActivo())){
            return c.getCategorias();
        }
        else {
            return null;
        }
    }

    @Override
    public List<Contenido> buscarContenidos(String nombre) {
        Query q = this.em.createQuery("SELECT c FROM Contenido c WHERE c.nombre LIKE '%"+nombre+"%' AND c.activo=true");
        List<Contenido> res = q.getResultList();
        return res;
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
                    if ((con.isActivo())&&(ca.getNombre() == categoria.getNombre())){
                        listaResultado.add(con);
                    }
                }
            }
        }

        List<Persona> elenco = contenido.getPersona(); // Elenco contenido principal

        for(Persona persona : elenco){
            for (Contenido con : listaContenidos){
                for(Persona p : con.getPersona()){
                    if ((p.getNombre().equals(persona.getNombre()))&&(con.isActivo())){
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
         if((con.getGeneradorContenidoid().getId()==idGC.getId())&&(con.isActivo())){
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
    public boolean agregarPersona(long idC, long idP) {
        Persona p = this.em.find(Persona.class,idP);
        Contenido c = this.em.find(Contenido.class,idC);
        if((p!=null)&&(c!=null)&&(!c.isBloqueado())){
            List<Persona> personas = c.getPersona();
            if(!personas.contains(p)){
                personas.add(p);
                c.setPersona(personas);
                this.em.merge(c);
                if(this.em.find(Contenido.class,idC).getPersona().contains(p)){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public boolean eliminarPersona(long idC, long idP) {
        Persona p = this.em.find(Persona.class,idP);
        Contenido c = this.em.find(Contenido.class,idC);
        if((p!=null)&&(c!=null)&&(!c.isBloqueado())){
            List<Persona> personas = c.getPersona();
            if(personas.contains(p)){
                personas.remove(p);
                c.setPersona(personas);
                this.em.merge(c);
                if(!this.em.find(Contenido.class,idC).getPersona().contains(p)){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public List<Persona> listarPersonas(long idC) {
        Contenido c = this.em.find(Contenido.class,idC);
        if((c!=null)){
            return c.getPersona();
        }
        else {
            return null;
        }
    }

    @Override
    public List<Contenido> listarContenidosTotales() {
        return this.em.createQuery("SELECT c FROM Contenido c").getResultList();
    }

    @Override
    public Integer estaPagoGc(long idCont, long idUser){
        Contenido c = this.em.find(Contenido.class,idCont);
        Usuario u = this.em.find(Usuario.class,idUser);
        if((c!=null)&&(u!=null)&&(c.isActivo())){
            if(c.getGeneradorContenidoid()==null){
                return -1;
            }
//            Query q = this.em.createQuery("SELECT s FROM Suscripcion s WHERE s.usuarioId=:user AND s.generadorContenidoid=:gc");
            Query q = this.em.createNativeQuery("SELECT * FROM `suscripciones` WHERE fecha_vencimiento > NOW() AND gc_id=:gc AND usuario_id=:user");
            q.setParameter("user",u.getId());
            q.setParameter("gc",c.getGeneradorContenidoid().getId());
            if(!q.getResultList().isEmpty()) {
                return 1;
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
        if((u!=null)&&(c!=null)&&(c.isActivo())) {
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
                return -2;
            }
        }
        else {
            return -1;
        }
    }

    @Override
    public Integer estaPago(long idCont, long idUser) {
        if(this.esPayPerView(idCont)){
            return this.estaPagoPV(idCont,idUser);
        }
        else{
            return this.estaPagoGc(idCont,idUser);

        }
    }

    @Override
    public List<Contenido> listarDestacados() {
        return this.em.createQuery("SELECT c FROM Contenido c WHERE c.destacado=true").getResultList();
    }

    @Override
    public List<Contenido> listarEnVivo() {
        List<Contenido> res = (List<Contenido>) this.em.createNativeQuery("SELECT * FROM contenidos c WHERE c.tipo_contenido = 'EVENTO_DEPORTIVO' " +
                "OR c.tipo_contenido = 'EVENTO_ESPECTACULO' AND c.fecha_comienzo <= NOW()").getResultList();
        return res;
    }

    public List<Contenido> listarmarcados(GeneradorContenido i){
        List<Contenido> listaContenidos = this.listarContenidosGenerador(i);
        List<Contenido> listaRET = new ArrayList<>();
        for (Contenido con : listaContenidos){
            if((con.isDestacado())&&(con.isActivo())){
                listaRET.add(con);
            }
        }
        return listaRET;
    }

    public List<Contenido> listarsinmarcar(GeneradorContenido i){
        List<Contenido> listaContenidos = this.listarContenidosGenerador(i);
        List<Contenido> listaRET = new ArrayList<>();
        for (Contenido con : listaContenidos){
            if((!con.isDestacado())&&(con.isActivo())){
                listaRET.add(con);
            }
        }
        return listaRET;
    }
}
