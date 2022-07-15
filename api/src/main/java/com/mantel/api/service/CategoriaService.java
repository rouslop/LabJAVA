package com.mantel.api.service;


import com.mantel.api.model.Categoria;

import java.util.List;
public interface CategoriaService {
    public void agregarCategoria(Categoria categoria);
    public List<Categoria> listaCategoria();
    public Categoria editarCategoria(Categoria categoria);
    public void eliminarCategoria(long id);
    public Categoria obtenerCategoria(long id);
}
