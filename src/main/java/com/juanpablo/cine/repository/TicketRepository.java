package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
