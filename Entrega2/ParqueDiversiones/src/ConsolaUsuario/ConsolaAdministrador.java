package ConsolaUsuario;

import LugarDeServicio.Cafeteria;
import LugarDeServicio.Taquilla;
import LugarDeServicio.Tienda;
import Persistencia.PersistenciaAtracciones;
import Persistencia.PersistenciaUsuarios;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import LugarDeServicio.Lugar;
import Roles.AdministradorR;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.Rol;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Tiquetes.TiqueteDiamante;
import Tiquetes.TiqueteFamiliar;
import Tiquetes.TiqueteOro;
import Tiquetes.Tiquetera;
import Usuarios.Administrador;
import Usuarios.Cliente;
import Usuarios.Empleado;
import Usuarios.Usuario;

import Atracciones.Atraccion;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Atracciones.Cultural;

import javax.swing.*;

import Administrador.Parque;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ConsolaAdministrador {

    private Parque parque;
    private Scanner scanner;
    private PersistenciaUsuarios persistencia;

    public ConsolaAdministrador(Parque parque) {
        this.parque = parque;
        this.scanner = new Scanner(System.in);
        this.persistencia = new PersistenciaUsuarios(parque);
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        try {
            File directorio = new File("./data");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            persistencia.leerUsuarios("./data/usuarios.txt");
            System.out.println("Datos de usuarios cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("AVISO: No se pudieron cargar usuarios existentes. " + e.getMessage());
        }
    }

    private boolean guardarDatos() {
        try {
            File archivo = new File("./data/usuarios.txt");
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            persistencia.guardarUsuarios("./data/usuarios.txt");
            System.out.println("Datos guardados exitosamente.");
            return true;
        } catch (IOException e) {
            System.out.println("Error grave al guardar: " + e.getMessage());
            return false;
        }
    }

    public void iniciar() throws IOException, ParseException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== CONSOLA ADMINISTRADOR ===");
            System.out.println("1. Crear atracción");
            System.out.println("2. Asignar atracción a empleado operador");
            System.out.println("3. Asignar empleado a lugar");
            System.out.println("4. Asignar turno a empleado");
            System.out.println("5. Ver turnos asignados");
            System.out.println("6. Ver todos los empleados");
            System.out.println("7. Ver lugares disponibles");
            System.out.println("8. Ver catálogo de atracciones");
            System.out.println("9. Salir");
            System.out.println("10. Crear empleado");
            System.out.println("11. Crear lugar");
            System.out.println("12. Comprar tiquete como administrador");
            System.out.println("13. Ver catálogo tiquetes");
            System.out.println("14. Simular compra de tiquete para cliente");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // consumir salto de línea

            switch (opcion) {
                case 1:
                    crearAtraccion();
                    break;
                case 2:
                    asignarAtraccionAEmpleado();
                    break;
                case 3:
                    asignarEmpleadoALugar();
                    break;
                case 4:
                    asignarTurno();
                    break;
                case 5:
                    verTurnos();
                    break;
                case 6:
                    verEmpleados();
                    break;
                case 7:
                    verLugares();
                    break;
                case 8:
                    parque.mostrarCatalogoAtracciones();
                    break;
                case 9:
                    salir = true;
                    guardarDatos();
                    System.out.println("Saliendo del sistema...");
                    break;
                case 10:
                    crearEmpleado();
                    break;
                case 11:
                    crearLugar();
                    break;
                case 12:
                    comprarTiqueteComoAdmin(null);
                    break;
                case 13:
                    parque.mostrarCatalogoTiquetes();
                    break;
                case 14:
                    simularCompraTiquete();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Métodos existentes (se mantienen igual)
    private void crearAtraccion() { /* ... */ }
    private void asignarAtraccionAEmpleado() { /* ... */ }
    private void asignarEmpleadoALugar() { /* ... */ }
    private void asignarTurno() { /* ... */ }
    private void verTurnos() { /* ... */ }
    private void verEmpleados() { /* ... */ }
    private void verLugares() { /* ... */ }
    private void crearEmpleado() { /* ... */ }
    private void crearLugar() { /* ... */ }

    // Métodos para manejo de tiquetes (nuevos o modificados)
    public void comprarTiqueteComoAdmin(Administrador admin) {
        System.out.println("Tipos de tiquete disponibles:");
        System.out.println("1. Diamante");
        System.out.println("2. Oro");
        System.out.println("3. Familiar");
        System.out.println("4. Básico");
        System.out.print("Seleccione tipo de tiquete (1-4): ");
        int opcionTipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese código del tiquete: ");
        String codigo = scanner.nextLine();

        System.out.print("Ingrese precio base del tiquete: ");
        double precio;
        try {
            precio = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Precio inválido.");
            return;
        }

        Tiquete tiquete = null;
        switch (opcionTipo) {
            case 1:
                tiquete = new TiqueteDiamante(codigo, admin, precio);
                break;
            case 2:
                tiquete = new TiqueteOro(codigo, admin, precio);
                break;
            case 3:
                tiquete = new TiqueteFamiliar(codigo, admin, precio);
                break;
            case 4:
                tiquete = new TiqueteBasico(codigo, admin, precio);
                break;
            default:
                System.out.println("Opción de tiquete no válida.");
                return;
        }

        parque.getTiquetera().agregarTiquete(tiquete);
        System.out.println("Tiquete creado y agregado correctamente a la tiquetera.");
    }

    private void simularCompraTiquete() {
        // Mostrar clientes
        System.out.println("\n--- CLIENTES REGISTRADOS ---");
        parque.mostrarClientes();
        
        // Seleccionar cliente
        System.out.print("\nIngrese el nombre del cliente: ");
        String nombreCliente = scanner.nextLine();
        
        Cliente cliente = parque.getClientes().stream()
            .filter(c -> c.getNombre().equalsIgnoreCase(nombreCliente))
            .findFirst()
            .orElse(null);
            
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        
        // Mostrar atracciones
        System.out.println("\n--- ATRACCIONES DISPONIBLES ---");
        parque.mostrarCatalogoAtracciones();
        
        // Seleccionar atracción
        System.out.print("\nIngrese el nombre de la atracción: ");
        String nombreAtraccion = scanner.nextLine();
        
        Atraccion atraccion = parque.buscarAtraccionPorNombre(nombreAtraccion);
        if (atraccion == null) {
            System.out.println("Atracción no encontrada.");
            return;
        }
        
        // Generar y mostrar tiquetes disponibles
        Map<String, List<Atraccion>> catalogo = parque.generarCatalogoTiquetes();
        Map<String, Tiquete> tiquetes = parque.crearTiquetesDesdeCatalogo(catalogo);
        
        System.out.println("\n--- TIPOS DE TIQUETE DISPONIBLES ---");
        List<String> tiposDisponibles = tiquetes.keySet().stream()
            .filter(tipo -> catalogo.get(tipo).contains(atraccion))
            .collect(Collectors.toList());
            
        tiposDisponibles.forEach(tipo -> 
            System.out.println("- " + tipo + " ($" + tiquetes.get(tipo).getPrecio() + ")")
        );
        
        // Seleccionar tipo de tiquete
        System.out.print("\nIngrese el tipo de tiquete: ");
        String tipoTiquete = scanner.nextLine();
        
        if (!tiposDisponibles.contains(tipoTiquete)) {
            System.out.println("Tipo de tiquete no válido para esta atracción.");
            return;
        }
        
        Tiquete tiquete = tiquetes.get(tipoTiquete);
        
        // Realizar compra
        boolean exito = parque.registrarCompraTiquete(
            cliente.getNombre(),
            atraccion,
            tiquete
        );
        
        if (exito) {
            System.out.println("\n¡Compra exitosa!");
            System.out.println("Cliente: " + cliente.getNombre());
            System.out.println("Atracción: " + atraccion.getNombre());
            System.out.println("Tiquete: " + tiquete.getCodigo() + " (" + tiquete.getTipo() + ")");
            System.out.println("Precio: $" + tiquete.getPrecio());
            
            mostrarTiqueteGrafico(cliente);
        } else {
            System.out.println("No se pudo completar la compra. Verifique restricciones.");
        }
    }

    private void mostrarTiqueteGrafico(Cliente cliente) {
        try {
            if (GraphicsEnvironment.isHeadless()) {
                System.out.println("\n[Modo gráfico no disponible en este entorno]");
                return;
            }

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Tiquete de " + cliente.getNombre());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel label = new JLabel(
                    "<html><center>" +
                    "<h2>PARQUE DE ATRACCIONES</h2>" +
                    "<h3>Tiquete " + cliente.getUltimoTiquete().getTipo() + "</h3>" +
                    "<hr>" +
                    "<p><b>Cliente:</b> " + cliente.getNombre() + "</p>" +
                    "<p><b>Código:</b> " + cliente.getUltimoTiquete().getCodigo() + "</p>" +
                    "<p><b>Fecha:</b> " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "</p>" +
                    "<p><b>Precio:</b> $" + cliente.getUltimoTiquete().getPrecio() + "</p>" +
                    "</center></html>",
                    SwingConstants.CENTER
                );
                
                panel.add(label, BorderLayout.CENTER);
                frame.add(panel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } catch (Exception e) {
            System.out.println("\nNo se pudo mostrar la versión gráfica del tiquete: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        Parque parque = new Parque();
        parque.cargarAtraccionesDesdeArchivo("./data/atracciones.txt");
        parque.cargarUsuariosDesdeArchivo("./data/usuarios.txt");

        ConsolaAdministrador consola = new ConsolaAdministrador(parque);
        consola.iniciar();
    }
}