package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Zona {
    private static int numZonas=0;
    private final String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private List<Asiento> asientos;
    private Recinto recinto;

    public Zona(String nombre, int capacidad, double precioBase, List<Asiento> asientos, Recinto recinto) {
        this.idZona= String.valueOf(++numZonas);
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.asientos = asientos;
        this.recinto = recinto;
    }
    public double calcularPorcentajeOcupacion() {
        long asientosOcupados = asientos.stream()
                .filter(a -> a.getEstado() != EstadoAsiento.DISPONIBLE)
                .count();
        return (asientosOcupados * 100.0) / capacidad;
    }
}
