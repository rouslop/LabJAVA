package com.mantel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "suscripcionesPerPayView")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class SuscripcionPerPayView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float monto;


    @ManyToOne()
    @JoinColumn(name="contenido_id")
    private Contenido contenidoId;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="usuario_id")
    private Usuario usuarioId;

}
