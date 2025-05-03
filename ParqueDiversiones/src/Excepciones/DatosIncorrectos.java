package Excepciones;

public class DatosIncorrectos extends Exception {

    public DatosIncorrectos() {
        super("Los datos proporcionados son incorrectos.");
    }

    public DatosIncorrectos(String mensaje) {
        super(mensaje);
    }

    public DatosIncorrectos(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public DatosIncorrectos(Throwable causa) {
        super(causa);
    }
}
