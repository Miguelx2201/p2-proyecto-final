package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Usuario {
    private static int numUsuarios=0;
    private final String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String contraseña;
    private RolUsuario rol;
    private List<IMetodoPago> metodosPago;
    private List<Compra> compras;

    public Usuario(String nombre, String correo, String telefono, String contraseña, RolUsuario rol, List<IMetodoPago> metodosPago) {
        //Aqui se intenta simular una llave primaria como en una base de datos, se intenta que cumpla con
        // las caracteristicas de llave primaria, es decir, autoincrementable y no nula.
        this.idUsuario = String.valueOf(++numUsuarios);
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.rol = rol;
        this.metodosPago = metodosPago;
        this.compras = new ArrayList<>();
    }
    public void agregarMetodoPago(IMetodoPago metodoPago) throws ProyectoException {
        if (metodoPago == null) throw new ProyectoException("El método de pago no puede ser nulo.");
        boolean yaExiste = buscarMetodoPagoPorNombre(metodoPago.getNombre()).isPresent();
        if (yaExiste) throw new ProyectoException("Ya existe un método de pago con ese nombre.");
        metodosPago.add(metodoPago);
    }
    public void actualizarMetodoPago(IMetodoPago metodoPago) throws ProyectoException {
        IMetodoPago encontrado = buscarMetodoPagoPorNombre(metodoPago.getNombre())
                .orElseThrow(() -> new ProyectoException("Método de pago no encontrado."));
        metodosPago.set(metodosPago.indexOf(encontrado), metodoPago);
    }
    public void eliminarMetodoPago(String nombre) throws ProyectoException {
        IMetodoPago encontrado = buscarMetodoPagoPorNombre(nombre)
                .orElseThrow(() -> new ProyectoException("Método de pago no encontrado."));
        metodosPago.remove(encontrado);
    }
    public List<IMetodoPago> listarMetodosPago() {
        return new ArrayList<>(metodosPago);
    }
    private Optional<IMetodoPago> buscarMetodoPagoPorNombre(String nombre) {
        return metodosPago.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
}
