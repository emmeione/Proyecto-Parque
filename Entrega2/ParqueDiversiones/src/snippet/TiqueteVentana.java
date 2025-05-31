package snippet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Tiquetes.Tiquete;
import Usuarios.Cliente;
import Usuarios.Usuario;
import Tiquetes.TiqueteDiamante;
import Tiquetes.Tiquetera;


public class TiqueteVentana extends JPanel {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 400;

    private BufferedImage qrImage;
    private BufferedImage parqueImage;
    private BufferedImage tipoImage;
    private Cliente cliente;
    private Tiquete tiquete;

    public TiqueteVentana(Cliente usuario) {
        this.cliente = cliente;
        this.tiquete = cliente.getUltimoTiquete();

        // Generar QR con info real
        String qrData = "ID: " + tiquete.getCodigo() + 
                        "\nTipo: " + tiquete.getTipo() + 
                        "\nFecha: " + LocalDate.now().toString();
        qrImage = crearQR(qrData);

        try {
            parqueImage = ImageIO.read(new File("./imagenes/parque-tiquetes.png"));
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen del parque: " + e.getMessage());
            parqueImage = null;
        }

        try {
            if (cliente.getTipo().equalsIgnoreCase("Diamante")) {
                tipoImage = ImageIO.read(new File("./imagenes/diamante.png"));
            } else if (tiquete.getTipo().equalsIgnoreCase("FAMILIAR")) {
                tipoImage = ImageIO.read(new File("./imagenes/familiar.png"));
            } else if (tiquete.getTipo().equalsIgnoreCase("ORO")) {
                tipoImage = ImageIO.read(new File("./imagenes/oro.png"));
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen del tipo de tiquete: " + e.getMessage());
            tipoImage = null;
        }
    }

    private BufferedImage crearQR(String texto) {
        int size = 150; // tamaño más pequeño del QR
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, size, size, hints);
            return toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0xFF000000;
        int white = 0xFFFFFFFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? black : white);
            }
        }
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

//        El color de fondo del tiquete
        g2.setColor(new Color(255, 173, 96));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

//        Las líneas puntadas para recortes
        g2.setColor(Color.BLACK);
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2.setStroke(dashed);
        g2.drawLine(WIDTH / 3, 20, WIDTH / 3, HEIGHT - 20);

        int leftX = 10;      
        int topY = 20;
        int bottomY = HEIGHT - 20;
        int lineX = WIDTH / 3;

        g2.drawLine(leftX, topY, lineX, topY);          
        g2.drawLine(leftX, bottomY, lineX, bottomY);    
        g2.drawLine(leftX, topY, leftX, bottomY);       


//        Nombre del parque y demás información
        g2.setFont(new Font("Serif", Font.BOLD, 28));
        g2.drawString("Parque de Atracciones de los Alpes", WIDTH / 3 + 30, 50);

//        Info del tiquete
        g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        g2.drawString("No. " + tiquete.getCodigo(), 30, 50);
        g2.drawString("Tiquete: " + tiquete.getTipo(), 30, 100);

        LocalDate today = LocalDate.now();
        String diaSemana = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String fechaFormateada = today.getDayOfMonth() + " de " +
                today.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) + " " +
                today.getYear();

        g2.drawString("Fecha Expedición: " + today, 30, 140);
        g2.drawString("Valor: $ 42.000", 30, 180);


//        Fecha actual bonita
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.drawString(diaSemana + " " + fechaFormateada, WIDTH / 3 + 120, HEIGHT - 40);


//        icono que depende del tipo de tiquete
        if (tipoImage != null) {
            int imgSize = 60;
            int imgX = 30;
            int imgY = HEIGHT - imgSize - 40;
            g2.drawImage(tipoImage, imgX, imgY, imgSize, imgSize, null);
        }

//        imagen del parque
        if (parqueImage != null) {
            int imgMaxWidth = 350;
            int imgMaxHeight = 220;
            int imgX = WIDTH / 3 + 30;
            int imgY = 80;

            double imgRatio = (double) parqueImage.getWidth() / parqueImage.getHeight();
            int finalWidth = imgMaxWidth;
            int finalHeight = (int) (imgMaxWidth / imgRatio);
            if (finalHeight > imgMaxHeight) {
                finalHeight = imgMaxHeight;
                finalWidth = (int) (imgMaxHeight * imgRatio);
            }

            g2.drawImage(parqueImage, imgX, imgY, finalWidth, finalHeight, null);
        }

        // Dibujar QR
        if (qrImage != null) {
            int qrX = WIDTH - qrImage.getWidth() - 30;
            int qrY = (HEIGHT - qrImage.getHeight()) / 2;
            g2.drawImage(qrImage, qrX, qrY, null);
        }
    }

    public static void mostrarTiquete(Cliente cliente) {
        JFrame frame = new JFrame("Imprimir boleta");
        TiqueteVentana panel = new TiqueteVentana(cliente);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH + 16, HEIGHT + 39);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void guardarImagenTiquete(String path) throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        this.paint(g2);  
        g2.dispose();
        ImageIO.write(image, "png", new File(path));
    }


    public static void main(String[] args) {
        Cliente cliente = new Cliente(
                "Juan",
                "Pérez",
                "12345678",
                "juanperez",
                "password123",
                25,
                1.75
        );
        TiqueteDiamante tiquete = new TiqueteDiamante("TIQ001", cliente, 42000);
        cliente.comprarTiquete(tiquete, 0.0);

        Tiquetera.mostrarYGuardarTiquete(cliente);
    }

}
