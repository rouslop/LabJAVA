package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ganancias")
public class Ganancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idGC;
    private int mes;
    private float gananciaMensual;
    private float gananciaMensualPPV;
}
