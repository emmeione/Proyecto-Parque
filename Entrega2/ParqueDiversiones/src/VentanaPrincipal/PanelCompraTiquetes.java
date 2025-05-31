package VentanaPrincipal;

import javax.swing.*;

import Administrador.Parque;
import Usuarios.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import Atracciones.Atraccion;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteOro;

public class PanelCompraTiquetes extends JPanel {
    private Parque parque;
    private JPanel panelDerecho;
    private JComboBox<String> comboClientes;
    private JComboBox<String> comboAtracciones;
    private JComboBox<String> comboTiposTiquete;
    private JLabel lblPrecioFinal;

    public PanelCompraTiquetes(Parque parque) {
        this.parque = parque;
        this.panelDerecho = panelDerecho;
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con borde y margen
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

        // Selector de cliente
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelPrincipal.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        comboClientes = new JComboBox<>();
        cargarClientes();
        panelPrincipal.add(comboClientes, gbc);
        fila++;

        // Selector de atracción
        gbc.gridx = 0;
        gbc.gridy = fila;
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
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarTiquete();
            }
        });
        panelPrincipal.add(btnComprar, gbc);

        add(panelPrincipal, BorderLayout.CENTER);

        // Actualizar precio inicial
        actualizarPrecio();
    }

    private void cargarClientes() {
        comboClientes.removeAllItems();
        for (Cliente cliente : parque.getClientes()) {
            comboClientes.addItem(cliente.getNombre() + " " + cliente.getApellido() + " (" + cliente.getIdentificacion() + ")");
        }
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

        // Asignar precio base según el tipo de tiquete
        switch (tipoTiquete) {
            case "Diamante":
                precioBase = 100.0;
                break;
            case "Oro":
                precioBase = 70.0;
                break;
            case "Familiar":
                precioBase = 50.0;
                break;
            case "Básico":
                precioBase = 30.0;
                break;
        }

        // Mostrar precio formateado
        lblPrecioFinal.setText(String.format("$%.2f", precioBase));
    }

    private void comprarTiquete() {
        // Obtener selecciones
        int indiceCliente = comboClientes.getSelectedIndex();
        int indiceAtraccion = comboAtracciones.getSelectedIndex();
        String tipoTiquete = (String) comboTiposTiquete.getSelectedItem();

        // Validar selecciones
        if (indiceCliente == -1 || indiceAtraccion == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente y una atracción.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener objetos correspondientes
        Cliente cliente = parque.getClientes().get(indiceCliente);
        Atraccion atraccion = parque.getAtracciones().get(indiceAtraccion);

        // Verificar restricciones
        if (!atraccion.usuarioCumpleRestricciones(cliente)) {
            JOptionPane.showMessageDialog(this, 
                "El cliente no cumple con las restricciones para esta atracción.", 
                "Restricción", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar cupos disponibles
        if (atraccion.getTiquetesVendidos() >= atraccion.getCupoMaximoClientes()) {
            JOptionPane.showMessageDialog(this, 
                "No hay cupos disponibles para esta atracción.", 
                "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear tiquete
        Tiquete tiquete = new TiqueteOro(generarCodigoTiquete(), cliente, obtenerPrecioTiquete(tipoTiquete));

        // Registrar compra
        boolean compraExitosa = parque.registrarCompraTiquete(cliente.getNombre(), atraccion, tiquete);

        if (compraExitosa) {
            JOptionPane.showMessageDialog(this, 
                "Tiquete comprado exitosamente!\n" +
                "Atracción: " + atraccion.getNombre() + "\n" +
                "Tipo: " + tipoTiquete + "\n" +
                "Precio: " + lblPrecioFinal.getText(),
                "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar listas por si hay cambios en disponibilidad
            cargarAtracciones();
            actualizarPrecio();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al procesar la compra del tiquete.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generarCodigoTiquete() {
        // Generar un código único para el tiquete
        return "TQ-" + System.currentTimeMillis();
    }

    private double obtenerPrecioTiquete(String tipoTiquete) {
        // Obtener el precio numérico del texto mostrado (que ya incluye descuentos si los hubiera)
        String precioTexto = lblPrecioFinal.getText().replace("$", "");
        return Double.parseDouble(precioTexto);
    }
}