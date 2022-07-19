package com.mantel.api.controller;

import com.mantel.api.model.*;
import com.mantel.api.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {

    private SuscripcionService suscripcionService;
    private GeneradorContenidoService generadorContenidoService;
    private UsuarioService usuarioService;
    private ContenidoService contenidoService;
    private SuscripcionPPVService suscripcionPPVService;

    public SuscripcionController(SuscripcionService suscripcionService,
                                 GeneradorContenidoService generadorContenidoService,
                                 SuscripcionPPVService suscripcionPPVService,
                                 ContenidoService contenidoService,
                                 UsuarioService usuarioService){
        super();
        this.suscripcionService = suscripcionService;
        this.generadorContenidoService = generadorContenidoService;
        this.suscripcionPPVService = suscripcionPPVService;
        this.contenidoService = contenidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/agregarSuscripcion/{idgc}/{idUsu}")
    public ResponseEntity<String> agregarSuscripcion(@RequestBody Suscripcion suscripcion, @PathVariable("idgc") long gcId, @PathVariable("idUsu") long idUsu){


        GeneradorContenido gc = generadorContenidoService.obtenerGeneradorContenido(gcId);

        LocalDate fechaActual = LocalDate.now();
        suscripcion.setFechaSuscripcion(fechaActual);
        suscripcion.setMonto(gc.getPrecio());

        gc.agregarSuscripcion(suscripcion);

        Usuario usuario = usuarioService.obtenerUsuario(idUsu);
        usuario.agregarSuscripcion(suscripcion);


        suscripcionService.agregarSuscripcion(suscripcion);
        return new ResponseEntity<String>("Nueva suscripción agregada!", HttpStatus.CREATED);

    }

    @PostMapping("/agrearSuscripcionPPV/{idContenido}/{idUsu}")
    public ResponseEntity<String> agrearSuscripcionPPV(@PathVariable("idContenido") long idContenido, @PathVariable("idUsu") long idUsu) {

        SuscripcionPerPayView suscripcionPerPayView = new SuscripcionPerPayView();

        Contenido contenido = contenidoService.obtenerContenido(idContenido);
        Usuario usuario = usuarioService.obtenerUsuario(idUsu);

        suscripcionPerPayView.setMonto(contenido.getPrecio());
        suscripcionPerPayView.setUsuarioId(usuario);
        suscripcionPerPayView.setContenidoId(contenido);
        LocalDate fechaActual = LocalDate.now();
        suscripcionPerPayView.setFechaSuscripcion(fechaActual);

        contenido.agregarSuscripcionPPV(suscripcionPerPayView);
        usuario.agregarSuscripcionPPV(suscripcionPerPayView);


        suscripcionPPVService.agregarSuscripcionPPV(suscripcionPerPayView);
        return new ResponseEntity<String>("Nueva suscripción PAY PER VIEW agregada!", HttpStatus.CREATED);
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

    @GetMapping("/obtenerSuscripcionPPV/{id}")
    public SuscripcionPerPayView obtenerSuscripcionPPV(@PathVariable("id") long id){
        SuscripcionPerPayView suscripcionPPV = suscripcionPPVService.obtenerSuscripcionPPV(id);
        if (suscripcionPPV == null) return null;
        return suscripcionPPV;
    }


    @PutMapping("/editarSuscripcion")
    public ResponseEntity<String> editarSuscripcion(@RequestBody Suscripcion suscripcion){
        suscripcionService.editarSuscripcion(suscripcion);
        return new ResponseEntity<String>("editada y tranquila", HttpStatus.OK);
    }


}
