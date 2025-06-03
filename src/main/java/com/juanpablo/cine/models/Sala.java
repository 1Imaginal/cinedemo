package com.juanpablo.cine.models;

public class Sala {
    private int id;

    private int capacidad;

    private boolean[][] asientos;

    public Sala(){
        asientos = new boolean[5][5];
    }
}
