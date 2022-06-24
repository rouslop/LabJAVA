package com.mantel.api.controller;

import com.mantel.api.model.GeneradorContenido;

import com.mantel.api.service.GeneradorContenidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/generadorcontenidos")

public class GeneradorContenidoController {
    private GeneradorContenidoService generadorContenidoService;

    public GeneradorContenidoController(GeneradorContenidoService generadorContenidoService){
        super();
        this.generadorContenidoService = generadorContenidoService;
    }
    @RequestMapping("/hola")
    public String index() {
        return "Congratulations from GeneradorContenidoController.java";
    }

    @PostMapping("/agregarGeneradorContenido")
    public ResponseEntity<String> agregarGeneradorContenido(@RequestBody GeneradorContenido g){
       g.setGanancia(0);
        generadorContenidoService.agregarGeneradorContenido(g);
        return new ResponseEntity<String>("Generador de contenido creado", HttpStatus.CREATED);
    }
}
