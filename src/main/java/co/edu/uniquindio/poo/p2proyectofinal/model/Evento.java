package co.edu.uniquindio.poo.p2proyectofinal.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Evento {
    private static int numEventos=0;
    private final String idEvento;
    private String nombre;
    private String categoria;
    private LocalDateTime fechaHora;
    private EstadoEvento estadoEvento;
    private String descripcion;
    private String ciudad;
    private String politicas;
    private List<Compra> compras;
    private Recinto recinto;

    public Evento(Recinto recinto, String politicas, String ciudad, String descripcion, EstadoEvento estadoEvento, LocalDateTime fechaHora, String categoria, String nombre) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idEvento = String.valueOf(++numEventos);
        this.compras = new ArrayList<>();
        this.recinto = recinto;
        this.politicas = politicas;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.estadoEvento = estadoEvento;
        this.fechaHora = fechaHora;
        this.categoria = categoria;
        this.nombre = nombre;
    }
}
