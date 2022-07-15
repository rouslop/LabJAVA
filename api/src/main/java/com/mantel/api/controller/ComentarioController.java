package com.mantel.api.controller;


import com.mantel.api.model.Contenido;
import com.mantel.api.service.ComentarioService;
import com.mantel.api.service.UsuarioService;
import com.mantel.api.model.Comentario;
import com.mantel.api.model.ComentarioIndividual;
import com.mantel.api.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    private ComentarioService comentarioService;
    private UsuarioService usuarioService;

    public ComentarioController(ComentarioService comentarioService,
                                UsuarioService usuarioService){
        super();
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;

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

    @PostMapping("/agregarComentarioIndividual/{idUsu1}/{idUsu2}")
    public ResponseEntity<ComentarioIndividual> agregarComentarioIndividual(@PathVariable("idUsu1") long idUsu1, @PathVariable("idUsu2") long idUsu2, @RequestBody ComentarioIndividual ci ){

        Usuario usuario1= this.usuarioService.obtenerUsuario(idUsu1);
        Usuario usuario2 = this.usuarioService.obtenerUsuario(idUsu2);

        ci.setIdUsu1(idUsu1);
        ci.setIdUsu2(idUsu2);
        ci.setNombreUsu1(usuario1.getNombre());
        ci.setNombreUsu2(usuario2.getNombre());
        ci.setTexto(ci.getTexto());

        comentarioService.agregarComentarioIndividual(ci);
        return new ResponseEntity<ComentarioIndividual>(ci,HttpStatus.OK);

    }

    @GetMapping("/listarMensajesEntreUsuarios/{idUsu1}/{idUsu2}")
    public ResponseEntity<List<ComentarioIndividual>> listarMensajesEntreUsuarios( @PathVariable("idUsu1") long idUsu1, @PathVariable("idUsu2") long idUsu2 ){

        List<ComentarioIndividual> listaMensajes = new ArrayList<>();
        listaMensajes = comentarioService.listarMensajesEntreUsuarios(idUsu1,idUsu2);

        return new ResponseEntity<List<ComentarioIndividual>>( listaMensajes, HttpStatus.OK);
    }

    // lista los usuarios con los que tuve una conversacion
    @GetMapping("/listaUsuariosConversacion/{idUsu}")
    public ResponseEntity<List<Usuario>> listaUsuariosConversacion(@PathVariable("idUsu") long idUsu){
        List<Usuario> listaUsuarios = new ArrayList<>();
        List<ComentarioIndividual> ci = comentarioService.listarMensajesDeUnUsu(idUsu);
        for (ComentarioIndividual c : ci){
            if (c.getIdUsu1() == idUsu){
                Usuario usu = usuarioService.obtenerUsuario(c.getIdUsu2());
                listaUsuarios.add(usu);
            }else{
                Usuario usu = usuarioService.obtenerUsuario(c.getIdUsu1());
                listaUsuarios.add(usu);
            }
        }

        //remuevo los duplicados
        Set<Usuario> set = new HashSet<>(listaUsuarios);
        listaUsuarios.clear();
        listaUsuarios.addAll(set);

        return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);

    }



}
