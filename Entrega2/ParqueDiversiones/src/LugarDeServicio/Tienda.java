package LugarDeServicio;

public class Tienda extends Lugar {

    public Tienda(String nombre, int capacidadMaxima, int zona) {
        super(nombre, 3, capacidadMaxima, zona);
    }

    @Override
    public void realizarActividad() {
        System.out.println("Vendiendo productos en la tienda.");
    }

}
