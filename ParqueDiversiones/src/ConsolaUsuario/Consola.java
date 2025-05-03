package ConsolaUsuario;

import java.util.Scanner;

public class Consola {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("  ¡Bienvenido al Paque de Diversiones!");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción (1 o 2): ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("\n¿Es usted:");
        System.out.println("1. Cliente");
        System.out.println("2. Empleado");
        System.out.print("Seleccione una opción (1 o 2): ");
        int tipoUsuario = scanner.nextInt();
        scanner.nextLine();

        if (tipoUsuario == 1) {
            System.out.println("\n>> Usted es un cliente.");
        } else if (tipoUsuario == 2) {
            System.out.println("\n¿Qué tipo de empleado es?");
            System.out.println("1. Empleado Regular");
            System.out.println("2. Administrador");
            System.out.print("Seleccione una opción (1 o 2): ");
            int tipoEmpleado = scanner.nextInt();
            scanner.nextLine();

            if (tipoEmpleado == 1) {
                System.out.println("\n>> Usted es un Empleado Regular.");
            } else if (tipoEmpleado == 2) {
                System.out.println("\n>> Usted es un Administrador.");
            } else {
                System.out.println("Opción inválida.");
            }
        } else {
            System.out.println("Opción inválida.");
        }

        System.out.println("\n¡Gracias por usar el sistema!");
        scanner.close();
    }
}
