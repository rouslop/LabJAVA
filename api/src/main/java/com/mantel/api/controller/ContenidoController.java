package com.mantel.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mantel.api.model.*;
import com.mantel.api.service.ComentarioService;
import com.mantel.api.service.ContenidoService;
import com.mantel.api.service.GeneradorContenidoService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/contenidos")
public class ContenidoController {

    private ContenidoService contenidoService;
    private GeneradorContenidoService generadorContenidoService;
    private UsuarioService usuarioService;
    private ComentarioService comentarioService;


    public ContenidoController(ContenidoService contenidoService,
                               GeneradorContenidoService generadorContenidoService,
                               UsuarioService usuarioService,
                               ComentarioService comentarioService){
        super();
        this.contenidoService = contenidoService;
        this.generadorContenidoService = generadorContenidoService;
        this.usuarioService = usuarioService;
        this.comentarioService = comentarioService;
    }

    @PostMapping("/agregarContenido/{idgc}")
    public ResponseEntity<String> agregarContenido(@RequestBody Contenido contenido, @PathVariable("idgc") long gcId){
        contenido.setRanking(0);
        contenido.setBloqueado(false);
        GeneradorContenido gc = generadorContenidoService.obtenerGeneradorContenido(gcId);
        gc.agregarContenido(contenido);

        contenidoService.agregarContenido(contenido);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
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

    @PostMapping("/marcarContenidoDestacado/{id}")
    public ResponseEntity<String> marcarContenidoDestacado(@PathVariable("id") long contenidoId){
        Contenido c = contenidoService.obtenerContenido(contenidoId);
        c.setDestacado(true);
        contenidoService.editarContenido(c);
        return new ResponseEntity<String>("Contenido destacado", HttpStatus.OK);
    }

    @PostMapping("/bloqueoContenido/{id}")
    public ResponseEntity<String> bloqueoContenido(@PathVariable("id") long contenidoId){
        Contenido c = contenidoService.obtenerContenido(contenidoId);
        c.setBloqueado(true);
        contenidoService.editarContenido(c);
        return new ResponseEntity<String>("Contenido bloqueado", HttpStatus.OK);
    }

    @PostMapping("/desbloqueoContenido/{id}")
    public ResponseEntity<String> desbloqueoContenido(@PathVariable("id") long contenidoId){
        Contenido c = contenidoService.obtenerContenido(contenidoId);
        c.setBloqueado(false);
        contenidoService.editarContenido(c);
        return new ResponseEntity<String>("Contenido desbloqueado", HttpStatus.OK);
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


}
