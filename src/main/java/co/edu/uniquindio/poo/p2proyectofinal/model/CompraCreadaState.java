package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraCreadaState implements IEstadoCompra{
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        System.out.println("La  compra ha sido pagada satisfactoriamente usando el metodo de pago: "+metodoPago.getNombre());
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        System.out.println("La compra ha sido cancelada satisfactoriamente.");
    }

    @Override
    public String getNombreEstado() {
        return "Creada.";
    }
}
