package co.edu.uniquindio.poo.p2proyectofinal.model;

public class CompraIncidenciaState implements IEstadoCompra{
    @Override
    public void pagar(Compra compra) throws ProyectoException {
        throw new ProyectoException("Ha ocurrido una incidencia con la compra. Comuniquese con el administrador.");
    }

    @Override
    public void cancelar(Compra compra) throws ProyectoException {
        throw new ProyectoException("Ha ocurrido una incidencia con la compra. No es posible cancelarla.");
    }

    @Override
    public String getNombreEstado() {
        return "Incidencia.";
    }
}
