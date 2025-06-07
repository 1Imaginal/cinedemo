package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Ticket;
import com.juanpablo.cine.repository.AsientoRepository;
import com.juanpablo.cine.repository.FuncionRepository;
import com.juanpablo.cine.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
                                @RequestParam(name = "idAsiento") long idAsiento){

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

        ticket.setIdFuncion((int) idFuncion);
        ticket.setIdAsiento((int) idAsiento);
        ticket.setIdUsuario(1);
        ticketRepository.save(ticket);

        return "tickets";
    }

    @GetMapping("/tickets")
    public String mostrarTickets(){
        return "tickets";
    }
}
