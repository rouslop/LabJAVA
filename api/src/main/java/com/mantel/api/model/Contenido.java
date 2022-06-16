package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Data
@Entity
@Table(name = "Contenidos")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TipoContenido tipoContenido;

    private String descripcion;
    private float ranking;
    private String fotoPortada;
    private float precio;
    private Time duracion;
    private boolean destacado;
}
