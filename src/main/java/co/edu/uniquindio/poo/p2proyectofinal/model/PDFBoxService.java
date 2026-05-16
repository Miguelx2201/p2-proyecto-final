package co.edu.uniquindio.poo.p2proyectofinal.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.util.List;

public class PDFBoxService {
    public void writePDF(String titulo, List<String[]> filas, String nombreArchivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText(titulo);
                contentStream.endText();

                // Lógica simple para escribir filas (esto es una simplificación)
                int yPosition = 700;
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                for (String[] fila : filas) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(String.join(" | ", fila));
                    contentStream.endText();
                    yPosition -= 20;
                }
            }
            document.save(nombreArchivo + ".pdf");
            System.out.println("Archivo PDF creado: " + nombreArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
