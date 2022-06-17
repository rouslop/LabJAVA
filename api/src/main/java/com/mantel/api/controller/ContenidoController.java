package com.mantel.api.controller;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.ContenidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Contenido> obtenerContenidos(){
        return contenidoService.obtenerContenidos();
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





}