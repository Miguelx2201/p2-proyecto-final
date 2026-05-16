package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteService {
    private IGeneradorReporte generador;

    public void setGenerador(IGeneradorReporte generador) {
        this.generador = generador;
    }

    public void generarReporteVentas(Evento evento, LocalDate inicio, LocalDate fin) {
            List<String[]> filas = new ArrayList<>();
            filas.add(new String[]{"ID Compra", "Fecha", "Total"}); // Encabezado
            List<Compra> compras =evento.getCompras();
            double granTotal = 0;
            for (Compra c : compras) {
                if (!c.getFechaCreacion().isBefore(inicio) && !c.getFechaCreacion().isAfter(fin)) {
                    filas.add(new String[]{c.getIdCompra(), c.getFechaCreacion().toString(), "$" + c.getTotal()});
                    granTotal += c.getTotal();
                }
            }
            filas.add(new String[]{"", "TOTAL GENERAL:", "$" + granTotal});
            generador.exportar("Ventas del " + inicio + " al " + fin, filas, "reporte_ventas");
    }

    public void generarReporteOcupacion(Evento evento) {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"Zona", "Capacidad", "Vendidos", "% Ocupación"});
        List<Zona> zonas=evento.getRecinto().getZonas();
        for (Zona z : zonas) {
            long ocupados = z.getAsientos().stream().filter(a -> a.getEstado().equals(EstadoAsiento.VENDIDO)).count();
            double porcentaje = (double) ocupados / z.getCapacidad() * 100;
            filas.add(new String[]{z.getNombre(), String.valueOf(z.getCapacidad()), String.valueOf(ocupados), String.format("%.2f%%", porcentaje)});
        }
        generador.exportar("Estado de Ocupación por Zona", filas, "reporte_ocupacion");
    }

    public void generarReporteIngresoServiciosAdicionales(Evento evento) {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"Servicio", "Ingreso Total"});
        double total=0;
        List<Compra> compras=evento.getCompras();
        List<IEntrada>entradas=new ArrayList<>();
        for(Compra compra : compras){
            entradas.addAll(compra.getEntradas());
        }
        for(IEntrada e : entradas) {
            total+=e.getPrecioFinal()-e.getPrecioBase();
            filas.add(new String[]{e.getDescripcion(), "Ingresos adicionales proporcionados: ",String.valueOf(e.getPrecioFinal()-e.getPrecioBase())});
        }
        generador.exportar("Ingresos por Servicios Extras", filas, "reporte_IngresosServicionsAdicionales");
    }

}
