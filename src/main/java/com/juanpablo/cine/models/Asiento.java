package com.juanpablo.cine.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Asientos")
@Getter @Setter
public class Asiento {
    @Id
    private int id;
    private int idFuncion;
    private int numero;
    private boolean disponible;
}

