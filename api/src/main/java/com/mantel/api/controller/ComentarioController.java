package com.mantel.api.controller;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Comentario;


import com.mantel.api.service.CategoriaService;
import com.mantel.api.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    private ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService){
        super();
        this.comentarioService = comentarioService;

    }
    @PostMapping("/agregarComentario")
    public ResponseEntity<String> agregarComentario(@RequestBody Comentario comentario){
        comentarioService.agregarComentario(comentario);
        return new ResponseEntity<String>("Comentario creada!", HttpStatus.CREATED);
    }
    @DeleteMapping("/eliminarComentario/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable("id") long id){
        comentarioService.eliminarComentario(id);
        return new ResponseEntity<String>("Comentario Eliminado", HttpStatus.OK);

    }
}
