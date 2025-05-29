package VentanaPrincipal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class PanelEmpleados extends JPanel {
    public PanelEmpleados() {
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Empleados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(3, 3, 10, 10));
        botones.add(new JButton("Agregar Empleado"));
        botones.add(new JButton("Eliminar Empleado"));
        botones.add(new JButton("Asignar Turnos"));
        botones.add(new JButton("Asignar a Atracción"));
        botones.add(new JButton("Asignar a Cafetería"));
        botones.add(new JButton("Asignar a Servicio General"));
        botones.add(new JButton("Consultar Capacitados"));
        botones.add(new JButton("Gestionar Capacitaciones"));
        botones.add(new JButton("Ver Turnos Asignados"));

        add(botones, BorderLayout.CENTER);
    }
}