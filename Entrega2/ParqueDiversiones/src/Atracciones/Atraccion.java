package Atracciones;

import java.util.ArrayList;
import java.util.List;

import Restricciones.Restriccion;
import Usuarios.Cliente;

public abstract class Atraccion {

    protected String nombre;
    protected int cupoMaximoClientes;
    protected int cupoMinimoEncargados;
    protected ArrayList<Restriccion> restricciones;
    protected NivelExclusividad nivelExclusividad;

//    Método constructor
    public Atraccion(String nombre, int cupoMaximoClientes, int cupoMinimoEncargados, ArrayList<Restriccion> restricciones, NivelExclusividad nivelExclusividad) {
        this.nombre = nombre;
        this.cupoMaximoClientes = cupoMaximoClientes;
        this.cupoMinimoEncargados = cupoMinimoEncargados;
        this.restricciones = restricciones;
        this.nivelExclusividad = nivelExclusividad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCupoMaximoClientes() {
        return cupoMaximoClientes;
    }

    public int getCupoMinimoEncargados() {
        return cupoMinimoEncargados;
    }

    public ArrayList<Restriccion> getRestricciones() {
        return restricciones;
    }

    public NivelExclusividad getNivelExclusividad() {
        return nivelExclusividad;
    }
    
    public boolean clienteCumpleRestricciones(Cliente cliente) {
        for (Restriccion r : restricciones) {
            if (!r.cumple(cliente)) {
                return false; // Si alguna no se cumple, el cliente no puede acceder
            }
        }
        return true; // Si todas se cumplen, puede acceder
    }
    
    public ArrayList<String> getRestriccionesNoCumplidas(Cliente cliente) {
        ArrayList<String> noCumplidas = new ArrayList<>();
        for (Restriccion r : restricciones) {
            if (!r.cumple(cliente)) {
                noCumplidas.add(r.getDescripcion());
            }
        }
        return noCumplidas;
    }

    public abstract String getTipo();
    public void setRestricciones(ArrayList<Restriccion> restricciones) {
        this.restricciones = restricciones;
    }
    
    public abstract String serializarDetalles();
    	
    


}
