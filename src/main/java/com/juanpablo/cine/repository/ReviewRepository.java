package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.models.Review;
import com.juanpablo.cine.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository  extends JpaRepository<Review, Long> {

    List<Review> findByPelicula(Pelicula pelicula);

    Optional<Review> findByPeliculaAndUsuario(Pelicula pelicula, Usuario usuario);
}
