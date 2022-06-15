package com.mantel.api.controller;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.CategoriaService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/categoria")
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

}
