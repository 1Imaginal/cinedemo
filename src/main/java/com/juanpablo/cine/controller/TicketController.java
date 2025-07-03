package com.juanpablo.cine.controller;

import com.juanpablo.cine.dto.TicketFuncion;
import com.juanpablo.cine.models.*;
import com.juanpablo.cine.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Transactional
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    AsientosCarritoRepository asientosCarritoRepository;

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    AsientoRepository asientoRepository;

    @PostMapping("/comprar")
    public String comprarTicket(@RequestParam("asientoIds") List<Long> asientoIds,
                                @AuthenticationPrincipal Usuario usuario){

        if (asientoIds == null || asientoIds.isEmpty()) {
            return "error";
        }

        for (Long id : asientoIds) {
            Asiento asiento = asientoRepository.findById(id).orElseThrow(); // o manejar con ifPresent
            Ticket ticket = new Ticket();
            ticket.setFuncion(asiento.getFuncion());
            ticket.setUsuario(usuario);
            ticket.setAsiento(asiento);
            ticketRepository.save(ticket);
            asientosCarritoRepository.deleteByAsiento(asiento);
        }

        carritoRepository.deleteAllByUsuario(usuario);

        return "redirect:/tickets";
    }

    @RequestMapping ("/tickets")
    public String mostrarTickets(Model model, @AuthenticationPrincipal Usuario usuario){
        List<Ticket> ticketList = ticketRepository.findAllByUsuario(usuario);

        model.addAttribute("tickets", ticketList);
        return "tickets";
    }
}
