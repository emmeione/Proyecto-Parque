package VentanaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import Administrador.Parque;
import Usuarios.Cliente;

public class VentanaCliente extends JFrame {

    private JPanel panelDerecho;
    private Parque parque;
    private Cliente clienteActual;

    public VentanaCliente(Cliente cliente) {
        this.clienteActual = cliente;
        this.parque = new Parque();

        parque.cargarAtraccionesDesdeArchivo("./data/atracciones.txt");

        setTitle("Usuario - Parque");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(235, 160, 185));
        panelIzquierdo.setLayout(new GridLayout(4, 1, 10, 10));

        String[] botones = {
            "Ver Perfil", "Comprar Tiquete", "Ver Atracciones Disponibles", "Cerrar Sesión"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.addActionListener(e -> cambiarPanelDerecho(texto));
            panelIzquierdo.add(boton);
        }

        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(255, 190, 200));
        panelDerecho.setLayout(new BorderLayout());

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cambiarPanelDerecho(String accion) {
        panelDerecho.removeAll();

        if ("Ver Perfil".equals(accion)) {
            mostrarPerfil();
        } 
        else if ("Comprar Tiquete".equals(accion)) {
            panelDerecho.add(new PanelCompraTiquetes(parque, clienteActual));
        }
        else if ("Ver Atracciones Disponibles".equals(accion)) {
            mostrarAtraccionesDisponibles();
        }
        else if ("Cerrar Sesión".equals(accion)) {
            this.dispose();
        }

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }

    private void mostrarPerfil() {
        panelDerecho.removeAll();
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Perfil del Cliente");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelDerecho.add(titulo, gbc);

        gbc.gridwidth = 1;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        JLabel valorNombre = new JLabel(clienteActual.getNombre());
        valorNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorNombre, gbc);
        fila++;

        // Apellido
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        JLabel valorApellido = new JLabel(clienteActual.getApellido());
        valorApellido.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorApellido, gbc);
        fila++;

        // Identificación
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Identificación:"), gbc);

        gbc.gridx = 1;
        JLabel valorIdentificacion = new JLabel(clienteActual.getIdentificacion());
        valorIdentificacion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorIdentificacion, gbc);
        fila++;

        // Edad
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Edad:"), gbc);

        gbc.gridx = 1;
        JLabel valorEdad = new JLabel(String.valueOf(clienteActual.getEdad()));
        valorEdad.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorEdad, gbc);
        fila++;

        // Estatura
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Estatura:"), gbc);

        gbc.gridx = 1;
        JLabel valorEstatura = new JLabel(clienteActual.getEstatura() + " cm");
        valorEstatura.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorEstatura, gbc);
        fila++;

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }

    private void mostrarAtraccionesDisponibles() {
        panelDerecho.removeAll();
        panelDerecho.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Atracciones Disponibles");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        panelDerecho.add(titulo, BorderLayout.NORTH);

        String[] columnNames = {"Nombre", "Tipo", "Restricciones"};
        Object[][] data = parque.getAtracciones().stream()
            .map(a -> new Object[]{
                a.getNombre(),
                a.getClass().getSimpleName(),
                a.getRestricciones().toString(),
            })
            .toArray(Object[][]::new);

        JTable tabla = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }
}