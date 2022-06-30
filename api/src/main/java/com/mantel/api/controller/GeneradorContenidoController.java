package com.mantel.api.controller;

import com.mantel.api.model.GeneradorContenido;

import com.mantel.api.model.Usuario;
import com.mantel.api.service.GeneradorContenidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/generadorcontenidos")

public class GeneradorContenidoController {
    private GeneradorContenidoService generadorContenidoService;

    public GeneradorContenidoController(GeneradorContenidoService generadorContenidoService){
        super();
        this.generadorContenidoService = generadorContenidoService;
    }

    @PostMapping("/agregarGeneradorContenido")
    public ResponseEntity<String> agregarGeneradorContenido(@RequestBody GeneradorContenido g){
        boolean existeGC = generadorContenidoService.existeGCPorEmail(g.getEmail());
        if(existeGC == false) {
            g.setGanancia(0);
            generadorContenidoService.agregarGeneradorContenido(g);
            return new ResponseEntity<String>("Generador de contenido creado", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<String>("ya existe un generador de contenido con ese email", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{id}")
    public GeneradorContenido obtenerGeneradorContenido(@PathVariable("id") long gcId){
        GeneradorContenido gc = generadorContenidoService.obtenerGeneradorContenido(gcId);
        if(gc == null) return null;

        return gc;
    }

    @GetMapping
    public ResponseEntity<List<GeneradorContenido>> obtenerGeneradores(){
        return new ResponseEntity<List<GeneradorContenido>>(generadorContenidoService.obtenerGeneradores(), HttpStatus.OK);
    }

}
