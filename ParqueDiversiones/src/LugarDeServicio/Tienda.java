package LugarDeServicio;

public class Tienda extends Lugar {

    public Tienda(String nombre, int capacidadMaxima) {
        super(nombre, "Venta de Productos", capacidadMaxima);
    }

    @Override
    public void realizarActividad() {
        System.out.println("Vendiendo productos en la tienda.");
    }

}
