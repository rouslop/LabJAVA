package com.mantel.api.model;


import lombok.Data;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
@Data
@Entity
@Table(name = "generadoresContenidos")
public class GeneradorContenido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private String nombre;
        private float ganancia;
        private String email;
        private  String contrasenia;
        private String metodoPago;

        @OneToMany(cascade = CascadeType.ALL)

        Set<Contenido> contenido= new HashSet<>();

}

