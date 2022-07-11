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
}
