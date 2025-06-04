package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import LugarDeServicio.Lugar;
import Persistencia.PersistenciaUsuarios;
import Roles.AdministradorR;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.Rol;
import Usuarios.Empleado;

import java.awt.*;
import java.io.IOException;

public class PanelGestionEmpleados extends JPanel {
    private Parque parque;
    private PersistenciaUsuarios persistencia;
    private String rutaArchivoUsuarios;
    
    public PanelGestionEmpleados(Parque parque, PersistenciaUsuarios persistencia, String rutaArchivoUsuarios) {
        this.parque = parque;
        this.persistencia = persistencia;
        this.rutaArchivoUsuarios = rutaArchivoUsuarios;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int fila = 0;

        // Título
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Gestión de Empleados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, gbc);

        // Panel de pestañas para Crear/Ver empleados
        JTabbedPane tabbedPane = new JTabbedPane();
        gbc.gridy = fila++; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Pestaña 1: Crear Empleado
        JPanel panelCrear = crearPanelCrearEmpleado();
        tabbedPane.addTab("Crear Empleado", panelCrear);
        
        // Pestaña 2: Ver Empleados
        JPanel panelVer = crearPanelVerEmpleados();
        tabbedPane.addTab("Ver Empleados", panelVer);
        
        add(tabbedPane, gbc);
    }
    
    private JPanel crearPanelCrearEmpleado() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        int fila = 0;
        
        // Campos de texto
        gbc.gridwidth = 1;

        // Nombre
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        JTextField campoNombre = new JTextField(15);
        panel.add(campoNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        JTextField campoApellido = new JTextField(15);
        panel.add(campoApellido, gbc);

        // Identificación
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1;
        JTextField campoIdentificacion = new JTextField(15);
        panel.add(campoIdentificacion, gbc);

        // Login
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Login:"), gbc);
        gbc.gridx = 1;
        JTextField campoLogin = new JTextField(15);
        panel.add(campoLogin, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField campoPassword = new JPasswordField(15);
        panel.add(campoPassword, gbc);

        // Rol
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        String[] roles = {
            "Cajero", "Cocinero", "Operador de Atracción", 
            "Administrador", "Servicio General"
        };
        JComboBox<String> comboRol = new JComboBox<>(roles);
        panel.add(comboRol, gbc);

        // Nivel de riesgo (solo para operador)
        gbc.gridx = 0; gbc.gridy = fila++;
        JLabel lblNivelRiesgo = new JLabel("Nivel de riesgo (1-3):");
        lblNivelRiesgo.setVisible(false);
        panel.add(lblNivelRiesgo, gbc);

        gbc.gridx = 1;
        JTextField campoRiesgo = new JTextField(15);
        campoRiesgo.setVisible(false);
        panel.add(campoRiesgo, gbc);

        comboRol.addActionListener(e -> {
            boolean mostrar = comboRol.getSelectedItem().equals("Operador de Atracción");
            lblNivelRiesgo.setVisible(mostrar);
            campoRiesgo.setVisible(mostrar);
            panel.revalidate();
            panel.repaint();
        });

        // Capacitaciones
        gbc.gridwidth = 2;
        gbc.gridx = 0;

        // Capacitado en alimentos
        gbc.gridy = fila++;
        JCheckBox chkAlimentos = new JCheckBox("¿Capacitado en manipulación de alimentos?");
        panel.add(chkAlimentos, gbc);

        // Capacitado en alto riesgo
        gbc.gridy = fila++;
        JCheckBox chkAltoRiesgo = new JCheckBox("¿Capacitado para atracciones de alto riesgo?");
        panel.add(chkAltoRiesgo, gbc);

        // Capacitado en medio riesgo
        gbc.gridy = fila++;
        JCheckBox chkMedioRiesgo = new JCheckBox("¿Capacitado para atracciones de medio riesgo?");
        panel.add(chkMedioRiesgo, gbc);

        // Botón Guardar
        gbc.gridy = fila++;
        gbc.fill = GridBagConstraints.CENTER;
        JButton btnGuardar = new JButton("Guardar Empleado");
        panel.add(btnGuardar, gbc);

        // Acción del botón Guardar
        btnGuardar.addActionListener(e -> {
            try {
                // Obtener valores de los campos
                String nombre = campoNombre.getText();
                String apellido = campoApellido.getText();
                String identificacion = campoIdentificacion.getText();
                String login = campoLogin.getText();
                String password = new String(campoPassword.getPassword());

                // Crear rol según selección
                Rol rol = crearRolSegunSeleccion(comboRol, campoRiesgo);

                // Obtener capacidades
                boolean capacitadoAlimentos = chkAlimentos.isSelected();
                boolean capacitadoAltoRiesgo = chkAltoRiesgo.isSelected();
                boolean capacitadoMedioRiesgo = chkMedioRiesgo.isSelected();

                // Asignar lugar (opcional)
                Lugar lugarAsignado = asignarLugarOpcional();

                // Crear y guardar empleado
                crearYGuardarEmpleado(
                    nombre, apellido, identificacion, login, password,
                    rol, capacitadoAlimentos, capacitadoAltoRiesgo, 
                    capacitadoMedioRiesgo, lugarAsignado
                );

                // Limpiar campos
                limpiarCampos(
                    campoNombre, campoApellido, campoIdentificacion, 
                    campoLogin, campoPassword, comboRol, campoRiesgo,
                    chkAlimentos, chkAltoRiesgo, chkMedioRiesgo
                );

            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese un nivel de riesgo válido (1-3) para Operador de Atracción.");
            } catch (Exception ex) {
                mostrarError("Error al crear empleado: " + ex.getMessage());
            }
        });
        
        return panel;
    }
    
    private Rol crearRolSegunSeleccion(JComboBox<String> comboRol, JTextField campoRiesgo) {
        String rolSeleccionado = (String) comboRol.getSelectedItem();
        switch (rolSeleccionado) {
            case "Cajero": return new Cajero();
            case "Cocinero": return new Cocinero();
            case "Operador de Atracción": 
                int nivelRiesgo = Integer.parseInt(campoRiesgo.getText());
                return new Roles.OperadorAtraccion(nivelRiesgo);
            case "Administrador": return new AdministradorR();
            case "Servicio General":
            default: return new Roles.ServicioGeneral();
        }
    }
    
    private Lugar asignarLugarOpcional() {
        return (!parque.getLugares().isEmpty()) ? parque.getLugares().get(0) : null;
    }
    
    private void crearYGuardarEmpleado(
    	    String nombre, String apellido, String identificacion, 
    	    String login, String password, Rol rol,
    	    boolean capacitadoAlimentos, boolean capacitadoAltoRiesgo,
    	    boolean capacitadoMedioRiesgo, Lugar lugarAsignado
    	) {
    	    Empleado nuevoEmpleado = new Empleado(
    	        nombre, apellido, identificacion, login, password,
    	        rol, capacitadoAlimentos, capacitadoAltoRiesgo,
    	        capacitadoMedioRiesgo, lugarAsignado
    	    );

    	    parque.registrarEmpleado(nuevoEmpleado);

    	    try {
    	        persistencia.guardarUsuarios(rutaArchivoUsuarios);
    	        mostrarExito("Empleado creado y guardado exitosamente.");
    	    } catch (IOException e) {
    	        mostrarError("Error al guardar el archivo de usuarios: " + e.getMessage());
    	    }
    	}
    
    

    
    private void limpiarCampos(
        JTextField nombre, JTextField apellido, JTextField identificacion,
        JTextField login, JPasswordField password, JComboBox<String> comboRol,
        JTextField campoRiesgo, JCheckBox chkAlimentos, 
        JCheckBox chkAltoRiesgo, JCheckBox chkMedioRiesgo
    ) {
        nombre.setText("");
        apellido.setText("");
        identificacion.setText("");
        login.setText("");
        password.setText("");
        comboRol.setSelectedIndex(0);
        campoRiesgo.setText("");
        campoRiesgo.setVisible(false);
        chkAlimentos.setSelected(false);
        chkAltoRiesgo.setSelected(false);
        chkMedioRiesgo.setSelected(false);
    }
    
    private JPanel crearPanelVerEmpleados() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Modelo de tabla para empleados
        String[] columnas = {"Nombre", "Apellido", "Identificación", "Rol", "Capacitaciones"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaEmpleados = new JTable(modelo);
        tablaEmpleados.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        
        // Botón para actualizar la lista
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> actualizarTablaEmpleados(modelo));
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        // Cargar datos iniciales
        actualizarTablaEmpleados(modelo);
        
        return panel;
    }

    private void actualizarTablaEmpleados(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        
        for (Empleado empleado : parque.getEmpleados()) {
            StringBuilder capacitaciones = new StringBuilder();

            
            String capacidades = capacitaciones.toString();
            if (capacidades.endsWith(", ")) {
                capacidades = capacidades.substring(0, capacidades.length() - 2);
            }
            
            modelo.addRow(new Object[]{
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getIdentificacion(),
                empleado.getRol().getClass().getSimpleName(),
                capacidades
            });
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}