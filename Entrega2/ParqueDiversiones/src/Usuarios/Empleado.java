package Usuarios;

import Roles.Rol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import LugarDeServicio.Lugar;

public class Empleado extends Usuario {
    private Rol rol;
    private boolean capacitadoAlimentos;
    private boolean capacitadoAltoRiesgo;
    private boolean capacitadoMedioRiesgo;
    private Map<String, ArrayList<String>> turnosAsignados;
    private ArrayList<Lugar> lugares;

    public Empleado(String nombre, String apellido, String identificacion, String login, String password,
                    Rol rol, boolean capacitadoAlimentos, boolean capacitadoAltoRiesgo, boolean capacitadoMedioRiesgo, Lugar lugarServicio) {
        super(nombre, apellido, identificacion, login, password);
        this.rol = rol;
        this.capacitadoAlimentos = capacitadoAlimentos;
        this.capacitadoAltoRiesgo = capacitadoAltoRiesgo;
        this.capacitadoMedioRiesgo = capacitadoMedioRiesgo;
        this.turnosAsignados = new HashMap<>();
        this.lugares =new ArrayList<>();
        
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean puedeSerCocinero() {
        return rol.getNombreRol().equals("Cocinero") && capacitadoAlimentos;
    }

    public boolean puedeOperarAtraccion(int riesgo) {
        if (riesgo == 2) return capacitadoAltoRiesgo;
        if (riesgo == 1) return capacitadoMedioRiesgo;
        return false;
    }

    public boolean esServicioGeneral() {
        return rol.getNombreRol().equalsIgnoreCase("Servicio General");
    }

    public boolean requiereCapacitacion() {
        return rol.requiereCapacitacion();
    }

    public void asignarTurno(String dia, String turnoTexto) {
        turnosAsignados.putIfAbsent(dia, new ArrayList<>());
        turnosAsignados.get(dia).add(turnoTexto);
    }

    public String consultarTurnos(String dia) {
        ArrayList<String> turnos = turnosAsignados.get(dia);
        if (turnos == null || turnos.isEmpty()) {
            return "No tiene turnos asignados para este día.";
        }
        return "Turnos del día " + dia + ": " + String.join(", ", turnos);
    }

    public String revisarTurnos() {
        if (turnosAsignados.isEmpty()) {
            return "Este empleado no tiene turnos asignados.";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ArrayList<String>> entry : turnosAsignados.entrySet()) {
            sb.append("Día: ").append(entry.getKey()).append(" | Turnos: ")
              .append(String.join(", ", entry.getValue())).append("\n");
        }
        return sb.toString();
    }
    public ArrayList<Lugar> getLugarDeServicio() {
        return lugares;  
    }


    @Override
    public boolean tieneDescuento() {
        return true;
    }

    @Override
    public String getTipo() {
        return "Empleado";
    }
    
    public int cantidadLugares() {
    	return lugares.size();
  
    }
    public boolean agregarLugar(Lugar nuevoLugar) {
        if (lugares.size() < 3) {
            lugares.add(nuevoLugar);
            return true;
        } else {
            return false;
        }
    }
    
    public void setLugarDeServicio(Lugar lugarDeServicio) {
        if (!agregarLugar(lugarDeServicio)) {
            System.out.println("No se pudo asignar el lugar de servicio.");
        }
    }
    
    public void asignarTurnoAEmpleado(Empleado otroEmpleado, String dia, String horario) {
        if (this.rol.getNombreRol().equals("Administrador")) {
            otroEmpleado.asignarTurno(dia, horario);
            System.out.println("Turno asignado por administrador " + this.getNombre());
        } else {
            System.out.println("No tiene permisos para asignar turnos.");
        }
    }
}
