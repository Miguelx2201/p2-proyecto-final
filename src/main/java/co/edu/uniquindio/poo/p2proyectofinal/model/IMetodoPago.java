package co.edu.uniquindio.poo.p2proyectofinal.model;

public interface IMetodoPago {
    boolean pagar();
    String getNombre(); //Aqui que retorne el nombre del metodo de pago usado. Ejemplo (PSE, Tarjeta, Efectivo)
}
