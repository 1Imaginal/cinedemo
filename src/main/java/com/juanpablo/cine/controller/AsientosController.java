package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AsientosController {

    @Autowired
    AsientoRepository asientoRepository;

    @GetMapping("/asientos")
    public String mostrarAsientos(Model model, @RequestParam(name = "idFuncion", required = true) int idFuncion){
        Optional<Asiento> optionalAsiento =asientoRepository.findByNumeroAndIdFuncion(1,idFuncion);
        if(optionalAsiento.isEmpty()){
            for(int i=1;i<=25;i++){
                Asiento asiento = new Asiento();
                asiento.setNumero(i);
                asiento.setIdFuncion(idFuncion);
                asiento.setDisponible(true);
                asientoRepository.save(asiento);
            }
        }
        List<Asiento> asientos= asientoRepository.findAllByIdFuncionOrderByNumeroAsc(idFuncion);
        model.addAttribute("asientos", asientos);
        return "asientos";
        }
    }
