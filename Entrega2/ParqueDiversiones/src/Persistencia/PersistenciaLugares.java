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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import Roles.*;

import Administrador.Parque;

public class PersistenciaLugares {
	
//	Persistencia con archivos de texto
	private Parque parque;
	
	
	public PersistenciaLugares(Parque p) {

		parque = p;}
	public void guardarLugares() throws IOException {
	    File archivo = new File("./data/lugares.txt");
	    HashSet<String> nombresZonaGuardados = new HashSet<>();

	    if (archivo.exists()) {
	        BufferedReader lector = new BufferedReader(new FileReader(archivo));
	        String linea = lector.readLine();
	        while (linea != null) {
	            String[] datos = linea.split(";");
	            if (datos.length >= 4) {
	                String nombre = datos[0];
	                int zona = Integer.parseInt(datos[3]);
	                nombresZonaGuardados.add(nombre + "_" + zona);
	            }
	            linea = lector.readLine();
	        }
	        lector.close();
	    }

	    PrintWriter escritor = new PrintWriter(new FileWriter(archivo, true));
	    ArrayList<Lugar> lugares = parque.getLugares();

	    for (Lugar l : lugares) {
	        String nombre = l.getNombre();
	        int tipo = l.getTipo();
	        int capacidadMaxima = l.getCapacidadMaxima();
	        int zona = l.getZona();

	        String clave = nombre + "_" + zona;

	        if (!nombresZonaGuardados.contains(clave)) {
	            escritor.println(nombre + ";" + tipo + ";" + capacidadMaxima + ";" + zona);
	            nombresZonaGuardados.add(clave);
	            System.out.println("Guardado lugar nuevo: " + nombre + " en zona " + Lugar.nombreZona(zona));
	        }
	    }

	    escritor.close();
	}


	
	public void leerLugares() throws IOException {
	    File f = new File("./data/lugares.txt");
	    BufferedReader lector = new BufferedReader(new FileReader(f));
	    Parque parque = this.parque;
	    parque.getLugares().clear();

	    String linea = lector.readLine();

	    while (linea != null) {
	        String[] datos = linea.split(";");
	        String nombre = datos[0];
	        int tipo = Integer.parseInt(datos[1]);
	        int capacidadMaxima = Integer.parseInt(datos[2]);
	        int zona = Integer.parseInt(datos[3]);

	        Lugar nuevo = null;

	        switch (tipo) {
	            case 1:
	                nuevo = new Cafeteria(nombre, capacidadMaxima, zona);
	                break;
	            case 3:
	                nuevo = new Tienda(nombre, capacidadMaxima, zona);
	                break;
	            case 2:
	                nuevo = new Taquilla(nombre, capacidadMaxima, zona);
	                break;
	            default:
	                System.out.println("El lugar " + tipo + " no existe en este parque.");
	                break;
	        }

	        if (nuevo != null) {
	            parque.agregarLugar(nuevo);
	            System.out.println("Lugar cargado: " + nombre + " en zona " + Lugar.nombreZona(zona));
	        }

	        linea = lector.readLine();
	    }

	    lector.close();
	}

	
	public static void main(String[] args) throws IOException {
		
    	Parque parque = new Parque();

		
        Lugar cafeteriaPrincipal = new Cafeteria("Cafetería Principal", 50, Lugar.ZONA_CENTRAL);
        Lugar tienda1 = new Tienda("Tienda de regalos", 30, Lugar.ZONA_ESTE);
        Lugar taquilla1 = new Taquilla("Taquilla 1", 20, Lugar.ZONA_NORTE);
        Lugar tienda2 = new Tienda("Tienda de dulces", 25, Lugar.ZONA_SUR);
        Lugar tienda3 = new Tienda("Tienda de peluches", 25, Lugar.ZONA_SUR);        
        Lugar tienda4 = new Tienda("Tienda de peluches", 25, Lugar.ZONA_OESTE);

        

        
        parque.agregarLugar(cafeteriaPrincipal);
        parque.agregarLugar(tienda1);
        parque.agregarLugar(taquilla1);
        parque.agregarLugar(tienda2);
        parque.agregarLugar(tienda3);
        parque.agregarLugar(tienda4);



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
