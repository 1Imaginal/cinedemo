package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsientoRepository extends JpaRepository<Asiento, Long> {
}
