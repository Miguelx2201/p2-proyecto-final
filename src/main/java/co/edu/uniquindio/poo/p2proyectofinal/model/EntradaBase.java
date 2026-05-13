package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntradaBase implements IEntrada{
    private String idEntrada;
    private Zona zona;
    private Asiento asiento;
    private EstadoEntrada estadoEntrada;
    private String descripcion;
    private double precio;

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
