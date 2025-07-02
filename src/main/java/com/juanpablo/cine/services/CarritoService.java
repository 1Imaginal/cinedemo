package com.juanpablo.cine.services;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.AsientosCarrito;
import com.juanpablo.cine.models.Carrito;
import com.juanpablo.cine.repository.AsientoRepository;
import com.juanpablo.cine.repository.AsientosCarritoRepository;
import com.juanpablo.cine.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    AsientosCarritoRepository asientosCarritoRepository;

    @Autowired
    AsientoRepository asientoRepository;

    public void agregarAsientosAlCarrito(Long idCarrito, List<Long> idsAsientos) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        for (Long idAsiento : idsAsientos) {
            Asiento asiento = asientoRepository.findById(idAsiento)
                    .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

            AsientosCarrito ac = new AsientosCarrito();
            ac.setCarrito(carrito);
            ac.setAsiento(asiento);
            asiento.setDisponible(false);

            asientosCarritoRepository.save(ac);
        }
    }
}
