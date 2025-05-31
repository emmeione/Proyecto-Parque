package Usuarios;

import java.util.ArrayList;
import java.util.List;

import Administrador.Parque;
import Atracciones.Atraccion;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteFamiliar;
import snippet.TiqueteVentana;

public class Cliente extends Usuario {

    private int edad;
    private double estatura;

    public Cliente(String nombre, String apellido, String identificacion, String login, String password, int edad, double estatura) {
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

    @Override
    public void comprarTiquete(Tiquete tiquete) {
        super.comprarTiquete(tiquete);
        System.out.println("Administrador " + this.getNombre() + " ha comprado un tiquete para: " + 
                          tiquete.getTipo() + " con precio: " + tiquete.getPrecioFinal());
    }

    public void comprarTiquete(Tiquete tiquete, double porcentajeDescuento) {
        double precioConDescuento = tiquete.getPrecio();
        tiquete.setPrecioFinal(precioConDescuento);
        super.comprarTiquete(tiquete);
    }
    
    @Override
    public void comprarTiquete(Tiquete tiquete, Atraccion atraccion, Parque parque) {
        if (parque.puedeComprarTiquete(this, atraccion)) {
            super.comprarTiquete(tiquete, atraccion, parque);
            System.out.println("(Compra administrativa)");
        }
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }
    
    @Override
    public boolean tieneDescuento() {
        return false;     }

    
    
}
