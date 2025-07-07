package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.repository.FuncionRepository;
import com.juanpablo.cine.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class FuncionesController {

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    PeliculasRepository peliculasRepository;

    @GetMapping("/funciones")
    public String mostrarFuncionesPelicula(Model model, @RequestParam(name = "id", required = false, defaultValue = "0") long id){
        if(id != 0){
            Optional<Pelicula> peliculaOptional = peliculasRepository.findById(id);
            if(peliculaOptional.isPresent()){
                Pelicula pelicula = peliculaOptional.get();
                model.addAttribute("nombre", pelicula.getNombre());
                List<Funcion> funciones = funcionRepository.findAllByPelicula(pelicula);
                if(!funciones.isEmpty()){
                    model.addAttribute("funciones", funciones);
                    funciones.forEach(f -> System.out.println(f.getHorario()));
                    return "funciones";
                }
            }
        } else {
            List<Funcion> funciones = funcionRepository.findAll();
            if(!funciones.isEmpty()){
                model.addAttribute("nombre", "CineDemo");
                model.addAttribute("funciones",funciones);
            }
            return "funciones";
        }

        return "error";
    }
}
