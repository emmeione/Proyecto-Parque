package Usuarios;

import java.util.ArrayList;
import java.util.List;

import Atracciones.Atraccion;
import Tiquetes.Tiquete;

public class Cliente extends Usuario {

    private int edad;
    private int estatura;

    public Cliente(String nombre, String apellido, String identificacion, String login, String password, int edad, int estatura) {
        super(nombre, apellido, identificacion, login, password);
        this.edad = edad;
        this.estatura = estatura;
    }

    public int getEdad() {
        return edad;
    }

    public double getEstatura() {
        return estatura;
    }


    public void agregarTiquete(Tiquete tiquete) {
        this.tiquetesAdquiridos.add(tiquete);
    }

    public boolean tieneTiqueteValidoPara(Atraccion atraccion) {
        for (Tiquete t : tiquetesAdquiridos) {
            if (t.esValidoPara(atraccion)) {
                return true;
            }
        }
        return false;
    }

    public void comprarTiquete(Tiquete tiquete, double descuento) {
        tiquete.aplicarDescuento(descuento);
        super.comprarTiquete(tiquete, descuento);
        
//        De momento mientras se crea la interfaz de usuario
        System.out.println("Tiquete " + tiquete.getTipo() + " comprado por " + this.getNombre() + " por $" + tiquete.getPrecioFinal());
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }
    
    @Override
    public boolean tieneDescuento() {
        return false;     }
}
