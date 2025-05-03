package Atracciones;

import java.util.ArrayList;
import java.util.List;

import Restricciones.Restriccion;

public class Mecanica extends Atraccion {

    private int limiteMaximoAltura;
    private int limiteMinimoAltura;
    private int limiteMaximoPeso;
    private int limiteMinimoPeso;
    private String contraindicaciones;
    private int nivelDeRiesgo;  
    private boolean enTemporada;  

    public Mecanica(String nombre, int cupoMaximoClientes, int cupoMinimoEncargados, ArrayList<Restriccion> restricciones, NivelExclusividad nivelExclusividad,
                    int limiteMaximoAltura, int limiteMinimoAltura, int limiteMaximoPeso, int limiteMinimoPeso, 
                    String contraindicaciones, int nivelDeRiesgo, boolean enTemporada) {
        super(nombre, cupoMaximoClientes, cupoMinimoEncargados, restricciones, nivelExclusividad);
        this.limiteMaximoAltura = limiteMaximoAltura;
        this.limiteMinimoAltura = limiteMinimoAltura;
        this.limiteMaximoPeso = limiteMaximoPeso;
        this.limiteMinimoPeso = limiteMinimoPeso;
        this.contraindicaciones = contraindicaciones;
        this.nivelDeRiesgo = nivelDeRiesgo;
        this.enTemporada = enTemporada;
    }

    @Override
    public String getTipo() {
        return "Mecánica";
    }

    public int getLimiteMaximoAltura() { return limiteMaximoAltura; }
    public int getLimiteMinimoAltura() { return limiteMinimoAltura; }
    public int getLimiteMaximoPeso() { return limiteMaximoPeso; }
    public int getLimiteMinimoPeso() { return limiteMinimoPeso; }
    public String getContraindicaciones() { return contraindicaciones; }
    public int getNivelDeRiesgo() { return nivelDeRiesgo; }
    public boolean isEnTemporada() { return enTemporada; }
    public String getDetalles() {
        return limiteMaximoAltura + "," + limiteMinimoAltura + "," + limiteMaximoPeso + "," + limiteMinimoPeso + "," +
               contraindicaciones + "," + nivelDeRiesgo + "," + enTemporada;
    }

	@Override
	public String serializarDetalles() {
		return limiteMaximoAltura + "," + limiteMinimoAltura + "," + limiteMaximoPeso + "," + limiteMinimoPeso + "," +
	               contraindicaciones + "," + nivelDeRiesgo + "," + enTemporada;
	}
}
