package Persistencia;

import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Administrador.Parque;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PersistenciaAtracciones {
    private Parque parque;

    public PersistenciaAtracciones(Parque p) {
        parque = p;
    }

    public void guardarAtracciones(String rutaArchivo) throws IOException {
        PrintWriter escritor = new PrintWriter(new FileWriter("./data/atracciones.txt"), false);

        ArrayList<Atraccion> atracciones = parque.getAtracciones();

        for (Atraccion a : atracciones) {
            StringBuilder sb = new StringBuilder();
            for (Restriccion r : a.getRestricciones()) {
                sb.append(r.serializar()).append("|");
            }
            if (!a.getRestricciones().isEmpty()) {
                sb.setLength(sb.length() - 1); 
            }

            String detalles = a.serializarDetalles();

            if (a instanceof Cultural) {
                Cultural c = (Cultural) a;
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(c.getFechaEspectaculo());
                detalles = fecha + "," + c.esReguladoPorClima() + "," + c.isEnTemporada();


                escritor.println(a.getNombre() + ";" + a.getTipo() + ";" + detalles + ";" + a.getCupoMinimoEncargados() + ";" +
                        a.getCupoMaximoClientes() + ";" + sb.toString() + ";" + c.getTipoDeCultura() + ";" + a.getNivelExclusividad().name());

            } else {
                escritor.println(a.getNombre() + ";" + a.getTipo() + ";" + detalles + ";" + a.getCupoMinimoEncargados() + ";" +
                        a.getCupoMaximoClientes() + ";" + sb.toString() + ";" + a.getNivelExclusividad().name());
            }
        }

        escritor.close();
    }

    public void leerAtracciones(String archivo) throws IOException, ParseException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));

        HashMap<String, Boolean> existentes = new HashMap<>();
        for (Atraccion a : parque.getAtracciones()) {
            existentes.put(a.getNombre().trim().toLowerCase(), true);
        }

        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] datos = linea.split(";", -1); 
            if (datos.length < 7) continue; 

            String nombre = datos[0].trim();
            String nombreKey = nombre.toLowerCase();

            if (existentes.containsKey(nombreKey)) {
                continue; 
            }

            String tipo = datos[1];
            String detalles = datos[2];
            int cupoEncargados = Integer.parseInt(datos[3]);
            int cupoClientes = Integer.parseInt(datos[4]);
            String restriccionesRaw = datos[5];
            ArrayList<Restriccion> restricciones = new ArrayList<>();
            if (!restriccionesRaw.isEmpty()) {
                String[] restriccionesSeparadas = restriccionesRaw.split("\\|");
                for (String r : restriccionesSeparadas) {
                    if (r.startsWith("EDAD:")) {
                        int edadMinima = Integer.parseInt(r.split(":")[1]);
                        restricciones.add(new RestriccionEdad(edadMinima));
                    } else if (r.startsWith("ALTURA:")) {
                        int alturaMinima = Integer.parseInt(r.split(":")[1]);
                        restricciones.add(new RestriccionAltura(alturaMinima));
                    }
                }
            }

            Atraccion nueva = null;

            if (tipo.equalsIgnoreCase("Cultural")) {

                if (datos.length < 8) {
                    System.out.println("Datos incompletos para atracción cultural: " + nombre);
                    continue;
                }

                String tipoCultural = datos[6];
                String nivelExclusividadStr = datos[7];

                String[] infoC = detalles.split(",");
                if (infoC.length < 3) {
                    System.out.println("Detalles de Cultural incompletos para " + nombre);
                    continue;
                }

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = formato.parse(infoC[0]);
                boolean clima = Boolean.parseBoolean(infoC[1]);
                boolean temporadaC = Boolean.parseBoolean(infoC[2]);

                nueva = new Cultural(nombre, cupoClientes, cupoEncargados, restricciones,
                        NivelExclusividad.valueOf(nivelExclusividadStr), tipoCultural, fecha, clima, temporadaC);

            } else if (tipo.equalsIgnoreCase("Mecánica")) {

                String nivelExclusividadStr = datos[6];
                String[] info = detalles.split(",");
                if (info.length < 7) {
                    System.out.println("Detalles de Mecánica incompletos para " + nombre);
                    continue;
                }

                try {
                    int altMax = Integer.parseInt(info[0]);
                    int altMin = Integer.parseInt(info[1]);
                    int pesoMax = Integer.parseInt(info[2]);
                    int pesoMin = Integer.parseInt(info[3]);
                    String contra = info[4];
                    int riesgo = Integer.parseInt(info[5]);
                    boolean temporada = Boolean.parseBoolean(info[6]);

                    nueva = new Mecanica(nombre, cupoClientes, cupoEncargados, restricciones,
                            NivelExclusividad.valueOf(nivelExclusividadStr),
                            altMax, altMin, pesoMax, pesoMin, contra, riesgo, temporada);
                } catch (NumberFormatException e) {
                    System.out.println("Error parseando detalles mecánica para " + nombre + ": " + e.getMessage());
                    continue;
                }

            } else {
                System.out.println("Tipo de atracción desconocido: " + tipo);
                continue;
            }

            if (nueva != null) {
                parque.agregarAtraccion(nueva);
                existentes.put(nombreKey, true); // Actualizamos el mapa para no repetir
            }
        }

        lector.close();
    }


    
    public static void main(String[] args) {
        try {
            // Crear parque
            Parque parque = new Parque();

            // Agregar atracciones al parque
            agregarAtraccionesDePrueba(parque);

            // Crear objeto de persistencia
            PersistenciaAtracciones persistencia = new PersistenciaAtracciones(parque);

            // Guardar atracciones en archivo
            String rutaArchivo = "./data/atracciones.txt";
            persistencia.guardarAtracciones(rutaArchivo);
            System.out.println("✅ Atracciones guardadas en: " + rutaArchivo);

            // Limpiar atracciones del parque para probar la lectura
            parque.getAtracciones().clear();
            System.out.println("🧹 Parque limpiado.");

            // Leer atracciones desde archivo
            persistencia.leerAtracciones(rutaArchivo);
            System.out.println("📂 Atracciones cargadas desde el archivo.\n");

            // Mostrar atracciones
            mostrarAtracciones(parque);

        } catch (IOException | ParseException e) {
            System.err.println("❌ Error durante la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Agrega dos atracciones de prueba al parque
    private static void agregarAtraccionesDePrueba(Parque parque) throws ParseException {

        
        ArrayList<Restriccion> restriccionesMec2 = new ArrayList<>();
        restriccionesMec2.add(new RestriccionEdad(18));
        restriccionesMec2.add(new RestriccionAltura(150));

        Atraccion mecanica2 = new Mecanica(
                "Montaña Rusa", 100, 5, restriccionesMec2, NivelExclusividad.DIAMANTE,
                200, 120, 100, 50, "Ninguna", 3, true
        );



        parque.agregarAtraccion(mecanica2);

        System.out.println("🎢 Atracciones de prueba agregadas al parque.");
    }

    // Muestra las atracciones actualmente en el parque
    private static void mostrarAtracciones(Parque parque) {
        System.out.println("🎡 Lista de atracciones cargadas:");
        for (Atraccion a : parque.getAtracciones()) {
            System.out.println("- " + a.getNombre() + " [" + a.getTipo() + "] (Exclusividad: " + a.getNivelExclusividad() + ")");
        }
    }
}
