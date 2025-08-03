package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Genero;
import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.models.Review;
import com.juanpablo.cine.models.Usuario;
import com.juanpablo.cine.repository.GeneroRepository;
import com.juanpablo.cine.repository.PeliculasRepository;
import com.juanpablo.cine.repository.ReviewRepository;
import com.juanpablo.cine.services.PeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    PeliculasRepository peliculaRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    PeliculasService peliculasService;

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/cartelera")
    public String mostrarCartelera(Model model,
                                   @RequestParam(required = false) List<Long> idGeneros,
                                   @RequestParam(required = false) String nombre) {
        if(idGeneros == null && nombre == null) {
            List<Pelicula> peliculas = peliculaRepository.findAll();
            model.addAttribute("peliculas", peliculas);
        }else if (nombre != null){
            List<Pelicula> peliculas = peliculaRepository.findByNombreContaining(nombre);
            model.addAttribute("peliculas", peliculas);
        } else {
            List<Genero> generosSeleccionados = generoRepository.findAllById(idGeneros);
            List<Pelicula> peliculas = peliculaRepository.findDistinctByGenerosIn(generosSeleccionados);
            model.addAttribute("peliculas", peliculas);
        }

        List<Genero> generos = generoRepository.findAll();
        model.addAttribute("generos", generos);
        return "cartelera";
    }

    @GetMapping("/{id:[0-9]+}")
    public String mostrarDetalles(Model model,
                                  @PathVariable long id,
                                  @AuthenticationPrincipal Usuario usuario){
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);
        if(peliculaOptional.isPresent()){
            Pelicula pelicula = peliculaOptional.get();
            model.addAttribute("pelicula", pelicula);

            List<Review> reviews = reviewRepository.findByPelicula(pelicula);
            model.addAttribute("reviews", reviews);

            Optional<Review> reviewExistente = reviewRepository.findByPeliculaAndUsuario(pelicula, usuario);
            model.addAttribute("reviewExistente", reviewExistente);

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

    @PostMapping("/calificar")
    public String guardarReview(@AuthenticationPrincipal Usuario usuario,
                                @RequestParam Long idPelicula,
                                @RequestParam int calificacion,
                                @RequestParam String contenido){

        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElseThrow(()->new RuntimeException("Pelicula no encontrada"));

        Optional<Review> reviewExistente = reviewRepository.findByPeliculaAndUsuario(pelicula, usuario);

        Review review;
        if(reviewExistente.isPresent()){
            review = reviewExistente.get();
            review.setCalificacion(calificacion);
            review.setContenido(contenido);
        } else {review = new Review(usuario, pelicula, calificacion, contenido);}

        peliculasService.guardarReview(review);
        return "redirect:/" + idPelicula;
    }

}
