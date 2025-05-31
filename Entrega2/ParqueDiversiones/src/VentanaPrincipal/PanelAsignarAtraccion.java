package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import Atracciones.Atraccion;
import Atracciones.Mecanica;
import Persistencia.PersistenciaUsuarios;
import Usuarios.Empleado;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PanelAsignarAtraccion extends JPanel {
    private Parque parque;
    
    public PanelAsignarAtraccion(Parque parque) {
        this.parque = parque;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        // Título
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Asignar Atracción a Operador");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titulo, gbc);

        // ComboBox de empleados operadores
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Operador:"), gbc);

        gbc.gridx = 1;
        JComboBox<Empleado> comboOperadores = new JComboBox<>();
        cargarOperadores(comboOperadores);
        add(comboOperadores, gbc);
        fila++;

        // ComboBox de atracciones
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Atracción:"), gbc);

        gbc.gridx = 1;
        JComboBox<Atraccion> comboAtracciones = new JComboBox<>();
        cargarAtracciones(comboAtracciones);
        add(comboAtracciones, gbc);
        fila++;

        // Botón para ver asignaciones actuales
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        JButton btnVerAsignaciones = new JButton("Ver Asignaciones Actuales");
        btnVerAsignaciones.addActionListener(e -> mostrarAsignacionesActuales());
        add(btnVerAsignaciones, gbc);
        fila++;

        // Botón asignar
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAsignar = new JButton("Asignar Atracción");
        btnAsignar.addActionListener(e -> asignarAtraccion(
            (Empleado) comboOperadores.getSelectedItem(),
            (Atraccion) comboAtracciones.getSelectedItem()
        ));
        add(btnAsignar, gbc);
    }
    
    private void cargarOperadores(JComboBox<Empleado> combo) {
        combo.removeAllItems();
        List<Empleado> operadores = parque.getEmpleados().stream()
            .filter(e -> e.getRol() instanceof Roles.OperadorAtraccion)
            .collect(Collectors.toList());
        
        for (Empleado operador : operadores) {
            combo.addItem(operador);
        }
        
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Empleado) {
                    Empleado emp = (Empleado) value;
                    String texto = emp.getNombre() + " " + emp.getApellido();
                    if (emp.getAtraccionOperada() != null) {
                        texto += " (Asignado a: " + emp.getAtraccionOperada().getNombre() + ")";
                    }
                    setText(texto);
                }
                return this;
            }
        });
    }
    
    private void cargarAtracciones(JComboBox<Atraccion> combo) {
        combo.removeAllItems();
        for (Atraccion atraccion : parque.getAtracciones()) {
            combo.addItem(atraccion);
        }
        
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Atraccion) {
                    Atraccion atr = (Atraccion) value;
                    setText(atr.getNombre() + " (" + atr.getClass().getSimpleName() + ")");
                }
                return this;
            }
        });
    }
    
    private void mostrarAsignacionesActuales() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Asignaciones Actuales");
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());
        
        String[] columnas = {"Operador", "Rol", "Atracción Asignada", "Tipo Atracción", "Nivel Riesgo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        List<Empleado> operadores = parque.getEmpleados().stream()
            .filter(e -> e.getRol() instanceof Roles.OperadorAtraccion)
            .collect(Collectors.toList());
        
        for (Empleado operador : operadores) {
            Atraccion atr = operador.getAtraccionOperada();
            modelo.addRow(new Object[]{
                operador.getNombre() + " " + operador.getApellido(),
                operador.getRol().getClass().getSimpleName(),
                atr != null ? atr.getNombre() : "Sin asignar",
                atr != null ? atr.getClass().getSimpleName() : "N/A",
                atr instanceof Mecanica ? ((Mecanica) atr).getNivelDeRiesgo() : "N/A"
            });
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        dialog.add(btnCerrar, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void asignarAtraccion(Empleado operador, Atraccion atraccion) {
        if (operador == null || atraccion == null) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un operador y una atracción válidos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!(operador.getRol() instanceof Roles.OperadorAtraccion)) {
            JOptionPane.showMessageDialog(this, 
                "El empleado seleccionado no es un operador de atracción", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        operador.asignarAtraccion(atraccion);
        JOptionPane.showMessageDialog(this, 
            "Atracción " + atraccion.getNombre() + " asignada a " + 
            operador.getNombre() + " " + operador.getApellido(), 
            "Asignación exitosa", JOptionPane.INFORMATION_MESSAGE);
        
        try {
            new PersistenciaUsuarios(parque).guardarUsuarios("./data/usuarios.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar cambios: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}