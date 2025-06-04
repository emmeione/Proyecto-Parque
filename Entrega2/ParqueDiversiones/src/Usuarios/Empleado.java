package Usuarios;

import Roles.Cajero;
import Roles.Cocinero;
import Roles.OperadorAtraccion;
import Roles.AdministradorR;

import Roles.Rol;
import Roles.ServicioGeneral;
import Atracciones.Atraccion;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import LugarDeServicio.Taquilla;
import LugarDeServicio.Tienda;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;

public class Empleado extends Usuario {
    private Rol rol;
    private boolean capacitadoAlimentos;
    private boolean capacitadoAltoRiesgo;
    private boolean capacitadoMedioRiesgo;
    private Atraccion atraccionOperada;
    private Map<LocalDate, ArrayList<String>> turnosAsignados;
    private ArrayList<Lugar> lugares;

    public Empleado(String nombre, String apellido, String identificacion, String login, String password,
            Rol rol, boolean capacitadoAlimentos, boolean capacitadoAltoRiesgo, boolean capacitadoMedioRiesgo, Lugar lugarServicio) {
	    	super(nombre, apellido, identificacion, login, password);
	    	this.rol = rol;
	    	this.capacitadoAlimentos = capacitadoAlimentos;
	    	this.capacitadoAltoRiesgo = capacitadoAltoRiesgo;
	    	this.capacitadoMedioRiesgo = capacitadoMedioRiesgo;
	    	this.turnosAsignados = new HashMap<>();
	    	this.lugares = new ArrayList<>();
	    	this.atraccionOperada = null;
	
	    	if (lugarServicio != null) {
	    		this.lugares.add(lugarServicio);
	    	}
	    }

//      Constructor para operadores de atracción que asigna atracción además de lugar (si quieres)
        public Empleado(String nombre, String apellido, String identificacion, String login, String password,
                        Rol rol, boolean capacitadoAlimentos, boolean capacitadoAltoRiesgo, boolean capacitadoMedioRiesgo, Lugar lugarServicio,
                        Atraccion atraccionOperada) {
            this(nombre, apellido, identificacion, login, password, rol, capacitadoAlimentos, capacitadoAltoRiesgo, capacitadoMedioRiesgo, lugarServicio);
            this.atraccionOperada = atraccionOperada;
	    	if (lugarServicio != null) {
	    		this.lugares.add(lugarServicio);
	    	}
        }
        
        public Atraccion getAtraccionOperada() {
            return atraccionOperada;
        }

        public void setAtraccionOperada(Atraccion atraccionOperada) {
            this.atraccionOperada = atraccionOperada;
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
        if (rol instanceof OperadorAtraccion) {
            OperadorAtraccion operador = (OperadorAtraccion) rol;
            return riesgo <= operador.getNivelRiesgoPermitido();
        }
        return false;
    }


    public boolean esServicioGeneral() {
        return rol.getNombreRol().equalsIgnoreCase("Servicio General");
    }

    public boolean requiereCapacitacion() {
        return rol.requiereCapacitacion();
    }
    
    public boolean asignarAtraccion(Atraccion atraccion) {
        if (this.rol instanceof OperadorAtraccion) {
            if (atraccion instanceof Mecanica) {
                Mecanica mecanica = (Mecanica) atraccion;
                int nivelRiesgoAtraccion = mecanica.getNivelDeRiesgo();
                if (this.puedeOperarAtraccion(nivelRiesgoAtraccion)) {
                    this.atraccionOperada = atraccion;
                    System.out.println("Atracción mecánica '" + atraccion.getNombre() + "' asignada al operador " + this.getNombre());
                    return true;
                } else {
                    System.out.println("El operador no tiene el nivel de riesgo suficiente para esta atracción mecánica.");
                    return false;
                }
            } else {
                // Si es Cultural u otro tipo que no requiere nivel de riesgo
                this.atraccionOperada = atraccion;
                System.out.println("Atracción '" + atraccion.getNombre() + "' (no mecánica) asignada al operador " + this.getNombre());
                return true;
            }
        } else {
            System.out.println("Este empleado no es un operador de atracción.");
            return false;
        }
    }





