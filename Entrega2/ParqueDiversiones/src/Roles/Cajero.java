package Roles;

public class Cajero implements Rol {
    @Override
    public String getNombreRol() {
        return "Cajero";
    }

    @Override
    public boolean requiereCapacitacion() {
        return false;
    }
}
