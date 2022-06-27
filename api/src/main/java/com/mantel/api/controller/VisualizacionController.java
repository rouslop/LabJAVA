package com.mantel.api.controller;
import com.mantel.api.model.Usuario;
import com.mantel.api.model.Visualizacion;
import com.mantel.api.service.UsuarioService;
import com.mantel.api.service.VisualizacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/visualizacion")
public class VisualizacionController {

    private VisualizacionService visualizacionService;

    public  VisualizacionController (VisualizacionService visualizacionService){
        super();
        this.visualizacionService = visualizacionService;
    }

    @PostMapping("/agregarVisualizacion")
    public ResponseEntity<String> agregarVisualizacion(@RequestBody Visualizacion visualizacion){
        visualizacionService.agregarVisualizacion(visualizacion);
        return new ResponseEntity<String>("Visualizacion creada", HttpStatus.CREATED);
    }
}
