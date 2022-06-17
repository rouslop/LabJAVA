package com.mantel.api.service;
import com.mantel.api.model.Categoria;
import com.mantel.api.model.Comentario;


import java.util.List;
public interface ComentarioService {
    public void agregarComentario(Comentario comentario);
    public void eliminarComentario(long comentarioId);
}
