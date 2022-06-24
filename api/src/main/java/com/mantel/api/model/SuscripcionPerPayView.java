package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "suscripcionesPerPayView")
public class SuscripcionPerPayView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float monto;

    @OneToOne()
    @JoinColumn(name="contenido_id")
    private Contenido contenido;

}
