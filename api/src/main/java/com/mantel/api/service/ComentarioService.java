package com.mantel.api.service;
import com.mantel.api.model.*;


import java.util.List;
public interface ComentarioService {
    public void agregarComentario(Comentario comentario);
    public void eliminarComentario(long comentarioId);
    public Comentario obtenerComentario(long id);
    public Comentario editarComentario(Comentario comentario);
    public List<Comentario> listarComentariosContenido(long idContenido);
    public void agregarComentarioIndividual(ComentarioIndividual ci);

    public List<ComentarioIndividual> listarMensajesEntreUsuarios(Long idUsu1, Long idUsu2);

}
