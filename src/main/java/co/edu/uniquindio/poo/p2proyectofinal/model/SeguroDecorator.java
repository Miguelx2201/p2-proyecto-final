package co.edu.uniquindio.poo.p2proyectofinal.model;


public class SeguroDecorator extends EntradaDecorator{
    public SeguroDecorator(IEntrada entrada) {
        super(entrada);
    }

    @Override
    public double getPrecioFinal() {
        return entrada.getPrecioFinal()+35000;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion()+" + Seguro de cancelación";
    }

    @Override
    public EstadoEntrada getEstado() {
        return entrada.getEstado();
    }
}
