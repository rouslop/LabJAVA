package com.mantel.api.controller;

import com.mantel.api.model.*;
import com.mantel.api.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/contenidos")
public class ContenidoController {

    private ContenidoService contenidoService;
    private GeneradorContenidoService generadorContenidoService;
    private UsuarioService usuarioService;
    private ComentarioService comentarioService;
    private CategoriaService categoriaService;
    private PersonaService personaService;

    private RankService rankService;


    public ContenidoController(ContenidoService contenidoService,
                               GeneradorContenidoService generadorContenidoService,
                               UsuarioService usuarioService,
                               ComentarioService comentarioService,
                               CategoriaService categoriaService,
                               PersonaService personaService,
                               RankService rankService){
        super();
        this.contenidoService = contenidoService;
        this.generadorContenidoService = generadorContenidoService;
        this.usuarioService = usuarioService;
        this.comentarioService = comentarioService;
        this.categoriaService = categoriaService;
        this.personaService=personaService;
        this.rankService = rankService;
    }

    @PostMapping("/agregarContenido/{idgc}")
    public ResponseEntity<String> agregarContenido(@RequestBody Contenido contenido, @PathVariable("idgc") long gcId){
        contenido.setBloqueado(false);

        List<Categoria> listaAsetearCat = new ArrayList<>(); // sera la lista de cats a setearle en el contenido
        List<Categoria> listaCategorias = categoriaService.listaCategoria();//obtengo las cat de la bd
        // IR EN BUSCA DE LAS CATEGORIAS Y DESP SETEARLAS

        for(Categoria categoria : contenido.getCategorias()){
            for(Categoria cat : listaCategorias){
                if (categoria.getNombre() == cat.getNombre()){
                    contenido.agregarCategoria(cat);
                }
            }
        }

        GeneradorContenido gc = generadorContenidoService.obtenerGeneradorContenido(gcId);
        if (gc != null){
            gc.agregarContenido(contenido);
        }else{
            System.out.println("gc es null");
        }


        contenidoService.agregarContenido(contenido);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
    }

