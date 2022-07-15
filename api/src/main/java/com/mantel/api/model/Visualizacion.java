package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "visualizaciones")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean terminado;
    private float time;

    @ManyToOne()
    @JoinColumn(name="contenido_id")
    private Contenido contenidoId;


    @ManyToOne()
    @JoinColumn(name="usuario_id")
    private Usuario usuarioId;

}
