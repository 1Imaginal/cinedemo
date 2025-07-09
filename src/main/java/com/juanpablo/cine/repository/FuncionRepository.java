package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionRepository extends JpaRepository<Funcion, Long> {
    List<Funcion> findAllByPeliculaOrderById(Pelicula pelicula);

    List<Funcion> findAllByOrderById();
}