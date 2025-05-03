package ConsolaUsuario;

import java.util.Scanner;

import Administrador.Parque;
import Usuarios.Empleado;
import Usuarios.Usuario;


public class ConsolaEmpleado {

	
    public static void consolaEmpleado(Empleado empleado, Parque parque, Scanner scanner) {
        System.out.println("\nBienvenido, " + empleado.getNombre() + " (Empleado)");

        boolean activo = true;
        while (activo) {
            System.out.println("\nMenú Empleado");
            System.out.println("1. Ver usuarios");
            System.out.println("2. Ver atracciones");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    for (Usuario u : parque.getClientes()) {
                        System.out.println("- " + u.getNombre());
                    }
                    break;
                case 2:
                    for (var a : parque.getAtracciones()) {
                        System.out.println("- " + a.getNombre());
                    }
                    break;
                case 3:
                    activo = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

}
