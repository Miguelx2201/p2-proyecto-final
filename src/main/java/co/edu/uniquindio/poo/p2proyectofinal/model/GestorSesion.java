package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorSesion{
    private static GestorSesion instance;

    private Usuario usuarioActual;
    private Map<String,Usuario> usuarios;
    private List<INotificacionObserver> observers;

    private GestorSesion() {
        usuarios = new HashMap<>();
        observers = new ArrayList<>();
        //Falta aqui poner un metodo para cargar los metodos de prueba.
    }

    public static GestorSesion getInstance() {
        if (instance == null) instance = new GestorSesion();
        return instance;
    }

    //Falta metodo iniciar sesion y cerrarsesion
    public Usuario getUsuarioActual() { return usuarioActual; }

    public void registrarUsuario(Usuario usuario) throws ProyectoException {
        if (usuarios.containsKey(usuario.getIdUsuario())) {
            throw new ProyectoException("Ya existe un usuario registrado con el id: " + usuario.getIdUsuario());
        }
        usuarios.put(usuario.getIdUsuario(), usuario);
    }
    public Usuario buscarUsuario(String id) throws ProyectoException {
        Usuario encontrado = usuarios.get(id);
        if (encontrado == null) {
            throw new ProyectoException("Usuario no encontrado.");
        }
        return encontrado;
    }
    public void actualizarUsuario(Usuario usuario) throws ProyectoException {
        if (!usuarios.containsKey(usuario.getIdUsuario())) {
            throw new ProyectoException("No se puede actualizar: El usuario no existe.");
        }
        // Sobrescritura
        usuarios.put(usuario.getIdUsuario(), usuario);
    }
    public void eliminarUsuario(String id) throws ProyectoException {
        if (usuarios.remove(id) == null) {
            throw new ProyectoException("No se pudo eliminar: El usuario no existía.");
        }
    }
    //Metodo para obtener una lista de usuarios. Pasarla de HashMap -> ArrayList
    public List<Usuario> obtenerListaUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    public void iniciarSesion(String id, String contraseña) throws ProyectoException {
        Usuario user = usuarios.get(id);

        if (user == null) {
            throw new ProyectoException("El usuario con ID " + id + " no existe.");
        }

        if (!user.getContraseña().equals(contraseña)) {
            throw new ProyectoException("Contraseña incorrecta.");
        }

        this.usuarioActual = user;
        System.out.println("Sesión iniciada para: " + user.getNombre());
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
}
