package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraCreadaState implements IEstadoCompra{
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        compra.setEstado(new CompraPagadaState()); // transición
        System.out.println("Compra pagada con: " + compra.getMetodoPago().getNombre());
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        compra.setEstado(new CompraCanceladaState()); // transición
        System.out.println("Compra cancelada satisfactoriamente.");
    }

    @Override
    public String getNombreEstado() {
        return "Creada.";
    }
}
