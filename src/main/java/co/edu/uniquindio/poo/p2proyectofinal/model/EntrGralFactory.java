package co.edu.uniquindio.poo.p2proyectofinal.model;

public class EntrGralFactory implements IEntradaFactory {

    @Override
    public IEntrada createEntrada(Zona zona, Asiento asiento, EstadoEntrada estadoEntrada, String descripcion, double precio) {
        return new EntradaGral(zona,asiento,estadoEntrada,descripcion,precio);
    }
}
