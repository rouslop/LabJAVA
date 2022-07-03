package com.mantel.api.service;
import com.mantel.api.model.Categoria;
import com.mantel.api.model.Comentario;
import com.mantel.api.model.Contenido;
import com.mantel.api.model.Usuario;


import java.util.List;
public interface ComentarioService {
    public void agregarComentario(Comentario comentario);
    public void eliminarComentario(long comentarioId);
    public Comentario obtenerComentario(long id);
    public Comentario editarComentario(Comentario comentario);
}
