package co.edu.uniquindio.poo.p2proyectofinal.model;

public class EntrPrefFactory implements IEntradaFactory {
    @Override
    public IEntrada createEntrada( Zona zona, Asiento asiento, EstadoEntrada estadoEntrada, String descripcion, double precio) {
        return new EntradaPreferencial(zona,asiento,estadoEntrada,descripcion,precio);
    }
}
