package Tiquetes;

import Usuarios.Cliente;
import Usuarios.Usuario;
import Atracciones.Atraccion;
import java.time.LocalDate;

public class TiqueteFastPass extends Tiquete {

    private LocalDate fecha;

    public TiqueteFastPass(String codigo, Usuario comprador, LocalDate fecha) {
        super(codigo, comprador, 0);  
        this.fecha = fecha;
    }

    @Override
    public String getTipo() {
        return "FastPass";
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public boolean esValidoPara(Atracciones.Atraccion atraccion) {
        return true; 
    }

    public boolean esValidoParaFecha(LocalDate fechaConsulta) {
        return !fechaConsulta.isBefore(fecha);  
    }

	@Override
	public String serializarTipos() {
		// TODO Auto-generated method stub
		return null;
	}
}
