package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.*;
@Setter
@Getter
public class GestorEventos {
    private static GestorEventos instance;

    private List<Evento> eventos;
    private List<Compra> compras;
    private List<INotificacionObserver> observers;

    private GestorEventos() {
        eventos = new ArrayList<>();
        compras = new ArrayList<>();
        observers = new ArrayList<>();
    }
    public static GestorEventos getInstance() {
        if(instance == null){
            instance = new GestorEventos();
        }
        return instance;
    }
    //Observer
    public void añadirObserver(INotificacionObserver observer){
        observers.add(observer);
    }
    public void notificarObservers(String mensaje) {
        observers.forEach( o -> o.notificar(mensaje));
    }
    //Crud eventos.
    public Optional<Evento> buscarEventoPorNombre(String texto) {
        return eventos.stream().filter(e -> e.getNombre()
                        .toLowerCase()
                        .contains(texto.toLowerCase()))
                .findFirst();
    }
    public List<Evento> buscarEventosPorNombre(String texto) {
        return eventos.stream().filter(e -> e.getNombre()
                .toLowerCase()
                .contains(texto.toLowerCase()))
                .collect(Collectors.toList());
    }
    public void añadirEvento(Evento evento) throws ProyectoException {
        if(evento == null) throw new ProyectoException("No es posible añadir un evento nulo.");
        Optional<Evento> encontrado = buscarEventoPorNombre(evento.getNombre());
        if(encontrado.isPresent()) throw new ProyectoException("El evento ya ha sido añadido.");
        eventos.add(evento);
        notificarObservers("¡Nuevo evento disponible! : "+evento.getNombre()); // Observers
    }
    //Compra. (aqui pueden faltar modificaciones para manejo del estado de la compra, etc.
    public void realizarCompra(Compra compra) throws ProyectoException {
        Usuario usuarioActual = GestorSesion.getInstance().getUsuarioActual();
        if(usuarioActual == null) {
            throw new ProyectoException("Inicia sesion antes de realizar una compra!");
        }
        //Aqui iria la logica de compra.
        //bla bla bla

        notificarObservers("¡Nueva compra realizada para el evento "+compra.getEvento().getNombre()+"!");
    }

}
