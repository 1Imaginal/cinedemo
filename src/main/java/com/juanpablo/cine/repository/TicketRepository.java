package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByIdUsuario(long idUsuario);
}
