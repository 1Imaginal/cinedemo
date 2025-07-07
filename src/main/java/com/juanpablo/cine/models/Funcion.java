package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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

    private Timestamp horario;

    private long precio;

}
