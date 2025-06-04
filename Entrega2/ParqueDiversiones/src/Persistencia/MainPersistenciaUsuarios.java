package Persistencia;

import Administrador.Parque;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import Persistencia.PersistenciaUsuarios;
import Roles.Cocinero;
import Roles.OperadorAtraccion;
import Roles.AdministradorR;
import Usuarios.Administrador;
import Usuarios.Cliente;
import Usuarios.Empleado;

public class MainPersistenciaUsuarios {
    public static void main(String[] args) {
        try {
            // 1. Crear parque y persistencia
            Parque parque = new Parque();
            PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);
            String archivo = "./data/usuarios.txt";

            // 2. Leer usuarios previos (si el archivo existe)
            persistencia.leerUsuarios(archivo);

            // 3. Crear un nuevo empleado (solo si no existe)
            if (parque.buscarEmpleadoPorNombre("EMP999") == null) {
                Cafeteria cafeteria = new Cafeteria("Cafetería Central", 30, Lugar.ZONA_NORTE);
                Empleado nuevoEmpleado = new Empleado("Laura", "Mejía", "EMP999", "lmejia", "pass123",
                        new Cocinero(), true, false, false, cafeteria);
                parque.registrarEmpleado(nuevoEmpleado);
                System.out.println("Empleado nuevo agregado: Laura Mejía");
            } else {
                System.out.println("Empleado EMP999 ya existe, no se agrega duplicado.");
            }

            // 4. Crear un nuevo cliente (solo si no existe)
            if (parque.buscarUsuarioPorId("CLI999") == null) {
                Cliente cliente = new Cliente("Roberto", "Pérez", "CLI999", "rperez", "cliente789", 28, 1.80);
                parque.registrarCliente(cliente);
                System.out.println("Cliente nuevo agregado: Roberto Pérez");
            } else {
                System.out.println("Cliente CLI999 ya existe, no se agrega duplicado.");
            }

            // 5. Crear un nuevo administrador (solo si no existe)
            if (parque.buscarAdministradorPorNombre("ADM999") == null) {
                Administrador admin = new Administrador("Sofía", "López", "ADM999", "slopez", "adminpass",
                        new AdministradorR());
                parque.registrarAdministrador(admin);
                System.out.println("Administrador nuevo agregado: Sofía López");
            } else {
                System.out.println("Administrador ADM999 ya existe, no se agrega duplicado.");
            }

            // 6. Guardar los usuarios actualizados (agrega al archivo)
            persistencia.guardarUsuarios(archivo);

            // 7. Mostrar los usuarios actuales
            System.out.println("\n=== ADMINISTRADORES ===");
            parque.getAdministradores().forEach(a ->
                    System.out.println(a.getNombre() + " " + a.getApellido() + " (" + a.getIdentificacion() + ")"));

            System.out.println("\n=== EMPLEADOS ===");
            parque.getEmpleados().forEach(e ->
                    System.out.println(e.getNombre() + " " + e.getApellido() + " (" + e.getIdentificacion() + ")"));

            System.out.println("\n=== CLIENTES ===");
            parque.getClientes().forEach(c ->
                    System.out.println(c.getNombre() + " " + c.getApellido() + " (" + c.getIdentificacion() + ")"));

        } catch (Exception e) {
            System.err.println("Error en persistencia: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
