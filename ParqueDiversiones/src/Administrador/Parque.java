package Administrador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Atracciones.Atraccion;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import LugarDeServicio.Taquilla;
import Roles.Administrador;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.ServicioGeneral;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Usuarios.Cliente;
import Usuarios.Empleado;
import Usuarios.Usuario;

public class Parque {

    private ArrayList<Atraccion> atracciones;
    private ArrayList<Cliente> clientes;
    private ArrayList<Empleado> empleados;
    private ArrayList<Tiquete> tiquetes;
    private Map<String, Usuario> usuarios;
    private ArrayList<Lugar> lugares;

    public Parque() {
        this.atracciones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.tiquetes = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.lugares = new ArrayList<>();

    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
        usuarios.put(cliente.getNombre(), cliente);
        System.out.println("Cliente registrado con éxito.");
    }

    public void registrarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        usuarios.put(empleado.getNombre(), empleado);
        System.out.println("Empleado registrado con éxito.");
    }

    public void venderTiquete(Tiquete tiquete, Cliente cliente) {
        tiquetes.add(tiquete);
        cliente.agregarTiquete(tiquete);
        System.out.println("Tiquete vendido al cliente: " + cliente.getNombre());
    }

    public void mostrarCatalogoAtracciones() {
        if (atracciones.isEmpty()) {
            System.out.println("No hay atracciones registradas aún.");
        } else {
            System.out.println("Catálogo de atracciones del parque:");
            for (Atraccion atr : atracciones) {
                System.out.println("- " + atr.getNombre() + " (" + atr.getTipo() + ")");
            }
        }
    }
    
    public void usarTiquete(Tiquete tiquete) {
//    	Para evitar que haya el fraude 
        System.out.println("Verificando tiquete: " + tiquete.getCodigo());

        if (!tiquete.estaUsado()) {
            tiquete.marcarComoUsado();
            System.out.println("Tiquete válido");
        } else {
            System.out.println("Este tiquete ya fue utilizado. NO VÁLIDO.");
        }
    }
    
    public Lugar buscarLugarPorNombre(String nombre) {
        for (Empleado empleado : empleados) {
            for (Lugar lugar : empleado.getLugarDeServicio()) {  
                if (lugar.getNombre().equalsIgnoreCase(nombre)) {
                    return lugar;  
                }
            }
        }
        return null;  
    }

    public void agregarAtraccion(Atraccion atraccion) {
        atracciones.add(atraccion);
    }

    public void agregarTiquetes(Tiquete tiquetess) {
    	tiquetes.add(tiquetess);
    }
    
    public void agregarCliente(Cliente cliente) {
    	clientes.add(cliente);
    }
    
    public void agregarEmpleado(Empleado empleado) {
    	empleados.add(empleado);
    }
    public void agregarLugar(Lugar lugar) {
    	lugares.add(lugar);
    }
    public Usuario buscarUsuarioPorNombre(String nombre) {
        return usuarios.get(nombre);
    }
    public void registrarTiquete(Tiquete tiquete) {
        this.tiquetes.add(tiquete);  
        System.out.println("Tiquete registrado: " + tiquete.getCodigo() + " para el cliente " + tiquete.getComprador().getNombre());
    }

    public ArrayList<Atraccion> getAtracciones() { return atracciones; }
    public ArrayList<Cliente> getClientes() { return clientes; }
    public ArrayList<Empleado> getEmpleados() { return empleados; }
    public ArrayList<Tiquete> getTiquetes() { return tiquetes; }
    public ArrayList<Lugar> getLugares(){return lugares;}
    
    public void setAtracciones(ArrayList<Atraccion> atracciones) {
    	this.atracciones = atracciones;
    }
    public void setTiquetes(ArrayList<Tiquete> tiquetes) {
    	this.tiquetes = tiquetes;
    	
    }
    public void setLugares(ArrayList<Lugar> lugares) {
    	this.lugares = lugares;
    }
    

    
    public class Main {
        public static void main(String[] args) {

            Parque parque = new Parque();

            Lugar cafeteria = new Cafeteria("Cafetería Central", 50);
            Lugar taquilla = new Taquilla("Taquilla Principal", 10);

            parque.agregarLugar(cafeteria);
            parque.agregarLugar(taquilla);

            Cliente betty = new Cliente("Betty", "Pinzón", "CC123", "bettyp", "clave123", 30, 165);
            parque.registrarCliente(betty);

            Empleado armando = new Empleado(
                    "Armando", "Mendoza", "EMP001", "amendoza", "pass",
                    new Cocinero(), true, false, false, cafeteria);
            armando.setLugarDeServicio(cafeteria);

            Empleado hugo = new Empleado(
                    "Hugo", "Lombardi", "EMP002", "hlombardi", "pass",
                    new Cajero(), false, false, false, taquilla);
            hugo.setLugarDeServicio(taquilla);

            Empleado marcela = new Empleado(
            	    "Marcela", "Valencia", "EMP003", "mvalencia", "pass",
            	    new Roles.Administrador(), false, false, false, null);


            parque.agregarEmpleado(armando);
            parque.agregarEmpleado(hugo);
            parque.agregarEmpleado(marcela);

            marcela.asignarTurnoAEmpleado(armando, "Lunes", "08:00 - 12:00");
            marcela.asignarTurnoAEmpleado(hugo, "Martes", "09:00 - 15:00");

            System.out.println("\nActividades en los lugares:");
            cafeteria.realizarActividad();
            taquilla.realizarActividad();

            Cafeteria cafe1 = new Cafeteria("Cafetería central", 8);
            Tiquete tiqueteBasico = new TiqueteBasico("00001", new Empleado("Armando", "Mendoza", "1010084918", "a.mendoza", "brutaslapoliciaaaa",
                    new Cajero(), false, false, false, cafe1), 100000);
            parque.agregarTiquetes(tiqueteBasico);
            betty.comprarTiquete(tiqueteBasico, 0); 
            parque.venderTiquete(tiqueteBasico, betty);

            parque.usarTiquete(tiqueteBasico);
            parque.usarTiquete(tiqueteBasico); 

            System.out.println("\nTurnos de Armando:");
            System.out.println(armando.revisarTurnos());

            System.out.println("\nTurnos de Hugo:");
            System.out.println(hugo.revisarTurnos());
        }
    
    }

}