package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Carrito;
import com.juanpablo.cine.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {
    Optional<Carrito> findByUsuario(Usuario usuario);

    void deleteAllByUsuario(Usuario usuario);
}
