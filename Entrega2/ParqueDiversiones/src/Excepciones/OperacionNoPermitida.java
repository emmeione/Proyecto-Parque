package Excepciones;

public class OperacionNoPermitida extends Exception {

    public OperacionNoPermitida() {
        super("La operación no está permitida.");
    }

    public OperacionNoPermitida(String mensaje) {
        super(mensaje);
    }

    public OperacionNoPermitida(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public OperacionNoPermitida(Throwable causa) {
        super(causa);
    }
}
