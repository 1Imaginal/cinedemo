package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsientoRepository extends JpaRepository<Asiento, Long> {

    Optional<Asiento> findByNumeroAndIdFuncion(int numero, int idFuncion);

    List<Asiento> findAllByIdFuncionOrderByNumeroAsc(int idFuncion);
}
