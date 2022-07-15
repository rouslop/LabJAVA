package com.mantel.api.model;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "comentarios")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String texto;
    private boolean spoiler;

    @ManyToOne()
    @JoinColumn(name="usuario_id")
    private Usuario usuario;


    @ManyToOne()
    @JoinColumn(name="contenido_id")
    private Contenido contenidoid;

}
