package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraCanceladaState implements IEstadoCompra{
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        throw new ProyectoException("La compra ha sido cancelada. No es posible realizar el pago.");
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        throw new ProyectoException("La compra ya ha sido cancelada.");
    }

    @Override
    public String getNombreEstado() {
        return "Cancelada.";
    }
}
