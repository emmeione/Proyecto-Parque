package Atracciones;

import java.util.ArrayList;
import java.util.List;

import Restricciones.Restriccion;
import Usuarios.Cliente;
import Usuarios.Usuario;

public abstract class Atraccion {

    protected String nombre;
    protected int cupoMaximoClientes;
    protected int cupoMinimoEncargados;
    protected ArrayList<Restriccion> restricciones;
    protected NivelExclusividad nivelExclusividad;
    protected int tiquetesVendidos = 0;


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
    
    public int getTiquetesVendidos() {
        return tiquetesVendidos;
    }

    public void venderTiquete() {
        tiquetesVendidos++;
    }
    
    public boolean usuarioCumpleRestricciones(Usuario usuario) {
        for (Restriccion restriccion : restricciones) {
            if (!restriccion.cumple(usuario)) {  // Asumiendo que Restricción también fue actualizada
                return false;
            }
        }
        return true;
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
