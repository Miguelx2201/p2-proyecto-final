package co.edu.uniquindio.poo.p2proyectofinal.model;

public interface IEntrada {
    double getPrecioBase();
    double getPrecioFinal();
    String getDescripcion();
    EstadoEntrada getEstado();
    Asiento getAsiento();
    void setAsiento(Asiento asiento);
}
