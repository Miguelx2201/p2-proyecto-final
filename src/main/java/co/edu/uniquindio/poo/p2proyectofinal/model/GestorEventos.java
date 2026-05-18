package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private List<Evento> filtrarEventosPublicados() {
        return eventos.stream()
                .filter(e -> e.getEstadoEvento() == EstadoEvento.PUBLICADO)
                .collect(Collectors.toList());
    }

    private List<Evento> filtrarEventosPorFecha(List<Evento> lista, LocalDateTime desde, LocalDateTime hasta) {
        return lista.stream()
                .filter(e -> desde == null || !e.getFechaHora().isBefore(desde))
                .filter(e -> hasta == null || !e.getFechaHora().isAfter(hasta))
                .collect(Collectors.toList());
    }

    private List<Evento> filtrarEventosPorCiudad(List<Evento> lista, String ciudad) {
        return lista.stream()
                .filter(e -> ciudad == null || e.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
    }

    private List<Evento> filtrarEventosPorCategoria(List<Evento> lista, String categoria) {
        return lista.stream()
                .filter(e -> categoria == null || e.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    private List<Evento> filtrarEventosPorPrecio(List<Evento> lista, Double precioMax) {
        return lista.stream()
                .filter(e -> precioMax == null || e.getRecinto().getZonas().stream()
                        .anyMatch(z -> z.getPrecioBase() <= precioMax))
                .collect(Collectors.toList());
    }
    public List<Evento> filtrarEventos(LocalDateTime desde, LocalDateTime hasta,
                                       String ciudad, String categoria, Double precioMax) {
        List<Evento> resultado = filtrarEventosPublicados();
        resultado = filtrarEventosPorFecha(resultado, desde, hasta);
        resultado = filtrarEventosPorCiudad(resultado, ciudad);
        resultado = filtrarEventosPorCategoria(resultado, categoria);
        resultado = filtrarEventosPorPrecio(resultado, precioMax);
        return resultado;
    }

    private List<Compra> filtrarComprasPorUsuarioActual() {
        Usuario usuarioActual = GestorSesion.getInstance().getUsuarioActual();
        return compras.stream()
                .filter(c -> c.getUsuario().equals(usuarioActual))
                .collect(Collectors.toList());
    }
    private List<Compra> filtrarComprasPorFecha(List<Compra> compras, LocalDate desde, LocalDate hasta) {
        return compras.stream()
                .filter(c -> desde == null || !c.getFechaCreacion().isBefore(desde))
                .filter(c -> hasta == null || !c.getFechaCreacion().isAfter(hasta))
                .collect(Collectors.toList());
    }
    private List<Compra> filtrarComprasPorEvento(List<Compra> compras, String nombreEvento) {
        return compras.stream()
                .filter(c -> nombreEvento == null || c.getEvento().getNombre()
                        .toLowerCase().contains(nombreEvento.toLowerCase()))
                .collect(Collectors.toList());
    }
    private List<Compra> filtrarComprasPorEstado(List<Compra> compras, IEstadoCompra estado) {
        return compras.stream()
                .filter(c -> estado == null || c.getEstado().getClass().equals(estado.getClass()))
                .collect(Collectors.toList());
    }
    public List<Compra> filtrarHistorialCompras(LocalDate desde, LocalDate hasta,
                                                String nombreEvento, IEstadoCompra estado) {
        List<Compra> resultado = filtrarComprasPorUsuarioActual();
        resultado = filtrarComprasPorFecha(resultado, desde, hasta);
        resultado = filtrarComprasPorEvento(resultado, nombreEvento);
        resultado = filtrarComprasPorEstado(resultado, estado);
        return resultado;
    }
}
