package Persistencia;

import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import LugarDeServicio.Taquilla;
import LugarDeServicio.Tienda;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Tiquetes.TiqueteDiamante;
import Tiquetes.TiqueteFamiliar;
import Tiquetes.TiqueteFastPass;
import Tiquetes.TiqueteOro;
import Usuarios.Empleado;
import Usuarios.Usuario;
import Atracciones.Atraccion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Roles.*;

import Administrador.Parque;

public class PersistenciaLugares {
	
//	Persistencia con archivos de texto
	private Parque parque;
	
	public PersistenciaLugares(Parque p) {

		parque = p;}
	public void guardarLugares() throws FileNotFoundException{
		PrintWriter escritor = new PrintWriter(new File("./data/lugares.txt"));
		ArrayList<Lugar>lugares = parque.getLugares();
		
		for (Lugar l: lugares) {
			
	        String nombre = l.getNombre();
	        String tipo = l.getTipo();
	        int capacidadMaxima = l.getCapacidadMaxima();
	        
	            escritor.println(nombre + ";" + tipo + ";" + capacidadMaxima);
	        
	    }

		escritor.close();
	}
	
	public void leerLugares()throws IOException {
		File f = new File("./data/lugares.txt");
		BufferedReader lector = new BufferedReader(new FileReader(f));
		String linea = lector.readLine();
		
		while(linea != null) {
			String[] datos = linea.split(";");
			String nombre = datos [0];
			String tipo = datos[1];
			int capacidadMaxima = Integer.parseInt(datos[2]);
			

			
			Lugar nuevo = null;
			tipo = tipo.trim();

			
			switch(tipo) {
//			Para los tipos de lugares
			case"Cafeteria":
				nuevo = new Cafeteria(nombre,capacidadMaxima);
				break;
            case "Tienda":
                nuevo = new Tienda(nombre,capacidadMaxima);
                break;
            case "Taquilla":
                nuevo = new Taquilla(nombre, capacidadMaxima);
                break;

            default:
                System.out.println("El lugar " + tipo+" no existe en este parque.");
                break;				
			}
			
	        if (nuevo != null) {
	            parque.agregarLugar(nuevo);
	            System.out.println("Lugar cargado: " + nombre);  

	        }
	        linea = lector.readLine();
	    }

	    lector.close();
	
			
	}
	
	public static void main(String[] args) throws IOException {
		
    	Parque parque = new Parque();

		
        Lugar cafeteriaPrincipal = new Cafeteria("Cafetería Principal", 50);
        Lugar tienda1 = new Tienda("Tienda de regalos", 30);
        Lugar taquilla1 = new Taquilla("Taquilla 1", 20);
        Lugar tienda2 = new Tienda("Tienda de dulces", 25);
        
        parque.agregarLugar(cafeteriaPrincipal);
        parque.agregarLugar(tienda1);
        parque.agregarLugar(taquilla1);
        parque.agregarLugar(tienda2);

        Rol cajero = new Cajero();
        Empleado donaCatalina = new Empleado(
                "Catalina", "Ángel", "999999", "donaCatalina", "nose...cartagena",
                cajero, false, false, false, cafeteriaPrincipal
        );
        
        donaCatalina.agregarLugar(cafeteriaPrincipal);
        donaCatalina.agregarLugar(tienda1);
        donaCatalina.agregarLugar(taquilla1);
        
//      Esta parte es para demostrar que no puede tener más de 3 lugares asignados :p
        boolean resultado = donaCatalina.agregarLugar(tienda2);
        if (!resultado) {
            System.out.println("Pasa el límite de lugares asignados.");
        }

        System.out.println("\nLugares asignados para el empleado:");
        for (Lugar l : donaCatalina.getLugarDeServicio()) {
            System.out.println("- " + l.getNombre());
        }

        PersistenciaLugares persistencia = new PersistenciaLugares(parque);

        persistencia.guardarLugares();
        
        persistencia.leerLugares();
    }

}
