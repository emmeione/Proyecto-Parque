package Excepciones;

public class ElementoNoEncontrado extends Exception {

    public ElementoNoEncontrado() {
        super("El elemento no fue encontrado.");
    }

    public ElementoNoEncontrado(String mensaje) {
        super(mensaje);
    }

    public ElementoNoEncontrado(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public ElementoNoEncontrado(Throwable causa) {
        super(causa);
    }
}
