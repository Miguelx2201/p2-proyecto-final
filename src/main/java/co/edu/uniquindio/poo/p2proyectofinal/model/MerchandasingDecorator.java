package co.edu.uniquindio.poo.p2proyectofinal.model;

public class MerchandasingDecorator extends EntradaDecorator{
    public MerchandasingDecorator(IEntrada entrada) {
        super(entrada);
    }

    @Override
    public double getPrecioFinal() {
        return entrada.getPrecioFinal()+75000;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion()+" + Merchandasing";
    }

    @Override
    public EstadoEntrada getEstado() {
        return entrada.getEstado();
    }
}
