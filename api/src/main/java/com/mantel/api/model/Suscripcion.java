package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "suscripciones")
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fechaVencimiento;
    private float monto;

    @Enumerated(EnumType.STRING)
    private TipoSuscripcion tipoSuscripcion;

}
