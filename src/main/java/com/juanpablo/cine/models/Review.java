package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter @Setter

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Pelicula pelicula;

    private int calificacion;

    private String contenido;

    public Review(Usuario usuario, Pelicula pelicula, int calificacion, String contenido) {
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.calificacion = calificacion;
        this.contenido = contenido;
    }

    public Review() {
    }
}
