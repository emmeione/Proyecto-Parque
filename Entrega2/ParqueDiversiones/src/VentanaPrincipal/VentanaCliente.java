package VentanaPrincipal;

import javax.swing.*;
import java.awt.*;

public class VentanaCliente extends JFrame {

    private JPanel panelDerecho;

    public VentanaCliente() {
        setTitle("Usuario - Parque");
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo con botones (fondo rosita)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(235, 160, 185)); // mismo rosita que usaste
        panelIzquierdo.setLayout(new GridLayout(4, 1, 10, 10));

        String[] botones = {
            "Ver Perfil", "Comprar Tiquete", "Ver Atracciones Disponibles", "Cerrar Sesión"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.addActionListener(e -> cambiarPanelDerecho(texto));
            panelIzquierdo.add(boton);
        }

        // Panel derecho también con fondo rosita claro
        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(255, 190, 200)); // mismo rosita claro que usaste
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
        else if("Comprar Tiquete".equals(accion)) {
        	mostrarFormularioComprarTiquete();}
        else if("Cerrar Sesión".equals(accion)) {
            this.dispose();}
        

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

        // Título
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Perfil del Cliente");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelDerecho.add(titulo, gbc);

        // Simulación de cliente, reemplaza con tu objeto real
        class Cliente {
            String getNombre() { return "Juan"; }
            String getApellido() { return "Pérez"; }
            String getIdentificacion() { return "12345678"; }
            int getEdad() { return 30; }
            int getEstatura() { return 175; }
        }
        Cliente cliente = new Cliente();

        gbc.gridwidth = 1;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        JLabel valorNombre = new JLabel(cliente.getNombre());
        valorNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorNombre, gbc);
        fila++;

        // Apellido
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        JLabel valorApellido = new JLabel(cliente.getApellido());
        valorApellido.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorApellido, gbc);
        fila++;

        // Identificación
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Identificación:"), gbc);

        gbc.gridx = 1;
        JLabel valorIdentificacion = new JLabel(cliente.getIdentificacion());
        valorIdentificacion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorIdentificacion, gbc);
        fila++;

        // Edad
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Edad:"), gbc);

        gbc.gridx = 1;
        JLabel valorEdad = new JLabel(String.valueOf(cliente.getEdad()));
        valorEdad.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorEdad, gbc);
        fila++;

        // Estatura
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Estatura:"), gbc);

        gbc.gridx = 1;
        JLabel valorEstatura = new JLabel(cliente.getEstatura() + " cm");
        valorEstatura.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorEstatura, gbc);
        fila++;

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }
    
    private void mostrarFormularioComprarTiquete() {
        panelDerecho.removeAll();
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        // Título
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Comprar Tiquete");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelDerecho.add(titulo, gbc);

        // Etiqueta y ComboBox tipo de tiquete
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Tipo de Tiquete:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> comboTipos = new JComboBox<>(new String[] {
            "Básico - acceso general", 
            "VIP - acceso prioritario y descuentos", 
            "Premium - todo VIP + ilimitado + souvenirs"
        });
        comboTipos.setPreferredSize(new Dimension(250, 25));
        panelDerecho.add(comboTipos, gbc);
        fila++;

        // Etiqueta y ComboBox atracciones
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Atracción:"), gbc);

        gbc.gridx = 1;
        // Simulamos las atracciones disponibles, en tu código usa parque.getAtracciones()
        JComboBox<String> comboAtracciones = new JComboBox<>(new String[] {
            "Montaña Rusa", "Casa Embrujada", "Rueda de la Fortuna"
        });
        comboAtracciones.setPreferredSize(new Dimension(250, 25));
        panelDerecho.add(comboAtracciones, gbc);
        fila++;

        // Botón comprar (aún sin funcionalidad)
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnComprar = new JButton("Comprar Tiquete");
        panelDerecho.add(btnComprar, gbc);

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaCliente::new);
    }
}
