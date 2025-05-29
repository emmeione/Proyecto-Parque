package LugarDeServicio;

public class Taquilla extends Lugar{
	
    public Taquilla(String nombre, int capacidadMaxima, int zona) {
        super(nombre, 2, capacidadMaxima, zona);
    }

    @Override
    public void realizarActividad() {
        System.out.println("Vendiendo tiquetes en la taquilla.");
    }

}
