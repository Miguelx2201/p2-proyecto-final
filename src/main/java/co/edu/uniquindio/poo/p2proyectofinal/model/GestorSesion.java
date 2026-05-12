package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.io.Serializable;
import java.util.List;

public class GestorSesion implements Serializable {
    private static GestorSesion instance;

    private Usuario usuarioActual;

    private GestorSesion() {}

    public static GestorSesion getInstance() {
        if (instance == null) instance = new GestorSesion();
        return instance;
    }

    //Falta metodo iniciar sesion y cerrarsesion
    public Usuario getUsuarioActual() { return usuarioActual; }
}
