package Tiquetes;

import Atracciones.Atraccion;
import Usuarios.Cliente;
import Usuarios.Usuario;

public class TiqueteOro extends Tiquete {

    public TiqueteOro(String codigo, Usuario comprador, double precio) {
        super(codigo, comprador, precio);
    }

    @Override
    public String getTipo() {
        return "Oro";
    }

    @Override
    public boolean esValidoPara(Atraccion atraccion) {
        return atraccion.getNivelExclusividad() == Atracciones.NivelExclusividad.FAMILIAR ||
               atraccion.getNivelExclusividad() == Atracciones.NivelExclusividad.ORO;
    }


}
