package VentanaPrincipal;

import javax.swing.*;
import java.awt.*;

public class VentanaEmpleado extends JFrame {

    private JPanel panelDerecho;

    public VentanaEmpleado() {
        setTitle("Empleado - Parque");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo con botones (fondo rosita)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(235, 160, 185));
        panelIzquierdo.setLayout(new GridLayout(5, 1, 10, 10));

        String[] botones = {
            "Crear Empleado", "Asignar a Atracción", "Asignar a Cafetería", "Ver Lista Empleados", "Cerrar Sesión"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.addActionListener(e -> cambiarPanelDerecho(texto));
            panelIzquierdo.add(boton);
        }

        // Panel derecho con fondo rosita claro
        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(255, 190, 200));
        panelDerecho.setLayout(new BorderLayout());

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cambiarPanelDerecho(String accion) {
        panelDerecho.removeAll();

        switch (accion) {
            case "Crear Empleado" -> mostrarFormularioCrearEmpleado();
            case "Asignar a Atracción" -> mostrarFormularioAsignarAtraccion();
            case "Asignar a Cafetería" -> mostrarFormularioAsignarCafeteria();
            case "Ver Lista Empleados" -> mostrarListaEmpleados();
            case "Cerrar Sesión" -> this.dispose();
        }

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }

    private void mostrarFormularioCrearEmpleado() {
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        JLabel titulo = new JLabel("Crear Empleado");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        panelDerecho.add(titulo, gbc);

        gbc.gridwidth = 1;

        String[] labels = {"Nombre:", "Apellido:", "Identificación:", "Login:", "Password:", "Edad:", "Estatura (cm):"};
        JTextField[] camposTexto = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = fila;
            panelDerecho.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            camposTexto[i] = new JTextField(20);
            panelDerecho.add(camposTexto[i], gbc);
            fila++;
        }

        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnCrear = new JButton("Crear");
        // Aquí puedes añadir el ActionListener para crear el empleado real
        panelDerecho.add(btnCrear, gbc);
    }

    private void mostrarFormularioAsignarAtraccion() {
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        JLabel titulo = new JLabel("Asignar Empleado a Atracción");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        panelDerecho.add(titulo, gbc);

        gbc.gridwidth = 1;

        // Labels y combos simulados (usa tus listas reales)
        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Empleado:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> comboEmpleados = new JComboBox<>(new String[] {"Empleado1", "Empleado2", "Empleado3"});
        comboEmpleados.setPreferredSize(new Dimension(250, 25));
        panelDerecho.add(comboEmpleados, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Atracción:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> comboAtracciones = new JComboBox<>(new String[] {"Montaña Rusa", "Casa Embrujada", "Rueda de la Fortuna"});
        comboAtracciones.setPreferredSize(new Dimension(250, 25));
        panelDerecho.add(comboAtracciones, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAsignar = new JButton("Asignar");
        // Aquí puedes añadir el ActionListener para asignar empleado a atracción
        panelDerecho.add(btnAsignar, gbc);
    }

    private void mostrarFormularioAsignarCafeteria() {
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        JLabel titulo = new JLabel("Asignar Empleado a Cafetería");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        panelDerecho.add(titulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Empleado:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> comboEmpleados = new JComboBox<>(new String[] {"Empleado1", "Empleado2", "Empleado3"});
        comboEmpleados.setPreferredSize(new Dimension(250, 25));
        panelDerecho.add(comboEmpleados, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Cafetería:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> comboCafeterias = new JComboBox<>(new String[] {"Cafetería Central", "Cafetería Sur", "Cafetería Norte"});
        comboCafeterias.setPreferredSize(new Dimension(250, 25));
        panelDerecho.add(comboCafeterias, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAsignar = new JButton("Asignar");
        // Aquí puedes añadir el ActionListener para asignar empleado a cafetería
        panelDerecho.add(btnAsignar, gbc);
    }

    private void mostrarListaEmpleados() {
        panelDerecho.setLayout(new BorderLayout());

        // Simulamos lista, luego conecta con tus datos reales
        String[] columnas = {"ID", "Nombre", "Apellido", "Rol"};
        Object[][] datos = {
            {"1", "Ana", "Gómez", "Atracción"},
            {"2", "Luis", "Martínez", "Cafetería"},
            {"3", "Marta", "Rodríguez", "Servicio General"}
        };

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaEmpleado::new);
    }
}
