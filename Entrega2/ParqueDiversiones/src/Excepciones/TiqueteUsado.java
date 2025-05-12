package Excepciones;

public class TiqueteUsado extends Exception {

    public TiqueteUsado() {
        super("El tiquete ya ha sido utilizado.");
    }

    public TiqueteUsado(String mensaje) {
        super(mensaje);
    }

    public TiqueteUsado(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public TiqueteUsado(Throwable causa) {
        super(causa);
    }
}
