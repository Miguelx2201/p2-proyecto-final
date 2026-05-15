package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Usuario {
    private static int numUsuarios=0;
    private String idUsuario;
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
    }
}
