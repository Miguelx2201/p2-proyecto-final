package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Recinto {
    private String idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Zona> zonas;
}
