package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import LugarDeServicio.Lugar;
import Persistencia.PersistenciaUsuarios;
import Usuarios.Empleado;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PanelAsignarEmpleado extends JPanel {
    private Parque parque;
    
    public PanelAsignarEmpleado(Parque parque) {
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
        JLabel titulo = new JLabel("Asignar Empleado a Lugar");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titulo, gbc);

        // ComboBox de empleados
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Empleado:"), gbc);

        gbc.gridx = 1;
        JComboBox<Empleado> comboEmpleados = new JComboBox<>();
        cargarEmpleados(comboEmpleados);
        add(comboEmpleados, gbc);
        fila++;

        // ComboBox de lugares
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Lugar:"), gbc);

        gbc.gridx = 1;
        JComboBox<Lugar> comboLugares = new JComboBox<>();
        cargarLugares(comboLugares);
        add(comboLugares, gbc);
        fila++;

        // Botón para ver lugares disponibles
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        JButton btnVerLugares = new JButton("Ver Lugares Disponibles");
        btnVerLugares.addActionListener(e -> mostrarTablaLugares());
        add(btnVerLugares, gbc);
        fila++;

        // Botón asignar
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAsignar = new JButton("Asignar Empleado");
        btnAsignar.addActionListener(e -> asignarEmpleado(
            (Empleado) comboEmpleados.getSelectedItem(),
            (Lugar) comboLugares.getSelectedItem()
        ));
        add(btnAsignar, gbc);
    }
    
    private void cargarEmpleados(JComboBox<Empleado> combo) {
        combo.removeAllItems();
        for (Empleado empleado : parque.getEmpleados()) {
            combo.addItem(empleado);
        }
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Empleado) {
                    Empleado emp = (Empleado) value;
                    setText(emp.getNombre() + " " + emp.getApellido() + " (" + emp.getRol().getClass().getSimpleName() + ")");
                }
                return this;
            }
        });
    }
    
    private void cargarLugares(JComboBox<Lugar> combo) {
        combo.removeAllItems();
        for (Lugar lugar : parque.getLugares()) {
            combo.addItem(lugar);
        }
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Lugar) {
                    Lugar lug = (Lugar) value;
                    setText(lug.getNombre() + " (" + lug.getClass().getSimpleName() + ")");
                }
                return this;
            }
        });
    }
    
    private void mostrarTablaLugares() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Lugares Disponibles");
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());
        
        String[] columnas = {"Nombre", "Tipo", "Capacidad", "Zona", "Empleados Asignados"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        for (Lugar lugar : parque.getLugares()) {
            modelo.addRow(new Object[]{
                lugar.getNombre(),
                lugar.getClass().getSimpleName(),
                lugar.getCapacidadMaxima(),
                convertirZonaATexto(lugar.getZona()),
                contarEmpleadosEnLugar(lugar)
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
    
    private String convertirZonaATexto(int zona) {
        switch (zona) {
            case Lugar.ZONA_NORTE: return "NORTE";
            case Lugar.ZONA_SUR: return "SUR";
            case Lugar.ZONA_ESTE: return "ESTE";
            case Lugar.ZONA_OESTE: return "OESTE";
            default: return "CENTRAL";
        }
    }
    
    private int contarEmpleadosEnLugar(Lugar lugar) {
        int count = 0;
        for (Empleado emp : parque.getEmpleados()) {
            if (lugar.equals(emp.getLugarDeServicio())) {
                count++;
            }
        }
        return count;
    }
    
    private void asignarEmpleado(Empleado empleado, Lugar lugar) {
        if (empleado == null || lugar == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado y un lugar válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        empleado.setLugarDeServicio(lugar);
        JOptionPane.showMessageDialog(this, 
            "Empleado " + empleado.getNombre() + " asignado a " + lugar.getNombre(), 
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