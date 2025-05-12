package LugarDeServicio;

public class Taquilla extends Lugar{
	
    public Taquilla(String nombre, int capacidadMaxima) {
        super(nombre, "Taquilla", capacidadMaxima);
    }

    @Override
    public void realizarActividad() {
        System.out.println("Vendiendo tiquetes en la taquilla.");
    }

}
