package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
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
}
