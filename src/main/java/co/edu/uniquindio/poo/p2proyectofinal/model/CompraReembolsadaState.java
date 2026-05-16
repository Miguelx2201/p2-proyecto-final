package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraReembolsadaState implements IEstadoCompra{
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        throw new ProyectoException("La compra ya fue reembolsada. No es posible pagar.");
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        throw new ProyectoException("La compra ya esta cancelada. Su reembolso ya fue realizado.");
    }

    @Override
    public String getNombreEstado() {
        return "Reembolsada.";
    }
}
