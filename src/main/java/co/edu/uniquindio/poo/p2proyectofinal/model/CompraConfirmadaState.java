package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraConfirmadaState implements IEstadoCompra{
    @Override
    public void pagar(IMetodoPago metodoPago) throws ProyectoException {
        throw new ProyectoException("La compra ya ha sido pagada.");
    }

    @Override
    public void cancelar() throws ProyectoException {
        throw new ProyectoException("La compra ya ha sido confirmada. No es posible cancelarla.");
    }
}
