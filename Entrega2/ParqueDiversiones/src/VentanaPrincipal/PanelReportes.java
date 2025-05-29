package VentanaPrincipal;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class PanelReportes extends JPanel {
    public PanelReportes() {
        setLayout(new GridLayout(1, 3, 10, 10));
        add(new JButton("Reporte de Ocupación"));
        add(new JButton("Ventas por Período"));
        add(new JButton("Afluencia por Atracción"));
    }
}