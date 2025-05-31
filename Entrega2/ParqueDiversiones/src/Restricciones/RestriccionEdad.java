package Restricciones;

import Usuarios.Cliente;
import Usuarios.Usuario;

public class RestriccionEdad implements Restriccion {
    private int edadMinima;

    public RestriccionEdad(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    @Override
    public boolean cumple(Usuario usuario) {
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            return cliente.getEdad() >= edadMinima;
        }
        return false; // No aplica para otros tipos de usuario
    }

	@Override
	public String getDescripcion() {
	    return "Edad mínima requerida: " + edadMinima + " años";
	}
	
	public String serializar() {
	    return "EDAD:" + edadMinima;
	}
}
