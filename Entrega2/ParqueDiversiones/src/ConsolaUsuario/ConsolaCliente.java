package ConsolaUsuario;

import Usuarios.Cliente;
import Atracciones.Atraccion;
import Persistencia.PersistenciaUsuarios;
import Administrador.Parque;

import java.util.List;
import java.util.Scanner;

public class ConsolaCliente {

    private Parque parque;
    private Scanner scanner;
    private PersistenciaUsuarios persistenciaUsuarios;

    public ConsolaCliente(Parque parque, PersistenciaUsuarios persistenciaUsuarios) {
        this.parque = parque;
        this.scanner = new Scanner(System.in);
        this.persistenciaUsuarios = persistenciaUsuarios;
    }

    public void iniciar() {
        try {
            persistenciaUsuarios.leerUsuarios("./data/usuarios");
        } catch (Exception e) {
            System.out.println("No se pudieron cargar los usuarios: " + e.getMessage());
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== CONSOLA CLIENTE ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    salir = true;
                    System.out.println("Saliendo de la consola cliente.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void registrarCliente() {
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
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        System.out.print("Estatura en cm: ");
        int estatura = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = new Cliente(nombre, apellido, identificacion, login, password, edad, estatura);
        parque.agregarCliente(cliente);
        try {
            persistenciaUsuarios.guardarUsuarios("./data/usuarios");
            System.out.println("Cliente registrado y guardado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
        }
    }

    private void iniciarSesion() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Cliente cliente = autenticarCliente(login, password);
        if (cliente != null) {
            System.out.println("Bienvenido, " + cliente.getNombre() + "!");
            menuCliente(cliente);
        } else {
            System.out.println("Login o password incorrectos.");
        }
    }

    private Cliente autenticarCliente(String login, String password) {
        for (Cliente c : parque.getClientes()) {
            if (c.getLogin().equals(login) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    private void menuCliente(Cliente cliente) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ CLIENTE ===");
            System.out.println("1. Ver perfil");
            System.out.println("2. Comprar tiquete");
            System.out.println("3. Ver atracciones disponibles");
            System.out.println("4. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarPerfil(cliente);
                    break;
                case 2:
                    comprarTiquete(cliente);
                    break;
                case 3:
                    parque.mostrarCatalogoAtracciones();
                    break;
                case 4:
                    salir = true;
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void mostrarPerfil(Cliente cliente) {
        System.out.println("\n=== PERFIL ===");
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Apellido: " + cliente.getApellido());
        System.out.println("Identificación: " + cliente.getIdentificacion());
        System.out.println("Edad: " + cliente.getEdad());
        System.out.println("Estatura: " + cliente.getEstatura() + " cm");
    }

    private void comprarTiquete(Cliente cliente) {
        System.out.println("\n=== TIPOS DE TIQUETE ===");
        System.out.println("1. Básico - Incluye acceso general a todas las atracciones estándar.");
        System.out.println("2. VIP - Incluye acceso prioritario, descuentos en comida, y atracciones exclusivas.");
        System.out.println("3. Premium - Incluye todo lo del VIP + acceso ilimitado + souvenirs.");

        System.out.print("Seleccione tipo de tiquete (1-3): ");
        int tipoTiquete = scanner.nextInt();
        scanner.nextLine();

        String descripcionTiquete = switch (tipoTiquete) {
            case 1 -> "Básico";
            case 2 -> "VIP";
            case 3 -> "Premium";
            default -> {
                System.out.println("Opción no válida, se seleccionará Básico por defecto.");
                yield "Básico";
            }
        };

        List<Atraccion> atracciones = parque.getAtracciones();

        if (atracciones.isEmpty()) {
            System.out.println("No hay atracciones disponibles.");
            return;
        }

        System.out.println("Seleccione atracción:");
        for (int i = 0; i < atracciones.size(); i++) {
            System.out.println((i + 1) + ". " + atracciones.get(i).getNombre());
        }
        int opcionAtraccion = scanner.nextInt();
        scanner.nextLine();

        if (opcionAtraccion < 1 || opcionAtraccion > atracciones.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        Atraccion seleccionada = atracciones.get(opcionAtraccion - 1);
        System.out.println("Compra simulada: Tiquete " + descripcionTiquete + " para la atracción " + seleccionada.getNombre());
    }

    public static void main(String[] args) {
        Parque parque = new Parque();
        PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios(parque);

        ConsolaCliente consola = new ConsolaCliente(parque, persistenciaUsuarios);
        consola.iniciar();
    }
}
