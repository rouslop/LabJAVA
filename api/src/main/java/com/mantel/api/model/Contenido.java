package com.mantel.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
@Data
@Entity
@Table(name = "contenidos")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comentario> comentario = new ArrayList<Comentario>();

    @ManyToOne()
    @JoinColumn(name="gc_id")
    //@ToString.Exclude
    private GeneradorContenido generadorContenidoid;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<SuscripcionPerPayView> suscripcionesPPV= new ArrayList<SuscripcionPerPayView>();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Visualizacion> visualizaciones= new ArrayList<Visualizacion>();



    public void agregarComentario(Comentario comentario){
        this.comentario.add(comentario);
        comentario.setContenidoid(this);
    }

    public void agregarSuscripcionPPV(SuscripcionPerPayView suscripcionPerPayView){
        this.suscripcionesPPV.add(suscripcionPerPayView);
        suscripcionPerPayView.setContenidoId(this);
    }

    public void agregarVisualizacion(Visualizacion visualizacion){
        this.visualizaciones.add(visualizacion);
        visualizacion.setContenidoId(this);
    }


}
