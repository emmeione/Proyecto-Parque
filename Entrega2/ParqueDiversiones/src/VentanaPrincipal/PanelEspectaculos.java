package VentanaPrincipal;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class PanelEspectaculos extends JPanel {
    public PanelEspectaculos() {
        setLayout(new GridLayout(1, 3, 10, 10));
        add(new JButton("Crear Espectáculo"));
        add(new JButton("Modificar Espectáculo"));
        add(new JButton("Eliminar Espectáculo"));
    }
}
