package com.mantel.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
@Data
@Entity
@Table(name = "generadoresContenidos")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class GeneradorContenido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private String nombre;
        private float precio;
        private String email;
        private  String contrasenia;
        private String metodoPago;
        private float ganancia;

        @JsonIgnore
        @ToString.Exclude
        @OneToMany(cascade = CascadeType.ALL,
                orphanRemoval = true)
        List<Contenido> contenido= new ArrayList<Contenido>();

        @JsonIgnore
        @OneToMany(cascade = CascadeType.ALL,
                orphanRemoval = true)
        List<Suscripcion> suscripciones= new ArrayList<Suscripcion>();

        public void agregarContenido(Contenido contenido) {
                this.contenido.add(contenido);
                contenido.setGeneradorContenidoid(this);
        }

        public void agregarSuscripcion(Suscripcion suscripcion){
                this.suscripciones.add(suscripcion);
                suscripcion.setGeneradorContenidoid(this);
        }


}

