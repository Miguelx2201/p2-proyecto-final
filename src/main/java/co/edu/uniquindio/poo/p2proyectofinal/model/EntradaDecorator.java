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

    @Override
    public Asiento getAsiento() {
        return entrada.getAsiento(); // Delega al objeto envuelto
    }

    @Override
    public void setAsiento(Asiento asiento) {
        entrada.setAsiento(asiento);
    }
}
