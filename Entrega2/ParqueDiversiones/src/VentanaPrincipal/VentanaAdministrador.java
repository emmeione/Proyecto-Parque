package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Usuarios.Administrador;
import Usuarios.Empleado;

import java.awt.*;

import javax.swing.*;
import java.awt.*;
import Administrador.Parque;
import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import LugarDeServicio.Taquilla;
import LugarDeServicio.Tienda;
import Persistencia.PersistenciaAtracciones;
import Persistencia.PersistenciaUsuarios;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Roles.AdministradorR;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.Rol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaAdministrador extends JFrame {

    private JPanel panelDerecho;
    private Parque parque;
    private PersistenciaUsuarios persistencia;
    private final String archivoUsuarios = "./data/usuarios.txt";
    private Administrador administradorActual;




    public VentanaAdministrador(Administrador admin) {
        this.parque = new Parque();
        this.administradorActual = admin;

        try {
            parque.cargarAtraccionesDesdeArchivo("./data/atracciones.txt");
            parque.cargarUsuariosDesdeArchivo("./data/usuarios.txt");
            this.persistencia = new PersistenciaUsuarios(parque);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos iniciales: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }


        setTitle("Administrador Parque");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(235, 160, 185));
        panelIzquierdo.setLayout(new GridLayout(7, 1, 10, 10));

        String[] botones = {
            "Crear atracción", "Crear empleado", "Crear lugar", 
            "Asignar empleado", "Asignar atracción", "Asignar turno","Comprar tiquete"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.addActionListener(e -> cambiarPanelDerecho(texto));
            panelIzquierdo.add(boton);
        }

        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(255, 190, 200));
        panelDerecho.setLayout(new FlowLayout());

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
        setVisible(true);
    }

    private void cambiarPanelDerecho(String accion) {
        panelDerecho.removeAll();

        if (accion.equals("Crear atracción")) {
            panelDerecho.add(new PanelCrearAtraccion(parque));
        }
        
        else if (accion.equals("Crear empleado")) {
            panelDerecho.add(new PanelGestionEmpleados(parque, persistencia, archivoUsuarios));
        }
        
        else if (accion.equals("Crear lugar")) {
            panelDerecho.add(new PanelGestionLugares(parque));
        }
        
        else if (accion.equals("Asignar empleado")) {
            panelDerecho.add(new PanelAsignarEmpleado(parque));
        }
        
        else if (accion.equals("Asignar atracción")) {
            panelDerecho.add(new PanelAsignarAtraccion(parque));
        }
        
        else if(accion.equals("Comprar tiquete")) {
        	panelDerecho.add(new PanelCompraTiquetes(parque, administradorActual));
        }
        else if(accion.equals("Asignar turno")) {
        	panelDerecho.add( new PanelGestionTurnos(parque));
        }

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }
   
 
    private void mostrarFormularioCrearTiquete() {
        panelDerecho.removeAll();
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        // Título
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Crear Tiquete");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelDerecho.add(titulo, gbc);

        // ComboBox tipo de tiquete
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Tipo de tiquete:"), gbc);

        gbc.gridx = 1;
        String[] tipos = { "Diamante", "Oro", "Familiar", "Básico" };
        JComboBox<String> comboTipos = new JComboBox<>(tipos);
        panelDerecho.add(comboTipos, gbc);
        fila++;

        // Campo código
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        JTextField campoCodigo = new JTextField(15);
        panelDerecho.add(campoCodigo, gbc);
        fila++;

        // Campo precio base
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDerecho.add(new JLabel("Precio base:"), gbc);

        gbc.gridx = 1;
        JTextField campoPrecio = new JTextField(15);
        panelDerecho.add(campoPrecio, gbc);
        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnCrear = new JButton("Crear Tiquete");
        panelDerecho.add(btnCrear, gbc);

        btnCrear.addActionListener(e -> {
            String tipoSeleccionado = (String) comboTipos.getSelectedItem();
            String codigo = campoCodigo.getText().trim();
            String precioStr = campoPrecio.getText().trim();

            if (codigo.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(panelDerecho, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio;
            try {
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelDerecho, "Precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            JOptionPane.showMessageDialog(panelDerecho, "Tiquete de tipo " + tipoSeleccionado + " creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }



}

