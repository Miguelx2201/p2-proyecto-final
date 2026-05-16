package co.edu.uniquindio.poo.p2proyectofinal.model;

public class EntradaVipFactory implements EntradaFactory{
    @Override
    public IEntrada createEntrada(Zona zona, Asiento asiento, EstadoEntrada estadoEntrada, String descripcion, double precio) {
        return new EntradaVip(zona,asiento,estadoEntrada,descripcion,precio);
    }
}
