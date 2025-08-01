package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneroRepository extends JpaRepository<Genero,Long> {
    Optional<Genero> findByNombre(String nombre);
}
