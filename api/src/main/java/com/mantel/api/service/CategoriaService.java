package com.mantel.api.service;


import com.mantel.api.model.Categoria;
import com.mantel.api.model.Usuario;

import java.util.List;
public interface CategoriaService {
    public void agregarCategoria(Categoria categoria);
    public List<Categoria> listaCategoria();
}
