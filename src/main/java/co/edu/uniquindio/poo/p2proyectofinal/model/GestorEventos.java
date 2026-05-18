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
    private List<Recinto> recintos;
    private List<INotificacionObserver> observers;

    private GestorEventos() {
        eventos = new ArrayList<>();
        compras = new ArrayList<>();
        recintos = new ArrayList<>();
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
    } //para la interfaz
    public List<Evento> buscarEventosPorNombre(String texto) {
        return eventos.stream().filter(e -> e.getNombre()
                .toLowerCase()
                .contains(texto.toLowerCase()))
                .collect(Collectors.toList());
    }  //para la interfaz
    public void añadirEvento(Evento evento) throws ProyectoException {
        if(evento == null) throw new ProyectoException("No es posible añadir un evento nulo.");
        Optional<Evento> encontrado = buscarEventoPorNombre(evento.getNombre());
        if(encontrado.isPresent()) throw new ProyectoException("El evento ya ha sido añadido.");
        eventos.add(evento);
        notificarObservers("¡Nuevo evento disponible! : "+evento.getNombre()); // Observers
    }
    public void actualizarEvento(Evento evento) throws ProyectoException {
        Evento encontrado = buscarEventoPorId(evento.getIdEvento())
                .orElseThrow(() -> new ProyectoException("No se puede actualizar: el evento no existe."));
        eventos.set(eventos.indexOf(encontrado), evento);
    }
    public void eliminarEvento(String idEvento) throws ProyectoException {
        Evento encontrado = buscarEventoPorId(idEvento)
                .orElseThrow(() -> new ProyectoException("No se puede eliminar: el evento no existe."));
        eventos.remove(encontrado);
    }
    public List<Evento> listarEventos() {
        return new ArrayList<>(eventos);
    }
    public void publicarEvento(String idEvento) throws ProyectoException {
        Evento encontrado = buscarEventoPorId(idEvento)
                .orElseThrow(() -> new ProyectoException("Evento no encontrado."));
        if (encontrado.getEstadoEvento() == EstadoEvento.CANCELADO)
            throw new ProyectoException("No se puede publicar un evento cancelado.");
        encontrado.setEstadoEvento(EstadoEvento.PUBLICADO);
        notificarObservers("¡El evento " + encontrado.getNombre() + " ha sido publicado!");
    }
    public void pausarEvento(String idEvento) throws ProyectoException {
        Evento encontrado = buscarEventoPorId(idEvento)
                .orElseThrow(() -> new ProyectoException("Evento no encontrado."));
        if (encontrado.getEstadoEvento() != EstadoEvento.PUBLICADO)
            throw new ProyectoException("Solo se puede pausar un evento publicado.");
        encontrado.setEstadoEvento(EstadoEvento.PAUSADO);
        notificarObservers("El evento " + encontrado.getNombre() + " ha sido pausado.");
    }
    public void cancelarEvento(String idEvento) throws ProyectoException {
        Evento encontrado = buscarEventoPorId(idEvento)
                .orElseThrow(() -> new ProyectoException("Evento no encontrado."));
        if (encontrado.getEstadoEvento() == EstadoEvento.FINALIZADO)
            throw new ProyectoException("No se puede cancelar un evento finalizado.");
        encontrado.setEstadoEvento(EstadoEvento.CANCELADO);
        notificarObservers("El evento " + encontrado.getNombre() + " ha sido cancelado.");
    }
    private Optional<Evento> buscarEventoPorId(String idEvento) { //mas rapido
        return eventos.stream()
                .filter(e -> e.getIdEvento().equals(idEvento))
                .findFirst();
    }
    public void agregarRecinto(Recinto recinto) throws ProyectoException {
        if (recinto == null) throw new ProyectoException("No es posible añadir un recinto nulo.");
        boolean yaExiste = recintos.stream()
                .anyMatch(r -> r.getNombre().equalsIgnoreCase(recinto.getNombre()));
        if (yaExiste) throw new ProyectoException("Ya existe un recinto con ese nombre.");
        recintos.add(recinto);
    }
    public void actualizarRecinto(Recinto recinto) throws ProyectoException {
        Recinto encontrado = buscarRecintoPorId(recinto.getIdRecinto())
                .orElseThrow(() -> new ProyectoException("No se puede actualizar: el recinto no existe."));
        recintos.set(recintos.indexOf(encontrado), recinto);
    }
    public void eliminarRecinto(String idRecinto) throws ProyectoException {
        Recinto encontrado = buscarRecintoPorId(idRecinto)
                .orElseThrow(() -> new ProyectoException("No se puede eliminar: el recinto no existe."));
        recintos.remove(encontrado);
    }
    public List<Recinto> listarRecintos() {
        return new ArrayList<>(recintos);
    }
    private Optional<Recinto> buscarRecintoPorId(String idRecinto) {
        return recintos.stream()
                .filter(r -> r.getIdRecinto().equals(idRecinto))
                .findFirst();
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
