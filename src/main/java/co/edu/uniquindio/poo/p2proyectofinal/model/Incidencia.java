package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Incidencia {
    private String idIncidencia;
    private TipoIncidencia tipoIncidencia;
    private String descripcion;
    private LocalDate fecha;
}
