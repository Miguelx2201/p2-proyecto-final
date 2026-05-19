package co.edu.uniquindio.poo.p2proyectofinal;

import co.edu.uniquindio.poo.p2proyectofinal.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatosPrueba {

    public static void cargar() throws ProyectoException {
        GestorSesion sesion = GestorSesion.getInstance();
        GestorEventos gestor = GestorEventos.getInstance();

        // Observers
        gestor.añadirObserver(new EmailObserver());
        gestor.añadirObserver(new SMSObserver());

        // ─── USUARIOS ───────────────────────────────────────────
        Usuario admin = new Usuario("Admin Sistema", "admin@eventos.com",
                "3001234567", "admin123", RolUsuario.ADMIN, new ArrayList<>());

        Usuario cliente1 = new Usuario("Juan Pérez", "juan@gmail.com",
                "3109876543", "juan123", RolUsuario.CLIENTE, new ArrayList<>());
        cliente1.agregarMetodoPago(new TarjetaPago("4111111111111111", "123"));
        cliente1.agregarMetodoPago(new PSEPago("Bancolombia", "juan@gmail.com"));

        Usuario cliente2 = new Usuario("María López", "maria@gmail.com",
                "3207654321", "maria123", RolUsuario.CLIENTE, new ArrayList<>());
        cliente2.agregarMetodoPago(new EfectivoPago());

        sesion.registrarUsuario(admin);
        sesion.registrarUsuario(cliente1);
        sesion.registrarUsuario(cliente2);

        // ─── RECINTOS Y ZONAS ────────────────────────────────────
        List<Asiento> asientosVip = generarAsientos("A", 1, 12);
        List<Asiento> asientosPref = generarAsientos("B", 1, 24);
        List<Asiento> asientosGral = generarAsientos("C", 1, 48);

        Zona zonaVip = new Zona("VIP", 12, 350000.0, asientosVip, null);
        Zona zonaPref = new Zona("Preferencial", 24, 180000.0, asientosPref, null);
        Zona zonaGral = new Zona("General", 48, 90000.0, asientosGral, null);

        Recinto estadio = new Recinto("Estadio Centenario", "Cra 1 #2-3",
                "Armenia", new ArrayList<>(List.of(zonaVip, zonaPref, zonaGral)), "/images/coliseo_del_cafe.JPG");

        // Asignar recinto a cada zona
        zonaVip.setRecinto(estadio);
        zonaPref.setRecinto(estadio);
        zonaGral.setRecinto(estadio);

        gestor.agregarRecinto(estadio);

        // ─── EVENTOS ─────────────────────────────────────────────
        Evento concierto = Evento.builder()
                .nombre("Concierto Juanes")
                .categoria("Concierto")
                .ciudad("Armenia")
                .descripcion("El mejor concierto del año")
                .estadoEvento(EstadoEvento.PUBLICADO)
                .fechaHora(LocalDateTime.of(2026, 8, 15, 20, 0))
                .politicas("No hay reembolsos después de 48h")
                .recinto(estadio)
                .build();

        Evento teatro = Evento.builder()
                .nombre("Romeo y Julieta")
                .categoria("Teatro")
                .ciudad("Bogotá")
                .descripcion("Obra de teatro clásica")
                .estadoEvento(EstadoEvento.PUBLICADO)
                .fechaHora(LocalDateTime.of(2026, 9, 10, 19, 0))
                .politicas("Reembolso hasta 24h antes")
                .recinto(estadio)
                .build();

        Evento conferencia = Evento.builder()
                .nombre("TechConf 2026")
                .categoria("Conferencia")
                .ciudad("Medellín")
                .descripcion("Conferencia de tecnología e innovación")
                .estadoEvento(EstadoEvento.BORRADOR)
                .fechaHora(LocalDateTime.of(2026, 10, 5, 9, 0))
                .politicas("Sin reembolsos")
                .recinto(estadio)
                .build();

        gestor.añadirEvento(concierto);
        gestor.añadirEvento(teatro);
        gestor.añadirEvento(conferencia);

        // ─── COMPRAS ─────────────────────────────────────────────
        sesion.iniciarSesion(cliente1.getIdUsuario(), "juan123");

        EntradaVipFactory vipFactory = new EntradaVipFactory();
        CompraFacade facade = new CompraFacade(vipFactory);

        List<Asiento> seleccion = List.of(asientosVip.get(0), asientosVip.get(1));
        Compra compra1 = facade.iniciarCompra(zonaVip, concierto, seleccion, List.of("Seguro"));
        facade.confirmarPago(compra1, new TarjetaPago("4111111111111111", "123"));
        gestor.realizarCompra(compra1);

        sesion.cerrarSesion();

        // ─── INCIDENCIAS ─────────────────────────────────────────
        gestor.registrarIncidencia(new Incidencia(
                "Pago duplicado",
                "El usuario intentó pagar dos veces la misma compra",
                LocalDate.now(),
                compra1
        ));
    }

    // Auxiliar para generar asientos de una zona
    private static List<Asiento> generarAsientos(String fila, int desde, int hasta) {
        List<Asiento> asientos = new ArrayList<>();
        for (int i = desde; i <= hasta; i++) {
            asientos.add(new Asiento(fila, i, EstadoAsiento.DISPONIBLE));
        }
        return asientos;
    }
}