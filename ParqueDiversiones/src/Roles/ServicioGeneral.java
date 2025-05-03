package Roles;

public class ServicioGeneral implements Rol {
    @Override
    public String getNombreRol() {
        return "Servicio General";
    }

    @Override
    public boolean requiereCapacitacion() {
        return false;
    }
}
