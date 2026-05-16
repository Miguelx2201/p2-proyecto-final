package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.util.List;

public class POIAdapter implements IGeneradorReporte {

    private POIService poiService = new POIService();

    @Override
    public void exportar(String titulo, List<String[]> filas, String nombreArchivo) {
        System.out.println("Generando CSV: " + titulo);
        poiService.exportToExcel(titulo,filas, nombreArchivo);
    }

    @Override
    public String getNombreFormato() {
        return "CVS";
    }
}
