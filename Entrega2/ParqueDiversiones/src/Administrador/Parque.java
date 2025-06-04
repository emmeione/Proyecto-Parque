package Administrador;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Atracciones.Atraccion;
import Atracciones.Cultural;
import Atracciones.Mecanica;
import Atracciones.NivelExclusividad;
import LugarDeServicio.Cafeteria;
import LugarDeServicio.Lugar;
import LugarDeServicio.Taquilla;
import Persistencia.PersistenciaAtracciones;
import Persistencia.PersistenciaTiquetes;
import Persistencia.PersistenciaUsuarios;
import Restricciones.Restriccion;
import Restricciones.RestriccionAltura;
import Restricciones.RestriccionEdad;
import Restricciones.RestriccionMedica;
import Roles.AdministradorR;
import Roles.Cajero;
import Roles.Cocinero;
import Roles.OperadorAtraccion;
import Roles.ServicioGeneral;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteBasico;
import Tiquetes.TiqueteDiamante;
import Tiquetes.TiqueteFamiliar;
import Tiquetes.TiqueteOro;
import Tiquetes.Tiquetera;
import Usuarios.Cliente;
import Usuarios.Administrador;
import Usuarios.Empleado;
import Usuarios.Usuario;

public class Parque {

    private ArrayList<Atraccion> atracciones;
    private ArrayList<Cliente> clientes;
    private ArrayList<Empleado> empleados;
    private ArrayList<Administrador> administradores;
    private ArrayList<Tiquete> tiquetes;
    private Map<String, Usuario> usuariosPorId;
    private ArrayList<Lugar> lugares;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaAtracciones persistenciaAtracciones;
    private ArrayList<Tiquete> tiquetesVendidos;
    private PersistenciaTiquetes persistenciaTiquetes;
    private Tiquetera tiquetera;
    private Administrador administradorActual;







    public Parque() {
        this.atracciones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.tiquetes = new ArrayList<>();
        this.usuariosPorId = new HashMap<>();
        this.lugares = new ArrayList<>();
        this.persistenciaUsuarios = new PersistenciaUsuarios(this);
        this.persistenciaAtracciones = new PersistenciaAtracciones(this);
        this.persistenciaTiquetes = new PersistenciaTiquetes(this);
        this.tiquetesVendidos = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.tiquetera = new Tiquetera();


    }

    public void registrarCliente(Cliente cliente) {
        if (usuariosPorId.containsKey(cliente.getIdentificacion())) {
            System.out.println("Cliente ya registrado con ID: " + cliente.getIdentificacion());
            return;
        }
        clientes.add(cliente);
        usuariosPorId.put(cliente.getIdentificacion(), cliente);
    }


    public void registrarEmpleado(Empleado empleado) {
        if (usuariosPorId.containsKey(empleado.getIdentificacion())) {
            System.out.println("Empleado ya registrado con esta ID.");
            return;
        }
        empleados.add(empleado);
        usuariosPorId.put(empleado.getIdentificacion(), empleado);
    }
    
    public void registrarAdministrador(Administrador administrador) {
        if (usuariosPorId.containsKey(administrador.getIdentificacion())) {
            System.out.println("Administrador ya registrado con ID: " + administrador.getIdentificacion());
            return;
        }
        administradores.add(administrador);
        usuariosPorId.put(administrador.getIdentificacion(), administrador);
    }
    
    public Usuario buscarUsuarioPorId(String id) {
        return usuariosPorId.get(id);
    }
    public Cliente buscarCliente(String login, String password) {
        for (Cliente cliente : clientes) {
            if (cliente.getLogin().equals(login) && cliente.getPassword().equals(password)) {
                return cliente;
            }
        }
        return null;
    }


    public void venderTiquete(Tiquete tiquete, Cliente cliente) {
        tiquetes.add(tiquete);
        cliente.agregarTiquete(tiquete);
        System.out.println("Tiquete vendido al cliente: " + cliente.getNombre());
    }

    public boolean registrarCompraTiquete(Usuario usuario, Atraccion atraccion, Tiquete tiquete) {
        if (usuario == null) {
            System.out.println("Usuario no puede ser null.");
            return false;
        }
        if (!puedeComprarTiquete(usuario, atraccion)) {
            return false;
        }

        tiquete.setComprador(usuario);
        usuario.agregarTiquete(tiquete);

        
        if (!tiquetes.contains(tiquete)) {
            tiquetes.add(tiquete);
        }
        
        usuario.agregarTiquete(tiquete);
        atraccion.venderTiquete();
        
        System.out.println("Tiquete " + tiquete.getCodigo() + " registrado correctamente para " + usuario.getNombre());
        return true;
    }


