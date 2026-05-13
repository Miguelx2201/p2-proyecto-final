package co.edu.uniquindio.poo.p2proyectofinal.model;

public interface EntradaFactory {
    IEntrada createEntrada( String idEntrada,Zona zona,Asiento asiento,EstadoEntrada estadoEntrada,String descripcion,double precio);
}
