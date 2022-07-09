package com.mantel.api.controller;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.TipoUsuario;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.ContenidoService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    private ContenidoService contenidoService;

    public UsuarioController(UsuarioService usuarioService, ContenidoService contenidoService){
        super();
        this.usuarioService = usuarioService;
        this.contenidoService = contenidoService;
    }

    @PostMapping("/agregarUsuario")
    public ResponseEntity<String> agregarUsuario(@RequestBody Usuario usuario){
        boolean existeUsu = usuarioService.existeUsuarioPorEmail(usuario.getEmail());
        if(existeUsu == false) {
            usuario.setActivo(true);
            usuario.setPago(false);
            usuario.setBloqueado(false);
            usuario.setTipoUsuario(TipoUsuario.CLIENTE);
            usuarioService.agregarUsuario(usuario);
            return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<String>("ya existe un usuario con ese email", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") long id){
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);

    }

    @DeleteMapping("/eliminadoLogico")
    public ResponseEntity<String> eliminarUsuarioLogico(@RequestBody String email){
        if(this.usuarioService.eliminadoLogico(email)){
            return new ResponseEntity<>("La cuenta ha sido desactivada", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No se ha podido desactivar la cuenta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        return new ResponseEntity<List<Usuario>>(usuarioService.obtenerUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable("id") long usuarioId){
        Usuario u = usuarioService.obtenerUsuario(usuarioId);
        if(u == null) return null;

        return u;
    }

    @PutMapping("/editarUsuario")
    public ResponseEntity<String> editarUsuario(@RequestBody Usuario usuario){
       usuarioService.editarUsuario(usuario);
        return new ResponseEntity<String>("editado y tranquilo", HttpStatus.OK);
    }

    @PostMapping("/bloquear")
    public ResponseEntity<String> bloquearUsuario(@RequestBody String email){
        if(this.usuarioService.bloquearUsuario(email)){
             return new ResponseEntity<String>("Usuario bloqueado", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Error, no se pudo bloquear el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/desbloquear")
    public ResponseEntity<String> desbloquearUsuario(@RequestBody String email){
        if(this.usuarioService.desbloquearUsuario(email)){
            return new ResponseEntity<String>("Usuario desbloqueado", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Error, no se pudo desbloquear el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/agregarContenidoFavorito/{id}/{idContenido}")
    public  ResponseEntity<String> agregarContenidoFavorito(@PathVariable("id") long usuarioId, @PathVariable("idContenido") long idContenido){
        Contenido c = contenidoService.obtenerContenido(idContenido);
        usuarioService.agregarContenidoAfavoritos(c, usuarioId);
        return new ResponseEntity<String>("Contenido agregado a favoritos", HttpStatus.OK);
    }

    @DeleteMapping("/eliminarContenidoDeFavoritos/{id}/{idContenido}")
    public  ResponseEntity<String> eliminarContenidoDeFavoritos(@PathVariable("id") long usuarioId, @PathVariable("idContenido") long idContenido){
        Contenido c = contenidoService.obtenerContenido(idContenido);
        usuarioService.eliminarContenidoDeFavoritos(c, usuarioId);
        return new ResponseEntity<String>("Contenido eliminado de favoritos", HttpStatus.OK);
    }

    @GetMapping("/listarRecomendados/{idUsu}")
    public ResponseEntity<List<Contenido>> listarRecomendados(@PathVariable("idUsu") long idUsu){
        return new ResponseEntity<List<Contenido>>(usuarioService.listarRecomendados(idUsu), HttpStatus.OK);
    }

}
