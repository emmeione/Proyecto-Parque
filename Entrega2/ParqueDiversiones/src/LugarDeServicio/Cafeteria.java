package LugarDeServicio;

public class Cafeteria extends Lugar{

    public Cafeteria(String nombre, int capacidadMaxima, int zona) {
        super(nombre, 1, capacidadMaxima, zona);
    }

    @Override
    public void realizarActividad() {
        System.out.println("Sirviendo comida y bebidas en la cafetería.");
    }
	

}
