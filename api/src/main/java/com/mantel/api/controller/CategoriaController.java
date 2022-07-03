package com.mantel.api.controller;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.CategoriaService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        super();
        this.categoriaService = categoriaService;

    }
    @PostMapping("/agregarCategoria")
    public ResponseEntity<String> agregarCategoria(@RequestBody Categoria cat){
        categoriaService.agregarCategoria(cat);
        return new ResponseEntity<String>("Categoria creada!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listaCategoria(){
        return new ResponseEntity<List<Categoria>>(categoriaService.listaCategoria(), HttpStatus.OK);
    }

    @PutMapping("/editarCategoria")
    public ResponseEntity<String> editarCategoria(@RequestBody Categoria cat){
        categoriaService.editarCategoria(cat);
        return new ResponseEntity<String>("Categoría editada.", HttpStatus.OK);
    }

    @DeleteMapping("/eliminarCategoria/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable("id") long id){
        categoriaService.eliminarCategoria(id);
        return new ResponseEntity<String>("Categoría eliminada.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable("id") long id){
        Categoria c = categoriaService.obtenerCategoria(id);
        return new ResponseEntity<Categoria>(c, HttpStatus.OK);
    }

}
