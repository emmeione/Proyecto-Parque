package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import Persistencia.PersistenciaAtracciones;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PanelCrearAtraccion extends JPanel {
    private Parque parque;
    
    public PanelCrearAtraccion(Parque parque) {
        this.parque = parque;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Título
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Crear atracción");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titulo, gbc);

        // Tipo de atracción
        gbc.gridy = fila++; gbc.gridwidth = 2;
        add(new JLabel("Seleccione el tipo de atracción:"), gbc);
        
        JRadioButton rbMecanica = new JRadioButton("Mecánica", true);
        JRadioButton rbCultural = new JRadioButton("Cultural");
        ButtonGroup bgTipo = new ButtonGroup();
        bgTipo.add(rbMecanica);
        bgTipo.add(rbCultural);
        
        gbc.gridy = fila++; gbc.gridwidth = 1;
        add(rbMecanica, gbc);
        gbc.gridx = 1;
        add(rbCultural, gbc);
        gbc.gridx = 0;

        // Campos comunes
        gbc.gridy = fila++; gbc.gridwidth = 1;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        JTextField txtNombre = new JTextField(20);
        add(txtNombre, gbc);
        gbc.gridx = 0;

        gbc.gridy = fila++;
        add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        JTextField txtCapacidad = new JTextField(10);
        add(txtCapacidad, gbc);
        gbc.gridx = 0;

        gbc.gridy = fila++;
        add(new JLabel("Cupo mínimo encargados:"), gbc);
        gbc.gridx = 1;
        JTextField txtCupoMinimo = new JTextField(10);
        add(txtCupoMinimo, gbc);
        gbc.gridx = 0;

        // Restricción altura
        gbc.gridy = fila++; gbc.gridwidth = 1;
        add(new JLabel("Restricción altura:"), gbc);
        gbc.gridx = 1;
        JPanel panelAltura = new JPanel();
        JRadioButton rbAlturaSi = new JRadioButton("Sí");
        JRadioButton rbAlturaNo = new JRadioButton("No", true);
        ButtonGroup bgAltura = new ButtonGroup();
        bgAltura.add(rbAlturaSi);
        bgAltura.add(rbAlturaNo);
        panelAltura.add(rbAlturaSi);
        panelAltura.add(rbAlturaNo);
        add(panelAltura, gbc);
        gbc.gridx = 0;

        gbc.gridy = fila++;
        add(new JLabel("Altura mínima (cm):"), gbc);
        gbc.gridx = 1;
        JTextField txtAlturaMin = new JTextField(10);
        txtAlturaMin.setEnabled(false);
        add(txtAlturaMin, gbc);
        gbc.gridx = 0;

        rbAlturaSi.addActionListener(e -> txtAlturaMin.setEnabled(true));
        rbAlturaNo.addActionListener(e -> {
            txtAlturaMin.setEnabled(false);
            txtAlturaMin.setText("");
        });

        // Restricción edad
        gbc.gridy = fila++; gbc.gridwidth = 1;
        add(new JLabel("Restricción edad:"), gbc);
        gbc.gridx = 1;
        JPanel panelEdad = new JPanel();
        JRadioButton rbEdadSi = new JRadioButton("Sí");
        JRadioButton rbEdadNo = new JRadioButton("No", true);
        ButtonGroup bgEdad = new ButtonGroup();
        bgEdad.add(rbEdadSi);
        bgEdad.add(rbEdadNo);
        panelEdad.add(rbEdadSi);
        panelEdad.add(rbEdadNo);
        add(panelEdad, gbc);
        gbc.gridx = 0;

        gbc.gridy = fila++;
        add(new JLabel("Edad mínima:"), gbc);
        gbc.gridx = 1;
        JTextField txtEdadMin = new JTextField(10);
        txtEdadMin.setEnabled(false);
        add(txtEdadMin, gbc);
        gbc.gridx = 0;

        rbEdadSi.addActionListener(e -> txtEdadMin.setEnabled(true));
        rbEdadNo.addActionListener(e -> {
            txtEdadMin.setEnabled(false);
            txtEdadMin.setText("");
        });

        // Nivel de exclusividad
        gbc.gridy = fila++; gbc.gridwidth = 1;
        add(new JLabel("Nivel exclusividad:"), gbc);
        gbc.gridx = 1;
        JPanel panelExclusividad = new JPanel();
        JRadioButton rbDiamante = new JRadioButton("DIAMANTE");
        JRadioButton rbOro = new JRadioButton("ORO", true);
        JRadioButton rbFamiliar = new JRadioButton("FAMILIAR");
        ButtonGroup bgExclusividad = new ButtonGroup();
        bgExclusividad.add(rbDiamante);
        bgExclusividad.add(rbOro);
        bgExclusividad.add(rbFamiliar);
        panelExclusividad.add(rbDiamante);
        panelExclusividad.add(rbOro);
        panelExclusividad.add(rbFamiliar);
        add(panelExclusividad, gbc);
        gbc.gridx = 0;

        // Panel para campos específicos de Mecánica
        JPanel panelMecanica = new JPanel(new GridBagLayout());
        GridBagConstraints gbcMec = new GridBagConstraints();
        gbcMec.insets = new Insets(5, 5, 5, 5);
        gbcMec.anchor = GridBagConstraints.WEST;
        gbcMec.fill = GridBagConstraints.HORIZONTAL;
        
        int filaMec = 0;
        
        gbcMec.gridx = 0; gbcMec.gridy = filaMec++;
        panelMecanica.add(new JLabel("Altura máxima (cm):"), gbcMec);
        gbcMec.gridx = 1;
        JTextField txtAlturaMax = new JTextField(10);
        panelMecanica.add(txtAlturaMax, gbcMec);
        
        gbcMec.gridx = 0; gbcMec.gridy = filaMec++;
        panelMecanica.add(new JLabel("Peso máximo (kg):"), gbcMec);
        gbcMec.gridx = 1;
        JTextField txtPesoMax = new JTextField(10);
        panelMecanica.add(txtPesoMax, gbcMec);
        
        gbcMec.gridx = 0; gbcMec.gridy = filaMec++;
        panelMecanica.add(new JLabel("Peso mínimo (kg):"), gbcMec);
        gbcMec.gridx = 1;
        JTextField txtPesoMin = new JTextField(10);
        panelMecanica.add(txtPesoMin, gbcMec);
        
        gbcMec.gridx = 0; gbcMec.gridy = filaMec++;
        panelMecanica.add(new JLabel("Restricciones especiales:"), gbcMec);
        gbcMec.gridx = 1;
        JTextField txtRestricciones = new JTextField(20);
        panelMecanica.add(txtRestricciones, gbcMec);
        
        gbcMec.gridx = 0; gbcMec.gridy = filaMec++;
        panelMecanica.add(new JLabel("Nivel de riesgo (1-5):"), gbcMec);
        gbcMec.gridx = 1;
        JTextField txtNivelRiesgo = new JTextField(10);
        panelMecanica.add(txtNivelRiesgo, gbcMec);
        
        gbcMec.gridx = 0; gbcMec.gridy = filaMec++;
        panelMecanica.add(new JLabel("En temporada:"), gbcMec);
        gbcMec.gridx = 1;
        JPanel panelTemporada = new JPanel();
        JRadioButton rbTemporadaSi = new JRadioButton("Sí");
        JRadioButton rbTemporadaNo = new JRadioButton("No", true);
        ButtonGroup bgTemporada = new ButtonGroup();
        bgTemporada.add(rbTemporadaSi);
        bgTemporada.add(rbTemporadaNo);
        panelTemporada.add(rbTemporadaSi);
        panelTemporada.add(rbTemporadaNo);
        panelMecanica.add(panelTemporada, gbcMec);

        // Panel para campos específicos de Cultural
        JPanel panelCultural = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCult = new GridBagConstraints();
        gbcCult.insets = new Insets(5, 5, 5, 5);
        gbcCult.anchor = GridBagConstraints.WEST;
        gbcCult.fill = GridBagConstraints.HORIZONTAL;
        
        int filaCult = 0;
        
        gbcCult.gridx = 0; gbcCult.gridy = filaCult++;
        panelCultural.add(new JLabel("Tipo de espectáculo:"), gbcCult);
        gbcCult.gridx = 1;
        JTextField txtTipoEspectaculo = new JTextField(20);
        panelCultural.add(txtTipoEspectaculo, gbcCult);
        
        gbcCult.gridx = 0; gbcCult.gridy = filaCult++;
        panelCultural.add(new JLabel("Fecha (YYYY-MM-DD):"), gbcCult);
        gbcCult.gridx = 1;
        JTextField txtFecha = new JTextField(10);
        panelCultural.add(txtFecha, gbcCult);
        
        gbcCult.gridx = 0; gbcCult.gridy = filaCult++;
        panelCultural.add(new JLabel("Interactivo:"), gbcCult);
        gbcCult.gridx = 1;
        JPanel panelInteractivo = new JPanel();
        JRadioButton rbInteractivoSi = new JRadioButton("Sí");
        JRadioButton rbInteractivoNo = new JRadioButton("No", true);
        ButtonGroup bgInteractivo = new ButtonGroup();
        bgInteractivo.add(rbInteractivoSi);
        bgInteractivo.add(rbInteractivoNo);
        panelInteractivo.add(rbInteractivoSi);
        panelInteractivo.add(rbInteractivoNo);
        panelCultural.add(panelInteractivo, gbcCult);
        
        gbcCult.gridx = 0; gbcCult.gridy = filaCult++;
        panelCultural.add(new JLabel("Incluye animales:"), gbcCult);
        gbcCult.gridx = 1;
        JPanel panelAnimales = new JPanel();
        JRadioButton rbAnimalesSi = new JRadioButton("Sí");
        JRadioButton rbAnimalesNo = new JRadioButton("No", true);
        ButtonGroup bgAnimales = new ButtonGroup();
        bgAnimales.add(rbAnimalesSi);
        bgAnimales.add(rbAnimalesNo);
        panelAnimales.add(rbAnimalesSi);
        panelAnimales.add(rbAnimalesNo);
        panelCultural.add(panelAnimales, gbcCult);

        // Mostrar/ocultar paneles según tipo
        panelMecanica.setVisible(rbMecanica.isSelected());
        panelCultural.setVisible(!rbMecanica.isSelected());
        
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        add(panelMecanica, gbc);
        add(panelCultural, gbc);
        
        // Listeners para cambiar entre paneles
        rbMecanica.addActionListener(e -> {
            panelMecanica.setVisible(true);
            panelCultural.setVisible(false);
            revalidate();
            repaint();
        });
        
        rbCultural.addActionListener(e -> {
            panelMecanica.setVisible(false);
            panelCultural.setVisible(true);
            revalidate();
            repaint();
        });

        // Botón para crear atracción
        JButton btnCrear = new JButton("Crear Atracción");
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCrear, gbc);
        
        // Botón para ver catálogo
        JButton btnCatalogo = new JButton("Ver Catálogo");
        gbc.gridy = fila++;
        add(btnCatalogo, gbc);

        // ActionListener para el botón de catálogo
        btnCatalogo.addActionListener(e -> mostrarCatalogoAtracciones());
        
        // ActionListener para crear atracción
        btnCrear.addActionListener(e -> crearAtraccion(
            rbMecanica, rbCultural, txtNombre, txtCapacidad, txtCupoMinimo,
            rbAlturaSi, txtAlturaMin, rbEdadSi, txtEdadMin,
            rbDiamante, rbOro, rbFamiliar,
            panelMecanica, panelCultural,
            txtAlturaMax, txtPesoMax, txtPesoMin, txtRestricciones, txtNivelRiesgo, rbTemporadaSi,
            txtTipoEspectaculo, txtFecha, rbInteractivoSi, rbAnimalesSi
        ));
    }
    
    private void mostrarCatalogoAtracciones() {
        if(parque == null || parque.getAtracciones() == null) {
            JOptionPane.showMessageDialog(this, 
                "No hay atracciones disponibles o el parque no está inicializado", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        removeAll();
        setLayout(new BorderLayout());
        
        JLabel tituloCatalogo = new JLabel("Catálogo de Atracciones", SwingConstants.CENTER);
        tituloCatalogo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(tituloCatalogo, BorderLayout.NORTH);
        
        String[] columnas = {"Nombre", "Tipo", "Capacidad", "Exclusividad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        try {
            for (Atraccion atraccion : parque.getAtracciones()) {
                modelo.addRow(new Object[]{
                    atraccion.getNombre(),
                    atraccion instanceof Mecanica ? "Mecánica" : "Cultural",
                    atraccion.getCupoMaximoClientes(),
                    atraccion.getNivelExclusividad().toString()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar las atracciones: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton btnVolver = new JButton("Volver al formulario");
        btnVolver.addActionListener(ev -> {
            removeAll();
            initComponents();
            revalidate();
            repaint();
        });
        add(btnVolver, BorderLayout.SOUTH);
        
        revalidate();
        repaint();
    }
    
    private void crearAtraccion(JRadioButton rbMecanica, JRadioButton rbCultural, 
                              JTextField txtNombre, JTextField txtCapacidad, JTextField txtCupoMinimo,
                              JRadioButton rbAlturaSi, JTextField txtAlturaMin, 
                              JRadioButton rbEdadSi, JTextField txtEdadMin,
                              JRadioButton rbDiamante, JRadioButton rbOro, JRadioButton rbFamiliar,
                              JPanel panelMecanica, JPanel panelCultural,
                              JTextField txtAlturaMax, JTextField txtPesoMax, JTextField txtPesoMin, 
                              JTextField txtRestricciones, JTextField txtNivelRiesgo, JRadioButton rbTemporadaSi,
                              JTextField txtTipoEspectaculo, JTextField txtFecha, 
                              JRadioButton rbInteractivoSi, JRadioButton rbAnimalesSi) {
        try {
            // Validar campos comunes
            if (txtNombre.getText().isEmpty() || txtCapacidad.getText().isEmpty() || txtCupoMinimo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener valores comunes
            String nombre = txtNombre.getText();
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            int cupoMinimo = Integer.parseInt(txtCupoMinimo.getText());

            // Crear lista de restricciones
            ArrayList<Restriccion> restricciones = new ArrayList<>();
            
            if (rbAlturaSi.isSelected() && !txtAlturaMin.getText().isEmpty()) {
                int alturaMin = Integer.parseInt(txtAlturaMin.getText());
                restricciones.add(new RestriccionAltura(alturaMin));
            }
            
            if (rbEdadSi.isSelected() && !txtEdadMin.getText().isEmpty()) {
                int edadMin = Integer.parseInt(txtEdadMin.getText());
                restricciones.add(new RestriccionEdad(edadMin));
            }

            // Obtener nivel de exclusividad
            NivelExclusividad nivelExclusividad;
            if (rbDiamante.isSelected()) {
                nivelExclusividad = NivelExclusividad.DIAMANTE;
            } else if (rbOro.isSelected()) {
                nivelExclusividad = NivelExclusividad.ORO;
            } else {
                nivelExclusividad = NivelExclusividad.FAMILIAR;
            }

            Atraccion nuevaAtraccion;
            
            if (rbMecanica.isSelected()) {
                // Validar campos específicos de mecánica
                if (txtAlturaMax.getText().isEmpty() || txtPesoMax.getText().isEmpty() || 
                    txtPesoMin.getText().isEmpty() || txtNivelRiesgo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe completar todos los campos de la atracción mecánica", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int alturaMax = Integer.parseInt(txtAlturaMax.getText());
                int pesoMax = Integer.parseInt(txtPesoMax.getText());
                int pesoMin = Integer.parseInt(txtPesoMin.getText());
                String restriccionesEspeciales = txtRestricciones.getText();
                int nivelRiesgo = Integer.parseInt(txtNivelRiesgo.getText());
                boolean enTemporada = rbTemporadaSi.isSelected();

                nuevaAtraccion = new Mecanica(
                    nombre, capacidad, cupoMinimo, restricciones, nivelExclusividad,
                    alturaMax, Integer.parseInt(txtAlturaMin.getText()), pesoMax, pesoMin, 
                    restriccionesEspeciales, nivelRiesgo, enTemporada
                );
            } else {
                // Validar campos específicos de cultural
                if (txtTipoEspectaculo.getText().isEmpty() || txtFecha.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe completar todos los campos de la atracción cultural", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String tipoEspectaculo = txtTipoEspectaculo.getText();
                Date fecha;
                try {
                    fecha = new SimpleDateFormat("yyyy-MM-dd").parse(txtFecha.getText());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean interactivo = rbInteractivoSi.isSelected();
                boolean incluyeAnimales = rbAnimalesSi.isSelected();

                nuevaAtraccion = new Cultural(
                    nombre, capacidad, cupoMinimo, restricciones, nivelExclusividad,
                    tipoEspectaculo, fecha, interactivo, incluyeAnimales
                );
            }

            // Agregar al parque y guardar
            parque.agregarAtraccion(nuevaAtraccion);
            new PersistenciaAtracciones(parque).guardarAtracciones("./data/atracciones.txt");

            JOptionPane.showMessageDialog(this, "Atracción creada exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos
            txtNombre.setText("");
            txtCapacidad.setText("");
            txtCupoMinimo.setText("");
            txtAlturaMin.setText("");
            txtEdadMin.setText("");
            
            if (rbMecanica.isSelected()) {
                txtAlturaMax.setText("");
                txtPesoMax.setText("");
                txtPesoMin.setText("");
                txtRestricciones.setText("");
                txtNivelRiesgo.setText("");
            } else {
                txtTipoEspectaculo.setText("");
                txtFecha.setText("");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la atracción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}