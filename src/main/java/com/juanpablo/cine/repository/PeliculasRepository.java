package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Genero;
import com.juanpablo.cine.models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeliculasRepository extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findByNombreContaining(String nombre);

    List<Pelicula> findDistinctByGenerosIn(List<Genero> generos);

}
