package co.edu.uniquindio.poo.p2proyectofinal.model;

public class EntrGralFactory implements IEntradaFactory {

    @Override
    public IEntrada createEntrada(String idEntrada, Zona zona, Asiento asiento, EstadoEntrada estadoEntrada, String descripcion, double precio) {
        return new EntradaGral(idEntrada,zona,asiento,estadoEntrada,descripcion,precio);
    }
}
