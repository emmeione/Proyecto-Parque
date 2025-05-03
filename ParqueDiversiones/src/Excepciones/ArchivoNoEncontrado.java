package Excepciones;

public class ArchivoNoEncontrado extends Exception {

    public ArchivoNoEncontrado() {
        super("El archivo no fue encontrado.");
    }

    public ArchivoNoEncontrado(String mensaje) {
        super(mensaje);
    }

    public ArchivoNoEncontrado(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public ArchivoNoEncontrado(Throwable causa) {
        super(causa);
    }
}
