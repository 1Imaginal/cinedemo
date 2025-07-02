package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Asientos")
@Getter @Setter
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @ManyToOne
    private Funcion funcion;

    private boolean disponible;
}

