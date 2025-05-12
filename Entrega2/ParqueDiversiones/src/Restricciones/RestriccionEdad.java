package Restricciones;

import Usuarios.Cliente;

public class RestriccionEdad implements Restriccion {
    private int edadMinima;

    public RestriccionEdad(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    @Override
    public boolean cumple(Cliente cliente) {
        return cliente.getEdad() >= edadMinima;
    }

	@Override
	public String getDescripcion() {
	    return "Edad mínima requerida: " + edadMinima + " años";
	}
	
	public String serializar() {
	    return "EDAD:" + edadMinima;
	}
}
