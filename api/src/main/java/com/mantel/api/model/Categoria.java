package com.mantel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "categorias")
    List<Contenido> contenidos = new ArrayList<>();

}

