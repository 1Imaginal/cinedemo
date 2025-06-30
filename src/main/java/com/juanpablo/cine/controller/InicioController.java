package com.juanpablo.cine.controller;

import com.juanpablo.cine.models.Usuario;
import com.juanpablo.cine.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
public class InicioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping("/")
    public String inicio(){
        ArrayList<String> peliculas;
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registro")
    public String register(){
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@ModelAttribute Usuario usuario){
        usuarioService.registrarUsuario(usuario);
            return "redirect:/login";
    }
}
