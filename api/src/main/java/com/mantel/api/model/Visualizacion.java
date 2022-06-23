package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "visualizaciones")
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean terminado;

    private float time;//cambiar a tipo time




}
