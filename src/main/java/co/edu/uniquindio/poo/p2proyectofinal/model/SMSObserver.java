package co.edu.uniquindio.poo.p2proyectofinal.model;

public class SMSObserver implements INotificacionObserver {
    @Override
    public void notificar(String mensaje) {
        System.out.println("[SMS] :"+mensaje);
    }
}
