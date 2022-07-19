package com.mantel.api.controller;

import com.mantel.api.model.Contenido;
import com.mantel.api.model.DtReporte;
import com.mantel.api.model.GeneradorContenido;

import com.mantel.api.service.GeneradorContenidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
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

    @GetMapping("/listar")
    public ResponseEntity<List<GeneradorContenido>> obtenerGeneradores(){
        return new ResponseEntity<List<GeneradorContenido>>(generadorContenidoService.obtenerGeneradores(), HttpStatus.OK);
    }

    @PutMapping("/editar")
    public ResponseEntity<String> editarGeneradorContenido(@RequestBody GeneradorContenido gc){
        if(this.generadorContenidoService.editarGC(gc)!=null){
            return new ResponseEntity<String>("Actualizado", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Error, no se pudo actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/generador")
    public ResponseEntity<String> eliminarGenerador(@RequestBody String email){
        if(this.generadorContenidoService.eliminarGenerador(email)){
            return new ResponseEntity<String>("Eliminado con éxito", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No se pude eliminar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarContenidos/{email}")
    public ResponseEntity<List<Contenido>> listarContenidos(@PathVariable("email") String email){
        return new ResponseEntity<List<Contenido>>(this.generadorContenidoService.listarContenidos(email),HttpStatus.OK);
    }

    @GetMapping("/obtenerReportes/{idGC}")
    public ResponseEntity<List<DtReporte>> obtenerResportes(@PathVariable("idGC") Integer idGC){
        List<DtReporte> res = this.generadorContenidoService.obtenerReportes(Long.parseLong(idGC.toString()));
        if(res!=null){
            return new ResponseEntity<List<DtReporte>>(res,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<DtReporte>>(res,HttpStatus.BAD_REQUEST);//el gc es null
        }
    }

}
