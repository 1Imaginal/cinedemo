package com.juanpablo.cine.repository;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.AsientosCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsientosCarritoRepository extends JpaRepository<AsientosCarrito, Long> {
    void deleteByAsiento(Asiento asiento);
}
