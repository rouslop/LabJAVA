package com.mantel.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String emailReceptor;
    private Long idEmisor;
    private Long idContenido;


}
