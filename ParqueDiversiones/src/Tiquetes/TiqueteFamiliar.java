package Tiquetes;

import Atracciones.Atraccion;
import Usuarios.Cliente;
import Usuarios.Usuario;

public class TiqueteFamiliar extends Tiquete {

    public TiqueteFamiliar(String codigo, Usuario comprador, double precio) {
        super(codigo, comprador, precio);
    }

    @Override
    public String getTipo() {
        return "Familiar";
    }

    @Override
    public boolean esValidoPara(Atraccion atraccion) {
        return atraccion.getNivelExclusividad() == Atracciones.NivelExclusividad.FAMILIAR;
    }

	@Override
	public String serializarTipos() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
