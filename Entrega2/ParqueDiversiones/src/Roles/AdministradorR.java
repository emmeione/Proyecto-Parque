package Roles;

public class AdministradorR implements Rol {
    @Override
    public String getNombreRol() {
        return "Administrador";
    }

    @Override
    public boolean requiereCapacitacion() {
        return false;
    }
}
