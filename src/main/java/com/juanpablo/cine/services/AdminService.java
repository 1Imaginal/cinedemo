package com.juanpablo.cine.services;

import com.juanpablo.cine.models.*;
import com.juanpablo.cine.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    PeliculasRepository peliculasRepository;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AsientoRepository asientoRepository;

    @Autowired
    AsientosCarritoRepository asientosCarritoRepository;

    @Autowired
    SalasRepository salasRepository;

    public void eliminarPelicul(Pelicula pelicula){
        peliculasRepository.delete(pelicula);
    }

    public String mostrarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "controlUsuarios";
    }

    public String mostrarCatalogo(Model model){
        List<Pelicula> peliculas = peliculasRepository.findAll();
        model.addAttribute("peliculas", peliculas);
        return "controlCatalogo";
    }

    public String mostrarFunciones(Model model){
        List<Funcion> funciones = funcionRepository.findAll();
        model.addAttribute("funciones", funciones);

        List<Pelicula> peliculas = peliculasRepository.findAll();
        model.addAttribute("peliculas", peliculas);

        List<Sala> salas = salasRepository.findAll();
        model.addAttribute("salas", salas);
        return "controlFunciones";
    }

    public String mostrarTickets(Model model){
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "controlTickets";
    }

    @Transactional
    public void eliminarUsuario(long id) {
       Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro al usuario"));

        carritoRepository.deleteAllByUsuario(usuario);
        ticketRepository.deleteAllByUsuario(usuario);

        usuarioRepository.delete(usuario);
    }

    @Transactional
    public void eliminarPelicula(long id){
        Pelicula pelicula = peliculasRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro la pelicula"));

        List<Funcion> funciones = funcionRepository.findAllByPelicula(pelicula);

        for(Funcion f: funciones){
            eliminarFuncion(f.getId());
        }

        peliculasRepository.delete(pelicula);
    }

    @Transactional
    public void eliminarFuncion(long id){
        Funcion funcion = funcionRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro la funcion"));

        List<Asiento> asientos = asientoRepository.findAllByFuncionOrderByNumeroAsc(funcion);

        ticketRepository.deleteAllByFuncion(funcion);

        for(Asiento a: asientos){
            asientosCarritoRepository.deleteByAsiento(a);
        }
        asientoRepository.deleteAllByFuncion(funcion);
        funcionRepository.delete(funcion);
    }

    @Transactional
    public void eliminarTicket(long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el ticket"));

        Asiento asiento = ticket.getAsiento();
        asiento.setDisponible(true);

        ticketRepository.delete(ticket);
    }

    @Transactional
    public void agregarFuncion(long idPelicula, List<Long> idSalas, String horarioString, long precio) {
        Pelicula pelicula = peliculasRepository.findById(idPelicula).orElseThrow(() -> new RuntimeException("No se encontro la pelicula"));
        List<Sala> salas = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp horario;

        try {
            java.util.Date parsedDate = dateFormat.parse(horarioString);
            horario = new Timestamp(parsedDate.getTime());
        }catch (Exception e){
            throw new RuntimeException("Error al convertir la fecha");
        }

        for(Long id: idSalas){
            Sala sala = salasRepository.findById(id).orElseThrow(() -> new RuntimeException("Id de sala invalido"));
            salas.add(sala);
        }

        for(Sala sala: salas){
            Funcion funcion = new Funcion();
            funcion.setPelicula(pelicula);
            funcion.setSala(sala);
            funcion.setHorario(horario);
            funcion.setPrecio(precio);

            funcionRepository.save(funcion);
        }

    }
}
