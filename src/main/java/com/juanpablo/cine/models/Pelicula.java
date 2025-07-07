package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

    @ManyToMany
    private List<Genero> generos;
}
