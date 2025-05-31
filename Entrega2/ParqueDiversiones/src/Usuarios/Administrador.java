package Usuarios;

import java.time.LocalDate;
import java.util.List;

import Administrador.Parque;
import Atracciones.Atraccion;
import Roles.Rol;
import Tiquetes.Tiquete;

public class Administrador extends Usuario {

    private Rol rol;

    public Administrador(String nombre, String apellido, String identificacion, String login, String password, Rol rol) {
        super(nombre, apellido, identificacion, login, password);
        this.rol = rol;
    }

    public void asignarTurnoEmpleado(Empleado empleado, String dia, String lugarYHorario) {
        LocalDate fecha = LocalDate.parse(dia);  
        empleado.asignarTurno(fecha, lugarYHorario);
    }


    @Override
    public String getTipo() {
        return "Administrador";
    }
    @Override
    public boolean tieneDescuento() {
        return true; 
    }
    
    @Override
    public void comprarTiquete(Tiquete tiquete) {
        super.comprarTiquete(tiquete);
        System.out.println("Administrador " + this.getNombre() + " ha comprado un tiquete para: " + 
                          tiquete.getTipo() + " con precio: " + tiquete.getPrecioFinal());
    }

    public void comprarTiquete(Tiquete tiquete, double porcentajeDescuento) {
        double precioConDescuento = tiquete.getPrecio() * (1 - porcentajeDescuento/100);
        tiquete.setPrecioFinal(precioConDescuento);
        super.comprarTiquete(tiquete);
    }
    
    @Override
    public void comprarTiquete(Tiquete tiquete, Atraccion atraccion, Parque parque) {
        if (parque.puedeComprarTiquete(this, atraccion)) {
            super.comprarTiquete(tiquete, atraccion, parque);
            System.out.println("(Compra administrativa)");
        }
    }
    
    public Rol getRol() {
        return rol;
    }

	@Override
	public double getEstatura() {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
