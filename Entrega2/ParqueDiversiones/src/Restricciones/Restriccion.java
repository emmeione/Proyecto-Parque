package Restricciones;

import Usuarios.Cliente;
import Usuarios.Usuario;

public interface Restriccion {
    public abstract boolean cumple(Usuario usuario);
    String getDescripcion();
    String serializar();
    
}

