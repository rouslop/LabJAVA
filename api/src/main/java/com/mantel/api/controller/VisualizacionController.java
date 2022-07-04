package com.mantel.api.controller;
import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;
import com.mantel.api.model.Visualizacion;
import com.mantel.api.service.ContenidoService;
import com.mantel.api.service.UsuarioService;
import com.mantel.api.service.VisualizacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/visualizacion")
public class VisualizacionController {

    private VisualizacionService visualizacionService;
    private UsuarioService usuarioService;
    private ContenidoService contenidoService;

    public  VisualizacionController (VisualizacionService visualizacionService,
                                     ContenidoService contenidoService,
                                     UsuarioService usuarioService){
        super();
        this.visualizacionService = visualizacionService;
        this.contenidoService = contenidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/agregarVisualizacion/{idContenido}/{idUsu}")
    public ResponseEntity<String> agregarVisualizacion(@RequestBody Visualizacion visualizacion,
             @PathVariable("idContenido") long idContenido,@PathVariable("idUsu") long idUsu){
        visualizacion.setTerminado(false);

        Contenido contenido = contenidoService.obtenerContenido(idContenido);
        contenido.agregarVisualizacion(visualizacion);

        Usuario usuario = usuarioService.obtenerUsuario(idUsu);
        usuario.agregarVisualizacion(visualizacion);

        visualizacionService.agregarVisualizacion(visualizacion);
        return new ResponseEntity<String>("Visualizacion registrada", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Visualizacion>> obtenerVisualizaciones(){
        return new ResponseEntity<List<Visualizacion>>(visualizacionService.obtenerVisualizaciones(), HttpStatus.OK);
    }

}
