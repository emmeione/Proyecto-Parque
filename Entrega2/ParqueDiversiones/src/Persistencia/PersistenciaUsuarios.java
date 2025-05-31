package Persistencia;

import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import LugarDeServicio.Lugar;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.OperadorAtraccion;
import Roles.Rol;
import Roles.ServicioGeneral;
import Roles.AdministradorR;
import LugarDeServicio.*;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Tiquetes.TiqueteDiamante;
import Tiquetes.TiqueteFamiliar;
import Tiquetes.TiqueteFastPass;
import Tiquetes.TiqueteOro;
import Usuarios.Administrador;
import Usuarios.Cliente;
import Usuarios.Empleado;
import Usuarios.Usuario;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import Administrador.Parque;

public class PersistenciaUsuarios {
//	Persistencia con archivos de texto
	private Parque parque;
	
	public PersistenciaUsuarios(Parque p) {

		parque = p;}
	public void guardarUsuarios(String archivo) throws IOException{
		PrintWriter escritor = new PrintWriter(new FileWriter("./data/usuarios.txt"));
		ArrayList<Cliente>clientes = parque.getClientes();
		ArrayList<Empleado> empleados = parque.getEmpleados();
		ArrayList<Administrador> administradores = parque.getAdministradores();
			
//		Esta parte guarda a los clientes
		for (Cliente c: clientes) {
			
	        String nombre = c.getNombre();
	        String apellido = c.getApellido();
	        String identificacion = c.getIdentificacion();
	        String login = c.getLogin();
	        String password = c.getPassword();
	        int edad =c.getEdad();
	        double estatura = c.getEstatura();
	        String tipo = c.getTipo();
	        
            escritor.println(nombre + ";" + apellido + ";" + identificacion + ";" + login + ";" + password + ";"+edad+";"+estatura+";"+tipo);

	    }
//		Esta parte guarda a los empleados
		for(Empleado e: empleados) {
			
			String nombre = e.getNombre();
			String apellido = e.getApellido();
			String identificacion = e.getIdentificacion();
			String login = e.getLogin();
			String password = e.getPassword();
			String rol = e.getRol().getNombreRol();
			boolean capacitadoAlimentos = e.puedeSerCocinero();
			boolean capacitadoAltoRiesgo = e.puedeOperarAtraccion(2);
			boolean capacitadoMedioRiesgo = e.puedeOperarAtraccion(1);
			String tipo = e.getTipo();
			
			String nombreLugar;
			for (Lugar lugar : e.getLugarDeServicio()) {
			    nombreLugar = lugar.getNombre();
			    escritor.println(nombre + ";" + apellido + ";" + identificacion + ";" + login + ";" + password + ";" +
			            rol + ";" + capacitadoAlimentos + ";" + capacitadoAltoRiesgo + ";" + capacitadoMedioRiesgo + ";" + tipo + ";" + nombreLugar);
			}
		}
		
//		Esta parte guarda a los administradores
		for(Administrador a: administradores) {
			
			String nombre = a.getNombre();
			String apellido = a.getApellido();
			String identificacion = a.getIdentificacion();
			String login = a.getLogin();
			String password = a.getPassword();
			String rol = a.getRol().getNombreRol();
			String tipo = a.getTipo();
			
			escritor.println(nombre+";"+apellido+";"+identificacion+";"+login+";"+password+";"+rol+";"+tipo);	
		}

		escritor.close();
	}
	
