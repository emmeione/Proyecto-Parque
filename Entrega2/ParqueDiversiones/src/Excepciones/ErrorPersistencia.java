package Excepciones;

public class ErrorPersistencia extends Exception {

    public ErrorPersistencia() {
        super("Ocurrió un error durante la persistencia de datos.");
    }

    public ErrorPersistencia(String mensaje) {
        super(mensaje);
    }

    public ErrorPersistencia(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public ErrorPersistencia(Throwable causa) {
        super(causa);
    }
}
