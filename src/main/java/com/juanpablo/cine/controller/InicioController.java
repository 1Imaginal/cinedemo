package com.juanpablo.cine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class InicioController {
    @RequestMapping("/")
    public String inicio(){
        ArrayList<String> peliculas;
        return "index.html";
    }
}
