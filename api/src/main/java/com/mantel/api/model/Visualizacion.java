package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "Visualizacion")
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean estado;
    private float time; //cambiar a tipo time

}
