package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "peliculas")
@Getter @Setter
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private int anio;

    private char clasificacion;

    private int duracion;

    private boolean proyectada;

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
