package com.mantel.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "suscripcionesPerPayView")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SuscripcionPerPayView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float monto;

    @ManyToOne()
    @JoinColumn(name="contenido_id")
    private Contenido contenidoId;

    @ManyToOne()
    @JoinColumn(name="usuario_id")
    private Usuario usuarioId;

}
