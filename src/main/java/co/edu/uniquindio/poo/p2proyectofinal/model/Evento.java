package co.edu.uniquindio.poo.p2proyectofinal.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Evento implements IIncidencia{
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

    // Constructor privado — solo lo usa el Builder
    @Builder
    private Evento(String nombre, String categoria, LocalDateTime fechaHora,
                   EstadoEvento estadoEvento, String descripcion, String ciudad,
                   String politicas, Recinto recinto) {
        this.idEvento = String.valueOf(++numEventos);
        this.compras = new ArrayList<>();
        this.nombre = nombre;
        this.categoria = categoria;
        this.fechaHora = fechaHora;
        this.estadoEvento = estadoEvento;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.politicas = politicas;
        this.recinto = recinto;
    }

    @Override
    public String getEntidadAfectada() {
        return "Evento: "+nombre+". ID: "+idEvento;
    }
}
