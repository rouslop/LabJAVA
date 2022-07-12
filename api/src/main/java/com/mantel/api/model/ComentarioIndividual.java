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

    private long idReceptor;
    private long idEmisor;
    private String nombreReceptor;
    private String nombreEmisor;
    private String texto;
}
