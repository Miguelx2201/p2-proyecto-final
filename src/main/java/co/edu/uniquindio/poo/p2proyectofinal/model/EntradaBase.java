package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public abstract class EntradaBase implements IEntrada{
    private static int numEntradas=0;
    private String idEntrada;
    private Zona zona;
    private Asiento asiento;
    private EstadoEntrada estadoEntrada;
    private String descripcion;
    private double precio;

    public EntradaBase(Zona zona, Asiento asiento, EstadoEntrada estadoEntrada, String descripcion, double precio) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idEntrada = String.valueOf(++numEntradas);
        this.zona = zona;
        this.asiento = asiento;
        this.estadoEntrada = estadoEntrada;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    @Override
    public double getPrecioFinal() {
        return precio;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public EstadoEntrada getEstado() {
        return estadoEntrada;
    }
}
