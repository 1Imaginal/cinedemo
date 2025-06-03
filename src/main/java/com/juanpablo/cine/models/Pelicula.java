package com.juanpablo.cine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "peliculas")
@Getter @Setter
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private int anio;

    private char clasificacion;

    private int duracion;

    private boolean proyectada;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public char getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(char clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public boolean isProyectada() {
        return proyectada;
    }

    public void setProyectada(boolean proyectada) {
        this.proyectada = proyectada;
    }
}
