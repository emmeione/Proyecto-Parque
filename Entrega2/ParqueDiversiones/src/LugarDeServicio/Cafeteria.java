package LugarDeServicio;

public class Cafeteria extends Lugar{

    public Cafeteria(String nombre, int capacidadMaxima) {
        super(nombre, "Cafetería", capacidadMaxima);
    }

    @Override
    public void realizarActividad() {
        System.out.println("Sirviendo comida y bebidas en la cafetería.");
    }
	

}
