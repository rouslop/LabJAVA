package com.mantel.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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
    Set<Categoria> categoria = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    Set<Persona> persona = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    Set<Comentario> comentario = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name="gc_id")
    @JsonBackReference
    private GeneradorContenido generadorContenidoid;


}
