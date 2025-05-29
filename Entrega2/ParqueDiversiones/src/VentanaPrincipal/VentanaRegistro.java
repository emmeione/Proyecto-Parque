package VentanaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaRegistro extends JFrame {

    public VentanaRegistro() {
        setTitle("Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        Color rosaPastel = new Color(255, 181, 192);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(rosaPastel);

        JLabel titulo = new JLabel("Crea tu cuenta", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(64, 43, 150));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        formPanel.setOpaque(false); // Para heredar el fondo rosado

        // Campos del formulario
        formPanel.add(new JTextField("Nombres"));
        formPanel.add(new JTextField("Apellidos"));
        formPanel.add(new JTextField("Número de documento"));
        formPanel.add(new JTextField("Tipo de documento"));
        formPanel.add(new JTextField("E-mail"));
        formPanel.add(new JTextField("Confirma tu E-mail"));
        formPanel.add(new JPasswordField("Crea tu contraseña"));
        formPanel.add(new JPasswordField("Confirma tu contraseña"));
        formPanel.add(new JTextField("Fecha nacimiento (yyyy-mm-dd)"));

        // Botón de registro
        JButton registrarse = new JButton("Regístrate");
        registrarse.setBackground(new Color(255, 102, 153)); // Rosa fuerte
        registrarse.setForeground(Color.WHITE);
        registrarse.setFont(new Font("SansSerif", Font.BOLD, 16));
        registrarse.setFocusPainted(false);

        // Acción al presionar el botón
        registrarse.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(
                this,
                "Registro exitoso, regrese a la página principal e inicie sesión.",
                "Registro completo",
                JOptionPane.INFORMATION_MESSAGE
            );
            this.dispose(); // Cierra la ventana actual

            // Abre la ventana principal real
            SwingUtilities.invokeLater(() -> new Ventana().setVisible(true));
        });

        JLabel login = new JLabel("Si tienes una cuenta, Ingresa aquí", SwingConstants.CENTER);
        login.setForeground(Color.BLUE);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.setOpaque(false);
        verticalBox.add(titulo);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(formPanel);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(registrarse);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(login);

        mainPanel.add(verticalBox, BorderLayout.CENTER);
        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaRegistro::new);
    }
}
