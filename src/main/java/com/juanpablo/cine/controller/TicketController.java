package com.juanpablo.cine.controller;

import com.juanpablo.cine.dto.TicketFuncion;
import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Ticket;
import com.juanpablo.cine.models.Usuario;
import com.juanpablo.cine.repository.AsientoRepository;
import com.juanpablo.cine.repository.FuncionRepository;
import com.juanpablo.cine.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Controller
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    AsientoRepository asientoRepository;

    @PostMapping("/tickets")
    public String comprarTicket(Model model, @RequestParam(name = "idFuncion") long idFuncion,
                                @RequestParam(name = "idAsiento") long idAsiento,
                                @AuthenticationPrincipal Usuario usuario){

        Optional<Funcion> funcionOptional = funcionRepository.findById(idFuncion);
        if(funcionOptional.isEmpty()){
            return "error";
        }

        Optional<Asiento> asientoOptional = asientoRepository.findById(idAsiento);
        if(asientoOptional.isEmpty()){
            return "error";
        }

        Asiento asiento = asientoOptional.get();

        asiento.setDisponible(false);
        asientoRepository.save(asiento);

        Ticket ticket = new Ticket();

        ticket.setIdFuncion(idFuncion);
        ticket.setIdAsiento(idAsiento);
        ticket.setIdUsuario(usuario.getId());
        ticketRepository.save(ticket);

        return "compra";
    }

    @GetMapping("/tickets")
    public String mostrarTickets(Model model, @AuthenticationPrincipal Usuario usuario){
        List<Ticket> ticketList = ticketRepository.findAllByIdUsuario(usuario.getId());
        List<TicketFuncion> ticketFuncionList = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            Funcion funcion = funcionRepository.findById((long) ticket.getIdFuncion()).orElse(null);
            ticketFuncionList.add(new TicketFuncion(ticket, funcion));
        }

        model.addAttribute("ticketsFuncion", ticketFuncionList);
        return "tickets";
    }
}
