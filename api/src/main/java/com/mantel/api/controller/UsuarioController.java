package com.mantel.api.controller;

import com.mantel.api.model.Usuario;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        super();
        this.usuarioService = usuarioService;
    }

    @RequestMapping("/hola")
    public String index() {
        return "Congratulations from UsuarioController.java";
    }

    @PostMapping("/agregarUsuario")
    public ResponseEntity<String> agregarUsuario(@RequestBody Usuario usuario){
        usuarioService.agregarUsuario(usuario);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") long id){
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);

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



}
