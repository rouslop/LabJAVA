package com.mantel.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String email;
    private boolean activo;
    private String contrasenia;
    private boolean pago;
    private boolean bloqueado;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToMany(cascade = CascadeType.ALL)
    List<Contenido> favoritos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Comentario> comentarios = new ArrayList<Comentario>();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Suscripcion> suscripciones= new ArrayList<Suscripcion>();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<SuscripcionPerPayView> suscripcionesPPV= new ArrayList<SuscripcionPerPayView>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Visualizacion> visualizaciones= new ArrayList<Visualizacion>();

    public void agregarComentario(Comentario comentario){
        this.comentarios.add(comentario);
        comentario.setUsuario(this);
    }

    public void agregarSuscripcion(Suscripcion suscripcion){
        this.suscripciones.add(suscripcion);
        suscripcion.setUsuarioId(this);
    }

    public void agregarSuscripcionPPV(SuscripcionPerPayView suscripcionPPV){
        this.suscripcionesPPV.add(suscripcionPPV);
        suscripcionPPV.setUsuarioId(this);
    }

    public void agregarVisualizacion(Visualizacion visualizacion){
        this.visualizaciones.add(visualizacion);
        visualizacion.setUsuarioId(this);
    }

}
