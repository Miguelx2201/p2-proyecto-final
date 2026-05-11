package co.edu.uniquindio.poo.p2proyectofinal.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Compra {
    private String idCompra;
    private LocalDate fechaCreacion;
    private double total;
    private IEstadoCompra estado;
    private Usuario usuario;
    private Evento evento;
    private List<IEntrada> entradas;
}
