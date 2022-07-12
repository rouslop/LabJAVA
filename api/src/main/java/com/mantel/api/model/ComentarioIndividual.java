package com.mantel.api.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ComentarioIndividual")
public class ComentarioIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idUsu;
    private long idContenido;
    private String nombreUsu;
    private String nombreCon;
    private String texto;
}
