package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<AsientosCarrito> asientosSeleccionados = new ArrayList<>();
}
