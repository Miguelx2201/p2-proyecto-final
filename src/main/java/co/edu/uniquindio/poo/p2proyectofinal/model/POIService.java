package co.edu.uniquindio.poo.p2proyectofinal.model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class POIService {
    public void exportToExcel(String titulo, List<String[]> filas, String nombreArchivo) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(titulo);
        int rowNum = 0;
        for (String[] datosFila : filas) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (String campo : datosFila) {
                row.createCell(colNum++).setCellValue(campo);
            }
        }
        try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo + ".xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Archivo Excel/CSV creado: " + nombreArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
