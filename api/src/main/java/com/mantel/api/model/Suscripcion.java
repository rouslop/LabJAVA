package com.mantel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "suscripciones")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fechaVencimiento;
    private LocalDate fechaSuscripcion;
    private float monto;

    @Enumerated(EnumType.STRING)
    private TipoSuscripcion tipoSuscripcion;


    @ManyToOne()
    @JoinColumn(name="gc_id")
    private GeneradorContenido generadorContenidoid;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="usuario_id")
    private Usuario usuarioId;

}
