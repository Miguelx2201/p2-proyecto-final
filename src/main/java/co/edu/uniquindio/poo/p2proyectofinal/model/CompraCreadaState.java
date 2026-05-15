package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraCreadaState implements IEstadoCompra{
    @Override
    public void pagar(IMetodoPago metodoPago) throws ProyectoException {
        System.out.println("La  compra ha sido pagada satisfactoriamente usando el metodo de pago: "+metodoPago.getNombre());
    }

    @Override
    public void cancelar() throws ProyectoException {
        System.out.println("La compra ha sido cancelada satisfactoriamente.");
    }
}
