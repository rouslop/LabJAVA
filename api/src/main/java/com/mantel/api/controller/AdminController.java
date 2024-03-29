package com.mantel.api.controller;


import com.mantel.api.model.Contenido;
import com.mantel.api.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService servicio;

    public AdminController(AdminService servicio) {
        super();
        this.servicio = servicio;
    }

    @GetMapping("/reporte/contenido/activo")
    public ResponseEntity<List<Contenido>> reportesContenidosActivos(){
        List<Contenido> res = this.servicio.reportesContenidosActivos();
        if(!res.isEmpty()){
            return new ResponseEntity<List<Contenido>>(res, HttpStatus.OK);
        }
        else return null;
    }

    @GetMapping("/reporte/contenido/disponible")
    public ResponseEntity<List<Contenido>> reportesContenidosDisponibles(){
        List<Contenido> res = this.servicio.reportesContenidosDisponibles();
        if(!res.isEmpty()){
            return new ResponseEntity<List<Contenido>>(res, HttpStatus.OK);
        }
        else return null;
    }

    @GetMapping("/reporte/contenido/visto")
    public ResponseEntity<List<Contenido>> reportesContenidosVistos(){
        List<Contenido> res = this.servicio.reportesContenidosVistos();
        if(!res.isEmpty()){
            return new ResponseEntity<List<Contenido>>(res, HttpStatus.OK);
        }
        else return null;
    }

    @GetMapping("/contenidosParaAprobar")
    public ResponseEntity<List<Contenido>> listaContenidosParaAprobar(){
        List<Contenido> res = this.servicio.listarContenidosParaAprobar();
        if(!res.isEmpty()){
            return new ResponseEntity<List<Contenido>>(res, HttpStatus.OK);
        }
        else return null;
    }

    @PutMapping("/contenido/aprobar/{id}")
    public ResponseEntity<String> aprobarContenido(@PathVariable("id") Integer id){
        Long i = Long.parseLong(id.toString());
        if(this.servicio.aprobarContenido(i)){
            return new ResponseEntity<String>("Aprobado con éxito", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contenido/bloquear/{id}")
    public ResponseEntity<String> bloquearContenido(@PathVariable("id") Integer id){
        Long i = Long.parseLong(id.toString());
        if(this.servicio.bloquearContenido(i)){
            return new ResponseEntity<String>("Bloqueado con éxito", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contenido/desbloquear/{id}")
    public ResponseEntity<String> desbloquearContenido(@PathVariable("id") Integer id){
        Long i = Long.parseLong(id.toString());
        if(this.servicio.desbloquearContenido(i)){
            return new ResponseEntity<String>("Desbloqueado con éxito", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
