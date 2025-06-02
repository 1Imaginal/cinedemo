package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PeliculasController {

    @Autowired
    private PeliculasRepository peliculaRepository;

    @GetMapping("/cartelera")
    public String mostrarCartelera(Model model){
        List<Pelicula> peliculas = peliculaRepository.findAll();
        model.addAttribute("peliculas", peliculas);
        peliculas.forEach(p -> System.out.println(p.getNombre()));
        return "cartelera";
    }
}
