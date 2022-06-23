package com.mantel.api.model;

import lombok.Data;
import java.sql.Time;
import javax.persistence.*;
@Data
@Entity
@Table(name = "visualizaciones")
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean terminado;
    private Time time;

    @OneToOne()
    @JoinColumn(name="contenido_id")
    private Contenido contenido;

    @OneToOne()
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

}
