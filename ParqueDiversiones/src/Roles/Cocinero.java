package Roles;

public class Cocinero implements Rol {
    @Override
    public String getNombreRol() {
        return "Cocinero";
    }

    @Override
    public boolean requiereCapacitacion() {
        return true;
    }
}
