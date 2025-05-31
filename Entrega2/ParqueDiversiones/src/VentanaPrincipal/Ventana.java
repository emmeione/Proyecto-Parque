package VentanaPrincipal;

import javax.swing.*;
import Administrador.Parque;
import LugarDeServicio.Taquilla;
import Persistencia.PersistenciaUsuarios;
import Roles.Cajero;
import Usuarios.Cliente;
import Usuarios.Empleado;

import java.awt.*;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {

    private JPanel panelOpciones;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JRadioButton rdbCliente, rdbAdministrador, rdbEmpleado;
    private JTextField txtEmail;
    private JPasswordField txtContrasena;

    public Ventana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Parque de diversiones");
        getContentPane().setBackground(new Color(255, 181, 192));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(255, 181, 192));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // Imagen
        ImageIcon icon = new ImageIcon("./imagenes/parque-de-atracciones.png");
        JLabel labelImagen = new JLabel(icon);
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelImagen.setBackground(new Color(255, 181, 192));
        panelImagen.add(labelImagen);

        // Campos de texto (atributos de clase, no locales)
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 30));
        lblEmail.setForeground(Color.WHITE);
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 28));
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 30));
        lblContrasena.setForeground(Color.WHITE);
        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 28));
        txtContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Panel de opciones
        JPanel panelOpcionesContenedor = new JPanel();
        panelOpcionesContenedor.setBackground(new Color(255, 181, 192));
        panelOpcionesContenedor.setLayout(new BoxLayout(panelOpcionesContenedor, BoxLayout.Y_AXIS));

        // Radio buttons
        rdbCliente = new JRadioButton("Cliente");
        rdbAdministrador = new JRadioButton("Administrador");
        rdbEmpleado = new JRadioButton("Empleado");

        JRadioButton[] radios = {rdbCliente, rdbAdministrador, rdbEmpleado};
        for (JRadioButton rdb : radios) {
            rdb.setFont(rdb.getFont().deriveFont(29f));
            rdb.setBackground(new Color(255, 181, 192));
            rdb.setForeground(Color.WHITE);
        }

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rdbCliente);
        grupo.add(rdbAdministrador);
        grupo.add(rdbEmpleado);

        JPanel panelSeleccion = new JPanel(new FlowLayout());
        panelSeleccion.setBackground(new Color(255, 181, 192));
        JLabel lblSeleccionRol = new JLabel("Selecciona tu rol: ");
        lblSeleccionRol.setFont(new Font("Arial", Font.BOLD, 40));
        lblSeleccionRol.setForeground(Color.WHITE);

        panelSeleccion.add(lblSeleccionRol);
        panelSeleccion.add(rdbCliente);
        panelSeleccion.add(rdbAdministrador);
        panelSeleccion.add(rdbEmpleado);

        panelOpciones = new JPanel();
        panelOpciones.setBackground(new Color(255, 181, 192));
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));

        // Botón Iniciar Sesión
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 28));
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciarSesion.setBackground(Color.WHITE);
        btnIniciarSesion.setForeground(Color.BLACK);
        btnIniciarSesion.setBorderPainted(false);
        btnIniciarSesion.setOpaque(true);
        btnIniciarSesion.setMaximumSize(new Dimension(300, 50));

        // Botón Registrarse
        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 28));
        btnRegistrarse.setForeground(Color.BLACK);
        btnRegistrarse.setBackground(Color.WHITE);
        btnRegistrarse.setOpaque(true);
        btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegistrarse.setMaximumSize(new Dimension(300, 50));

        // Agregar componentes
        panelOpcionesContenedor.add(panelSeleccion);
        panelOpcionesContenedor.add(Box.createRigidArea(new Dimension(0, 20)));
        panelOpcionesContenedor.add(lblEmail);
        panelOpcionesContenedor.add(txtEmail);
        panelOpcionesContenedor.add(Box.createRigidArea(new Dimension(0, 20)));
        panelOpcionesContenedor.add(lblContrasena);
        panelOpcionesContenedor.add(txtContrasena);
        panelOpcionesContenedor.add(Box.createRigidArea(new Dimension(0, 40)));
        panelOpcionesContenedor.add(btnIniciarSesion);
        panelOpcionesContenedor.add(Box.createRigidArea(new Dimension(0, 20)));
        panelOpcionesContenedor.add(btnRegistrarse);

        // Agregar paneles al contenedor principal
        panelPrincipal.add(panelImagen, BorderLayout.WEST);
        panelPrincipal.add(panelOpcionesContenedor, BorderLayout.CENTER);
        add(panelPrincipal);

        // Listeners para los radio buttons
        ActionListener listener = e -> actualizarOpciones();
        rdbCliente.addActionListener(listener);
        rdbAdministrador.addActionListener(listener);
        rdbEmpleado.addActionListener(listener);

        // Inicialmente ocultos
        btnIniciarSesion.setVisible(false);
        btnRegistrarse.setVisible(false);

        setVisible(true);
    }

    private void actualizarOpciones() {
        if (rdbCliente.isSelected()) {
            btnIniciarSesion.setVisible(true);
            btnRegistrarse.setVisible(true);
        } else if (rdbEmpleado.isSelected() || rdbAdministrador.isSelected()) {
            btnIniciarSesion.setVisible(true);
            btnRegistrarse.setVisible(false);
        } else {
            btnIniciarSesion.setVisible(false);
            btnRegistrarse.setVisible(false);
        }
    }

    private void iniciarSesion() {
        String email = txtEmail.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (rdbCliente.isSelected()) {
            iniciarComoCliente(email, contrasena);
        } else if (rdbEmpleado.isSelected()) {
            iniciarComoEmpleado(email, contrasena);
        } else if (rdbAdministrador.isSelected()) {
            iniciarComoAdministrador(email, contrasena);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un rol.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarComoCliente(String email, String contrasena) {
        try {
            Parque parque = new Parque();
            PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);
            Cliente cliente = new Cliente("Beatriz", "Pinzón", "10308437824", email, contrasena, 25, 170);
            parque.agregarCliente(cliente);
            persistencia.guardarUsuarios();

            JOptionPane.showMessageDialog(this, "Sesión iniciada como cliente.");
            new VentanaCliente(cliente);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error iniciando como cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarComoEmpleado(String email, String contrasena) {
        try {
            Parque parque = new Parque();
            PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);
            Empleado empleado = new Empleado("Nombre", "Apellido", "123456", email, contrasena,
                    new Cajero(), false, false, false, new Taquilla("Taquilla X", 5));
            parque.agregarEmpleado(empleado);
            persistencia.guardarUsuarios("");

            JOptionPane.showMessageDialog(this, "Sesión iniciada como empleado.");
            new VentanaEmpleado(empleado);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error iniciando como empleado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarComoAdministrador(String email, String contrasena) {
        try {
            Parque parque = new Parque();
            PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);
            JOptionPane.showMessageDialog(this, "Sesión iniciada como administrador.");
            new VentanaAdministrador();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error iniciando como administrador: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Ventana();
    }
}
