package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "asientos_carrito")
@Getter @Setter
public class AsientosCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne
    private Asiento asiento;
}
