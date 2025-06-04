package Tiquetes;

import Usuarios.Cliente;
import Usuarios.Usuario;

import java.util.ArrayList;
import java.util.Date;

import Atracciones.Atraccion;

public abstract class Tiquete {
    protected String codigo;
    protected Usuario comprador; 
    protected boolean usado;
    protected double precio;
    protected double precioFinal;
    protected ArrayList<Atraccion> atraccionesIncluidas;
    private Date fechaCompra;
    private String estado;


//  Método constructor
    public Tiquete(String codigo, Usuario comprador, double precio) {
        this.codigo = codigo;
        this.comprador = comprador;
        this.usado = false;
        this.precio = precio;
        this.precioFinal = precio;
        this.fechaCompra = new Date();
        this.estado = "Activo";
    }

    public String getCodigo() {
        return codigo;
    }

    public Usuario getComprador() { 
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
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

    public abstract String getTipo();

    public abstract boolean esValidoPara(Atraccion atraccion);

    public void aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento > 0) {
            this.precioFinal -= this.precioFinal * porcentajeDescuento;
        }
        
        
    }
}
