package VentanaPrincipal;

import Atracciones.*;
import GeneradorTiqueteQR.TiqueteVentana;
import Tiquetes.*;
import Usuarios.Cliente;
import Restricciones.Restriccion;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.SwingUtilities;

import Administrador.Parque;

public class MainTest {

    public static void main(String[] args) {
        Parque parque = new Parque();
        ArrayList<Restriccion> restricciones = new ArrayList<>();

        Atraccion atraccionCultural = new Cultural(
            "Show de Magia", 
            50, 
            2, 
            restricciones, 
            NivelExclusividad.FAMILIAR,
            "Espectáculo", 
            new Date(), 
            false, 
            true
        );

        Atraccion atraccionMecanica = new Mecanica(
            "Montaña Rusa", 
            20, 
            3, 
            restricciones, 
            NivelExclusividad.DIAMANTE,
            200, 
            120, 
            100, 
            40, 
            "No apto para personas con problemas cardíacos",
            5, 
            true
        );

        Cliente cliente1 = new Cliente("Roberto", "Pérez", "CLI999", "rperez", "cliente789", 28, 1.80);
        Cliente cliente2 = new Cliente("Daniel", "Pérez", "1234", "danielp", "cliente789", 28, 1.80);

        // Crear tiquetes
        Tiquete tiqueteBasico = new TiqueteBasico("B001", cliente1, 100);
        Tiquete tiqueteFamiliar = new TiqueteFamiliar("F001", cliente2, 150);
        Tiquete tiqueteDiamante = new TiqueteDiamante("D001", cliente1, 300);

        // Cliente1 compra tiqueteDiamante
        cliente1.comprarTiquete(tiqueteDiamante, atraccionMecanica, parque);
        // Mostrar ventana con tiquete comprado de cliente1
        SwingUtilities.invokeLater(() -> {
            TiqueteVentana.mostrarTiquete(cliente1);
        });

        // Cliente2 compra tiqueteFamiliar
        cliente2.comprarTiquete(tiqueteFamiliar, atraccionCultural, parque);
        // Mostrar ventana con tiquete comprado de cliente2
        SwingUtilities.invokeLater(() -> {
            TiqueteVentana.mostrarTiquete(cliente2);
        });

        // Opcional: puedes hacer más compras y mostrar ventanas así
    }
}