    public String revisarTurnos() {
        if (turnosAsignados.isEmpty()) {
            return "Este empleado no tiene turnos asignados.";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<LocalDate, ArrayList<String>> entry : turnosAsignados.entrySet()) {
            sb.append("Fecha: ").append(entry.getKey()).append(" | Turnos: ")
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
    public String getId() {
    	return identificacion;
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
    
    public void asignarTurno(LocalDate fecha, String turnoTexto) {
        turnosAsignados.putIfAbsent(fecha, new ArrayList<>());
        turnosAsignados.get(fecha).add(turnoTexto);
    }

    public String consultarTurnos(LocalDate fecha) {
        ArrayList<String> turnos = turnosAsignados.get(fecha);
        if (turnos == null || turnos.isEmpty()) {
            return "No tiene turnos asignados para la fecha " + fecha + ".";
        }
        return "Turnos para la fecha " + fecha + ": " + String.join(", ", turnos);
    }


    public void asignarTurnoAEmpleado(Empleado otroEmpleado, LocalDate fecha, String horario) {
        if (this.rol.getNombreRol().equals("Administrador")) {
            otroEmpleado.asignarTurno(fecha, horario);
            System.out.println("Turno asignado por administrador " + this.getNombre() + " para fecha " + fecha);
        } else {
            System.out.println("No tiene permisos para asignar turnos.");
        }
    }

    
    public static void main(String[] args) {
        // Crear roles
        Rol cajero = new Cajero();
        Rol administradorRol = new AdministradorR();
        Rol servicioGeneral = new ServicioGeneral();
        Rol cocineroRol = new Cocinero();
        Rol operadorAltoRiesgoRol = new OperadorAtraccion(2); 

        // Crear lugares
        Lugar cafeteria = new Cafeteria("Cafetería Principal", 50, Lugar.ZONA_CENTRAL);
        Lugar tienda = new Tienda("Tienda de Regalos", 30, Lugar.ZONA_NORTE);
        Lugar taquilla = new Taquilla("Taquilla Norte", 20, Lugar.ZONA_NORTE);
        NivelExclusividad nivelExclusividad = NivelExclusividad.DIAMANTE; 
        ArrayList<Restriccion> restricciones = new ArrayList<>();
        restricciones.add(new RestriccionAltura(120));
        restricciones.add(new RestriccionEdad(10));


        Mecanica montañaRusa = new Mecanica(
            "Montaña Rusa Extrema",   
            40,                      
            2,                       
            restricciones,           
            nivelExclusividad,       
            200,                     
            120,                     
            100,                     
            30,                      
            "No apto para personas con problemas cardíacos",  
            2,                       
            true                     
        );
        // Crear empleados
        Empleado catalina = new Empleado("Catalina", "Ángel", "12345", "cataUser", "clave123",
                cajero, true, false, false, cafeteria);

        Empleado andres = new Empleado("Andrés", "Gómez", "67890", "andresAdmin", "admin123",
                administradorRol, true, true, true, taquilla);

        Empleado maria = new Empleado("María", "Pérez", "54321", "mariaUser", "clave321",
                servicioGeneral, false, false, false, tienda);

        Empleado juan = new Empleado("Juan", "Cocina", "11223", "juanCocinero", "cocina123",
                cocineroRol, true, false, false, cafeteria);
        Empleado sofia = new Empleado("Sofía", "Riesgo", "44556", "sofiaOperadora", "riesgo123",
                operadorAltoRiesgoRol, false, true, false, cafeteria, montañaRusa);


        // Mostrar roles
        System.out.println("\nRoles:");
        System.out.println("Catalina: " + catalina.getRol().getNombreRol());
        System.out.println("Andrés: " + andres.getRol().getNombreRol());
        System.out.println("María: " + maria.getRol().getNombreRol());
        System.out.println("Juan: " + juan.getRol().getNombreRol());
        System.out.println("Sofía: " + sofia.getRol().getNombreRol());

        // Mostrar permisos especiales
        System.out.println("\n¿Juan puede ser cocinero? " + juan.puedeSerCocinero());
        System.out.println("¿Sofía puede operar atracción de alto riesgo? " + sofia.puedeOperarAtraccion(2));

        // Asignar turnos usando LocalDate
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        LocalDate pasadoManana = hoy.plusDays(2);

        // Intento no autorizado
        System.out.println("\nIntento de Catalina (no admin) para asignar turno a María:");
        catalina.asignarTurnoAEmpleado(maria, hoy, "Mañana");

        // Asignación por administrador
        System.out.println("\nAndrés (administrador) asigna turnos:");
        andres.asignarTurnoAEmpleado(maria, manana, "Tarde");
        andres.asignarTurnoAEmpleado(juan, manana, "Mañana");
        andres.asignarTurnoAEmpleado(sofia, pasadoManana, "Noche");

        // Revisar turnos
        System.out.println("\nTurnos de María:");
        System.out.println(maria.revisarTurnos());

        System.out.println("\nTurnos de Juan:");
        System.out.println(juan.revisarTurnos());

        System.out.println("\nTurnos de Sofía:");
        System.out.println(sofia.revisarTurnos());
    }
	@Override
	public double getEstatura() {
		// TODO Auto-generated method stub
		return 0;
	}
}
