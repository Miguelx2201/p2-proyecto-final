package co.edu.uniquindio.poo.p2proyectofinal.model;
//State pattern.
public interface IEstadoCompra {
    void pagar(Compra compra) throws ProyectoException;
    void cancelar(Compra compra)throws ProyectoException;
    String getNombreEstado();
}
