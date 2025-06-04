package VentanaPrincipal;

import javax.swing.*;

import Administrador.Parque;
import Persistencia.PersistenciaUsuarios;
import Usuarios.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistroCliente extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtIdentificacion;
    private JTextField txtEmail;
    private JPasswordField txtContrasena;
    private JSpinner spinnerEdad;
    private JSpinner spinnerEstatura;
    private Ventana ventanaPrincipal;

    public VentanaRegistroCliente(Ventana ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        setTitle("Registro de Cliente");
        setSize(500, 600);
        setLocationRelativeTo(ventanaPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel(new GridLayout(0, 1, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(255, 181, 192));

        // Campos del formulario
        panelPrincipal.add(crearCampo("Nombre:", txtNombre = new JTextField()));
        panelPrincipal.add(crearCampo("Apellido:", txtApellido = new JTextField()));
        panelPrincipal.add(crearCampo("Identificación:", txtIdentificacion = new JTextField()));
        panelPrincipal.add(crearCampo("Email:", txtEmail = new JTextField()));
        panelPrincipal.add(crearCampo("Contraseña:", txtContrasena = new JPasswordField()));
        
        // Edad
        JPanel panelEdad = new JPanel(new BorderLayout());
        panelEdad.setBackground(new Color(255, 181, 192));
        panelEdad.add(new JLabel("Edad:"), BorderLayout.WEST);
        spinnerEdad = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));
        panelEdad.add(spinnerEdad, BorderLayout.CENTER);
        panelPrincipal.add(panelEdad);
        
        // Estatura
        JPanel panelEstatura = new JPanel(new BorderLayout());
        panelEstatura.setBackground(new Color(255, 181, 192));
        panelEstatura.add(new JLabel("Estatura (cm):"), BorderLayout.WEST);
        spinnerEstatura = new JSpinner(new SpinnerNumberModel(170, 50, 250, 1));
        panelEstatura.add(spinnerEstatura, BorderLayout.CENTER);
        panelPrincipal.add(panelEstatura);
        
        // Botón de registro
        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarNuevoCliente();
            }
        });
        panelPrincipal.add(btnRegistrar);
        
        add(panelPrincipal);
        setVisible(true);
    }

    private JPanel crearCampo(String etiqueta, JComponent componente) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 181, 192));
        panel.add(new JLabel(etiqueta), BorderLayout.WEST);
        componente.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(componente, BorderLayout.CENTER);
        return panel;
    }

    private void registrarNuevoCliente() {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String identificacion = txtIdentificacion.getText();
            String email = txtEmail.getText();
            String contrasena = new String(txtContrasena.getPassword());
            int edad = (Integer) spinnerEdad.getValue();
            double estatura = (Integer) spinnerEstatura.getValue();
            
            if (nombre.isEmpty() || apellido.isEmpty() || identificacion.isEmpty() || 
                email.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Parque parque = new Parque();
            PersistenciaUsuarios persistencia = new PersistenciaUsuarios(parque);
            Cliente cliente = new Cliente(nombre, apellido, identificacion, email, contrasena, edad, estatura);
            parque.agregarCliente(cliente);
            persistencia.guardarUsuarios("./data/usuarios.txt");
            
            JOptionPane.showMessageDialog(this, "Registro exitoso! Ahora puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}