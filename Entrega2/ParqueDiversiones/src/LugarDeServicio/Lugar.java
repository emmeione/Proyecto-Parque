package LugarDeServicio;

import java.util.ArrayList;
import Atracciones.Atraccion;

public abstract class Lugar {
    private String nombre;
    private int tipo;
    private int capacidadMaxima;
    private boolean estaAbierto;
    private int zona;
    private ArrayList<Atraccion> atraccionesAsociadas;
    
    public static final int ZONA_CENTRAL = 1;
    public static final int ZONA_NORTE = 2;
    public static final int ZONA_SUR = 3;
    public static final int ZONA_ESTE = 4;
    public static final int ZONA_OESTE = 5;

    public Lugar(String nombre, int tipo, int capacidadMaxima, int zona) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.estaAbierto = true;
        this.atraccionesAsociadas = new ArrayList<>();
        this.zona = zona;

    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
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
    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }
    
//    Esta parte es para que se imprima bonito lo de las zonas
    public static String nombreZona(int zona) {
        switch (zona) {
            case ZONA_CENTRAL: return "Central";
            case ZONA_NORTE: return "Norte";
            case ZONA_SUR: return "Sur";
            case ZONA_ESTE: return "Este";
            case ZONA_OESTE: return "Oeste";
            default: return "Desconocida";
        }
    }
}
