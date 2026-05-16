package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.util.List;

public class PDFBoxAdapter implements IGeneradorReporte{
    private PDFBoxService pdfService = new PDFBoxService();

    @Override
    public void exportar(String titulo, List<String[]> filas, String nombreArchivo) {
        pdfService.writePDF(titulo,filas, nombreArchivo);
    }
    @Override
    public String getNombreFormato() {
        return "PDF";
    }
}
