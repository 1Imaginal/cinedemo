package com.juanpablo.cine.services;

import com.juanpablo.cine.models.Genero;
import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.GeneroRepository;
import com.juanpablo.cine.repository.PeliculasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PeliculasService {

    @Autowired
    PeliculasRepository peliculasRepository;

    @Autowired
    GeneroRepository generoRepositoy;

    public String mostrarPelicula(Model model, long id) {
        Pelicula pelicula = peliculasRepository.findById(id).orElseThrow(()->new RuntimeException("No se encotro la pelicula"));
        model.addAttribute("pelicula", pelicula);

        List<Genero> generos = generoRepositoy.findAll();
        model.addAttribute("generos", generos);

        return "modificarPelicula";

    }

    @Transactional
    public void modificarPelicula(long id, String nombre, String descripcion, Integer anio, boolean disponible, String clasificacion, Integer duracion, List<Long> idGeneros){

        Pelicula pelicula = peliculasRepository.findById(id).orElseThrow(()->new RuntimeException("Pelicula no encontrada"));
        if(!nombre.isEmpty())pelicula.setNombre(nombre);
        if(!descripcion.isEmpty())pelicula.setDescripcion(descripcion);
        if(anio != null)pelicula.setAnio(anio);
        if(duracion != null)pelicula.setDuracion(duracion);
        pelicula.setDisponible(disponible);
        pelicula.setClasificacion(clasificacion);

        Set<Genero> generos = new HashSet<>();
        for(long idGenero: idGeneros){
            Genero genero = generoRepositoy.findById(idGenero).orElseThrow(()->new RuntimeException("Genero no valido"));
            generos.add(genero);
        }

        pelicula.setGeneros(generos);

        peliculasRepository.save(pelicula);
    }

    @Transactional
    public void agregarPelicula(String nombre, String descripcion, Integer anio, String clasificacion, boolean disponible,  Integer duracion,Set<Long> idGeneros){
        Set<Genero> generos = new HashSet<>();
        generos.addAll(generoRepositoy.findAllById(idGeneros));

        Pelicula pelicula = new Pelicula(nombre, descripcion, anio, clasificacion, duracion, disponible, generos);
        peliculasRepository.save(pelicula);
    }
}
