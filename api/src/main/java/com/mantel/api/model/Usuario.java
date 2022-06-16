package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String email;
    private boolean activo;
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}
