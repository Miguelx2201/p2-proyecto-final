package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraFacade {
    private EntradaFactory entradaFactory;
    private ReporteService reporteService;

    public CompraFacade(EntradaFactory factory) {
        this.entradaFactory = factory;
        this.reporteService = new ReporteService();
    }

    public Compra iniciarCompra(Zona zona, List<Asiento> asientosSeleccionados, List<String> extras) {
        System.out.println("Procesando reserva de " + asientosSeleccionados.size() + " asientos...");

        List<IEntrada> entradasDecoradas = new ArrayList<>();
        double totalAcumulado = 0;
        for (Asiento asiento : asientosSeleccionados) {
            // Factory: Crear entrada base
            IEntrada entrada = entradaFactory.createEntrada(
                    zona,
                    asiento,
                    EstadoEntrada.ACTIVA,
                    "Entrada " + zona.getNombre() + " - Asiento: " + asiento.getNumero(),
                    zona.getPrecioBase()
            );

            entrada = aplicarDecoradores(entrada, extras);

            entradasDecoradas.add(entrada);
            totalAcumulado += entrada.getPrecioFinal();

            asiento.setEstado(EstadoAsiento.RESERVADO);
        }

        Compra nuevaCompra = Compra.builder()
                .fechaCreacion(LocalDate.now())
                .total(totalAcumulado)
                .estado(new CompraCreadaState())
                .entradas(entradasDecoradas)
                .build();

        return nuevaCompra;
    }

    public void confirmarPago(Compra compra, IMetodoPago metodoPago) throws ProyectoException {
        if (metodoPago.pagar(compra)){
            compra.setEstado(new CompraConfirmadaState());
            for (IEntrada e : compra.getEntradas()) {
                Asiento asiento = e.getAsiento();
                asiento.setEstado(EstadoAsiento.VENDIDO);
            }
            notificar(compra);
        }
    }

    private IEntrada aplicarDecoradores(IEntrada entrada, List<String> extras) {
        IEntrada decorada = entrada;
        for (String servicio : extras) {
            if (servicio.equalsIgnoreCase("Seguro")) decorada = new SeguroDecorator(decorada);
            if (servicio.equalsIgnoreCase("Parqueadero")) decorada = new ParqueaderoDecorator(decorada);
        }
        return decorada;
    }

    private void notificar(Compra compra) {
        GestorEventos.getInstance().notificarObservers("Se ha realizado una nueva compra: "+compra.getIdCompra());
    }
}
