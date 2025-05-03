package LugarDeServicio;

import java.util.ArrayList;
import Atracciones.Atraccion;

public abstract class Lugar {
    private String nombre;
    private String tipo;
    private int capacidadMaxima;
    private boolean estaAbierto;
    private ArrayList<Atraccion> atraccionesAsociadas;

    public Lugar(String nombre, String tipo, int capacidadMaxima) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.estaAbierto = true;
        this.atraccionesAsociadas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public boolean estaAbierto() {
        return estaAbierto;
    }

    public void cerrar() {
        this.estaAbierto = false;
    }

    public void abrir() {
        this.estaAbierto = true;
    }

    public void toggleEstado() {
        estaAbierto = !estaAbierto;
    }

    public ArrayList<Atraccion> getAtraccionesAsociadas() {
        return new ArrayList<>(atraccionesAsociadas);
    }

    public boolean agregarAtraccion(Atraccion atraccion) {
        if (atraccion != null && !atraccionesAsociadas.contains(atraccion)) {
            atraccionesAsociadas.add(atraccion);
            return true;
        }
        return false;
    }

    public String obtenerInfo() {
        return toString();
    }

    @Override
    public String toString() {
        return "Lugar: " + nombre +
               ", Tipo: " + tipo +
               ", Capacidad: " + capacidadMaxima +
               ", Estado: " + (estaAbierto ? "Abierto" : "Cerrado") +
               ", Atracciones: " + atraccionesAsociadas.size();
    }

    public abstract void realizarActividad();
}
