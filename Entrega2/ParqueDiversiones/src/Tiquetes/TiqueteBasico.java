package Tiquetes;

import Atracciones.Atraccion;
import Usuarios.Cliente;
import Usuarios.Usuario;

public class TiqueteBasico extends Tiquete {

    public TiqueteBasico(String codigo, Usuario comprador, double precio) {
        super(codigo, comprador, precio);
    }

    @Override
    public String getTipo() {
        return "Básico";
    }

    @Override
    public boolean esValidoPara(Atraccion atraccion) {
        return false; 
    }


}
