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

    private long idUsu1;
    private long idUsu2;
    private String nombreUsu1;
    private String nombreUsu2;
    private String texto;
}
