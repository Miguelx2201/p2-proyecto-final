package co.edu.uniquindio.poo.p2proyectofinal.model;

public abstract class EntradaDecorator implements IEntrada{
    protected IEntrada entrada;

    public EntradaDecorator(IEntrada entrada) {
        this.entrada = entrada;
    }

    @Override
    public double getPrecioBase() {
        return entrada.getPrecioBase();
    }
}
