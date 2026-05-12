package co.edu.uniquindio.poo.p2proyectofinal.model;

public class ParqueaderoDecorator extends EntradaDecorator{
    public ParqueaderoDecorator(IEntrada entrada) {
        super(entrada);
    }

    @Override
    public double getPrecioFinal() {
        return entrada.getPrecioFinal()+25000;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion()+" + Parqueadero";
    }

    @Override
    public EstadoEntrada getEstado() {
        return entrada.getEstado();
    }
}
