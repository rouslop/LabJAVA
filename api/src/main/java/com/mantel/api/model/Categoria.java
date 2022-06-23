package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contenido contenido;
}

