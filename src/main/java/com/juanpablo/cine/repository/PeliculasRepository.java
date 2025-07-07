package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeliculasRepository extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findByNombreContaining(String nombre);
}
