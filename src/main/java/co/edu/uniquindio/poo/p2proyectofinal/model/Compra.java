package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Compra {
    private static int numCompras=0;
    private final String idCompra;
    private LocalDate fechaCreacion;
    private double total;
    private IEstadoCompra estado;
    private Usuario usuario;
    private Evento evento;
    private IMetodoPago metodoPago;
    private List<IEntrada> entradas;

    public Compra(LocalDate fechaCreacion, Usuario usuario, Evento evento, List<IEntrada> entradas) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idCompra = String.valueOf(++numCompras);
        this.fechaCreacion = fechaCreacion;
        this.estado = new CompraCreadaState();
        this.usuario = usuario;
        this.evento = evento;
        this.entradas = entradas;
        this.metodoPago=metodoPago;
        this.total = calcularTotalCompra();
    }
    private double calcularTotalCompra(){
        return entradas.stream().mapToDouble(IEntrada::getPrecioFinal).sum();
    }
    public void pagar() throws ProyectoException{
        estado.pagar(this);
    }
    public void cancelar() throws ProyectoException{
        estado.cancelar(this);
    }
}
