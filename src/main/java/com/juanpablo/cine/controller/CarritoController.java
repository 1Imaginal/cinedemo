package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.*;
import com.juanpablo.cine.repository.*;
import com.juanpablo.cine.services.CarritoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    AsientoRepository asientoRepository;

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/carrito")
    public String AgregarAlCarrito(@RequestParam(name = "idAsiento") List<Long> idsAsientos,
                                @AuthenticationPrincipal Usuario usuario){
        Optional<Carrito> carritoOptional = carritoRepository.findByUsuario(usuario);

        if(carritoOptional.isEmpty()) {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            carritoRepository.save(carrito);
            carritoService.agregarAsientosAlCarrito(carrito.getId(), idsAsientos);
        }else{
            Carrito carrito = carritoOptional.get();
            carritoService.agregarAsientosAlCarrito(carrito.getId(), idsAsientos);
        }
        
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, @AuthenticationPrincipal Usuario usuario){
        Optional<Carrito> carritoOptional = carritoRepository.findByUsuario(usuario);
        if(carritoOptional.isPresent()){
            Carrito carrito = carritoOptional.get();
            List<AsientosCarrito> asientosCarrito = carrito.getAsientosSeleccionados();
            List<Asiento> asientos = new ArrayList<>();

            for(AsientosCarrito asientosC : asientosCarrito){
                asientos.add(asientosC.getAsiento());
            }
            model.addAttribute("carrito",carrito);
            model.addAttribute("asientos", asientos);
            return "carrito";
        } else {
            return "error";
        }
    }
}
