package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraCanceladaState implements IEstadoCompra{
    @Override
    public void pagar(IMetodoPago metodoPago) throws ProyectoException {
        throw new ProyectoException("La compra ha sido cancelada. No es posible realizar el pago.");
    }

    @Override
    public void cancelar() throws ProyectoException {
        throw new ProyectoException("La compra ya ha sido cancelada.");
    }
}
