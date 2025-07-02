package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Tickets")
@Getter @Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_asiento", nullable = false)
    private Asiento asiento;

    @ManyToOne
    @JoinColumn(name = "id_funcion", nullable = false)
    private Funcion funcion;
}
