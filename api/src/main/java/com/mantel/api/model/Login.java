package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Logins")
public class Login {


    @Id
    private String email;

    private String contrasenia;

    private Long idUsuario;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

}
