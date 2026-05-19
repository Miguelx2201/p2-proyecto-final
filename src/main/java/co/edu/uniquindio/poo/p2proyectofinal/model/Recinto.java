package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Recinto {
    private static int numRecintos=0;
    private final String idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Zona> zonas;
    private String rutaImagen;

    public Recinto(String nombre, String direccion, String ciudad, List<Zona> zonas, String rutaImagen) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idRecinto = String.valueOf(++numRecintos);
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.zonas = zonas;
        this.rutaImagen = rutaImagen;
    }
}
