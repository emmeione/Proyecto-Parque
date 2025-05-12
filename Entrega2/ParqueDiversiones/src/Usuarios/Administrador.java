package Usuarios;

import java.util.List;

import Tiquetes.Tiquete;

public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String identificacion, String login, String password) {
        super(nombre, apellido, identificacion, login, password);
    }

    public void asignarTurnoEmpleado(Empleado empleado, String dia, String lugarYHorario) {
        empleado.asignarTurno(dia, lugarYHorario);
    }

    public void actualizarRolEmpleado(Empleado empleado, Roles.Rol nuevoRol) {
        empleado.setRol(nuevoRol);
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }
    @Override
    public boolean tieneDescuento() {
        return true; 
    }
    
    public void comprarTiquete(Tiquete tiquete, double porcentajeDescuento) {
    	super.comprarTiquete(tiquete, porcentajeDescuento);
       
        System.out.println("Empleado " + this.getNombre() + " ha comprado un tiquete para: " + tiquete.getTipo() +
                " con un precio final de: " + tiquete.getPrecioFinal());
    }
    
}
