package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Administrador.Parque;
import Usuarios.Usuario;
import Atracciones.Atraccion;
import GeneradorTiqueteQR.TiqueteVentana;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteDiamante;
import Tiquetes.TiqueteOro;
import Tiquetes.TiqueteFamiliar;
import Tiquetes.TiqueteBasico;
import Restricciones.Restriccion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelCompraTiquetes extends JPanel {
    private Parque parque;
    private Usuario comprador;
    private JComboBox<String> comboAtracciones;
    private JComboBox<String> comboTiposTiquete;
    private JLabel lblPrecioFinal;

    public PanelCompraTiquetes(Parque parque, Usuario comprador) {
        this.parque = parque;
        this.comprador = comprador;
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        // Título
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Compra de Tiquetes");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelPrincipal.add(titulo, gbc);

        // Mostrar nombre del comprador
        gbc.gridy = fila++;
        JLabel lblComprador = new JLabel("Comprador: " + comprador.getNombre());
        lblComprador.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelPrincipal.add(lblComprador, gbc);

        // Selector de atracción
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        panelPrincipal.add(new JLabel("Atracción:"), gbc);

        gbc.gridx = 1;
        comboAtracciones = new JComboBox<>();
        cargarAtracciones();
        panelPrincipal.add(comboAtracciones, gbc);
        fila++;

        // Selector de tipo de tiquete
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelPrincipal.add(new JLabel("Tipo de Tiquete:"), gbc);

        gbc.gridx = 1;
        comboTiposTiquete = new JComboBox<>(new String[]{"Diamante", "Oro", "Familiar", "Básico"});
        comboTiposTiquete.addActionListener(e -> actualizarPrecio());
        panelPrincipal.add(comboTiposTiquete, gbc);
        fila++;

        // Mostrar precio
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelPrincipal.add(new JLabel("Precio Final:"), gbc);

        gbc.gridx = 1;
        lblPrecioFinal = new JLabel("$0.00");
        lblPrecioFinal.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelPrincipal.add(lblPrecioFinal, gbc);
        fila++;

        // Botón Comprar
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnComprar = new JButton("Comprar Tiquete");
        btnComprar.addActionListener(e -> comprarTiquete());
        panelPrincipal.add(btnComprar, gbc);
        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JButton btnVerTiquetes = new JButton("Ver Mis Tiquetes Comprados");
        btnVerTiquetes.addActionListener(e -> mostrarTiquetesComprados());

        panelPrincipal.add(btnVerTiquetes, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
        actualizarPrecio();
    }

    private void mostrarTiquetesComprados() {
    	Window owner = SwingUtilities.getWindowAncestor(this);
    	JDialog dialog = new JDialog(owner, "Tiquetes de " + comprador.getNombre(), Dialog.ModalityType.APPLICATION_MODAL);
    	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setTitle("Tiquetes de " + comprador.getNombre());
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        String[] columnas = {"Código", "Tipo", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Tiquete t : comprador.getTiquetesAdquiridos()) {
            modelo.addRow(new Object[] {
                t.getCodigo(),
                t.getClass().getSimpleName().replace("Tiquete", ""),
                
                String.format("$%.2f", t.getPrecio())
            });
        }

        if (modelo.getRowCount() == 0) {
            modelo.addRow(new Object[]{"No has comprado tiquetes", "", ""});
        }

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCerrar);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(panelInferior, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void cargarAtracciones() {
        comboAtracciones.removeAllItems();
        for (Atraccion atraccion : parque.getAtracciones()) {
            comboAtracciones.addItem(atraccion.getNombre());
        }
    }

    private void actualizarPrecio() {
        String tipoTiquete = (String) comboTiposTiquete.getSelectedItem();
        double precioBase = 0.0;

        switch (tipoTiquete) {
            case "Diamante": precioBase = 100.0; break;
            case "Oro": precioBase = 70.0; break;
            case "Familiar": precioBase = 50.0; break;
            case "Básico": precioBase = 30.0; break;
        }

        lblPrecioFinal.setText(String.format("$%.2f", precioBase));
    }

    private void comprarTiquete() {
        int indiceAtraccion = comboAtracciones.getSelectedIndex();
        String tipoTiquete = (String) comboTiposTiquete.getSelectedItem();

        if (indiceAtraccion < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una atracción.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Atraccion atraccion = parque.getAtracciones().get(indiceAtraccion);

        if (atraccion.getTiquetesVendidos() >= atraccion.getCupoMaximoClientes()) {
            JOptionPane.showMessageDialog(this, "No hay cupos disponibles.", "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> restriccionesNoCumplidas = obtenerRestriccionesNoCumplidas(comprador, atraccion);
        if (!restriccionesNoCumplidas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Restricciones no cumplidas:\n" + String.join("\n", restriccionesNoCumplidas),
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Tiquete tiquete = crearTiquete(tipoTiquete);
        boolean compraExitosa = parque.registrarCompraTiquete(comprador, atraccion, tiquete);

        if (compraExitosa) {
            JOptionPane.showMessageDialog(this, 
                "¡Tiquete comprado!\n" + 
                "Tipo: " + tipoTiquete + "\n" +
                "Precio: " + lblPrecioFinal.getText(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            TiqueteVentana.mostrarTiquete(comprador);
        } else {
            JOptionPane.showMessageDialog(this, "Error al comprar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Tiquete crearTiquete(String tipoTiquete) {
        
        String precioTexto = lblPrecioFinal.getText().replace("$", "").replace(",", ".");
        double precio = Double.parseDouble(precioTexto);
        String codigo = "TQ-" + System.currentTimeMillis();

        switch (tipoTiquete) {
            case "Diamante": return new TiqueteDiamante(codigo, comprador, precio);
            case "Oro": return new TiqueteOro(codigo, comprador, precio);
            case "Familiar": return new TiqueteFamiliar(codigo, comprador, precio);
            default: return new TiqueteBasico(codigo, comprador, precio);
        }
    }

    private List<String> obtenerRestriccionesNoCumplidas(Usuario usuario, Atraccion atraccion) {
        List<String> restricciones = new java.util.ArrayList<>();
        for (Restriccion restriccion : atraccion.getRestricciones()) {
            if (!restriccion.cumple(usuario)) {
                restricciones.add(restriccion.getDescripcion());
            }
        }
        return restricciones;
    }
}