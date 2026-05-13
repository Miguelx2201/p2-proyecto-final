package co.edu.uniquindio.poo.p2proyectofinal.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    private String idEvento;
    private String nombre;
    private String categoria;
    private LocalDateTime fechaHora;
    private EstadoEvento estadoEvento;
    private String descripcion;
    private String ciudad;
    private String politicas;
    private List<Compra> compras;
    private Recinto recinto;
    
}
