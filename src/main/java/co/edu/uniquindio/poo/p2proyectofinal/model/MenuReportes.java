package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuReportes {
    private final ReporteService reporteService;
    private final Scanner scanner;

    public MenuReportes(ReporteService reporteService) {
        this.reporteService = reporteService;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu(Evento evento,LocalDate fechaInicio, LocalDate fechaFinal) {
        System.out.println("\n===== SISTEMA DE REPORTES OPERATIVOS =====");

        // 1. Selección de Formato (Adapter)
        System.out.println("Seleccione el formato de exportación:");
        System.out.println("1. PDF (usando PDFBox)");
        System.out.println("2. CSV/Excel (usando Apache POI)");
        int formato = leerEntero();

        if (formato == 1) {
            reporteService.setGenerador(new PDFBoxAdapter());
        } else {
            reporteService.setGenerador(new POIAdapter());
        }

        // 2. Selección del Tipo de Reporte
        System.out.println("\nSeleccione el tipo de reporte:");
        System.out.println("1. Ventas por Periodo");
        System.out.println("2. Ocupación por Zona");
        System.out.println("3. Ingresos por Servicios Adicionales");
        System.out.println("4. Volver");
        int opcion = leerEntero();

        procesarOpcion(opcion,evento);
    }

    private void procesarOpcion(int opcion, Evento evento) {
        switch (opcion) {
            case 1:
                LocalDate inicio = pedirFecha("inicio (AAAA-MM-DD)");
                LocalDate fin = pedirFecha("fin (AAAA-MM-DD)");
                reporteService.generarReporteVentas(evento, inicio, fin);
                break;
            case 2:
                reporteService.generarReporteOcupacion(evento);
                break;
            case 3:
                reporteService.generarReporteIngresoServiciosAdicionales(evento);
                break;
            case 4:
                return;
            default:
                System.out.println("Opcíon no válida.");
        }
    }

    private LocalDate pedirFecha(String tipo) {
        while (true) {
            try {
                System.out.print("Ingrese fecha de " + tipo + ": ");
                return LocalDate.parse(scanner.next());
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use AAAA-MM-DD.");
            }
        }
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número.");
            scanner.next();
        }
        return scanner.nextInt();
    }

}
