package com.mantel.api.controller;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Comentario;


import com.mantel.api.service.CategoriaService;
import com.mantel.api.service.ComentarioService;
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

    public ComentarioController(ComentarioService comentarioService){
        super();
        this.comentarioService = comentarioService;

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


}
