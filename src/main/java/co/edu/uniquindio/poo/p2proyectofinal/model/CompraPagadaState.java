package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraPagadaState implements IEstadoCompra {
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        throw new ProyectoException("La compra ya ha sido pagada. No es posible realizar un pago doble.");
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        System.out.println("La compra ha sido cancelada.");
        System.out.println("Si desea el reembolso debe comunicarse con el administrador.");
    }

    @Override
    public String getNombreEstado() {
        return "Pagada.";
    }
}
