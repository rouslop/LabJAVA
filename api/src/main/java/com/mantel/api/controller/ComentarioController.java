package com.mantel.api.controller;


import com.mantel.api.model.*;


import com.mantel.api.service.CategoriaService;
import com.mantel.api.service.ComentarioService;
import com.mantel.api.service.ContenidoService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    private ComentarioService comentarioService;
    private UsuarioService usuarioService;
    private ContenidoService contenidoService;

    public ComentarioController(ComentarioService comentarioService,
                                UsuarioService usuarioService,
                                ContenidoService contenidoService){
        super();
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.contenidoService = contenidoService;

    }

    @DeleteMapping("/eliminarComentario/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable("id") long id){
        comentarioService.eliminarComentario(id);
        return new ResponseEntity<String>("Comentario Eliminado", HttpStatus.OK);

    }

    @PostMapping("/marcarComentarioSpoiler/{id}")
    public ResponseEntity<String> marcarComentarioSpoiler(@PathVariable("id") long id){
        Comentario comentario = comentarioService.obtenerComentario(id);
        comentario.setSpoiler(true);
        comentarioService.editarComentario(comentario);
        return new ResponseEntity<String>("Comentario marcado como Spoiler!", HttpStatus.OK);
    }

    @GetMapping("/listarComentariosContenido/{idContenido}")
    public ResponseEntity<List<Comentario>> listarComentariosContenido(@PathVariable("idContenido") long idContenido){

        List<Comentario> resultado = comentarioService.listarComentariosContenido(idContenido);

        return new ResponseEntity<List<Comentario>>(resultado, HttpStatus.OK);
    }

    @PostMapping("/agregarComentarioIndividual/{idCon}/{idUsu}")
    public ResponseEntity<ComentarioIndividual> agregarComentarioIndividual( @PathVariable("idUsu") long idUsu, @PathVariable("idCon") long idCon, @RequestBody String texto ){
        Contenido contenido = this.contenidoService.obtenerContenido(idCon);
        Usuario usuario = this.usuarioService.obtenerUsuario(idUsu);

        ComentarioIndividual ci = new ComentarioIndividual();
        ci.setIdContenido(idCon);
        ci.setIdUsu(idUsu);
        ci.setNombreCon(contenido.getNombre());
        ci.setNombreUsu(usuario.getNombre());
        ci.setTexto(texto);

        comentarioService.agregarComentarioIndividual(ci);
        return new ResponseEntity<ComentarioIndividual>(ci,HttpStatus.OK);

    }

}
