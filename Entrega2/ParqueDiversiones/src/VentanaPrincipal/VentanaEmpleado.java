package VentanaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Administrador.Parque;
import LugarDeServicio.Lugar;

import java.util.ArrayList;
import java.util.List;

import Usuarios.Empleado;

import java.awt.*;

public class VentanaEmpleado extends JFrame {

    private JPanel panelDerecho;
    private Empleado empleadoActual;
    private Parque parque;

    public VentanaEmpleado(Empleado empleado) {
    	
    	this.empleadoActual=empleado;
        this.parque = new Parque();

        setTitle("Empleado - Parque");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(235, 160, 185));
        panelIzquierdo.setLayout(new GridLayout(4, 1, 10, 10));

        String[] botones = {
            "Ver Perfil", 
            "Ver Turnos Asignados", 
            "Ver Lugares Asignados", 
            "Comprar Tiquete"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.addActionListener(e -> cambiarPanelDerecho(texto));
            panelIzquierdo.add(boton);
        }

        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(255, 190, 200));
        panelDerecho.setLayout(new BorderLayout());

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cambiarPanelDerecho(String accion) {
        panelDerecho.removeAll();

        switch (accion) {
            case "Ver Perfil" -> mostrarPerfil();
            case "Ver Turnos Asignados" -> mostrarTurnosAsignados();
            case "Ver Lugares Asignados" -> mostrarLugaresAsignados();
            case "Comprar Tiquete" -> panelDerecho.add(new PanelCompraTiquetes(parque, empleadoActual));
            
        }

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }

    private void mostrarPerfil() {
        panelDerecho.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int fila = 0;

        JLabel titulo = new JLabel("Perfil del Empleado");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        panelDerecho.add(titulo, gbc);

        gbc.gridwidth = 1;


        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        JLabel valorNombre = new JLabel(empleadoActual.getNombre());
        valorNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorNombre, gbc);
        fila++;

        // Apellido
        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        JLabel valorApellido = new JLabel(empleadoActual.getApellido());
        valorApellido.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorApellido, gbc);
        fila++;

        // ID Empleado
        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("ID Empleado:"), gbc);
        gbc.gridx = 1;
        JLabel valorId = new JLabel(empleadoActual.getIdentificacion());
        valorId.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorId, gbc);
        fila++;

        // Rol
        gbc.gridx = 0; gbc.gridy = fila;
        panelDerecho.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        JLabel valorRol = new JLabel(empleadoActual.getRol().getNombreRol());
        valorRol.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelDerecho.add(valorRol, gbc);
        fila++;


    }
    private void mostrarTurnosAsignados() {
        panelDerecho.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Mis Turnos Asignados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        panelDerecho.add(titulo, BorderLayout.NORTH);

        // Columnas de la tabla
        String[] columnas = {"Fecha", "Hora Inicio", "Hora Fin", "Lugar Asignado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        
        String turnos = empleadoActual.revisarTurnos();
        if (!turnos.isEmpty()) {
            String[] lineasTurnos = turnos.split("\n");
            for (String linea : lineasTurnos) {
                if (linea.contains(":")) {
                    String[] partes = linea.split(":");
                    String fecha = partes[0].trim();
                    String[] horarioLugar = partes.length > 1 ? partes[1].trim().split("-") : new String[0];
                    
                    String horaInicio = horarioLugar.length > 0 ? horarioLugar[0].trim() : "";
                    String horaFin = horarioLugar.length > 1 ? horarioLugar[1].trim() : "";
                    String lugar = horarioLugar.length > 2 ? horarioLugar[2].trim() : "";
                    
                    modeloTabla.addRow(new Object[]{fecha, horaInicio, horaFin, lugar});
                }
            }
        } else {
            JLabel sinTurnos = new JLabel("No tienes turnos asignados actualmente");
            sinTurnos.setFont(new Font("SansSerif", Font.PLAIN, 16));
            sinTurnos.setHorizontalAlignment(JLabel.CENTER);
            panelDerecho.add(sinTurnos, BorderLayout.CENTER);
            return;
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        // Botón para actualizar
        JButton btnActualizar = new JButton("Actualizar Turnos");
        btnActualizar.addActionListener(e -> mostrarTurnosAsignados()); 
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizar);
        panelDerecho.add(panelBoton, BorderLayout.SOUTH);
    }
    private void mostrarLugaresAsignados() {
        panelDerecho.removeAll();
        panelDerecho.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Mis Lugares Asignados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        panelDerecho.add(titulo, BorderLayout.NORTH);

        ArrayList<Lugar> lugaresAsignados = empleadoActual.getLugarDeServicio();

        if (lugaresAsignados == null || lugaresAsignados.isEmpty()) {
            JLabel sinLugares = new JLabel("No tienes lugares asignados actualmente");
            sinLugares.setFont(new Font("SansSerif", Font.PLAIN, 16));
            sinLugares.setHorizontalAlignment(JLabel.CENTER);
            panelDerecho.add(sinLugares, BorderLayout.CENTER);
        } else {
            // Crear modelo de tabla
            String[] columnas = {"Tipo", "Nombre", "Ubicación"};
            DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Lugar lugar : lugaresAsignados) {
                String tipo = lugar.getClass().getSimpleName(); 
                String nombre = lugar.getNombre();
                int ubicacion = lugar.getZona(); 

                modeloTabla.addRow(new Object[]{
                    tipo,
                    nombre,
                    ubicacion
                });
            }


            JTable tabla = new JTable(modeloTabla);
            tabla.setRowHeight(25);
            tabla.setAutoCreateRowSorter(true);

            JScrollPane scrollPane = new JScrollPane(tabla);
            panelDerecho.add(scrollPane, BorderLayout.CENTER);
        }

        // Botón de actualización
        JButton btnActualizar = new JButton("Actualizar Lugares");
        btnActualizar.addActionListener(e -> mostrarLugaresAsignados());
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizar);
        panelDerecho.add(panelBoton, BorderLayout.SOUTH);

        panelDerecho.revalidate();
        panelDerecho.repaint();
    }



}