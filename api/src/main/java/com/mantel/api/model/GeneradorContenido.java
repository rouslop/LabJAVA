package com.mantel.api.model;


import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "GeneradorContenido")
public class GeneradorContenido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long idgeneradorC;

        private String nombre;
        private float ganancia;
        private String email;
        private  String contrasenia;
        private String metodoPago;



}

