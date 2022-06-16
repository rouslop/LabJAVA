package com.mantel.api.controller;

import com.mantel.api.model.Persona;
import com.mantel.api.service.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private PersonaService personaService;

    public PersonaController(PersonaService personaService){
        super();
        this.personaService=personaService;
    }

    @PostMapping("/agregarPersona")
    public ResponseEntity<String> agregarPersona(@RequestBody Persona persona){
        personaService.agregarPersona(persona);
        return new ResponseEntity<String>("creado y tranquilo", HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarPersona/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable("id") long id){
        personaService.eliminarPersona(id);
        return new ResponseEntity<String>("eliminado y tranquilo", HttpStatus.OK);

    }

    @GetMapping
    public List<Persona> obtenerPersonas(){
        return personaService.obtenerPersonas();
    }

    @GetMapping("/{id}")
    public Persona obtenerPersona(@PathVariable("id") long personaId){
        Persona p = personaService.obtenerPersona(personaId);
        if(p == null) return null;
        return p;
    }

    @PutMapping("/editarPersona")
    public ResponseEntity<String> editarPersona(@RequestBody Persona persona){
        personaService.editarPersona(persona);
        return new ResponseEntity<String>("editado y tranquilo", HttpStatus.OK);
    }

}