	public void leerUsuarios(String archivo) throws IOException {
	    File f = new File("./data/usuarios.txt");
	    
	    if (!f.exists()) {
	        System.out.println("El archivo de usuarios no existe. Creando uno nuevo...");
	        f.createNewFile(); 
	        return; 
	    }
	    BufferedReader lector = new BufferedReader(new FileReader(f));
	    Parque parque = this.parque;

	    String linea = lector.readLine();
	    
	    while (linea != null) {
	        String[] datos = linea.split(";");

	        if (datos.length == 8 && datos[7].equals("Cliente")) {
//	        	Para clientes
	        	String nombre = datos[0];
	            String apellido = datos[1];
	            String identificacion = datos[2];
	            String login = datos[3];
	            String password = datos[4];
	            int edad = Integer.parseInt(datos[5]);
	            double estatura = Double.parseDouble(datos[6]);
	            
	            Cliente cliente = new Cliente(nombre, apellido, identificacion, login, password, edad, estatura);
	            parque.registrarCliente(cliente);}
	            
	            
	            
	            else if (datos.length == 7 && datos[6].equals("Administrador")) {
	                String nombre = datos[0];
	                String apellido = datos[1];
	                String identificacion = datos[2];
	                String login = datos[3];
	                String password = datos[4];
	                String nombreRol = datos[5];
		            Rol rol = crearRol(nombreRol, 0); 

                    Administrador administrador = new Administrador(nombre, apellido, identificacion, login, password, rol);
	                parque.registrarAdministrador(administrador);

	        } else if (datos.length == 11 && datos[9].equals("Empleado")) {
 
//	        	Para empleados
	        	String nombre = datos[0];
	            String apellido = datos[1];
	            String identificacion = datos[2];
	            String login = datos[3];
	            String password = datos[4];
	            String nombreRol = datos[5];
	            boolean capAlimentos = Boolean.parseBoolean(datos[6]);
	            boolean capAltoRiesgo = Boolean.parseBoolean(datos[7]);
	            boolean capMedioRiesgo = Boolean.parseBoolean(datos[8]);
	            String lugar = datos[9];
	            

	            int nivelRiesgo = 0;

	            if (Boolean.parseBoolean(datos[6])) { 
	                nivelRiesgo = 2;
	            } else if (Boolean.parseBoolean(datos[7])) { 
	                nivelRiesgo = 1;
	            } else {
	                nivelRiesgo = 0;
	            }
	            
	            Rol rol = crearRol(nombreRol, nivelRiesgo); 
	            Lugar lugarServicio = parque.buscarLugarPorNombre(lugar);


	            Empleado empleado = new Empleado(nombre, apellido, identificacion, login, password,
	                    rol, capAlimentos, capAltoRiesgo, capMedioRiesgo, lugarServicio);
	            parque.registrarEmpleado(empleado);
	        }

	        linea = lector.readLine();
	    }

	    lector.close();
	}

//	Esta vaina me ayuda a crear el rol ya que se me ocurrió la brillante idea de hacerlo como interfaz
	private Rol crearRol(String nombreRol, int nivelRiesgo) {
	    switch (nombreRol) {
	        case "Cajero":
	            return new Cajero();
	        case "Cocinero":
	            return new Cocinero();
	        case "Administrador":
	            return new AdministradorR();
	        case "Servicio General":
	            return new ServicioGeneral();
	        case "Operador de Atracción":
	            return new OperadorAtraccion(nivelRiesgo);
	        default:
	            throw new IllegalArgumentException("Rol desconocido: " + nombreRol);
	    }
	    
	
	}
    public static void main(String[] args) {
        // 1. Crear instancia del parque
        Parque parque = new Parque();
        
        // 2. Crear instancia de persistencia
        PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);
        
        // 3. Crear administradores de prueba
        Administrador admin1 = new Administrador(
            "Maria", 
            "Garcia", 
            "ADM001", 
            "mgarcia", 
            "admin123", 
            new AdministradorR()
        );
        
        Administrador admin2 = new Administrador(
            "Carlos", 
            "Lopez", 
            "ADM002", 
            "clopez", 
            "secure456", 
            new AdministradorR()
        );
        
        // 4. Registrar administradores en el parque
        parque.registrarAdministrador(admin1);
        parque.registrarAdministrador(admin2);
        
        try {
            System.out.println("=== ANTES DE GUARDAR ===");
            parque.mostrarAdministradores();
            
            // 5. Guardar los administradores
            persistencia.guardarUsuarios("./data/usuarios.txt");
            System.out.println("\nAdministradores guardados en el archivo.");
            
            // 6. Limpiar la lista para simular nueva sesión
            parque.getAdministradores().clear();
            System.out.println("\nLista de administradores limpiada.");
            parque.mostrarAdministradores();
            
            // 7. Cargar los datos del archivo
            persistencia.leerUsuarios("./data/usuarios.txt");
            System.out.println("\n=== DESPUÉS DE CARGAR ===");
            parque.mostrarAdministradores();
            
            // 8. Verificar integridad de los datos
            System.out.println("\n=== VERIFICACIÓN ===");

            
        } catch (IOException e) {
            System.err.println("Error en la persistencia: " + e.getMessage());
            e.printStackTrace();
        }
    }


}