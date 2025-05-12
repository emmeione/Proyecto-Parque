package Roles;

public class OperadorAtraccion implements Rol {
    private int nivelRiesgoPermitido; 

    public OperadorAtraccion(int nivelRiesgoPermitido) {
        this.nivelRiesgoPermitido = nivelRiesgoPermitido;
    }

    @Override
    public String getNombreRol() {
        return "Operador de Atracción";
    }

    @Override
    public boolean requiereCapacitacion() {
        return true;
    }

    public int getNivelRiesgoPermitido() {
        return nivelRiesgoPermitido;
    }
}
