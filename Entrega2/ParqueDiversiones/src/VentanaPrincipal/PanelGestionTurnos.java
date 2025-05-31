package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import Persistencia.PersistenciaUsuarios;
import Usuarios.Empleado;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PanelGestionTurnos extends JPanel {
    private Parque parque;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;

    public PanelGestionTurnos(Parque parque) {
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
        JLabel titulo = new JLabel("Gestión de Turnos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titulo, gbc);

        // Panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        gbc.gridy = fila++; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Pestaña 1: Asignar Turno
        JPanel panelAsignar = crearPanelAsignarTurno();
        tabbedPane.addTab("Asignar Turno", panelAsignar);

        // Pestaña 2: Ver Turnos
        JPanel panelVer = crearPanelVerTurnos();
        tabbedPane.addTab("Ver Turnos", panelVer);

        add(tabbedPane, gbc);
    }

    private JPanel crearPanelAsignarTurno() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int fila = 0;

        // Título
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Asignar Nuevo Turno");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titulo, gbc);

        // ComboBox de empleados
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Empleado:"), gbc);

        gbc.gridx = 1;
        JComboBox<Empleado> comboEmpleados = new JComboBox<>();
        cargarEmpleados(comboEmpleados);
        panel.add(comboEmpleados, gbc);
        fila++;

        // Campo fecha
        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        JTextField campoFecha = new JTextField(15);
        panel.add(campoFecha, gbc);
        fila++;

        // Campo horario
        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Horario (ej. 08:00-12:00):"), gbc);

        gbc.gridx = 1;
        JTextField campoHorario = new JTextField(15);
        panel.add(campoHorario, gbc);
        fila++;

        // Botón asignar
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAsignar = new JButton("Asignar Turno");
        btnAsignar.addActionListener(e -> asignarTurno(
            (Empleado) comboEmpleados.getSelectedItem(),
            campoFecha.getText(),
            campoHorario.getText()
        ));
        panel.add(btnAsignar, gbc);

        return panel;
    }

    private JPanel crearPanelVerTurnos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Configurar modelo de tabla
        String[] columnas = {"Empleado", "Rol", "Fecha", "Horario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTurnos = new JTable(modeloTabla);
        tablaTurnos.setRowHeight(25);
        tablaTurnos.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> actualizarTablaTurnos());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizar);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        // Cargar datos iniciales
        actualizarTablaTurnos();

        return panel;
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

    private void asignarTurno(Empleado empleado, String fechaStr, String horario) {
        if (empleado == null) {
            mostrarError("Seleccione un empleado válido");
            return;
        }

        if (fechaStr.isEmpty() || horario.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            empleado.asignarTurno(fecha, horario);
            mostrarExito("Turno asignado correctamente");
            actualizarTablaTurnos();
            
            // Guardar cambios
            try {
                new PersistenciaUsuarios(parque).guardarUsuarios("./data/usuarios.txt");
            } catch (IOException ex) {
                mostrarError("Error al guardar: " + ex.getMessage());
            }
        } catch (DateTimeParseException ex) {
            mostrarError("Formato de fecha inválido. Use YYYY-MM-DD");
        } catch (Exception ex) {
            mostrarError("Error: " + ex.getMessage());
        }
    }

    private void actualizarTablaTurnos() {
        modeloTabla.setRowCount(0); // Limpiar tabla

        for (Empleado empleado : parque.getEmpleados()) {
            String turnos = empleado.revisarTurnos();
            if (!turnos.isEmpty()) {
                String[] lineasTurnos = turnos.split("\n");
                for (String linea : lineasTurnos) {
                    if (linea.contains(":")) {
                        String[] partes = linea.split(":");
                        String fecha = partes[0].trim();
                        String horario = partes.length > 1 ? partes[1].trim() : "";
                        
                        modeloTabla.addRow(new Object[]{
                            empleado.getNombre() + " " + empleado.getApellido(),
                            empleado.getRol().getClass().getSimpleName(),
                            fecha,
                            horario
                        });
                    }
                }
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}