package com.juanpablo.cine.models;

public class Sala {
    private int id;

    private boolean[][] asientos;

    public Sala(){
        asientos = new boolean[5][5];
    }
}
