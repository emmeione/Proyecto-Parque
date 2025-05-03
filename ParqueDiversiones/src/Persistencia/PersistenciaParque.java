package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Administrador.Parque;
import Usuarios.Cliente;
import Usuarios.Empleado;
import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.Rol;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteDiamante;

public class PersistenciaParque {

//	Persistencia con archivos de texto
	private Parque parque;
	
	public PersistenciaParque(Parque p) {

		parque = p;
	}
	
	public void guardarParque() throws FileNotFoundException{
		PrintWriter escritor = new PrintWriter(new File("./data/parque.txt"));
		
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
	
	public Parque leerParque(String archivo) throws IOException, ParseException {
	    BufferedReader lector = new BufferedReader(new FileReader(archivo));
	    Parque parque = new Parque();
	    String linea;

	    while ((linea = lector.readLine()) != null) {
	        String[] datos = linea.split(";");
	        String nombre = datos[0];
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

	    lector.close();
	    return parque;
	}


	
	
}
    


