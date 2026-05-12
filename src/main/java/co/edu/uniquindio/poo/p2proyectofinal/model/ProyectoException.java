package co.edu.uniquindio.poo.p2proyectofinal.model;


//Excepcion creada con el fin de llevarle seguimiento a las excepciones lanzadas por nosotros mismos
// y diferenciarlas de las excepciones lanzadas por Java.
public class ProyectoException extends Exception {
    public ProyectoException(String message) {
        super(message);
    }
}
