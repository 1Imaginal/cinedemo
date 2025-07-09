package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "funciones")
@Getter @Setter
public class Funcion {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    Sala sala;

    @ManyToOne
    Pelicula pelicula;

    @OneToMany
    List<Asiento> asientos;

    private Timestamp horario;

    private long precio;

}
