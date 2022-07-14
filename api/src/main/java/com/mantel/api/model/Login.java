package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Logins")
public class Login {



    private long idUsuario;

    @Id
    private String email;

    private String contrasenia;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

}