    @PostMapping("/agregarPersona/{idC}/{idP}")
    public ResponseEntity<String> agregarPersona(@PathVariable("idC") Integer idC, @PathVariable("idP") Integer idP){
        if(this.contenidoService.agregarPersona(Long.parseLong(idC.toString()),Long.parseLong(idP.toString()))){
            return new ResponseEntity<String>("Agregada con éxito",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No se pudo agregar",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<List<Contenido>> buscarContenidosPorNombre(@PathVariable("nombre") String nombre){
        return new ResponseEntity<List<Contenido>>(this.contenidoService.buscarContenidos(nombre),HttpStatus.OK);
    }

    @GetMapping("/esFavorito/{idC}/{idU}")
    public ResponseEntity<String> esFavorito(@PathVariable("idC") Integer idC, @PathVariable("idU") Integer idU){
        if(this.contenidoService.esFavorito(Long.parseLong(idC.toString()),Long.parseLong(idU.toString()))){
            return new ResponseEntity<String>("Es favorito",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No es favorito",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/estaValorado/{idC}/{idU}")
    public ResponseEntity<String> estaValorado(@PathVariable("idC") Integer idC, @PathVariable("idU") Integer idU){
        if(this.contenidoService.estaValorado(Long.parseLong(idC.toString()),Long.parseLong(idU.toString()))){
            return new ResponseEntity<String>("Ya fue valorado",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No ha sido valorado",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listarDestacados")
    public ResponseEntity<List<Contenido>> listarDestacados(){
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarDestacados(),HttpStatus.OK);
    }

    @GetMapping("/listarEnVivo")
    public ResponseEntity<List<Contenido>> listarEnVivo(){
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarEnVivo(),HttpStatus.OK);
    }

    @PutMapping("/agregarCategoria/{idCont}/{idCat}")
    public ResponseEntity<String> agregarCategoria(@PathVariable("idCont")Integer idCont, @PathVariable("idCat")Integer idCat){
        Long icont, icat;
        icont = Long.parseLong(idCont.toString());
        icat = Long.parseLong(idCat.toString());
        if(this.contenidoService.agregarCategoria(icont,icat)){
            return new ResponseEntity<String>("Todo ok",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No se pudo agregar la categoria",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarCategoria/{idCont}/{idCat}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable("idCont")Integer idCont, @PathVariable("idCat") Integer idCat){
        Long icont, icat;
        icont = Long.parseLong(idCont.toString());
        icat = Long.parseLong(idCat.toString());
        if(this.contenidoService.eliminarCategoria(icont,icat)){
            return new ResponseEntity<String>("Todo ok",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No se pudo eliminar la categoria",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarCategorias/{idCont}")
    public ResponseEntity<List<Categoria>> listarCategorias(@PathVariable("idCont") Integer idCont){
        Long icont = Long.parseLong(idCont.toString());
        List<Categoria> res = this.contenidoService.listarCategorias(icont);
        if(res!=null){
            return new ResponseEntity<List<Categoria>>(res,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<List<Categoria>>(res,HttpStatus.INTERNAL_SERVER_ERROR); //no se encontro el contenido o no esta activo
        }
    }

    @DeleteMapping("/eliminarContenido/{id}")
    public ResponseEntity<String> eliminarContenido(@PathVariable("id") long id){
        Contenido c = contenidoService.obtenerContenido(id);
        contenidoService.eliminarContenido(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);
    }

    @GetMapping("/contenidos/{limit}/{offset}")
    public Json obtenerContenidos(@PathVariable ("limit") int limit, @PathVariable ("offset") int offset){
        return contenidoService.obtenerContenidos(limit, offset);
    }

    @GetMapping("/{id}")
    public Contenido obtenerContenido(@PathVariable("id") long contenidoId){
        Contenido c = contenidoService.obtenerContenido(contenidoId);
        if (c == null) return null;
        return c;
    }

    @PutMapping("/editarContenido")
    public ResponseEntity<String> editarContenido(@RequestBody Contenido contenido){
        contenidoService.editarContenido(contenido);
        return new ResponseEntity<String>("editado y tranquilo", HttpStatus.OK);
    }

    @PutMapping("/marcarContenidoDestacado/{id}")
    public ResponseEntity<String> marcarContenidoDestacado(@PathVariable("id") Integer id){
        Long i = Long.parseLong(id.toString());
        if(this.contenidoService.marcarContenido(i)){
            return new ResponseEntity<String>("Marcado con éxito", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/desmarcarContenidoDestacado/{id}")
    public ResponseEntity<String> desmarcarContenidoDestacado(@PathVariable("id") long contenidoId){
        Contenido c = contenidoService.obtenerContenido(contenidoId);
        c.setDestacado(false);
        contenidoService.editarContenido(c);
        return new ResponseEntity<String>("Contenido editado", HttpStatus.OK);
    }

    @PostMapping("/comentarContenido/{idContenido}/{idUsu}")
    public ResponseEntity<String> comentarContenido(@PathVariable("idContenido") long idContenido, @PathVariable("idUsu") long idUsu, @RequestBody Comentario comentario){
        comentario.setSpoiler(false);
        Contenido c = contenidoService.obtenerContenido(idContenido);
        Usuario u = usuarioService.obtenerUsuario(idUsu);

        u.agregarComentario(comentario);
        c.agregarComentario(comentario);

        comentarioService.agregarComentario(comentario);

        return new ResponseEntity<String>("Comentario creado", HttpStatus.CREATED);
    }

    @GetMapping("/listarRelacionados/{idContenido}")
    public ResponseEntity<List<Contenido>> listarRelacionados(@PathVariable("idContenido") long idContenido){
        return new ResponseEntity<List<Contenido>>(contenidoService.listarRelacionados(idContenido), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Contenido>> listaContenidos(){
        return new ResponseEntity<List<Contenido>>(contenidoService.listaContenidos(), HttpStatus.OK);
    }

    @GetMapping("/listarTotales")
    public ResponseEntity<List<Contenido>> listaContenidosTotales(){
        return new ResponseEntity<List<Contenido>>(contenidoService.listarContenidosTotales(), HttpStatus.OK);
    }

    @GetMapping("/listarPorCategoria/{id}")
    public ResponseEntity<List<Contenido>> listarPorCategoria(@PathVariable("id") Integer id){
        Long i = Long.parseLong(id.toString());
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarPorCategoria(i),HttpStatus.OK);
    }

    @GetMapping("/listarContenidosGenerador/{idGC}")
    public ResponseEntity<List<Contenido>> listarContenidosGenerador(@PathVariable("idGC") String idGC){
        GeneradorContenido gc = generadorContenidoService.obtenerGCPorEmail(idGC);
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarContenidosGenerador(gc), HttpStatus.OK);
    }

    @GetMapping("/estaPago/{idC}/{idU}")
    public ResponseEntity<String> estaPago(@PathVariable("idC") Integer idC, @PathVariable("idU") Integer idU){
        long ic = Long.parseLong(idC.toString());
        long iu = Long.parseLong(idU.toString());
        if(this.contenidoService.estaPago(ic,iu)==1){
            return new ResponseEntity<String>("Está pago",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No puede acceder a este contenido",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listarPorTipo/{tipo}")
    public ResponseEntity<List<Contenido>> listarPorTipo(@PathVariable("tipo") String tipo){
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarPorTipo(tipo),HttpStatus.OK);
    }

    @GetMapping("/listarPorTipoCat/{idCat}/{t}")
    public ResponseEntity<List<Contenido>> listarPorTipoCat(@PathVariable("t") String t, @PathVariable("idCat") Integer cat){
        Long id = Long.parseLong(cat.toString());
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarPorTipoCategoria(t,id),HttpStatus.OK);
    }

    @GetMapping("/listarmarcados/{idGC}")
    public ResponseEntity<List<Contenido>> listarmarcados(@PathVariable("idGC") String id){
        GeneradorContenido gc = generadorContenidoService.obtenerGCPorEmail(id);
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarmarcados(gc),HttpStatus.OK);
    }
    @GetMapping("/listarsinmarcar/{idGC}")
    public ResponseEntity<List<Contenido>> listarsinmarcar(@PathVariable("idGC") String id){
        GeneradorContenido gc = generadorContenidoService.obtenerGCPorEmail(id);
        return new ResponseEntity<List<Contenido>>(this.contenidoService.listarsinmarcar(gc),HttpStatus.OK);
    }

    @GetMapping("/obtenerPuntaje/{idC}")
    public ResponseEntity<DtPuntaje> obtenerPuntaje(@PathVariable("idC") Integer idC){
        Long iC = Long.parseLong(idC.toString());
        DtPuntaje res = new DtPuntaje();
        res.setIdC(iC);
        double p = this.rankService.obtenerRank(iC);
        res.setPuntaje(Math.round(p * 100) / 100d);
        return new ResponseEntity<DtPuntaje>(res,HttpStatus.OK);
    }

    @GetMapping("/listarPersonas/{idC}")
    public ResponseEntity<List<Persona>> listarPersonas(@PathVariable("idC") Integer idC){
        List<Persona> res = this.contenidoService.listarPersonas(Long.parseLong(idC.toString()));
        if(res!=null) {
            return new ResponseEntity<List<Persona>>(res, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<Persona>>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminarPersona/{idC}/{idP}")
    public ResponseEntity<String> eliminarPersona(@PathVariable("idC") Integer idC, @PathVariable("idP") Integer idP){
        if(this.contenidoService.eliminarPersona(Long.parseLong(idC.toString()),Long.parseLong(idP.toString()))){
            return new ResponseEntity<>("Eliminada correctamente",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No se pudo eliminar",HttpStatus.INTERNAL_SERVER_ERROR);
            //POSIBLES ERRORES:
            //1-No encontró la persona en el contenido
            //2-No encontró la persona en el sistema
            //3-No encontró el contenido en el sistema
            //4-El contenido se encuentra bloqueado
        }
    }
}