    public boolean puedeComprarTiquete(Usuario usuario, Atraccion atraccion) {
        if (atraccion.getTiquetesVendidos() >= atraccion.getCupoMaximoClientes()) {
            System.out.println("No hay cupo disponible.");
            return false;
        }
        return atraccion.usuarioCumpleRestricciones(usuario) ;
    }




    public boolean puedeComprarTiquete(Cliente cliente, Atraccion atraccion) {
        if (atraccion.getTiquetesVendidos() >= atraccion.getCupoMaximoClientes()) {
            System.out.println("No hay cupos disponibles en la atracción: " + atraccion.getNombre());
            return false;
        }
        if (!atraccion.usuarioCumpleRestricciones(cliente)) {
            System.out.println("Cliente no cumple las restricciones para la atracción: " + atraccion.getNombre());
            return false;
        }
        return true;
    }
    
    public boolean usarTiquete(Tiquete tiquete, Atraccion atraccion, Usuario cliente) {
        if (tiquete.estaUsado() && !tiquete.getTipo().equals("Básico")) {
            System.out.println("Tiquete ya usado.");
            return false;
        }
        if (!tiquete.esValidoPara(atraccion)) {
            System.out.println("Tiquete no válido para esta atracción.");
            return false;
        }
        if (!atraccion.usuarioCumpleRestricciones(cliente)) {
            System.out.println("Cliente no cumple las restricciones para esta atracción.");
            return false;
        }

        tiquete.marcarComoUsado();
        System.out.println("Acceso permitido a la atracción: " + atraccion.getNombre());
        return true;
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
    
    public void mostrarCatalogoTiquetes() {
        if (tiquetes.isEmpty()) {
            System.out.println("No hay atracciones registradas aún.");
        } else {
            System.out.println("Catálogo de atracciones del parque:");
            for (Tiquete t : tiquetes) {
                System.out.println("- " + t.getCodigo() + " (" + t.getTipo() + ")");
            }
        }
    }
    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados aún.");
        } else {
            System.out.println("Clientes registrados:");
            for (Cliente cliente : clientes) {
                System.out.println("- " + cliente.getNombre() + " " + cliente.getApellido());
            }
        }
    }

    public void mostrarEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados aún.");
        } else {
            System.out.println("Empleados registrados:");
            Map<String, Empleado> empleadosUnicos = new HashMap<>();
            for (Empleado empleado : empleados) {
                String clave = empleado.getNombre() + empleado.getApellido() + empleado.getRol().getNombreRol();
                empleadosUnicos.putIfAbsent(clave, empleado);
            }
            
            for (Empleado emp : empleadosUnicos.values()) {
                System.out.println("- " + emp.getNombre() + " " + emp.getApellido() + 
                                 " (Rol: " + emp.getRol().getNombreRol() + ")");
            }
        }
    }
    public void mostrarAdministradores() {
        List<Administrador> administradores = getAdministradores(); 
        if (administradores.isEmpty()) {
            System.out.println("No hay administradores registrados aún.");
        } else {
            System.out.println("Administradores registrados:");
            for (Administrador admin : administradores) {
                System.out.println("- " + admin.getNombre() + " " + admin.getApellido());
            }
        }
    }

    public Empleado buscarEmpleado(String email, String contrasena) {
        for (Empleado e : empleados) {
            if (e.getLogin().equals(email) && e.getPassword().equals(contrasena)) {
                return e;
            }
        }
        return null;
    }


    
    public boolean usarTiqueteParaAtraccion(Tiquete tiquete, Atraccion atraccion, Usuario usuario) {
        System.out.println("Verificando tiquete: " + tiquete.getCodigo());

        // 1. Validar estado del tiquete
        if (tiquete.estaUsado() && !tiquete.getTipo().equals("Básico")) {
            System.out.println("Tiquete ya usado.");
            return false;
        }

        // 2. Validar compatibilidad tiquete-atracción
        if (!tiquete.esValidoPara(atraccion)) {
            System.out.println("Tiquete no válido para esta atracción.");
            return false;
        }

        // 3. Validar restricciones del usuario (ahora genérico)
        if (!atraccion.usuarioCumpleRestricciones(usuario)) {  // <- Cambio clave aquí
            System.out.println("Usuario no cumple las restricciones para esta atracción.");
            return false;
        }

        // 4. Marcar como usado y permitir acceso
        tiquete.marcarComoUsado();
        System.out.println("Acceso permitido a " + atraccion.getNombre() + " para " + usuario.getNombre());
        return true;
    }

    public Lugar buscarLugarPorNombre(String nombre) {
        for (Lugar lugar : lugares) {
            if (lugar.getNombre().equalsIgnoreCase(nombre)) {
                return lugar;
            }
        }
        return null;
    }


    public Atraccion buscarAtraccionPorNombre(String nombre) {
        for (Atraccion atraccion : atracciones) {
            if (atraccion.getNombre().equalsIgnoreCase(nombre)) {
                return atraccion;
            }
        }
        return null;
    }

    public void agregarAtraccion(Atraccion atraccion) {
        atracciones.add(atraccion);
    }

    public void agregarTiquetes(Tiquete tiquete) {
        tiquetes.add(tiquete);
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

  

    public void registrarTiquete(Tiquete tiquete) {
        this.tiquetes.add(tiquete);
        System.out.println("Tiquete registrado: " + tiquete.getCodigo() + " para el cliente " + tiquete.getComprador().getNombre());
    }

    public void listarAtraccionesYOperadores() {
        System.out.println("\nAtracciones:");
        for (Atraccion atr : atracciones) {
            System.out.println("- " + atr.getNombre() + " (" + atr.getTipo() + ")");
        }

        System.out.println("\nEmpleados operadores de atracción:");
        for (Empleado emp : empleados) {
            if (emp.getRol() instanceof OperadorAtraccion) {
                System.out.println("- " + emp.getNombre());
            }
        }
    }
    
    public Collection<Usuario> getUsuarios() {
        return usuariosPorId.values();
    }
    
    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes); // Retorna copia, no modifica
    }
    
    public ArrayList<Empleado> getEmpleados() {
        return new ArrayList<>(empleados);
    }

    public ArrayList<Administrador> getAdministradores() {
        return new ArrayList<>(administradores);
    }


    public ArrayList<Atraccion> getAtracciones() { return atracciones; }
    public ArrayList<Tiquete> getTiquetes() { return tiquetes; }
    public ArrayList<Lugar> getLugares() { return lugares; }

    public void setAtracciones(ArrayList<Atraccion> atracciones) {
        this.atracciones = atracciones;
    }

    public void setTiquetes(ArrayList<Tiquete> tiquetes) {
        this.tiquetes = tiquetes;
    }

    public void setLugares(ArrayList<Lugar> lugares) {
        this.lugares = lugares;
    }

    public void limpiarAtracciones() {
        this.atracciones.clear();
    }
    
    
