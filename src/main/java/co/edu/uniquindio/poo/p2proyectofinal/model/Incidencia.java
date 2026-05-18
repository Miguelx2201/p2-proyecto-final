package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Incidencia {
    private static int numIncidencias = 0;
    private final String idIncidencia;
    private String tipoIncidencia;
    private String descripcion;
    private LocalDate fecha;
    private IIncidencia entidad;

    public Incidencia(String tipoIncidencia, String descripcion, LocalDate fecha, IIncidencia entidad) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idIncidencia = String.valueOf(++numIncidencias);
        this.tipoIncidencia = tipoIncidencia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidad = entidad;
    }
}
