package Roles;

public class Administrador implements Rol {
    @Override
    public String getNombreRol() {
        return "Administrador";
    }

    @Override
    public boolean requiereCapacitacion() {
        return false;
    }
}
