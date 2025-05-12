package Persistencia;

import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Roles.Cajero;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Tiquetes.TiqueteDiamante;
import Usuarios.Cliente;
import Usuarios.Empleado;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Administrador.Parque;

public class PersistenciaAtracciones {
//	Persistencia con archivos de texto
	private Parque parque;
	
	public PersistenciaAtracciones(Parque p) {

		parque = p;
	}
	
	public void guardarAtracciones() throws IOException{
		PrintWriter escritor = new PrintWriter(new FileWriter("./data/atracciones.txt", true));
		
//		Lista para las atracciones del parque
		ArrayList<Atraccion> atracciones = parque.getAtracciones();
	

		for(Atraccion a: atracciones) {
			
//			Esto es para poder traer las restricciones
	        StringBuilder sb = new StringBuilder();
	
	        for (Restriccion r : a.getRestricciones()) {
//	            Como son varias restricciones se separan con una , para poder diferenciarlas de los otros campos
	        	sb.append(r.serializar()).append(",");  
	        }
	        if (!a.getRestricciones().isEmpty()) {
//	            Para que se elimine el , que queda al final
	        	sb.setLength(sb.length() - 1); 
	        }
//			El formato del archivo es: <nombre>;<tipo>;<detalles>;<cupoMínimoEncargados>;<cupoMaximoClientes>;
			escritor.println(a.getNombre()+ ";"+ a.getTipo()+ ";"+a.serializarDetalles()+";"+a.getCupoMinimoEncargados()+";"+a.getCupoMaximoClientes()+ ";"+ sb.toString()+ ";"+a.getNivelExclusividad().name());
		}
		escritor.close();
	}
	
	public void leerAtracciones(String archivo) throws IOException, ParseException {
	    BufferedReader lector = new BufferedReader(new FileReader(archivo));
	    Parque parque = this.parque;
	    String linea;

//	    Mapa para evitar los duplicados 
	    HashMap<String, Boolean> existentes = new HashMap<>();
	    for (Atraccion a : parque.getAtracciones()) {
	    	existentes.put(a.getNombre().trim().toLowerCase(), true);
	    }

	    while ((linea = lector.readLine()) != null) {
	        String[] datos = linea.split(";");
	        String nombre = datos[0].trim().toLowerCase();

//	        Revisar en el mapa si ya existe la atraccion
	        if (existentes.containsKey(nombre)) {
	            continue;
	        }

	        String tipo = datos[1];
	        String detalles = datos[2];
	        int cupoEncargados = Integer.parseInt(datos[3]);
	        int cupoClientes = Integer.parseInt(datos[4]);
	        String[] restriccionesSeparadas = datos[5].split(",");
	        NivelExclusividad nivel = NivelExclusividad.valueOf(datos[6]);

	        ArrayList<Restriccion> restricciones = new ArrayList<>();
	        for (String r : restriccionesSeparadas) {
	            if (r.startsWith("EDAD:")) {
	                int edadMinima = Integer.parseInt(r.split(":")[1]);
	                restricciones.add(new RestriccionEdad(edadMinima));
	            } else if (r.startsWith("ALTURA:")) {
	                int alturaMinima = Integer.parseInt(r.split(":")[1]);
	                restricciones.add(new RestriccionAltura(alturaMinima));
	            }
	            
//	            este if puede ir teniendo más info dependiendo de si más adelante se requiere tener en cuenta nuevas restricciones
	        }

	        Atraccion nueva = null;

	        switch (tipo) {
	            case "Mecánica":
	                String[] info = detalles.split(",");
	                int altMax = Integer.parseInt(info[0]);
	                int altMin = Integer.parseInt(info[1]);
	                int pesoMax = Integer.parseInt(info[2]);
	                int pesoMin = Integer.parseInt(info[3]);
	                String contra = info[4];
	                int riesgo = Integer.parseInt(info[5]);
	                boolean temporada = Boolean.parseBoolean(info[6]);

	                nueva = new Mecanica(nombre, cupoClientes, cupoEncargados, restricciones, nivel,
	                                     altMax, altMin, pesoMax, pesoMin, contra, riesgo, temporada);
	                break;

	            case "Cultural":
	            	
//	            	(String nombre, int cupoMaximoClientes, int cupoMinimoEncargados, ArrayList<Restriccion> restricciones, NivelExclusividad nivelExclusividad,
//	                        String tipoDeCultura, Date fecha, boolean esReguladoPorClima, boolean enTemporada) {
//	            super(nombre, cupoMaximoClientes, cupoMinimoEncargados, restricciones, nivelExclusividad);
	            	String[] infoC = detalles.split(",");
	            	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	                Date fecha = formato.parse(infoC[0]);
	            	boolean clima = Boolean.parseBoolean(infoC[1]);
	            	boolean temporadaC = Boolean.parseBoolean(infoC[2]);
	            	String tipoCultural = datos[3];
	            	
	            	
	            	
	                nueva = new Cultural(nombre, cupoClientes, cupoEncargados, restricciones, nivel,tipoCultural, fecha, clima, temporadaC);
	                break;

	        }

	        if (nueva != null) {
	            parque.agregarAtraccion(nueva);
	        }
	    }

	    lector.close();}
	
    public static void main(String[] args) {
        try {

        	Parque parque = new Parque();

            ArrayList<Restriccion> restricciones = new ArrayList<>();
            restricciones.add(new RestriccionEdad(18));
            restricciones.add(new RestriccionAltura(150));

            Atraccion atraccion = new Mecanica("Montaña Rusa", 100, 5, restricciones, NivelExclusividad.DIAMANTE,
                                               200, 120, 100, 50, "Ninguna", 3, true);
            
            ArrayList<Restriccion> restriccionesCultural = new ArrayList<>();
            restricciones.add(new RestriccionEdad(10));
            String fecha = "2025-03-18"; 
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaa = formato.parse(fecha);

            
            Atraccion atraccionCultural = new Cultural(    "Espectáculo gatuno", 5, 1,restriccionesCultural,NivelExclusividad.FAMILIAR,
            		"Espectáculo", fechaa, true, true);
            


            parque.agregarAtraccion(atraccion);
            parque.agregarAtraccion(atraccionCultural);
            


//          Se guarda el archivo
            PersistenciaAtracciones persistencia = new PersistenciaAtracciones(parque);
            persistencia.guardarAtracciones();;
            System.out.println("Atracción guardada correctamente.");

            parque.getAtracciones().clear(); 
//          Se lee el archivo
            persistencia.leerAtracciones("./data/atracciones.txt");
            System.out.println("Atracciones leídas correctamente.");

//          Detallitos
            System.out.println("\nAtracciones del parque leído:");
            for (Atraccion a : parque.getAtracciones()) {
                System.out.println("- " + a.getNombre() + " (" + a.getTipo() + ")");
            }

        } catch (Exception e) {
            System.out.println("Error al guardar o leer la atracción: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