//    Persistencia de atracciones
    public void cargarAtraccionesDesdeArchivo(String rutaArchivo) {
        try {
            persistenciaAtracciones.leerAtracciones(rutaArchivo);
            System.out.println("Atracciones cargadas desde archivo correctamente.");
        } catch (IOException | ParseException e) {
            System.out.println("Error al cargar atracciones desde archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void guardarAtraccionesEnArchivo(String rutaArchivo) {
        try {
            persistenciaAtracciones.guardarAtracciones(rutaArchivo);
            System.out.println("Atracciones guardadas en archivo correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar atracciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void cargarUsuariosDesdeArchivo(String ruta) throws IOException {
        try {
            persistenciaUsuarios.leerUsuarios("./data/usuarios.txt");
            System.out.println("Usuarios cargados desde archivo correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios desde archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void guardarUsuariosEnArchivo(String rutaArchivo) {
        try {
            persistenciaUsuarios.guardarUsuarios(rutaArchivo);
            System.out.println("Usuarios guardados en archivo correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
    public boolean permitirAccesoAtraccion(Tiquete tiquete, Atraccion atraccion, Usuario cliente) {
        if (tiquete.estaUsado() && !tiquete.getTipo().equals("Básico")) {
            System.out.println("Tiquete ya usado.");
            return false;
        }
        if (!tiquete.esValidoPara(atraccion)) {
            System.out.println("Tiquete no válido para esta atracción.");
            return false;
        }
        if (!atraccion.usuarioCumpleRestricciones(cliente)) {
            System.out.println("Cliente no cumple las restricciones para esta atracción.");
            return false;
        }

        tiquete.marcarComoUsado();
        return true;
    }

    public Map<String, List<Atraccion>> generarCatalogoTiquetes() {
        Map<String, List<Atraccion>> catalogo = new HashMap<>();

        List<Atraccion> familiar = new ArrayList<>();
        List<Atraccion> oro = new ArrayList<>();
        List<Atraccion> diamante = new ArrayList<>();

        for (Atraccion atraccion : atracciones) {
            NivelExclusividad nivel = atraccion.getNivelExclusividad();

            if (nivel == NivelExclusividad.FAMILIAR) {
                familiar.add(atraccion);
                oro.add(atraccion);
                diamante.add(atraccion);
            } else if (nivel == NivelExclusividad.ORO) {
                oro.add(atraccion);
                diamante.add(atraccion);
            } else if (nivel == NivelExclusividad.DIAMANTE) {
                diamante.add(atraccion);
            }
        }

        catalogo.put("Básico", new ArrayList<>()); 
        catalogo.put("Familiar", familiar);
        catalogo.put("Oro", oro);
        catalogo.put("Diamante", diamante);

        return catalogo;
    }


    public Map<String, Tiquete> crearTiquetesDesdeCatalogo(Map<String, List<Atraccion>> catalogo) {
        Map<String, Tiquete> tiquetes = new HashMap<>();
        TiquetePrefijo factory = new TiquetePrefijo();

        for (String tipo : catalogo.keySet()) {
            List<Atraccion> listaAtracciones = catalogo.get(tipo);
            if (listaAtracciones.isEmpty()) continue;  // no creamos tiquete sin atracciones

            String codigo = factory.generarCodigoTiquete(tipo);
            double precio = calcularPrecio(tipo); // El precio lo calcula el tiquete según el tipo
            Tiquete tiquete;

            // Puedes usar distintas subclases según el tipo
            switch (tipo) {
                case "Familiar":
                    tiquete = new TiqueteFamiliar(codigo, null, precio);
                    break;
                case "Oro":
                    tiquete = new TiqueteOro(codigo, null, precio);
                    break;
                case "Diamante":
                    tiquete = new TiqueteDiamante(codigo, null, precio);
                    break;
                default:
                    tiquete = new TiqueteBasico(codigo, null, precio);
            }

            tiquetes.put(tipo, tiquete);
        }

        return tiquetes;
    }

    private double calcularPrecio(String tipo) {
        // Aquí defines el precio base según tipo
        switch (tipo) {
            case "Familiar": return 100.0;
            case "Oro": return 200.0;
            case "Diamante": return 300.0;
            default: return 50.0;
        }
    }

//    Creo esto para poder filtrar los tiquetes
    public class TiquetePrefijo {
        private int contadorFamiliar = 1;
        private int contadorOro = 1;
        private int contadorDiamante = 1;

        public String generarCodigoTiquete(String tipo) {
            String prefijoId;

            switch (tipo) {
                case "Familiar":
                	prefijoId = "TFAM-" + contadorFamiliar++;
                    break;
                case "Oro":
                	prefijoId = "TORO-" + contadorOro++;
                    break;
                case "Diamante":
                	prefijoId = "TDIA-" + contadorDiamante++;
                    break;
                default:
                	prefijoId = "TGEN-" + (int)(Math.random() * 10000);
            }

            return prefijoId;
        }
    }
    public void cargarAtraccionesDesdeArchivo() {
        String rutaArchivoAtracciones = "./data/atracciones.txt";
        String rutaArchivoTiquetes = "./data/tiquetes.txt";

        File archivoAtracciones = new File(rutaArchivoAtracciones);
        File archivoTiquetes = new File(rutaArchivoTiquetes);

        try {
            if (!archivoAtracciones.exists()) {
                archivoAtracciones.getParentFile().mkdirs();
                archivoAtracciones.createNewFile();
                System.out.println("Archivo atracciones.txt creado porque no existía.");
            }
            if (!archivoTiquetes.exists()) {
                archivoTiquetes.getParentFile().mkdirs();
                archivoTiquetes.createNewFile();
                System.out.println("Archivo de tiquetes creado porque no existía.");
            }

            persistenciaAtracciones.leerAtracciones(rutaArchivoAtracciones);

            Map<String, List<Atraccion>> catalogo = generarCatalogoTiquetes();

            Map<String, Tiquete> nuevosTiquetes = crearTiquetesDesdeCatalogo(catalogo);

            this.tiquetes.clear();
            this.tiquetes.addAll(nuevosTiquetes.values());

            persistenciaTiquetes.guardarTiquetes();

            System.out.println("Atracciones y tiquetes cargados y guardados automáticamente.");

        } catch (IOException | ParseException e) {
            System.out.println("Error al cargar atracciones y tiquetes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Tiquetera getTiquetera() {
        return tiquetera;
    }

    public void setTiquetera(Tiquetera tiquetera) {
        this.tiquetera = tiquetera;
    }
    
    public void setAdministradorActual(Administrador admin) {
        this.administradorActual = admin;
    }

    public Administrador getAdministradorActual() {
        return administradorActual;
    }



}