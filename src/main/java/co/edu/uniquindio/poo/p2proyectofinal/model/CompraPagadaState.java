package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraPagadaState implements IEstadoCompra {
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        throw new ProyectoException("La compra ya ha sido pagada. No es posible realizar un pago doble.");
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        compra.setEstado(new CompraReembolsadaState()); // transición
        System.out.println("Compra cancelada. Reembolso registrado.");
    }

    @Override
    public String getNombreEstado() {
        return "Pagada.";
    }
}
