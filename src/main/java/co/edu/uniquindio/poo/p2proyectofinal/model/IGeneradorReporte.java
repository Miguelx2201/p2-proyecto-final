package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.util.List;

public interface IGeneradorReporte {
    void exportar(String titulo, List<String[]> filas, String nombreArchivo);
    String getNombreFormato();  // "CSV" o "PDF"
}
