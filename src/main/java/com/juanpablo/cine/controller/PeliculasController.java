package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculasController {

    @Autowired
    private PeliculasRepository peliculaRepository;

    @GetMapping
    public List<Pelicula> obtenerPeliculas() {
        return peliculaRepository.findAll();
    }
}
