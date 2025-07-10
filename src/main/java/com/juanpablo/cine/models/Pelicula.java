package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String clasificacion;

    private int duracion;

    private boolean disponible;

    @ManyToMany
    private Set<Genero> generos;

    public Pelicula() {
    }

    public Pelicula(String nombre, String descripcion, int anio, String clasificacion, int duracion, boolean disponible, Set<Genero> generos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.anio = anio;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.disponible = disponible;
        this.generos = generos;
    }
}
