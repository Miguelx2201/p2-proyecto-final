package co.edu.uniquindio.poo.p2proyectofinal.model;
//State pattern.
public interface IEstadoCompra {
    void pagar(IMetodoPago metodoPago) throws ProyectoException;
    void cancelar()throws ProyectoException;
}
