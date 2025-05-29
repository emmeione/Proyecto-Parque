package VentanaPrincipal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class PanelAtracciones extends JPanel {
    public PanelAtracciones() {
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Atracciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(3, 2, 10, 10));
        botones.add(new JButton("Crear Atracción"));
        botones.add(new JButton("Actualizar Atracción"));
        botones.add(new JButton("Eliminar Atracción"));
        botones.add(new JButton("Cambiar Nivel de Exclusividad"));
        botones.add(new JButton("Verificar Disponibilidad"));
        botones.add(new JButton("Gestionar Temporadas/Mantenimiento"));

        add(botones, BorderLayout.CENTER);
    }
}
