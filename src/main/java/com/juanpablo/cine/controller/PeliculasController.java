package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.PeliculasRepository;
import com.juanpablo.cine.services.PeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class PeliculasController {

    @Autowired
    private PeliculasRepository peliculaRepository;

    @Autowired
    PeliculasService peliculasService;

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

    @PostMapping("/agregarPelicula")
    public String agregarPelicula(@RequestParam String nombre,
                                  @RequestParam String descripcion,
                                  @RequestParam int anio,
                                  @RequestParam String clasificacion,
                                  @RequestParam boolean disponible,
                                  @RequestParam int duracion,
                                  @RequestParam Set<Long> idGeneros){

        peliculasService.agregarPelicula(nombre,descripcion,anio,clasificacion,disponible,duracion,idGeneros);
        return "redirect:/control/catalogo";
    }
}
