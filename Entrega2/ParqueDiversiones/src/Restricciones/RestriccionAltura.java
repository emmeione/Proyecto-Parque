package Restricciones;

import Usuarios.Cliente;

public class RestriccionAltura implements Restriccion {
    private int alturaMinima;

    public RestriccionAltura(int alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    @Override
    public boolean cumple(Cliente cliente) {
        return cliente.getEstatura() >= alturaMinima;
    }

	@Override
	public String getDescripcion() {
	    return "Debe tener una altura mínima de " + alturaMinima + " metros.";
	}
	public String serializar() {
	    return "ALTURA:" + alturaMinima;
	}

}
