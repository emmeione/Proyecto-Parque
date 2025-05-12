package ConsolaUsuario;

import Administrador.Parque;

import java.util.Scanner;

import Administrador.Parque;
import Usuarios.Empleado;
import Usuarios.Usuario;

public class ConsolaAdministrador {
	
    public static void consolaAdministrador(Empleado admin, Parque parque, Scanner scanner) {
        System.out.println("\nBienvenido, " + admin.getNombre() + " (Administrador)");

        boolean activo = true;
        while (activo) {
            System.out.println("\nMenú Administrador");
            System.out.println("1. Ver todos los usuarios");
            System.out.println("2. Ver todas las atracciones");
            System.out.println("3. Ver todos los tiquetes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    for (Usuario u : parque.getClientes()) {
                        System.out.println("- " + u.getNombre() + " (" + u.getClass().getSimpleName() + ")");
                    }
                    break;
                case 2:
                    for (var a : parque.getAtracciones()) {
                        System.out.println("- " + a.getNombre());
                    }
                    break;
                case 3:
                    for (var t : parque.getTiquetes()) {
                        System.out.println("- " + t.getTipo());
                    }
                    break;
                case 4:
                    activo = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }


}
