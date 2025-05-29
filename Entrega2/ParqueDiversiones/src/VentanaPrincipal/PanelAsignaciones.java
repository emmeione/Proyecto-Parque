package VentanaPrincipal;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class PanelAsignaciones extends JPanel {
    public PanelAsignaciones() {
        setLayout(new GridLayout(2, 2, 10, 10));
        add(new JButton("Asignar Empleado"));
        add(new JButton("Liberar Asignación"));
        add(new JButton("Verificar Asignación"));
        add(new JButton("Obtener Lugar Asignado"));
    }
}