module co.edu.uniquindio.poo.p2proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;


    opens co.edu.uniquindio.poo.p2proyectofinal to javafx.fxml;
    exports co.edu.uniquindio.poo.p2proyectofinal;
}