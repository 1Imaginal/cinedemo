package com.juanpablo.cine.services;

import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.AsientoRepository;
import com.juanpablo.cine.repository.FuncionRepository;
import com.juanpablo.cine.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminService {

    @Autowired
    PeliculasRepository peliculasRepository;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    AsientoRepository asientoRepository;

    public void registrarPelicula(Pelicula pelicula){
        peliculasRepository.save(pelicula);
    }

    public void eliminarPelicul(Pelicula pelicula){
        peliculasRepository.delete(pelicula);
    }

    public void registrarFuncion(Funcion funcion){
        funcionRepository.save(funcion);
    }

    public void eliminarFuncion(Funcion funcion){
        funcionRepository.delete(funcion);
    }
}
