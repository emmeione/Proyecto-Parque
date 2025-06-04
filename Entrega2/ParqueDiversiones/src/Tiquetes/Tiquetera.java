package Tiquetes;

import Tiquetes.Tiquete;
import Usuarios.Cliente;
import Usuarios.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;

import GeneradorTiqueteQR.TiqueteVentana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiquetera {

    private ArrayList<Tiquete> tiquetes;

    public static void mostrarYGuardarTiquete(Usuario usuario) {
        Tiquete tiquete = usuario.getUltimoTiquete();
        if (tiquete == null) {
            System.out.println("El usuario no tiene tiquetes para mostrar.");
            return;
        }

        TiqueteVentana panel = new TiqueteVentana(usuario);

        String codigo = tiquete.getCodigo();
        String rutaImagen = "./tiquetes/" + codigo + ".png";

        try {
            File dir = new File("./tiquetes/");
            if (!dir.exists()) dir.mkdirs();

            panel.guardarImagenTiquete(rutaImagen);
            guardarInfoTiquete(usuario, rutaImagen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Imprimir boleta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(916, 439); // Tamaño exacto del ticket
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void guardarInfoTiquete(Usuario cliente, String rutaImagen) {
        Tiquete tiquete = cliente.getUltimoTiquete();
        String info = tiquete.getCodigo() + ";" + tiquete.getTipo() + ";" + LocalDate.now() + ";" + rutaImagen + "\n";
        String archivo = "./tiquetes/cliente_" + cliente.getLogin() + "_tiquetes.txt";

        try (FileWriter fw = new FileWriter(archivo, true)) {
            fw.write(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarTiquetera(Usuario cliente) {
        JFrame frame = new JFrame("Tiquetera de " + cliente.getLogin());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String archivo = "./tiquetes/cliente_" + cliente.getLogin() + "_tiquetes.txt";
        try (Scanner sc = new Scanner(new File(archivo))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    String codigo = partes[0];
                    String tipo = partes[1];
                    String fecha = partes[2];
                    String rutaImg = partes[3];

                    BufferedImage img = ImageIO.read(new File(rutaImg));
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(250, 100, Image.SCALE_SMOOTH));

                    JLabel label = new JLabel("Código: " + codigo + ", Tipo: " + tipo + ", Fecha: " + fecha, icon, JLabel.LEFT);
                    label.setHorizontalTextPosition(JLabel.RIGHT);
                    label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                    panel.add(label);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "No se encontraron tiquetes guardados.");
        }

        JScrollPane scroll = new JScrollPane(panel);
        frame.add(scroll);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void agregarTiquete(Tiquete tiquete) {
        tiquetes.add(tiquete);
    }

    public ArrayList<Tiquete> getTiquetes() {
        return tiquetes;
    }
}
