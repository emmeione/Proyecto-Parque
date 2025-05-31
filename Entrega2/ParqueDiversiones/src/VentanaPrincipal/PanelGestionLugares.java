package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import LugarDeServicio.Taquilla;
import LugarDeServicio.Tienda;

import java.awt.*;
import java.io.IOException;

public class PanelGestionLugares extends JPanel {
    private Parque parque;
    
    public PanelGestionLugares(Parque parque) {
        this.parque = parque;
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
        JLabel titulo = new JLabel("Gestión de Lugares");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, gbc);

        // Panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        gbc.gridy = fila++; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Pestaña 1: Crear Lugar
        JPanel panelCrear = crearPanelCrearLugar();
        tabbedPane.addTab("Crear Lugar", panelCrear);
        
        // Pestaña 2: Ver Lugares
        JPanel panelVer = crearPanelVerLugares();
        tabbedPane.addTab("Ver Lugares", panelVer);
        
        add(tabbedPane, gbc);
    }
    
    private JPanel crearPanelCrearLugar() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        int fila = 0;

        // Título
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Crear nuevo lugar");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titulo, gbc);

        // Tipo de lugar
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Tipo de lugar:"), gbc);
        gbc.gridx = 1;
        String[] tipos = { "Cafetería", "Tienda", "Taquilla" };
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        panel.add(comboTipo, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        JTextField campoNombre = new JTextField(15);
        panel.add(campoNombre, gbc);

        // Capacidad
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        JTextField campoCapacidad = new JTextField(15);
        panel.add(campoCapacidad, gbc);

        // Zona
        gbc.gridx = 0; gbc.gridy = fila++;
        panel.add(new JLabel("Zona:"), gbc);
        gbc.gridx = 1;
        String[] zonas = { "CENTRAL", "NORTE", "SUR", "ESTE", "OESTE" };
        JComboBox<String> comboZona = new JComboBox<>(zonas);
        panel.add(comboZona, gbc);

        // Botón Guardar
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton btnGuardar = new JButton("Guardar lugar");
        panel.add(btnGuardar, gbc);

        // Acción del botón Guardar
        btnGuardar.addActionListener(e -> {
            try {
                // Validar y obtener datos
                String tipo = (String) comboTipo.getSelectedItem();
                String nombre = campoNombre.getText().trim();
                int capacidad = Integer.parseInt(campoCapacidad.getText().trim());
                int zona = convertirZonaACodigo((String) comboZona.getSelectedItem());

                // Crear lugar según el tipo
                Lugar nuevoLugar = crearLugarPorTipo(tipo, nombre, capacidad, zona);
                if (nuevoLugar == null) {
                    mostrarError("Tipo de lugar no válido");
                    return;
                }

                // Guardar en el sistema
                guardarLugar(nuevoLugar);
                
                // Limpiar campos
                limpiarCampos(campoNombre, campoCapacidad);
                
                // Mostrar éxito
                mostrarExito("Lugar creado exitosamente!");

            } catch (NumberFormatException ex) {
                mostrarError("La capacidad debe ser un número válido");
            } catch (IOException ex) {
                mostrarError("Error al guardar el lugar: " + ex.getMessage());
            } catch (Exception ex) {
                mostrarError("Error: " + ex.getMessage());
            }
        });
        
        return panel;
    }
    
    private int convertirZonaACodigo(String zona) {
        switch (zona) {
            case "NORTE": return Lugar.ZONA_NORTE;
            case "SUR": return Lugar.ZONA_SUR;
            case "ESTE": return Lugar.ZONA_ESTE;
            case "OESTE": return Lugar.ZONA_OESTE;
            default: return Lugar.ZONA_CENTRAL;
        }
    }
    
    private Lugar crearLugarPorTipo(String tipo, String nombre, int capacidad, int zona) {
        switch (tipo) {
            case "Cafetería": return new Cafeteria(nombre, capacidad, zona);
            case "Tienda": return new Tienda(nombre, capacidad, zona);
            case "Taquilla": return new Taquilla(nombre, capacidad, zona);
            default: return null;
        }
    }
    
    private void guardarLugar(Lugar lugar) throws IOException {
        parque.agregarLugar(lugar);
        parque.guardarAtraccionesEnArchivo("./data/atracciones.txt");
    }
    
    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
    
    private JPanel crearPanelVerLugares() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Modelo de tabla
        String[] columnas = {"Nombre", "Tipo", "Capacidad", "Zona", "Ocupación"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaLugares = new JTable(modelo);
        tablaLugares.setRowHeight(25);
        tablaLugares.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(tablaLugares);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> actualizarTablaLugares(modelo));
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        // Carga inicial
        actualizarTablaLugares(modelo);
        
        return panel;
    }
    
    private void actualizarTablaLugares(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        
        for (Lugar lugar : parque.getLugares()) {
            String zonaStr = convertirCodigoAZona(lugar.getZona());
            modelo.addRow(new Object[]{
                lugar.getNombre(),
                lugar.getClass().getSimpleName(),
                lugar.getCapacidadMaxima(),
                zonaStr,
                lugar.getCapacidadMaxima()
            });
        }
    }
    
    private String convertirCodigoAZona(int codigoZona) {
        switch (codigoZona) {
            case Lugar.ZONA_NORTE: return "NORTE";
            case Lugar.ZONA_SUR: return "SUR";
            case Lugar.ZONA_ESTE: return "ESTE";
            case Lugar.ZONA_OESTE: return "OESTE";
            default: return "CENTRAL";
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}