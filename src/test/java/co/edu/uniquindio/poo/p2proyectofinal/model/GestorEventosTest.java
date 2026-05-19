package co.edu.uniquindio.poo.p2proyectofinal.model;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GestorEventosTest {

    private GestorEventos gestor;
    private Recinto recinto;
    private Zona zonaVip;

    @BeforeEach
    void setUp() throws ProyectoException {
        GestorEventos.resetForTesting();
        GestorSesion.resetForTesting();
        gestor = GestorEventos.getInstance();

        // Datos base reutilizables
        List<Asiento> asientos = List.of(
                new Asiento("A", 1, EstadoAsiento.DISPONIBLE),
                new Asiento("A", 2, EstadoAsiento.DISPONIBLE)
        );
        zonaVip = new Zona("VIP", 2, 300000.0, new ArrayList<>(asientos), null);
        recinto = new Recinto("Estadio", "Cra 1", "Armenia",
                new ArrayList<>(List.of(zonaVip)), "/images/coliseo_del_cafe.JPG");
        zonaVip.setRecinto(recinto);
        gestor.agregarRecinto(recinto);
    }

    // ─── EVENTOS ──────────────────────────────────────────────

    @Test
    void añadirEvento_deberiaAgregarCorrectamente() throws ProyectoException {
        Evento evento = crearEvento("Concierto Juanes", EstadoEvento.PUBLICADO);
        gestor.añadirEvento(evento);
        assertEquals(1, gestor.listarEventos().size());
    }

    @Test
    void añadirEvento_duplicado_deberiaLanzarExcepcion() throws ProyectoException {
        Evento evento = crearEvento("Concierto Juanes", EstadoEvento.PUBLICADO);
        gestor.añadirEvento(evento);
        assertThrows(ProyectoException.class, () -> gestor.añadirEvento(evento));
    }

    @Test
    void publicarEvento_cancelado_deberiaLanzarExcepcion() throws ProyectoException {
        Evento evento = crearEvento("Concierto", EstadoEvento.CANCELADO);
        gestor.añadirEvento(evento);
        assertThrows(ProyectoException.class,
                () -> gestor.publicarEvento(evento.getIdEvento()));
    }

    @Test
    void pausarEvento_noPublicado_deberiaLanzarExcepcion() throws ProyectoException {
        Evento evento = crearEvento("Concierto", EstadoEvento.BORRADOR);
        gestor.añadirEvento(evento);
        assertThrows(ProyectoException.class,
                () -> gestor.pausarEvento(evento.getIdEvento()));
    }

    @Test
    void filtrarEventos_porCiudad_deberiaRetornarSoloCorrespondientes() throws ProyectoException {
        gestor.añadirEvento(crearEvento("Evento Armenia", EstadoEvento.PUBLICADO));
        gestor.añadirEvento(crearEventoEnCiudad("Evento Bogotá", "Bogotá"));
        List<Evento> resultado = gestor.filtrarEventos(null, null, "Armenia", null, null);
        assertEquals(1, resultado.size());
        assertEquals("Evento Armenia", resultado.get(0).getNombre());
    }

    // ─── COMPRAS ──────────────────────────────────────────────

    @Test
    void registrarCompra_sinSesion_deberiaLanzarExcepcion() {
        Compra compra = new Compra(LocalDate.now(), null, null, new ArrayList<>());
        assertThrows(ProyectoException.class, () -> gestor.realizarCompra(compra));
    }

    @Test
    void cancelarCompra_enEstadoCreada_deberiaTransicionarEstado() throws ProyectoException {
        Compra compra = prepararCompraEnSistema();
        gestor.cancelarCompra(compra.getIdCompra());
        assertInstanceOf(CompraCanceladaState.class, compra.getEstado());
    }

    @Test
    void modificarCompra_enEstadoPagada_deberiaLanzarExcepcion() throws ProyectoException {
        Compra compra = prepararCompraEnSistema();
        compra.setEstado(new CompraPagadaState());
        assertThrows(ProyectoException.class,
                () -> gestor.modificarCompra(compra.getIdCompra(), new ArrayList<>()));
    }

    // ─── ZONAS Y ASIENTOS ─────────────────────────────────────

    @Test
    void ocupacionZona_sinVentas_deberiaSerCero() {
        assertEquals(0.0, zonaVip.calcularPorcentajeOcupacion());
    }

    @Test
    void ocupacionZona_todosVendidos_deberiaSerCien() {
        zonaVip.getAsientos().forEach(a -> a.setEstado(EstadoAsiento.VENDIDO));
        assertEquals(100.0, zonaVip.calcularPorcentajeOcupacion());
    }

    // ─── FILTROS ──────────────────────────────────────────────

    @Test
    void filtrarEventos_noBorrador_noDeberiaAparecer() throws ProyectoException {
        gestor.añadirEvento(crearEvento("Evento Borrador", EstadoEvento.BORRADOR));
        List<Evento> resultado = gestor.filtrarEventos(null, null, null, null, null);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void filtrarHistorial_porEstado_deberiaRetornarSoloCorrespondientes() throws ProyectoException {
        Compra compra = prepararCompraEnSistema();
        compra.setEstado(new CompraPagadaState());
        List<Compra> resultado = gestor.filtrarHistorialCompras(null, null, null, new CompraPagadaState());
        assertEquals(1, resultado.size());
    }

// ─── USUARIO ──────────────────────────────────────────────

    @Test
    void agregarMetodoPago_duplicado_deberiaLanzarExcepcion() throws ProyectoException {
        Usuario cliente = new Usuario("Juan", "juan@test.com", "300",
                "pass", RolUsuario.CLIENTE, new ArrayList<>());
        cliente.agregarMetodoPago(new EfectivoPago());
        assertThrows(ProyectoException.class, () -> cliente.agregarMetodoPago(new EfectivoPago()));
    }

// ─── ESTADOS DE COMPRA ────────────────────────────────────

    @Test
    void pagarCompra_yaConfirmada_deberiaLanzarExcepcion() throws ProyectoException {
        Compra compra = prepararCompraEnSistema();
        compra.setEstado(new CompraConfirmadaState());
        assertThrows(ProyectoException.class, () -> compra.pagar());
    }

    @Test
    void cancelarCompra_yaReembolsada_deberiaLanzarExcepcion() throws ProyectoException {
        Compra compra = prepararCompraEnSistema();
        compra.setEstado(new CompraReembolsadaState());
        assertThrows(ProyectoException.class, () -> compra.cancelar());
    }

// ─── SESION ───────────────────────────────────────────────

    @Test
    void iniciarSesion_contrasenaIncorrecta_deberiaLanzarExcepcion() throws ProyectoException {
        Usuario cliente = new Usuario("Juan", "juan@test.com", "300",
                "pass", RolUsuario.CLIENTE, new ArrayList<>());
        GestorSesion.getInstance().registrarUsuario(cliente);
        assertThrows(ProyectoException.class,
                () -> GestorSesion.getInstance().iniciarSesion(cliente.getIdUsuario(), "incorrecta"));
    }
    // ─── AUXILIARES ───────────────────────────────────────────

    private Evento crearEvento(String nombre, EstadoEvento estado) {
        return Evento.builder()
                .nombre(nombre)
                .categoria("Concierto")
                .ciudad("Armenia")
                .descripcion("Descripción")
                .estadoEvento(estado)
                .fechaHora(LocalDateTime.now().plusDays(30))
                .politicas("Políticas")
                .recinto(recinto)
                .build();
    }

    private Evento crearEventoEnCiudad(String nombre, String ciudad) {
        return Evento.builder()
                .nombre(nombre)
                .categoria("Concierto")
                .ciudad(ciudad)
                .descripcion("Descripción")
                .estadoEvento(EstadoEvento.PUBLICADO)
                .fechaHora(LocalDateTime.now().plusDays(30))
                .politicas("Políticas")
                .recinto(recinto)
                .build();
    }

    private Compra prepararCompraEnSistema() throws ProyectoException {
        Usuario cliente = new Usuario("Juan", "juan@test.com", "300",
                "pass", RolUsuario.CLIENTE, new ArrayList<>());
        GestorSesion.getInstance().registrarUsuario(cliente);
        GestorSesion.getInstance().iniciarSesion(cliente.getIdUsuario(), "pass");
        Evento evento = crearEvento("Evento Test", EstadoEvento.PUBLICADO);
        gestor.añadirEvento(evento);
        Compra compra = new Compra(LocalDate.now(), cliente, evento, new ArrayList<>());
        gestor.realizarCompra(compra);
        return compra;
    }
}