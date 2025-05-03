package Tiquetes;

import Usuarios.Cliente;
import Usuarios.Usuario;
import Atracciones.Atraccion;

public abstract class Tiquete {
    protected String codigo;
    protected Usuario comprador; 
    protected boolean usado;
    protected double precio;
    protected double precioFinal;

//  Método constructor
    public Tiquete(String codigo, Usuario comprador, double precio) {
        this.codigo = codigo;
        this.comprador = comprador;
        this.usado = false;
        this.precio = precio;
        this.precioFinal = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public Usuario getComprador() { 
        return comprador;
    }

    public boolean estaUsado() {
        return usado;
    }

    public void marcarComoUsado() {
        this.usado = true;
    }

    public double getPrecio() {
        return precio;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }
    
    public Usuario getUsuario() {
    	return comprador;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }
    public String obtenerNombreAtraccion(Atraccion atraccion) {
        if (esValidoPara(atraccion)) {
            return atraccion.getNombre();
        }
        return "Tiquete no válido para esta atracción";
    }
    public abstract String serializarTipos();

    public abstract String getTipo();

    public abstract boolean esValidoPara(Atraccion atraccion);

    public void aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento > 0) {
            this.precioFinal -= this.precioFinal * porcentajeDescuento;
        }
        
        
    }
}
