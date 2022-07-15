package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rankings")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idUsuario;
    private long idContenido;
    private int puntaje;
}
