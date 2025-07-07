package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsientoRepository extends JpaRepository<Asiento, Long> {

    Optional<Asiento> findByNumeroAndFuncion(int numero, Funcion Funcion);

    List<Asiento> findAllByFuncionOrderByNumeroAsc(Funcion funcion);

    void deleteAllByFuncion(Funcion funcion);
}
