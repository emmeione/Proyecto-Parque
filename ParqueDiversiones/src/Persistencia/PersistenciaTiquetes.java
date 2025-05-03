package Persistencia;

import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.OperadorAtraccion;
import Roles.ServicioGeneral;
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

public class PersistenciaTiquetes {
//	Persistencia con archivos de texto
	private Parque parque;
	
	public PersistenciaTiquetes(Parque p) {

		parque = p;}
	public void guardarTiquetes() throws FileNotFoundException{
		PrintWriter escritor = new PrintWriter(new File("./data/tiquetes.txt"));
		ArrayList<Tiquete>tiquetes = parque.getTiquetes();
		
		for (Tiquete t: tiquetes) {
			
	        String codigo = t.getCodigo();
	        String nombre = t.getComprador().getNombre();
	        String apellido = t.getComprador().getApellido();
	        double precio = t.getPrecioFinal();
	        String tipo = t.getTipo();
	        
			
	        String fecha = "";
	        if (tipo.equals("FastPass") && t.serializarTipos() != null) {
	            fecha = t.serializarTipos();
	        }
			
	        if (tipo.equals("FastPass")) {
	            escritor.println(codigo + ";" + nombre + ";" + apellido + ";" + precio + ";" + tipo + ";" + fecha);
	        } else {
	            escritor.println(codigo + ";" + nombre + ";" + apellido + ";" + precio + ";" + tipo);
	        }
	    }

		escritor.close();
	}
	
	public void leerTiquetes()throws IOException {
		File f = new File("./data/tiquetes.txt");
		BufferedReader lector = new BufferedReader(new FileReader(f));
		String linea = lector.readLine();
		
		while(linea != null) {
			String[] datos = linea.split(";");
			String codigo = datos [0];
			double precio = Double.parseDouble(datos[3]);
			String comprador = datos[1];
			String tipo = datos[4];
			
			Usuario compradorr = parque.buscarUsuarioPorNombre(comprador);
            if (compradorr == null) {
                System.out.println("Comprador no encontrado: " + comprador);
                linea = lector.readLine();
                continue;
            }
			
			Tiquete nuevo = null;
			tipo = tipo.trim();

			
			switch(tipo) {
//			Para los tiquetes tipo DIAMANTE
			case"Diamante":
				nuevo = new TiqueteDiamante(codigo,compradorr,precio);
				break;
            case "Familiar":
                nuevo = new TiqueteFamiliar(codigo, compradorr, precio);
                break;
            case "Oro":
                nuevo = new TiqueteOro(codigo, compradorr, precio);
                break;
            case "Básico":
                nuevo = new TiqueteBasico(codigo, compradorr, precio);
                break;
            case "FastPass":
                if (datos.length >= 6) {
        			String fechaa = datos[5];

                    LocalDate fechap = LocalDate.parse(fechaa);
                    nuevo = new TiqueteFastPass(codigo, compradorr, fechap);
                } else {
                    System.out.println("Fecha faltante para FastPass: " + codigo);
                    break;
                }
                break;
            default:
                System.out.println("El tiquete " + tipo+" no se maneja en este parque.");
                break;
				
				
			}
			
	        if (nuevo != null) {
	            parque.agregarTiquetes(nuevo);
	        }
	        linea = lector.readLine();
	    }

	    lector.close();
	
			
	}
	
	public static void main(String[] args) {
        try {
//        	Aquí se crea el parque para traer los datos y hacer la persistencia
        	Parque parque = new Parque();
            PersistenciaTiquetes persistencia = new PersistenciaTiquetes(parque);
            Cafeteria cafe1 = new Cafeteria("Cafetería central", 8);

//          Crear y agregar tiquetes
            Tiquete tiqueteBasico = new TiqueteBasico("00001", new Empleado("Armando", "Mendoza", "1010084918", "a.mendoza", "brutaslapoliciaaaa",
                    new Cajero(), false, false, false, cafe1), 100000);
            parque.agregarTiquetes(tiqueteBasico);
            

//          Guardar tiquetes
            persistencia.guardarTiquetes();
            System.out.println("Tiquetes guardados correctamente.");

//          Crear nuevo parque y cargar los tiquetes desde archivo
            Parque parqueLeido = new Parque();
            PersistenciaTiquetes persistenciaLeida = new PersistenciaTiquetes(parqueLeido);
            persistenciaLeida.leerTiquetes();
            System.out.println("Tiquetes leídos correctamente.");

//          Imprimir tiquetes leídos
            System.out.println("\nTiquetes:");
            for (Tiquete t : parqueLeido.getTiquetes()) {
                System.out.println("- " + t.getCodigo() + " " + t.getPrecioFinal() +t.getUsuario().getNombre()+t.getUsuario().getApellido()+ ")");
            }



        } catch (Exception e) {
            System.out.println("Error durante la prueba de persistencia de tiquetes: " + e.getMessage());
            e.printStackTrace();
        }}
		


}