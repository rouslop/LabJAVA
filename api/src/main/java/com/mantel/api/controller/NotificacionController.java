package com.mantel.api.controller;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.Notificacion;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.ContenidoService;
import com.mantel.api.service.NotificacionService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private NotificacionService notificacionService;
    private UsuarioService usuarioService;
    private ContenidoService contenidoService;

    public NotificacionController(NotificacionService notificacionService, UsuarioService usuarioService,ContenidoService contenidoService){
        super();
        this.usuarioService = usuarioService;
        this.notificacionService = notificacionService;
        this.contenidoService = contenidoService;
    }

    @PostMapping("/agregarNotificacion/{idEmisor}/{idContenido}")
    public ResponseEntity<String> agregarNotificacion(@RequestBody Notificacion notificacion, @PathVariable("idEmisor") long idEmisor, @PathVariable("idContenido") long idContenido){
        Usuario u = usuarioService.obtenerUsuario(idEmisor);
        Contenido c = contenidoService.obtenerContenido(idContenido);
        notificacion.setIdEmisor(idEmisor);
        notificacion.setIdContenido(idContenido);

        String texto = u.getNombre() + " te recomienda este contenido: " + c.getNombre();
        notificacion.setTexto(texto);
        notificacionService.agregarNotificacion(notificacion);
        return new ResponseEntity<String>("Notificacion enviada!", HttpStatus.CREATED);

    }

    @GetMapping("/listarNotificaciones/{idUsu}")
    public ResponseEntity<List<Notificacion>> listarNotificaciones(@PathVariable("idUsu") long idUsu){
        Usuario u = usuarioService.obtenerUsuario(idUsu);
        List<Notificacion> notificaciones = notificacionService.listarNotificaciones(u.getEmail());
        return new ResponseEntity<List<Notificacion>>(notificaciones, HttpStatus.OK);
    }




}
