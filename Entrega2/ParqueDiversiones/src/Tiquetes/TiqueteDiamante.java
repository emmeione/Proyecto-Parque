package Tiquetes;

import Usuarios.Usuario;
import Atracciones.Atraccion;

public class TiqueteDiamante extends Tiquete {

    public TiqueteDiamante(String codigo, Usuario comprador, double precio) {
        super(codigo, comprador, precio);
    }

    @Override
    public String getTipo() {
        return "Diamante";
    }

    @Override
    public boolean esValidoPara(Atraccion atraccion) {
        return true;
    }

	
}
