package com.juanpablo.cine.controller;

import com.juanpablo.cine.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AsientosController {

    @Autowired
    AsientoRepository asientoRepository;

    @GetMapping("/asientos")
    public String mostrarAsientos(Model model, @RequestParam(name = "idFuncion", required = true) int idFuncion){
        return "asientos";
    }
}
