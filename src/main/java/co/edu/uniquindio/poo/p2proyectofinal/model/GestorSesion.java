package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.io.Serializable;
import java.util.List;

public class GestorSesion implements Serializable {
    private static final long serialVersionUID = 1L;
    private static GestorSesion instance;

    private Usuario usuarioActual;

    private GestorSesion() {}

    public static GestorSesion getInstance() {
        if (instance == null) instance = new GestorSesion();
        return instance;
    }

    protected Object readResolve() { return getInstance(); }

    // Login
    public boolean iniciarSesion(String correo, String password, BaseDatos bd) {
        Usuario encontrado = bd.getUsuarios().stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);

        if (encontrado != null) {
            this.usuarioActual = encontrado;
            return true;
        }
        return false;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
}
