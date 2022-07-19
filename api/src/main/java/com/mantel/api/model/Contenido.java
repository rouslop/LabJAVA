package com.mantel.api.model;


import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "contenidos")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TipoContenido tipoContenido;
    private String nombre;
    private String descripcion;
    private String fotoPortada;
    private String video;
    private float precio;
    private Time duracion;
    private Time comienzo;
    private String fecha_comienzo;
    @Column(columnDefinition = "boolean default false")
    private boolean destacado;
    @Column(columnDefinition = "boolean default false")
    private boolean bloqueado;
    @Column(columnDefinition = "boolean default false")
    private boolean activo;

    @ManyToMany
    @JoinTable(name = "contenido_categoria",
    joinColumns = { @JoinColumn(name = "contenido_id")},
            inverseJoinColumns = { @JoinColumn(name = "categoria_id")}
    )
    //@ToString.Exclude
    List<Categoria> categorias = new ArrayList<>();


    @ManyToOne()
    @JoinColumn(name="gc_id")
    private GeneradorContenido generadorContenidoid;

    @ManyToMany
    @JoinTable(name = "contenido_personas",
            joinColumns = { @JoinColumn(name = "contenido_id")},
            inverseJoinColumns = { @JoinColumn(name = "persona_id")})
    List<Persona> persona = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comentario> comentario = new ArrayList<Comentario>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<SuscripcionPerPayView> suscripcionesPPV= new ArrayList<SuscripcionPerPayView>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Visualizacion> visualizaciones= new ArrayList<Visualizacion>();


    //  FUNCIONES AUXILIARES

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

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }


}
