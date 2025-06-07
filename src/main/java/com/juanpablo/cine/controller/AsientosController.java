package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.Funcion;
import com.juanpablo.cine.repository.AsientoRepository;
import com.juanpablo.cine.repository.FuncionRepository;
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

    @Autowired
    FuncionRepository funcionRepository;

    @GetMapping("/asientos")
    public String mostrarAsientos(Model model, @RequestParam(name = "idFuncion", required = true) long idFuncion){
        Optional<Funcion> funcionOptional = funcionRepository.findById(idFuncion);
        if(funcionOptional.isEmpty()){
            return "error";
        }
        Funcion funcion = funcionOptional.get();
        model.addAttribute("funcion", funcion);

        Optional<Asiento> optionalAsiento =asientoRepository.findByNumeroAndIdFuncion(1, (int) idFuncion);
        if(optionalAsiento.isEmpty()){
            for(int i=1;i<=25;i++){
                Asiento asiento = new Asiento();
                asiento.setNumero(i);
                asiento.setIdFuncion((int) idFuncion);
                asiento.setDisponible(true);
                asientoRepository.save(asiento);
            }
        }
        List<Asiento> asientos= asientoRepository.findAllByIdFuncionOrderByNumeroAsc((int) idFuncion);
        model.addAttribute("asientos", asientos);
        return "asientos";
        }
    }
