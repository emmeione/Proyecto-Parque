package VentanaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaCliente extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel panelContenedor = new JPanel(cardLayout);

    // Paneles
    JPanel panelInicioSesion;
    JPanel panelRegistro;
    JPanel panelMenuCliente;
    JPanel panelComprarTiquete;
    JPanel panelVerTiquetes;
    JPanel panelVerAtracciones;
    JPanel panelInformacionPersonal;

    public VentanaCliente() {
        setTitle("Parque de Atracciones - Cliente");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        crearPanelInicioSesion();
        crearPanelRegistro();
        crearPanelMenuCliente();
        crearPanelComprarTiquete();
        crearPanelVerTiquetes();
        crearPanelVerAtracciones();
        crearPanelInformacionPersonal();

        panelContenedor.add(panelInicioSesion, "InicioSesion");
        panelContenedor.add(panelRegistro, "Registro");
        panelContenedor.add(panelMenuCliente, "MenuCliente");
        panelContenedor.add(panelComprarTiquete, "ComprarTiquete");
        panelContenedor.add(panelVerTiquetes, "VerTiquetes");
        panelContenedor.add(panelVerAtracciones, "VerAtracciones");
        panelContenedor.add(panelInformacionPersonal, "InformacionPersonal");

        add(panelContenedor);
        cardLayout.show(panelContenedor, "InicioSesion");
    }

    private void crearPanelInicioSesion() {
        panelInicioSesion = new JPanel(new BorderLayout());
        JPanel botones = new JPanel();

        JLabel titulo = new JLabel("Bienvenido al Parque de Atracciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelInicioSesion.add(titulo, BorderLayout.NORTH);

        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        JButton btnRegistrarse = new JButton("Registrarse");
        JButton btnSalir = new JButton("Salir");

        botones.add(btnIniciarSesion);
        botones.add(btnRegistrarse);
        botones.add(btnSalir);

        panelInicioSesion.add(botones, BorderLayout.CENTER);

        // Acciones (solo cambio de panel)
        btnIniciarSesion.addActionListener(e -> cardLayout.show(panelContenedor, "MenuCliente"));
        btnRegistrarse.addActionListener(e -> cardLayout.show(panelContenedor, "Registro"));
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void crearPanelRegistro() {
        panelRegistro = new JPanel(new GridLayout(6, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panelRegistro.add(new JLabel("Nombre:"));
        panelRegistro.add(new JTextField());

        panelRegistro.add(new JLabel("ID:"));
        panelRegistro.add(new JTextField());

        panelRegistro.add(new JLabel("Email:"));
        panelRegistro.add(new JTextField());

        panelRegistro.add(new JLabel("Contraseña:"));
        panelRegistro.add(new JPasswordField());

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver");

        panelRegistro.add(btnRegistrar);
        panelRegistro.add(btnVolver);

        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "InicioSesion"));
        btnRegistrar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Registro simulado"));

    }

    private void crearPanelMenuCliente() {
        panelMenuCliente = new JPanel(new GridLayout(6, 1, 10, 10));
        panelMenuCliente.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        JLabel titulo = new JLabel("Menú Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelMenuCliente.add(titulo);

        JButton btnComprarTiquete = new JButton("Comprar Tiquete");
        JButton btnVerTiquetes = new JButton("Ver Mis Tiquetes");
        JButton btnVerAtracciones = new JButton("Ver Atracciones");
        JButton btnVerInfoPersonal = new JButton("Ver Información Personal");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelMenuCliente.add(btnComprarTiquete);
        panelMenuCliente.add(btnVerTiquetes);
        panelMenuCliente.add(btnVerAtracciones);
        panelMenuCliente.add(btnVerInfoPersonal);
        panelMenuCliente.add(btnCerrarSesion);

        btnComprarTiquete.addActionListener(e -> cardLayout.show(panelContenedor, "ComprarTiquete"));
        btnVerTiquetes.addActionListener(e -> cardLayout.show(panelContenedor, "VerTiquetes"));
        btnVerAtracciones.addActionListener(e -> cardLayout.show(panelContenedor, "VerAtracciones"));
        btnVerInfoPersonal.addActionListener(e -> cardLayout.show(panelContenedor, "InformacionPersonal"));
        btnCerrarSesion.addActionListener(e -> cardLayout.show(panelContenedor, "InicioSesion"));
    }

    private void crearPanelComprarTiquete() {
        panelComprarTiquete = new JPanel(new GridLayout(4, 1, 10, 10));
        panelComprarTiquete.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        JLabel titulo = new JLabel("Compra de Tiquete", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelComprarTiquete.add(titulo);

        JButton btnRegular = new JButton("Tiquete Regular");
        JButton btnFastPass = new JButton("FastPass");
        JButton btnVolver = new JButton("Volver");

        panelComprarTiquete.add(btnRegular);
        panelComprarTiquete.add(btnFastPass);
        panelComprarTiquete.add(btnVolver);

        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "MenuCliente"));
        btnRegular.addActionListener(e -> JOptionPane.showMessageDialog(this, "Comprar Tiquete Regular - no implementado"));
        btnFastPass.addActionListener(e -> JOptionPane.showMessageDialog(this, "Comprar FastPass - no implementado"));
    }

    private void crearPanelVerTiquetes() {
        panelVerTiquetes = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Mis Tiquetes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea areaTiquetes = new JTextArea("Listado de tiquetes...\n(Solo visual)");
        areaTiquetes.setEditable(false);

        JButton btnVolver = new JButton("Volver");

        panelVerTiquetes.add(titulo, BorderLayout.NORTH);
        panelVerTiquetes.add(new JScrollPane(areaTiquetes), BorderLayout.CENTER);
        panelVerTiquetes.add(btnVolver, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "MenuCliente"));
    }

    private void crearPanelVerAtracciones() {
        panelVerAtracciones = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Atracciones Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea areaAtracciones = new JTextArea("Listado de atracciones...\n(Solo visual)");
        areaAtracciones.setEditable(false);

        JButton btnVolver = new JButton("Volver");

        panelVerAtracciones.add(titulo, BorderLayout.NORTH);
        panelVerAtracciones.add(new JScrollPane(areaAtracciones), BorderLayout.CENTER);
        panelVerAtracciones.add(btnVolver, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "MenuCliente"));
    }

    private void crearPanelInformacionPersonal() {
        panelInformacionPersonal = new JPanel(new GridLayout(5, 1, 10, 10));
        panelInformacionPersonal.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JLabel titulo = new JLabel("Información Personal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelInformacionPersonal.add(titulo);

        panelInformacionPersonal.add(new JLabel("Nombre: (simulado)"));
        panelInformacionPersonal.add(new JLabel("Usuario: (simulado)"));
        panelInformacionPersonal.add(new JLabel("Tiquetes activos: (simulado)"));

        JButton btnVolver = new JButton("Volver");
        panelInformacionPersonal.add(btnVolver);

        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "MenuCliente"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCliente ventana = new VentanaCliente();
            ventana.setVisible(true);
        });
    }
}
