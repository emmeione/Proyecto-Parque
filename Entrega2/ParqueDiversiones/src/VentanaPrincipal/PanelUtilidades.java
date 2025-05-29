package VentanaPrincipal;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class PanelUtilidades extends JPanel {
    public PanelUtilidades() {
        setLayout(new GridLayout(1, 2, 10, 10));
        add(new JButton("Listas Internas"));
        add(new JButton("Manejar Parque"));
    }
}