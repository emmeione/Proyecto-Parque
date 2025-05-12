package Restricciones;

import Usuarios.Cliente;

public interface Restriccion {
    boolean cumple(Cliente cliente);
    String getDescripcion();
    String serializar();
}

