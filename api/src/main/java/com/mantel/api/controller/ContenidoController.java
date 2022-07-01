package com.mantel.api.controller;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Json;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.ContenidoService;
import com.mantel.api.service.GeneradorContenidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/contenidos")
public class ContenidoController {

    private ContenidoService contenidoService;
    private GeneradorContenidoService generadorContenidoService;

    public ContenidoController(ContenidoService contenidoService, GeneradorContenidoService generadorContenidoService){
        super();
        this.contenidoService = contenidoService;
        this.generadorContenidoService = generadorContenidoService;
    }

    @PostMapping("/agregarContenido/{idgc}")
    public ResponseEntity<String> agregarContenido(@RequestBody Contenido contenido, @PathVariable("idgc") long gcId){

        GeneradorContenido gc = generadorContenidoService.obtenerGeneradorContenido(gcId);
        gc.agregarContenido(contenido);
        contenido.setRanking(0);
        contenido.setBloqueado(false);
        contenidoService.agregarContenido(contenido);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarContenido/{id}")
    public ResponseEntity<String> eliminarContenido(@PathVariable("id") long id){
        Contenido c = contenidoService.obtenerContenido(id);
        contenidoService.eliminarContenido(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);
    }

    @GetMapping("/contenidos/{limit}/{offset}")
    public Json obtenerContenidos(@PathVariable ("limit") int limit, @PathVariable ("offset") int offset){
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

    @PostMapping("/marcarContenidoDestacado/{id}")
    public ResponseEntity<String> marcarContenidoDestacado(@PathVariable("id") long contenidoId){
        Contenido c = contenidoService.obtenerContenido(contenidoId);
        c.setDestacado(true);
        contenidoService.editarContenido(c);
        return new ResponseEntity<String>("Contenido destacado", HttpStatus.OK);
    }


}
