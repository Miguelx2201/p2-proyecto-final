package co.edu.uniquindio.poo.p2proyectofinal.model;

public interface IEntradaFactory {
    IEntrada createEntrada(Zona zona,Asiento asiento,EstadoEntrada estadoEntrada,String descripcion,double precio);
}
