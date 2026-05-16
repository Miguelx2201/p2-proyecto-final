package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Asiento {
    private static int numAsientos=0;
    private String idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;
    public Asiento(String fila, int numero, EstadoAsiento estado) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idAsiento = String.valueOf(++numAsientos);
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }
}
