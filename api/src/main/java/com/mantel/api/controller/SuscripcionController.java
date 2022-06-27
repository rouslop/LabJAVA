package com.mantel.api.controller;

import com.mantel.api.model.Suscripcion;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.SuscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {

    private SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService){
        super();
        this.suscripcionService = suscripcionService;
    }

    @PostMapping("/agregarSuscripcion")
    public ResponseEntity<String> agregarSuscripcion(@RequestBody Suscripcion suscripcion){
        suscripcionService.agregarSuscripcion(suscripcion);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarSuscripcion/{id}")
    public ResponseEntity<String> eliminarSuscripcion(@PathVariable("id") long id){
        suscripcionService.eliminarSuscripcion(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);
    }

    @GetMapping
    public List<Suscripcion> obtenerSuscripciones(){
        return suscripcionService.obtenerSuscripciones();
    }

    @GetMapping("/{id}")
    public Suscripcion obtenerSuscripcion(@PathVariable("id") long suscripcionId){
        Suscripcion s = suscripcionService.obtenerSuscripcion(suscripcionId);
        if(s == null) return null;

        return s;
    }

    @PutMapping("/editarSuscripcion")
    public ResponseEntity<String> editarSuscripcion(@RequestBody Suscripcion suscripcion){
        suscripcionService.editarSuscripcion(suscripcion);
        return new ResponseEntity<String>("editada y tranquila", HttpStatus.OK);
    }


}
