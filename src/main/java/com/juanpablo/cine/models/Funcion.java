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

    private int idSala;

    private int idPelicula;

    private Timestamp horario;

    public int getId() {
        return id;
    }

    public int getIdSala() {
        return idSala;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public Timestamp getHorario() {
        return horario;
    }
}
