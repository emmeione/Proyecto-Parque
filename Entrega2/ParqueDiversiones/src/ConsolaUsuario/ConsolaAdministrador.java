package ConsolaUsuario;

import LugarDeServicio.Cafeteria;
import LugarDeServicio.Taquilla;
import LugarDeServicio.Tienda;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import LugarDeServicio.Lugar;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.Rol;
import Usuarios.Administrador;
import Usuarios.Empleado;
import Usuarios.Usuario;

import Atracciones.Atraccion;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Atracciones.Cultural;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Administrador.Parque;

public class ConsolaAdministrador {

    private Parque parque;
    private Scanner scanner;

    public ConsolaAdministrador(Parque parque) {
        this.parque = parque;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
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
                    asignarTurnoAEmpleado();
                    break;
                case 5:
                    verTurnosAsignados();
                    break;
                case 6:
                    verTodosEmpleados();
                    break;
                case 7:
                    verLugaresDisponibles();
                    break;
                case 8:
                    parque.mostrarCatalogoAtracciones();
                    break;
                case 9:
                    salir = true;
                    System.out.println("Saliendo de la consola admin.");
                    break;
                case 10:
                    crearEmpleado();
                    break;
                case 11:
                    crearLugar();
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void crearAtraccion() {
        System.out.print("Nombre de la atracción: ");
        String nombre = scanner.nextLine();

        System.out.print("Capacidad: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Zona (CENTRAL/NORTE/SUR/ESTE/OESTE): ");
        String zona = scanner.nextLine();

        ArrayList<Restriccion> restricciones = new ArrayList<>();

        // Preguntar por RestriccionAltura
        System.out.print("¿Agregar restricción de altura mínima? (S/N): ");
        String respuestaAltura = scanner.nextLine();
        if (respuestaAltura.equalsIgnoreCase("S")) {
            System.out.print("Ingrese altura mínima en cm: ");
            int alturaMin = scanner.nextInt();
            scanner.nextLine();
            restricciones.add(new RestriccionAltura(alturaMin));
        }

        // Preguntar por RestriccionEdad
        System.out.print("¿Agregar restricción de edad mínima? (S/N): ");
        String respuestaEdad = scanner.nextLine();
        if (respuestaEdad.equalsIgnoreCase("S")) {
            System.out.print("Ingrese edad mínima en años: ");
            int edadMin = scanner.nextInt();
            scanner.nextLine();
            restricciones.add(new RestriccionEdad(edadMin));
        }

        NivelExclusividad nivelExclusividad = NivelExclusividad.DIAMANTE;

        Atraccion nuevaAtraccion = new Mecanica(
            nombre,
            capacidad,
            2,                  // cupo mínimo encargados
            restricciones,
            nivelExclusividad,
            200,                // altura máxima cm
            120,                // altura mínima cm
            100,                // peso máximo kg
            30,                 // peso mínimo kg
            "No apto para cardíacos",
            2,                  // nivel de riesgo
            true                // en temporada
        );

        parque.agregarAtraccion(nuevaAtraccion);
        System.out.println("Atracción mecánica creada y agregada al parque.");
    }

    private void asignarAtraccionAEmpleado() {
        System.out.print("Nombre del empleado operador: ");
        String nombreEmpleado = scanner.nextLine();
        Empleado empleado = parque.buscarEmpleadoPorNombre(nombreEmpleado);

        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        if (!(empleado.getRol() instanceof Roles.OperadorAtraccion)) {
            System.out.println("El empleado no es un operador de atracción.");
            return;
        }

        System.out.print("Nombre de la atracción: ");
        String nombreAtraccion = scanner.nextLine();
        Atraccion atraccion = parque.buscarAtraccionPorNombre(nombreAtraccion);

        if (atraccion == null) {
            System.out.println("Atracción no encontrada.");
            return;
        }

        empleado.asignarAtraccion(atraccion);
        System.out.println("Atracción asignada al empleado operador.");
    }

    private void asignarEmpleadoALugar() {
        System.out.print("Nombre del empleado: ");
        String nombreEmpleado = scanner.nextLine();
        Empleado empleado = parque.buscarEmpleadoPorNombre(nombreEmpleado);

        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.print("Nombre del lugar: ");
        String nombreLugar = scanner.nextLine();
        Lugar lugar = parque.buscarLugarPorNombre(nombreLugar);

        if (lugar == null) {
            System.out.println("Lugar no encontrado.");
            return;
        }

        empleado.setLugarDeServicio(lugar);
        System.out.println("Lugar asignado al empleado.");
    }

    private void asignarTurnoAEmpleado() {
        System.out.print("Nombre del empleado: ");
        String nombreEmpleado = scanner.nextLine();
        Empleado empleado = parque.buscarEmpleadoPorNombre(nombreEmpleado);

        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.print("Día del turno (YYYY-MM-DD): ");
        String dia = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(dia);

        System.out.print("Horario (ej. 08:00 - 12:00): ");
        String horario = scanner.nextLine();

        empleado.asignarTurno(fecha, horario);
        System.out.println("Turno asignado al empleado.");
    }

    private void verTurnosAsignados() {
        for (Empleado empleado : parque.getEmpleados()) {
            System.out.println("Empleado: " + empleado.getNombre());
            System.out.println(empleado.revisarTurnos());
            System.out.println("----------------------------");
        }
    }

    private void verTodosEmpleados() {
        System.out.println("=== LISTA DE EMPLEADOS ===");
        for (Empleado empleado : parque.getEmpleados()) {
            System.out.println("- " + empleado.getNombre() + " (" + empleado.getRol().getClass().getSimpleName() + ")");
        }
    }

    private void verLugaresDisponibles() {
        System.out.println("=== LUGARES DISPONIBLES ===");
        for (Lugar lugar : parque.getLugares()) {
            System.out.println("- " + lugar.getNombre() + " (Zona: " + lugar.getZona() + ")");
        }
    }

    private void crearEmpleado() {
        System.out.println("=== CREAR NUEVO EMPLEADO ===");

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

        System.out.println("Seleccione el rol:");
        System.out.println("1. Cajero");
        System.out.println("2. Cocinero");
        System.out.println("3. Operador de Atracción");
        System.out.println("4. Administrador");
        System.out.println("5. Servicio General");
        System.out.print("Opción: ");
        int opcionRol = scanner.nextInt();
        scanner.nextLine();

        Roles.Rol rol = null;
        switch (opcionRol) {
            case 1: rol = new Roles.Cajero(); break;
            case 2: rol = new Roles.Cocinero(); break;
            case 3:
                System.out.print("Nivel de riesgo permitido para Operador de Atracción (ej. 1, 2): ");
                int nivelRiesgo = scanner.nextInt();
                scanner.nextLine();
                rol = new Roles.OperadorAtraccion(nivelRiesgo);
                break;
            case 4: rol = new Roles.Administrador(); break;
            case 5: rol = new Roles.ServicioGeneral(); break;
            default:
                System.out.println("Rol no válido. Se asignará Servicio General.");
                rol = new Roles.ServicioGeneral();
        }

        Lugar lugarAsignado = null;
        if (!parque.getLugares().isEmpty()) {
            lugarAsignado = parque.getLugares().get(0); // Asignar el primer lugar disponible
        }

        boolean capacitadoAlimentos = false;
        boolean capacitadoAltoRiesgo = false;
        boolean capacitadoMedioRiesgo = false;

        Empleado nuevoEmpleado = new Empleado(
            nombre,
            apellido,
            identificacion,
            login,
            password,
            rol,
            capacitadoAlimentos,
            capacitadoAltoRiesgo,
            capacitadoMedioRiesgo,
            lugarAsignado
        );

        parque.agregarEmpleado(nuevoEmpleado);
        System.out.println("Empleado creado y agregado exitosamente.");
    }
    private void crearLugar() {
        System.out.println("=== CREAR NUEVO LUGAR ===");
        
        System.out.print("Tipo de lugar (1. Cafetería, 2. Tienda, 3. Taquilla): ");
        int tipoLugar = scanner.nextInt();
        scanner.nextLine();  // consumir salto de línea
        
        System.out.print("Nombre del lugar: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Capacidad: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Zona:");
        System.out.println("1. CENTRAL");
        System.out.println("2. NORTE");
        System.out.println("3. SUR");
        System.out.println("4. ESTE");
        System.out.println("5. OESTE");
        System.out.print("Seleccione zona (1-5): ");
        int opcionZona = scanner.nextInt();
        scanner.nextLine();

        int zona;

        switch (opcionZona) {
            case 1: zona = Lugar.ZONA_CENTRAL; break;
            case 2: zona = Lugar.ZONA_NORTE; break;
            case 3: zona = Lugar.ZONA_SUR; break;
            case 4: zona = Lugar.ZONA_ESTE; break;
            case 5: zona = Lugar.ZONA_OESTE; break;
            default:
                System.out.println("Zona no válida, se asigna CENTRAL por defecto.");
                zona = Lugar.ZONA_CENTRAL;
                break;
        }

        Lugar nuevoLugar = null;

        switch (tipoLugar) {
            case 1:
                nuevoLugar = new Cafeteria(nombre, capacidad, zona);
                break;
            case 2:
                nuevoLugar = new Tienda(nombre, capacidad, zona);
                break;
            case 3:
                nuevoLugar = new Taquilla(nombre, capacidad, zona);
                break;
            default:
                System.out.println("Tipo de lugar no válido.");
                return;
        }
        
        parque.agregarLugar(nuevoLugar);
        System.out.println("Lugar creado y agregado al parque.");
    }



    // MÉTODO MAIN para arrancar la consola desde aquí
    public static void main(String[] args) {
        Parque parque = new Parque(); // Puedes crear un parque con datos para probar
        ConsolaAdministrador consola = new ConsolaAdministrador(parque);
        consola.iniciar();
    }
}
