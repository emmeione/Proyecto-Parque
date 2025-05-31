package ConsolaUsuario;

import Administrador.Parque;
import Persistencia.PersistenciaUsuarios;
import Usuarios.Empleado;
import Usuarios.Administrador;
import Roles.*;
import LugarDeServicio.Lugar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConsolaPrueba {
    private Parque parque;
    private Scanner scanner;
    private PersistenciaUsuarios persistencia;
    private static final String ARCHIVO_USUARIOS = "./data/usuarios.txt";

    public ConsolaPrueba(Parque parque) {
        this.parque = parque;
        this.scanner = new Scanner(System.in);
        this.persistencia = new PersistenciaUsuarios(parque);
        inicializarSistema();
    }

    private void inicializarSistema() {
        // Crear directorio si no existe
        File directorio = new File("./data");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        // Limpiar archivo al iniciar
        try {
            new FileWriter(ARCHIVO_USUARIOS, false).close();
        } catch (IOException e) {
            System.out.println("Advertencia: No se pudo limpiar el archivo inicial");
        }
        
        cargarDatosIniciales();
    }

    private boolean guardarDatos() {
        try {
            // Usar modo sobrescritura (false) para evitar duplicados
            try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS, false))) {
                // Guardar todos los datos actuales
                persistencia.guardarUsuarios(ARCHIVO_USUARIOS);
            }
            
            System.out.println("Datos guardados exitosamente en: " + 
                             new File(ARCHIVO_USUARIOS).getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    private void cargarDatosIniciales() {
        try {
            File archivo = new File(ARCHIVO_USUARIOS);
            if (!archivo.exists()) {
                System.out.println("No existe archivo de usuarios. Se creará uno nuevo.");
                return;
            }
            
            // Solo cargar datos si el archivo no está vacío
            if (archivo.length() > 0) {
                persistencia.leerUsuarios(ARCHIVO_USUARIOS);
                System.out.println("Datos de usuarios cargados exitosamente.");
            } else {
                System.out.println("El archivo de usuarios está vacío.");
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }


    public void iniciar() throws IOException {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1: 
                    crearEmpleado(); 
                    break;
                case 2: 
                    parque.mostrarEmpleados(); 
                    break;
                case 3: 
                    parque.mostrarClientes(); 
                    break;
                case 4: 
                    parque.mostrarAdministradores(); 
                    break;
                case 5: 
                    verificarArchivoUsuarios(); 
                    break;
                case 6: 
                    salir = true;
                    if (guardarDatos()) {
                        System.out.println("Datos guardados correctamente. Saliendo...");
                    } else {
                        System.out.println("No se pudieron guardar los datos. Saliendo igual...");
                    }
                    break;
                default: 
                    System.out.println("Opción no válida");
            }
        }
    }
    
    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear empleado");
        System.out.println("2. Ver empleados");
        System.out.println("3. Ver clientes");
        System.out.println("4. Ver administradores");
        System.out.println("5. Ver diagnóstico de archivo");
        System.out.println("6. Salir y guardar");
        System.out.print("Seleccione opción: ");
    }


    private void crearEmpleado() {
        System.out.println("\n=== CREAR NUEVO EMPLEADO ===");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Identificación: ");
        String identificacion = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Seleccione rol:");
        System.out.println("1. Cajero");
        System.out.println("2. Cocinero");
        System.out.println("3. Operador de Atracción");
        System.out.print("Opción: ");
        int opcionRol = scanner.nextInt();
        scanner.nextLine();

        Rol rol;
        switch (opcionRol) {
            case 1: rol = new Cajero(); break;
            case 2: rol = new Cocinero(); break;
            case 3: rol = new OperadorAtraccion(1); break; // Nivel de riesgo por defecto
            default:
                System.out.println("Opción inválida, asignando Cajero por defecto");
                rol = new Cajero();
        }

        System.out.print("¿Puede manipular alimentos? (S/N): ");
        boolean alimentos = scanner.nextLine().equalsIgnoreCase("S");

        System.out.print("¿Puede operar atracciones de alto riesgo? (S/N): ");
        boolean altoRiesgo = scanner.nextLine().equalsIgnoreCase("S");

        System.out.print("¿Puede operar atracciones de medio riesgo? (S/N): ");
        boolean medioRiesgo = scanner.nextLine().equalsIgnoreCase("S");

        // Asignar lugar (simplificado para el ejemplo)
        Lugar lugar = parque.getLugares().isEmpty() ? null : parque.getLugares().get(0);

        Empleado nuevoEmpleado = new Empleado(
            nombre, apellido, identificacion, login, password,
            rol, alimentos, altoRiesgo, medioRiesgo, lugar
        );

        parque.agregarEmpleado(nuevoEmpleado);
        parque.registrarEmpleado(nuevoEmpleado);
        System.out.println("Empleado creado exitosamente!");
    }

    public void verificarArchivoUsuarios() {
        File archivo = new File("./data/usuarios.txt");
        System.out.println("\n=== DIAGNÓSTICO DEL ARCHIVO ===");
        System.out.println("Ruta absoluta: " + archivo.getAbsolutePath());
        System.out.println("Existe: " + archivo.exists());
        System.out.println("Tamaño: " + archivo.length() + " bytes");
        System.out.println("Se puede leer: " + archivo.canRead());
        System.out.println("Se puede escribir: " + archivo.canWrite());
        
        if (archivo.exists() && archivo.length() > 0) {
            System.out.println("\nPrimeras 5 líneas del archivo:");
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                while ((linea = br.readLine()) != null && contador < 5) {
                    System.out.println(linea);
                    contador++;
                }
            } catch (IOException e) {
                System.out.println("Error al leer archivo: " + e.getMessage());
            }
        }
        System.out.println("==============================\n");
    }

    public static void main(String[] args) throws IOException {
        Parque parque = new Parque();
        ConsolaPrueba consola = new ConsolaPrueba(parque);
        consola.iniciar();
    }
}