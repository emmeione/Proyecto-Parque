package VentanaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelBanner extends JPanel {

	  private JLabel banner;

	    public PanelBanner() {
	        setLayout(new BorderLayout());
	        

	        // Ruta del archivo
	        String ruta = "./imagenes/parque-de-atracciones.png";
	        File archivo = new File(ruta);

	        if (archivo.exists()) {
	            ImageIcon icon = new ImageIcon(ruta);
	            Image img = icon.getImage();
	            Image resizedImage = img.getScaledInstance(-1, 170, Image.SCALE_SMOOTH); 
	            banner = new JLabel(new ImageIcon(resizedImage));
	            banner.setHorizontalAlignment(SwingConstants.CENTER);
	            add(banner, BorderLayout.CENTER);
	        } else {
	            JLabel error = new JLabel("No se encontró el banner.");
	            error.setHorizontalAlignment(SwingConstants.CENTER);
	            add(error, BorderLayout.CENTER);
	        }
	    }
	    
		public static void main(String[] args) {
			PanelBanner v = new PanelBanner();
		}
}
