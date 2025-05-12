package Restricciones;

import Usuarios.Cliente;

public class RestriccionMedica implements Restriccion{
    public boolean cumple(Cliente cliente) {
        return true; 
    }
    public String getDescripcion() {
        return "Se requiere buen estado de salud.";
    }
    public String serializar() {
    	return "Se requiere tener un buen estado de salud";
    }


}
