package com.juanpablo.cine.services;

import com.juanpablo.cine.models.*;
import com.juanpablo.cine.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    PeliculasRepository peliculasRepository;

    @Autowired
    GeneroRepository generoRepository;

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

        List<Genero> generos = generoRepository.findAll();
        model.addAttribute("generos", generos);

        return "controlCatalogo";
    }

    public String mostrarFunciones(Model model){
        List<Funcion> funciones = funcionRepository.findAllByOrderById();
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

        List<Funcion> funciones = funcionRepository.findAllByPeliculaOrderById(pelicula);

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp horario;

        try {
            java.util.Date parsedDate = dateFormat.parse(horarioString);
            horario = new Timestamp(parsedDate.getTime());
        }catch (Exception e){
            throw new RuntimeException("Error al convertir la fecha");
        }

        List<Sala> salas = new ArrayList<>();
        salas.addAll(salasRepository.findAllById(idSalas));

        for(Sala sala: salas){
            Funcion funcion = new Funcion(sala, pelicula, horario, precio);
            funcionRepository.save(funcion);
        }
    }

    public String mostrarFuncion(Model model, long id) {
        Funcion funcion = funcionRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro la funcion"));
        model.addAttribute("funcion", funcion);

        List<Pelicula> peliculas = peliculasRepository.findAll();
        model.addAttribute("peliculas", peliculas);

        List<Asiento> asientos = asientoRepository.findAllByFuncionOrderByNumeroAsc(funcion);
        model.addAttribute("asientos", asientos);

        return "modificarFuncion";
    }

    @Transactional
    public String modificarFuncion(long id, long idPelicula, List<Long> asientosOcupados, String horarioString, long precio) {
        Funcion funcion = funcionRepository.findById(id).orElseThrow(() ->new RuntimeException("No se encontro la funcion"));
        Pelicula pelicula = peliculasRepository.findById(idPelicula).orElseThrow(()-> new RuntimeException("No se encontro la pelicula"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp horario;

        try {
            java.util.Date parsedDate = dateFormat.parse(horarioString);
            horario = new Timestamp(parsedDate.getTime());
        }catch (Exception e){
            throw new RuntimeException("Error al convertir la fecha");
        }

        List<Asiento> asientosFuncion = asientoRepository.findAllByFuncionOrderByNumeroAsc(funcion);
        for(Asiento asiento: asientosFuncion){
            if (asientosFuncion != null && asientosOcupados.contains(asiento.getId())) {
                asiento.setDisponible(false);
            } else {
                asiento.setDisponible(true);
            }
        }
        asientoRepository.saveAll(asientosFuncion);
        funcion.setPelicula(pelicula);
        funcion.setPrecio(precio);
        funcion.setHorario(horario);

        return "redirect:/control/funciones/}";
    }
}
