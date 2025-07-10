package com.juanpablo.cine.controller;

import com.juanpablo.cine.dto.TicketFuncion;
import com.juanpablo.cine.models.*;
import com.juanpablo.cine.repository.*;
import com.juanpablo.cine.services.TicketsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.*;

@Controller
@Transactional
public class TicketController {

    @Autowired
    TicketsService ticketsService;

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

    @RequestMapping("/tickets")
    public String mostrarTickets(Model model, @AuthenticationPrincipal Usuario usuario){
        List<Ticket> ticketList = ticketRepository.findAllByUsuario(usuario);

        model.addAttribute("tickets", ticketList);
        return "tickets";
    }

    @PostMapping("/reembolsarTicket")
    public String reembolsarTicket(@RequestParam long id,
                                   @AuthenticationPrincipal Usuario usuario){

        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("Ticket no encontrado"));

        if(ticket.getUsuario().equals(usuario)){
            ticketsService.eliminarTicket(id);
        }else {
            throw new RuntimeException("No se puede realizar esta accion");
        }

        return "redirect:/tickets";
    }

    @GetMapping("/mostrarQR")
    public String mostrarQR(Model model,
                            @RequestParam long id,
                            @AuthenticationPrincipal Usuario usuario){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        if (!ticket.getUsuario().equals(usuario)) {
            return "redirect:/tickets";
        }

        String pelicula = ticket.getFuncion().getPelicula().getNombre();
        String horario = ticket.getFuncion().getHorario().toString();
        String sala = String.valueOf(ticket.getFuncion().getSala().getId());
        String asiento = String.valueOf(ticket.getAsiento().getNumero());

        String contenidoQR = pelicula + "\n" + horario + "\n Sala" + sala + "\n Asiento #" + asiento;

        try {
            String qrBase64 = ticketsService.generateQRCodeBase64(contenidoQR);
            model.addAttribute("qrBase64", qrBase64);
        } catch (Exception e) {
            throw new RuntimeException("Error generando el QR", e);
        }

        return "TicketQR";
    }
}
