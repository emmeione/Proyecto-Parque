package VentanaPrincipal;

import javax.swing.*;

import Usuarios.Administrador;

import java.awt.*;

import javax.swing.*;
import java.awt.*;
import Administrador.Parque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAdministrador extends JFrame {

    public VentanaAdministrador() {
        setTitle("Sistema de Administración del Parque de Diversiones");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuBar.add(crearMenu("Atracciones"));
        menuBar.add(crearMenu("Empleados"));
        menuBar.add(crearMenu("Espectáculos"));
        menuBar.add(crearMenu("Servicios"));
        menuBar.add(crearMenu("Reportes"));
        menuBar.add(crearMenu("Utilidades"));

        JPanel panelAccesoRapido = new JPanel(new GridLayout(3, 3, 10, 10));
        panelAccesoRapido.setBorder(BorderFactory.createTitledBorder("Acceso Rápido"));

        panelAccesoRapido.add(crearBoton("Atracciones", new PanelAtracciones()));
        panelAccesoRapido.add(crearBoton("Empleados", new PanelEmpleados()));
        panelAccesoRapido.add(crearBoton("Espectáculos", new PanelEspectaculos()));
        panelAccesoRapido.add(crearBoton("Servicios", new PanelLugaresServicio()));
        panelAccesoRapido.add(crearBoton("Reportes", new PanelReportes()));
        panelAccesoRapido.add(crearBoton("Asignaciones", new PanelAsignaciones()));
        panelAccesoRapido.add(crearBoton("Utilidades", new PanelUtilidades()));

        add(panelAccesoRapido, BorderLayout.CENTER);
    }

    private JMenu crearMenu(String titulo) {
        JMenu menu = new JMenu(titulo);
        menu.add(new JMenuItem("Opción 1"));
        menu.add(new JMenuItem("Opción 2"));
        return menu;
    }

    private JButton crearBoton(String texto, JPanel panelDestino) {
        JButton boton = new JButton(texto);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ventana = new JFrame(texto);
                ventana.setSize(600, 400);
                ventana.setLocationRelativeTo(null);
                ventana.add(panelDestino);
                ventana.setVisible(true);
            }
        });
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaAdministrador().setVisible(true);
        });
    }
}

