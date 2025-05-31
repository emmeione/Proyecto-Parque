package Usuarios;

import java.util.ArrayList;
import java.util.List;

import Administrador.Parque;
import Atracciones.Atraccion;
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
    public abstract double getEstatura();

    public String getPassword() {
        return password;
    }
    public List<Tiquete> getTiquetesAdquiridos() {
        return tiquetesAdquiridos;
    }
    
    public Tiquete getUltimoTiquete() {
        if (tiquetesAdquiridos.isEmpty()) {
            return null;
        }
        return tiquetesAdquiridos.get(tiquetesAdquiridos.size() - 1);
    }
    
    public void agregarTiquete(Tiquete tiquete) {
        this.tiquetesAdquiridos.add(tiquete);
    }

    public abstract boolean tieneDescuento();

    public abstract String getTipo();

    
    public void comprarTiquete(Tiquete tiquete) {
        tiquete.setComprador(this);
        tiquetesAdquiridos.add(tiquete);
        System.out.println(nombre + " ha comprado el tiquete: " + tiquete.getCodigo());
    }
    
    public void comprarTiquete(Tiquete tiquete, Atraccion atraccion, Parque parque) {
        if (parque.puedeComprarTiquete(this, atraccion)) {
            tiquete.setComprador(this);
            tiquetesAdquiridos.add(tiquete);
            parque.registrarTiquete(tiquete);
            System.out.println(nombre + " ha comprado el tiquete: " + tiquete.getCodigo());
        } else {
            System.out.println("No se pudo completar la compra.");
        }
    }

}