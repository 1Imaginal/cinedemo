package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "generos")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    private String nombre;

    @ManyToMany(mappedBy = "generos")
    private List<Pelicula> peliculas;
}
