package com.mantel.api.model;

import com.mantel.api.model.Categoria;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;
import java.util.HashSet;
@Data
@Entity
@Table(name = "contenidos")
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
    private boolean bloqueado;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Set<Categoria> orderItems = new HashSet<>();

    /*
    @OneToMany(mappedBy = "contenidos", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Categoria> cat = new HashSet<>();*/


}
