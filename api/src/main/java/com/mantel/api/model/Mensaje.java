package com.mantel.api.model;
import lombok.Data;
import javax.persistence.*;


@Data
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombreEmisor;
    private String nombreReceptor;
    private long idReceptor;
    private long idEmisor;
    private String texto;

}

