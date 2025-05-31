package Restricciones;

import Usuarios.Cliente;
import Usuarios.Usuario;

public class RestriccionAltura implements Restriccion {
    private int alturaMinima;

    public RestriccionAltura(int alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    @Override
    public boolean cumple(Usuario usuario) {
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            return cliente.getEstatura() >= alturaMinima;
        }
        return false;
    }

	@Override
	public String getDescripcion() {
	    return "Debe tener una altura mínima de " + alturaMinima + " metros.";
	}
	public String serializar() {
	    return "ALTURA:" + alturaMinima;
	}
	

}
