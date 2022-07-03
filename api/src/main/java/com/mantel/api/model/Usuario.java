package com.mantel.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    Set<Contenido> favoritos = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    Set<Suscripcion> suscripciones = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    Set<SuscripcionPerPayView> suscripcionesPerPayView = new HashSet<>();

    //@JsonIgnore //para que no se listen cuando obtenemos usuario
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Comentario> comentarios = new ArrayList<Comentario>();

    public void agregarComentario(Comentario comentario){
        this.comentarios.add(comentario);
        comentario.setUsuario(this);
    }


}
