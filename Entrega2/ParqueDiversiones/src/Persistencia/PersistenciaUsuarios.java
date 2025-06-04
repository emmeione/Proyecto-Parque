package Persistencia;

import Administrador.Parque;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Roles.*;
import Usuarios.*;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;

import java.io.*;
import java.util.ArrayList;

public class PersistenciaUsuarios {
    private Parque parque;

    public PersistenciaUsuarios(Parque parque) {
        this.parque = parque;
    }

    private static final String TIPO_CLIENTE = "CLIENTE";
    private static final String TIPO_EMPLEADO = "EMPLEADO";
    private static final String TIPO_ADMIN = "ADMIN";

    /**
     * Guarda todos los usuarios del parque en un archivo (agregando al final, no sobrescribiendo)
     */
    public void guardarUsuarios(String archivo) throws IOException {
        new File("./data").mkdirs(); // Asegura que el directorio exista

        // Agregar al archivo en lugar de sobrescribirlo
        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivo, true))) {
            for (Cliente cliente : parque.getClientes()) {
                guardarCliente(escritor, cliente);
            }

            for (Empleado empleado : parque.getEmpleados()) {
                guardarEmpleado(escritor, empleado);
            }

            for (Administrador admin : parque.getAdministradores()) {
                guardarAdministrador(escritor, admin);
            }
        }
    }

    private void guardarCliente(PrintWriter escritor, Cliente cliente) {
        escritor.println(String.join(";",
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getIdentificacion(),
                cliente.getLogin(),
                cliente.getPassword(),
                String.valueOf(cliente.getEdad()),
                String.valueOf(cliente.getEstatura()),
                TIPO_CLIENTE
        ));
    }

    private void guardarEmpleado(PrintWriter escritor, Empleado empleado) {
        for (Lugar lugar : empleado.getLugarDeServicio()) {
        	
        	String lugarsito = "";
        	
        	if (lugar.getNombre()==null || lugar.getNombre().equals("")) {
        		lugarsito = "Sin asignar";
        	
        		
        	}
        	else {
        		lugarsito = lugar.getNombre();
        	}
            escritor.println(String.join(";",
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getIdentificacion(),
                    empleado.getLogin(),
                    empleado.getPassword(),
                    empleado.getRol().getNombreRol(),
                    String.valueOf(empleado.puedeSerCocinero()),
                    String.valueOf(empleado.puedeOperarAtraccion(2)),
                    String.valueOf(empleado.puedeOperarAtraccion(1)),
                    lugarsito,
                    TIPO_EMPLEADO
            ));
        }
    }

    private void guardarAdministrador(PrintWriter escritor, Administrador admin) {
        escritor.println(String.join(";",
                admin.getNombre(),
                admin.getApellido(),
                admin.getIdentificacion(),
                admin.getLogin(),
                admin.getPassword(),
                admin.getRol().getNombreRol(),
                TIPO_ADMIN
        ));
    }

    /**
     * Carga usuarios desde un archivo
     */
    public void leerUsuarios(String archivo) throws IOException {
        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("Archivo no encontrado, se creará uno nuevo al guardar.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                procesarLinea(linea.trim());
            }
        }
        
    }

    private void procesarLinea(String linea) {
        if (linea.isEmpty()) return;

        String[] partes = linea.split(";");
        if (partes.length < 2) return;

        try {
            String tipoUsuario = partes[partes.length - 1];

            switch (tipoUsuario) {
                case TIPO_CLIENTE:
                    if (partes.length == 8) crearCliente(partes);
                    break;
                case TIPO_EMPLEADO:
                    if (partes.length == 11) crearEmpleado(partes);
                    break;
                case TIPO_ADMIN:
                    if (partes.length == 7) crearAdministrador(partes);
                    break;
                default:
                    System.err.println("Tipo de usuario desconocido: " + tipoUsuario);
                    System.out.println("Procesando usuario: " + linea);
                    System.out.println("Partes: " + partes.length + " → Tipo: " + tipoUsuario);

            }
        } catch (Exception e) {
            System.err.println("Error procesando línea: " + linea);
            e.printStackTrace();
            
        }
    }

    private void crearCliente(String[] datos) {
        Cliente cliente = new Cliente(
                datos[0], datos[1], datos[2], datos[3], datos[4],
                Integer.parseInt(datos[5]), Double.parseDouble(datos[6]));
        parque.registrarCliente(cliente);
    }

    private void crearEmpleado(String[] datos) {
        Rol rol = crearRol(datos[5]);

        Empleado empleado = new Empleado(
                datos[0], datos[1], datos[2], datos[3], datos[4],
                rol,
                Boolean.parseBoolean(datos[6]),
                Boolean.parseBoolean(datos[7]),
                Boolean.parseBoolean(datos[8]),
                parque.buscarLugarPorNombre(datos[9])
        );

        parque.registrarEmpleado(empleado);
    }

    private void crearAdministrador(String[] datos) {
        Administrador admin = new Administrador(
                datos[0], datos[1], datos[2], datos[3], datos[4],
                crearRol(datos[5])
        );
        parque.registrarAdministrador(admin);
    }

    private Rol crearRol(String nombreRol) {
        switch (nombreRol.toUpperCase()) {
            case "CAJERO":
                return new Cajero();
            case "COCINERO":
                return new Cocinero();
            case "ADMINISTRADOR":
                return new AdministradorR();
            case "SERVICIO GENERAL":
                return new ServicioGeneral();
            case "OPERADOR DE ATRACCIÓN":
                return new OperadorAtraccion(0);
            default:
                throw new IllegalArgumentException("Rol desconocido: " + nombreRol);
        }
    }
}
