package com.juanpablo.cine.dto;

import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Ticket;

public class TicketFuncion {
    private Ticket ticket;
    private Funcion funcion;

    public TicketFuncion(Ticket ticket, Funcion funcion) {
        this.ticket = ticket;
        this.funcion = funcion;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Funcion getFuncion() {
        return funcion;
    }
}
