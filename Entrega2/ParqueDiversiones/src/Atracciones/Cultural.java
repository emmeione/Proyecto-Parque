package Atracciones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Restricciones.Restriccion;


public class Cultural extends Atraccion {

    private String tipoDeCultural;  
    private Date fecha;  
    private boolean esReguladoPorClima;  
    private boolean enTemporada;  

    public Cultural(String nombre, int cupoMaximoClientes, int cupoMinimoEncargados, ArrayList<Restriccion> restricciones, NivelExclusividad nivelExclusividad,
                    String tipoDeCultural, Date fecha, boolean esReguladoPorClima, boolean enTemporada) {
        super(nombre, cupoMaximoClientes, cupoMinimoEncargados, restricciones, nivelExclusividad);
//        Es que puede ser algún show de talentos, espectáculo, etc
        this.tipoDeCultural = tipoDeCultural;
        this.fecha = fecha;
        this.esReguladoPorClima = esReguladoPorClima;
        this.enTemporada = enTemporada;
    }

    @Override
    public String getTipo() {
        return "Cultural";
    }


    public String getTipoDeCultura() { return tipoDeCultural; }
    public Date getFechaEspectaculo() { return fecha; }
    public boolean esReguladoPorClima() { return esReguladoPorClima; }
    public boolean isEnTemporada() { return enTemporada; }
    public String getDetalles() {
        return fecha + "," + esReguladoPorClima + "," + enTemporada + tipoDeCultural;
    }

	@Override
	public String serializarDetalles() {
		// TODO Auto-generated method stub
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		return formato.format(fecha) + "," + esReguladoPorClima + "," + enTemporada + tipoDeCultural;
	}
    
    
}
