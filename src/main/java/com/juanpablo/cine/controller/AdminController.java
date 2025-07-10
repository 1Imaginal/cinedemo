package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.services.AdminService;
import com.juanpablo.cine.services.PeliculasService;
import com.juanpablo.cine.services.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    PeliculasService peliculasService;

    @Autowired
    TicketsService ticketsService;

    @RequestMapping("/control")
    public String mostarPanelControl(){
        return "control";
    }

    @GetMapping("/control/usuarios")
    public String controlUsuarios(Model model){
        return adminService.mostrarUsuarios(model);
    }

    @PostMapping("/control/usuarios")
    public String eliminarUsuario(@RequestParam long id){
        adminService.eliminarUsuario(id);
        return "redirect:/control/usuarios";
    }

    @GetMapping("/control/catalogo")
    public String controlCatalogo(Model model){
        return adminService.mostrarCatalogo(model);
    }

    @PostMapping("/control/catalogo")
    public String eliminarPelicula(@RequestParam long id){
        adminService.eliminarPelicula(id);
        return "redirect:/control/catalogo";
    }
    @GetMapping("/control/funciones")
    public String controlFuncion(Model model){
       return  adminService.mostrarFunciones(model);
    }

    @PostMapping("/control/funciones")
    public String eliminarFuncion(@RequestParam long id){
        adminService.eliminarFuncion(id);
        return "redirect:/control/funciones";
    }

    @GetMapping("/control/tickets")
    public String controlTickets(Model model){
        return adminService.mostrarTickets(model);
    }

    @PostMapping("/control/tickets")
    public String eliminarTicket(@RequestParam long id){
       ticketsService.eliminarTicket(id);
        return "redirect:/control/tickets";
    }

    @PostMapping("/control/agregarFuncion")
    public String agregarFuncion(@RequestParam long idPelicula,
                                 @RequestParam List<Long> idSalas,
                                 @RequestParam String horario,
                                 @RequestParam long precio){
        adminService.agregarFuncion(idPelicula, idSalas, horario, precio);
        return "redirect:/control/funciones";
    }

    @GetMapping("/control/funciones/{id}")
    public String mostrarFuncion(Model model, @RequestParam long id){
        return adminService.mostrarFuncion(model, id);
    }

    @PostMapping("/control/funciones/{id}")
    public String modificarFuncion(
            @RequestParam long id,
            @RequestParam long pelicula,
            @RequestParam(name = "idAsiento") List<Long> idsAsientos,
            @RequestParam String horario,
            @RequestParam long precio) {
        return adminService.modificarFuncion(id, pelicula, idsAsientos, horario, precio);
    }

    @GetMapping("/control/catalogo/{id}")
    public String mostrarPelicula(Model model, @PathVariable long id){
        return peliculasService.mostrarPelicula(model, id);
    }

    @PostMapping("/control/catalogo/{id}")
    public String modificarPelicula(@PathVariable long id,
                                    @RequestParam(required = false) String nombre,
                                    @RequestParam(required = false) String descripcion,
                                    @RequestParam(required = false) Integer anio,
                                    @RequestParam(required = false) boolean disponible,
                                    @RequestParam(required = false) String clasificacion,
                                    @RequestParam(required = false) Integer duracion,
                                    @RequestParam(required = false) List<Long> idGeneros){
        peliculasService.modificarPelicula(id, nombre, descripcion, anio, disponible, clasificacion, duracion, idGeneros);

        return "redirect:/control/catalogo/" + id;
    }
}
