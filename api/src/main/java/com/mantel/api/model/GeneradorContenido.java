package com.mantel.api.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
@Data
@Entity
@Table(name = "generadoresContenidos")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class GeneradorContenido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private String nombre;
        private float ganancia;
        private String email;
        private  String contrasenia;
        private String metodoPago;


        @OneToMany(cascade = CascadeType.ALL,
                orphanRemoval = true)
        @ToString.Exclude
        List<Contenido> contenido= new ArrayList<Contenido>();

        public void agregarContenido(Contenido contenido) {
                this.contenido.add(contenido);
                contenido.setGeneradorContenidoid(this);
        }


}

