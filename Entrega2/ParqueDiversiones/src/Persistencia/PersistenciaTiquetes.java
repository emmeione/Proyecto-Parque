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
	public void guardarTiquetes() throws IOException {
	    PrintWriter escritor = new PrintWriter(new FileWriter("./data/tiquetes.txt", true));
	    ArrayList<Tiquete> tiquetes = parque.getTiquetes();

	    for (Tiquete t : tiquetes) {
	        String codigo = t.getCodigo();
	        String nombre = t.getComprador().getNombre();
	        String apellido = t.getComprador().getApellido();
	        double precio = t.getPrecioFinal();
	        String tipo = t.getTipo();

	        String fecha = (t instanceof TiqueteFastPass) ? ((TiqueteFastPass) t).getFecha().toString() : "";
	        String rutaImagen = "./tiquetes/" + codigo + ".png";

	        escritor.println(codigo + ";" + nombre + ";" + apellido + ";" + precio + ";" + tipo + ";" + fecha + ";" + rutaImagen);
	    }

	    escritor.close();
	}

	
	public void leerTiquetes() throws IOException {
	    File f = new File("./data/tiquetes.txt");
	    BufferedReader lector = new BufferedReader(new FileReader(f));
	    Parque parque = this.parque;

	    String linea = lector.readLine();
	    HashMap<String, Boolean> tiquetesExistentes = new HashMap<>();

	    while (linea != null) {
	        String[] datos = linea.split(";");
	        if (datos.length < 6) {
	            linea = lector.readLine();
	            continue;
	        }

	        String codigo = datos[0];
	        String comprador = datos[1];
	        String tipo = datos[4];
	        double precio = Double.parseDouble(datos[3]);

	        if (tiquetesExistentes.containsKey(codigo)) {
	            linea = lector.readLine();
	            continue;
	        }
	        tiquetesExistentes.put(codigo, true);

	        Usuario compradorr = parque.buscarUsuarioPorNombre(comprador);
	        if (compradorr == null) {
	            System.out.println("Comprador no encontrado: " + comprador);
	            linea = lector.readLine();
	            continue;
	        }

	        Tiquete nuevo = null;
	        switch (tipo.trim()) {
	            case "Diamante":
	                nuevo = new TiqueteDiamante(codigo, compradorr, precio);
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
	                if (datos.length >= 7) {
	                    LocalDate fecha = LocalDate.parse(datos[5]);
	                    nuevo = new TiqueteFastPass(codigo, compradorr, fecha);
	                }
	                break;
	            default:
	                System.out.println("El tiquete " + tipo + " no se maneja.");
	        }

	        if (nuevo != null) {
	            parque.agregarTiquetes(nuevo);
	            // Puedes usar datos[6] para guardar la ruta de la imagen si deseas asociarla.
	            // También puedes crear un Map<String, String> para asociar códigos con rutas si quieres mostrar la tiquetera luego.
	        }

	        linea = lector.readLine();
	    }

	    lector.close();
	}

	
	public static void main(String[] args) {
	    try {
	        Parque parque = new Parque();
	        PersistenciaTiquetes persistencia = new PersistenciaTiquetes(parque);
	        Cafeteria cafe1 = new Cafeteria("Cafetería central", 8, Lugar.ZONA_CENTRAL);

	        Tiquete tiqueteBasico = new TiqueteBasico("00001", new Empleado("Armando", "Mendoza", "1010084918", "a.mendoza", "brutaslapoliciaaaa",
	                new Cajero(), false, false, false, cafe1), 100000);
	        parque.agregarTiquetes(tiqueteBasico);

	        persistencia.guardarTiquetes();
	        System.out.println("Tiquetes guardados correctamente.");

	        // Crear nuevo parque y cargar usuarios antes de los tiquetes
	        Parque parqueLeido = new Parque();
	        PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios(parqueLeido);
	        persistenciaUsuarios.leerUsuarios("./data/tiquetes.txt");

	        PersistenciaTiquetes persistenciaLeida = new PersistenciaTiquetes(parqueLeido);
	        persistenciaLeida.leerTiquetes();
	        System.out.println("Tiquetes leídos correctamente.");

	        System.out.println("\nTiquetes:");
	        for (Tiquete t : parqueLeido.getTiquetes()) {
	            System.out.println("- " + t.getCodigo() + " " + t.getPrecioFinal() + t.getUsuario().getNombre() + t.getUsuario().getApellido() + ")");
	        }

	    } catch (Exception e) {
	        System.out.println("Error durante la prueba de persistencia de tiquetes: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


}