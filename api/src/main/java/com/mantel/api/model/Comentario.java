package com.mantel.api.model;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "Comentario")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String texto;
    private boolean spoiler;
}
