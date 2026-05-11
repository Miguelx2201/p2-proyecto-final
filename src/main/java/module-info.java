module co.edu.uniquindio.poo.p2proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.p2proyectofinal to javafx.fxml;
    exports co.edu.uniquindio.poo.p2proyectofinal;
}