package com.mantel.api.controller;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.ContenidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/contenidos")
public class ContenidoController {

    private ContenidoService contenidoService;

    public ContenidoController(ContenidoService contenidoService){
        super();
        this.contenidoService = contenidoService;
    }

    @PostMapping("/agregarContenido")
    public ResponseEntity<String> agregarContenido(@RequestBody Contenido contenido){
        contenidoService.agregarContenido(contenido);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarContenido/{id}")
    public ResponseEntity<String> eliminarContenido(@PathVariable("id") long id){
        contenidoService.eliminarContenido(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);
    }

    @GetMapping("/contenidos/{limit}/{offset}")
    public List<Contenido> obtenerContenidos(@PathVariable ("limit") int limit, @PathVariable ("offset") int offset){
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
/*
    @PostMapping("/agregarCategoria")
    public ResponseEntity<String> agregarCategoria(@RequestBody long contenidoId, long categoriaId){
        contenidoService.agregarCategoria(contenidoId,categoriaId);
        return new ResponseEntity<String>("categoria agregada correctamente", HttpStatus.CREATED);
    }*/





}
