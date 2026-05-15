package co.edu.uniquindio.poo.p2proyectofinal.model;

public class EmailObserver implements INotificacionObserver {
    @Override
    public void notificar(String mensaje) {
        System.out.println("[EMAIL] :" +mensaje);
    }
}
