package Restricciones;

import Usuarios.Cliente;
import Usuarios.Usuario;

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
	@Override
	public boolean cumple(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}


}
