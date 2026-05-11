package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private RolUsuario rol;
    private List<IMetodoPago> metodosPago;
    private List<Compra> compras;
}
