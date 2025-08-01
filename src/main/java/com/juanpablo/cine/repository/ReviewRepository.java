package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Pelicula;
import com.juanpablo.cine.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long> {

    List<Review> findByPelicula(Pelicula pelicula);
}
