package VentanaPrincipal;

import javax.swing.*;

import Usuarios.Empleado;

import java.awt.*;

public class VentanaEmpleado extends JFrame {

    public VentanaEmpleado(Empleado empleado) {
        setTitle("Ventana Empleado - Bienvenido " + empleado.getNombre());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Bienvenido, " + empleado.getNombre() + " - Rol: " + empleado.getRol().getNombreRol());
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }
}
