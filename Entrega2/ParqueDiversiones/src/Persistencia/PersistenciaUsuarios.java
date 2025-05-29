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
import LugarDeServicio.*;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Tiquetes.TiqueteDiamante;
import Tiquetes.TiqueteFamiliar;
import Tiquetes.TiqueteFastPass;
import Tiquetes.TiqueteOro;
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
	public void guardarUsuarios() throws IOException{
		PrintWriter escritor = new PrintWriter(new FileWriter("./data/usuarios.txt", true));
		ArrayList<Cliente>clientes = parque.getClientes();
		ArrayList<Empleado> empleados = parque.getEmpleados();
		
		
		
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
//		Esta parte guarda a los empleados/admin
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
		


		escritor.close();
	}
	
	public void leerUsuarios() throws IOException {
	    File f = new File("./data/usuarios.txt");
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
	            parque.registrarCliente(cliente);

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
	        case "Servicio General":
	            return new ServicioGeneral();
	        case "Operador de Atracción":
	            return new OperadorAtraccion(nivelRiesgo);
	        default:
	            throw new IllegalArgumentException("Rol desconocido: " + nombreRol);
	    }
	    
	
	}

	public static void main(String[] args) {
        try {

//        	Crear el parque
        	Parque parque = new Parque();
            PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);

//            Prueba del cliente
            Cliente cliente = new Cliente("Beatriz", "Pinzón", "10308437824", "b.pinzon", "tandivinoo", 25, 170);
            parque.agregarCliente(cliente);

//          Puebras para distintos tipos de empleados
            
            Taquilla taquilla = new Taquilla("Taquilla 1", 5, Lugar.ZONA_CENTRAL);
            Cafeteria cafe1 = new Cafeteria("Caferería central", 8, Lugar.ZONA_ESTE);
            Tienda tienda1 = new Tienda("",7, Lugar.ZONA_OESTE);
            Tienda tienda2 = new Tienda("",8, Lugar.ZONA_OESTE);
            Tienda tienda3 = new Tienda("",9, Lugar.ZONA_SUR);
            
            


            Empleado cajero = new Empleado("Armando", "Mendoza", "1010084918", "a.mendoza", "brutaslapoliciaaaa",
            	    new Cajero(), false, false, false, taquilla);
            Empleado cocinero = new Empleado("Marcela", "Valencia", "102934876", "ma.valencia", "peroyotemerezcoooo",
                    new Cocinero(), true, false, false, cafe1);
            Empleado operadorAlto = new Empleado("Patricia", "Fernandez", "13982783", "pati.fer", "yllegaronlosmeseroooos",
                    new OperadorAtraccion(2), false, true, false, tienda1);
            Empleado operadorMedio = new Empleado("Hugo", "Lombardi", "1288374", "hugo.lombardi", "llegolamagiallegoelcolorllegolavida",
                    new OperadorAtraccion(1), false, false, true, tienda2);
            Empleado general = new Empleado("Nicolás", "Mora", "93812", "nico.mora", "esaeslafamaquetengoahora",
                    new ServicioGeneral(), false, false, false, tienda3);

            parque.agregarEmpleado(cajero);
            parque.agregarEmpleado(cocinero);
            parque.agregarEmpleado(operadorAlto);
            parque.agregarEmpleado(operadorMedio);
            parque.agregarEmpleado(general);

//          Guardar a los usuarios
            persistencia.guardarUsuarios();
            System.out.println("Usuarios guardados correctamente.");

//          Crear nuevo parque y cargar los usuarios desde archivo
            Parque parqueLeido = new Parque();
            PersistenciaUsuarios persistenciaLeida = new PersistenciaUsuarios(parqueLeido);
            persistenciaLeida.leerUsuarios();
            System.out.println("Usuarios leídos correctamente.");

//          Imprimir usuarios leídos
            System.out.println("\nClientes:");
            for (Cliente c : parqueLeido.getClientes()) {
                System.out.println("- " + c.getNombre() + " " + c.getApellido() + " (" + c.getIdentificacion() + ")");
            }

            System.out.println("\nEmpleados:");
            for (Empleado e : parqueLeido.getEmpleados()) {
                System.out.println("- " + e.getNombre() + " " + e.getApellido() + " - Rol: " + e.getRol().getNombreRol());
            }

        } catch (Exception e) {
            System.out.println("Error durante la prueba de persistencia de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

			
	
		


}