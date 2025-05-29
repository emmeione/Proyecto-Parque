package VentanaPrincipal;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class PanelLugaresServicio extends JPanel {
    public PanelLugaresServicio() {
        setLayout(new GridLayout(1, 3, 10, 10));
        add(new JButton("Crear Cafetería"));
        add(new JButton("Crear Taquilla"));
        add(new JButton("Crear Tienda"));
    }
}