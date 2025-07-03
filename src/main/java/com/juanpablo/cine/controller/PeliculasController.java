package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id:[0-9]+}")
    public String mostrarDetalles(Model model, @PathVariable long id){
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);
        if(peliculaOptional.isPresent()){
            Pelicula pelicula = peliculaOptional.get();
            model.addAttribute("pelicula", pelicula);
            return "pelicula";
        }else{
            return "error";
        }
    }
}
