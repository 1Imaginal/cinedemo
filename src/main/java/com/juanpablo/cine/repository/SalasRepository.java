package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalasRepository extends JpaRepository<Sala, Long> {
}
