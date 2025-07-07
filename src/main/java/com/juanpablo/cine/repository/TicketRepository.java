package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Ticket;
import com.juanpablo.cine.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUsuario(Usuario usuario);

    void deleteAllByUsuario(Usuario usuario);

    void deleteAllByFuncion(Funcion funcion);
}
