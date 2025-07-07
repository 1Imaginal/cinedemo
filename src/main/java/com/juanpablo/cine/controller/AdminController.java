package com.juanpablo.cine.controller;

import com.juanpablo.cine.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

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
        adminService.eliminarTicket(id);
        return "redirect:/control/tickets";
    }

    @PostMapping("/control/agregarFuncion")
    public String agregarFuncion(@RequestParam long idPelicula, @RequestParam List<Long> idSalas,
                                 @RequestParam String horario, @RequestParam long precio){
        adminService.agregarFuncion(idPelicula, idSalas, horario, precio);
        return "redirect:/control/funciones";
    }
}
