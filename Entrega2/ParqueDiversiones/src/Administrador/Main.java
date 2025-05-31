package Administrador;

import Atracciones.*;
import Restricciones.*;
import Usuarios.*;
import Roles.*;
import LugarDeServicio.*;
import Tiquetes.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // 1. Inicializar el parque
        Parque parque = new Parque();
        
        // 2. Crear datos demo
        crearDatosDemo(parque);

        // 3. Mostrar estado inicial
        System.out.println("\n=== ESTADO INICIAL DEL PARQUE ===");
        mostrarEstadoInicial(parque);

        // 4. Simular operaciones
        System.out.println("\n=== SIMULACIÓN DE OPERACIONES ===");
        simularOperaciones(parque);

        // 5. Mostrar estado final
        System.out.println("\n=== ESTADO FINAL ===");
        mostrarEstadoFinal(parque);
    }

    private static void crearDatosDemo(Parque parque) {
        // Crear clientes
        Cliente cliente1 = new Cliente("Juan", "Pérez", "123456789", "juan.perez", "cliente123", 25, 170);
        Cliente cliente2 = new Cliente("María", "Gómez", "987654321", "maria.gomez", "cliente456", 15, 150);
        parque.registrarCliente(cliente1);
        parque.registrarCliente(cliente2);

        // Crear empleados (solo uno para evitar duplicados)
        Rol operador = new OperadorAtraccion(5);
        Lugar cafeteria = new Cafeteria("Cafetería Principal", 5, Lugar.ZONA_ESTE);
        
        Empleado empleado1 = new Empleado(
            "Carlos", "López", "111222333", "carlos.lopez", "empleado123",
            operador, true, true, false, cafeteria
        );
        parque.registrarEmpleado(empleado1);

        // Crear atracciones
        crearAtraccionesDemo(parque);
    }

    private static void crearAtraccionesDemo(Parque parque) {
        // Montaña Rusa
        ArrayList<Restriccion> restriccionesMontaña = new ArrayList<>();
        restriccionesMontaña.add(new RestriccionAltura(150));
        restriccionesMontaña.add(new RestriccionEdad(12));

        Atraccion montañaRusa = new Mecanica(
            "Montaña Rusa Extrema",
            100, 2, restriccionesMontaña, NivelExclusividad.ORO,
            200, 140, 120, 40, "Problemas cardíacos", 5, true
        );
        parque.agregarAtraccion(montañaRusa);

        // Museo Interactivo
        ArrayList<Restriccion> restriccionesMuseo = new ArrayList<>();
        try {
            Date fechaEvento = new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-15");
            
            Atraccion museo = new Cultural(
                "Museo Interactivo",
                50, 1, restriccionesMuseo, NivelExclusividad.FAMILIAR,
                "Exposición", fechaEvento, false, true
            );
            parque.agregarAtraccion(museo);
        } catch (ParseException e) {
            System.out.println("Error al crear fecha demo: " + e.getMessage());
        }
    }

    private static void mostrarEstadoInicial(Parque parque) {
        System.out.println("\n--- Atracciones Disponibles ---");
        parque.mostrarCatalogoAtracciones();
        
        System.out.println("\n--- Clientes Registrados ---");
        parque.mostrarClientes();
        
        System.out.println("\n--- Empleados Registrados ---");
        parque.mostrarEmpleados();
    }

    private static void simularOperaciones(Parque parque) {
        // Obtener usuarios y atracciones
        Cliente cliente1 = parque.getClientes().get(0);
        Cliente cliente2 = parque.getClientes().get(1);
        Empleado empleado1 = parque.getEmpleados().get(0);
        
        Atraccion montañaRusa = parque.buscarAtraccionPorNombre("Montaña Rusa Extrema");
        Atraccion museo = parque.buscarAtraccionPorNombre("Museo Interactivo");

        // Generar tiquetes
        Map<String, List<Atraccion>> catalogo = parque.generarCatalogoTiquetes();
        Map<String, Tiquete> tiquetes = parque.crearTiquetesDesdeCatalogo(catalogo);

        // Simular compras
        System.out.println("\n--- COMPRA DE TIQUETES ---");
        
        // Cliente 1 compra tiquete Oro
        Tiquete tiqueteOro = tiquetes.get("Oro");
        if (tiqueteOro != null) {
            boolean compraExitosa = parque.registrarCompraTiquete(
                cliente1.getNombre(), 
                montañaRusa, 
                tiqueteOro
            );
            System.out.println(cliente1.getNombre() + (compraExitosa ? " compró" : " no pudo comprar") + 
                             " tiquete Oro para " + montañaRusa.getNombre());
        }

        // Cliente 2 intenta comprar tiquete Oro
        if (tiqueteOro != null) {
            boolean compraExitosa = parque.registrarCompraTiquete(
                cliente2.getNombre(), 
                montañaRusa, 
                tiqueteOro
            );
            System.out.println(cliente2.getNombre() + (compraExitosa ? " compró" : " no pudo comprar") + 
                             " tiquete Oro para " + montañaRusa.getNombre());
        }

        // Empleado compra tiquete Familiar
        Tiquete tiqueteFamiliar = tiquetes.get("Familiar");
        if (tiqueteFamiliar != null) {
            boolean compraExitosa = parque.registrarCompraTiquete(
                empleado1.getNombre(), 
                museo, 
                tiqueteFamiliar
            );
            System.out.println(empleado1.getNombre() + (compraExitosa ? " compró" : " no pudo comprar") + 
                             " tiquete Familiar para " + museo.getNombre());
        }

        // Simular uso de tiquetes
        System.out.println("\n--- USO DE TIQUETES ---");
        
        if (tiqueteOro != null) {
            boolean accesoPermitido = parque.usarTiqueteParaAtraccion(
                tiqueteOro, 
                montañaRusa, 
                cliente1
            );
            System.out.println(cliente1.getNombre() + (accesoPermitido ? " accedió" : " no accedió") + 
                             " a " + montañaRusa.getNombre());
        }

        if (tiqueteFamiliar != null) {
            boolean accesoPermitido = parque.usarTiqueteParaAtraccion(
                tiqueteFamiliar, 
                museo, 
                empleado1
            );
            System.out.println(empleado1.getNombre() + (accesoPermitido ? " accedió" : " no accedió") + 
                             " a " + museo.getNombre());
        }
    }

    private static void mostrarEstadoFinal(Parque parque) {
        System.out.println("\n--- Tiquetes Vendidos ---");
        if (parque.getTiquetes().isEmpty()) {
            System.out.println("No se vendieron tiquetes");
        } else {
            for (Tiquete t : parque.getTiquetes()) {
                System.out.println("- " + t.getCodigo() + " (" + t.getTipo() + ") para " + 
                                 (t.getComprador() != null ? t.getComprador().getNombre() : "N/A"));
            }
        }

        System.out.println("\n--- Cupos Restantes ---");
        for (Atraccion a : parque.getAtracciones()) {
            System.out.println(a.getNombre() + ": " + 
                             (a.getCupoMaximoClientes() - a.getTiquetesVendidos()) + "/" + 
                             a.getCupoMaximoClientes());
        }
    }
}