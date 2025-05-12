package Usuarios;

import java.util.ArrayList;
import java.util.List;

import Tiquetes.Tiquete;

public abstract class Usuario {
    protected String nombre;
    protected String apellido;
    protected String identificacion;
    protected String login;
    protected String password;
    protected List<Tiquete> tiquetesAdquiridos;

    
//  Método constructor
    public Usuario(String nombre, String apellido, String identificacion, String login, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.login = login;
        this.password = password;
        this.tiquetesAdquiridos = new ArrayList<>();

    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public List<Tiquete> getTiquetesAdquiridos() {
        return tiquetesAdquiridos;
    }

    public abstract boolean tieneDescuento();

    public abstract String getTipo();

    
    public void comprarTiquete(Tiquete tiquete, double porcentajeDescuento) {
        tiquete.aplicarDescuento(porcentajeDescuento);
        tiquetesAdquiridos.add(tiquete);
    }
}