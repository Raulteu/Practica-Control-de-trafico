package Vehiculo;

import java.util.List;
import java.util.Random;

import Cruce.GenericJunction;
import Exceptions.VehiculoNoPosible;
import ini.IniSection;

public class Car extends Vehicle{
	
	protected int kmDesdeUltAveria;
	protected int resistenciaKm;
	protected double probAveria;
	protected int durationMaxAveria;
	protected Random numAleatorio;
	

	public Car(String id, int maxVel, List<GenericJunction<?>> iti, int resistencia, double prob, int duracion, Random numAleatorio) throws VehiculoNoPosible {
		super(id, maxVel, iti);

		this.numAleatorio = numAleatorio;
		this.resistenciaKm = resistencia;
		this.durationMaxAveria = duracion;
		this.probAveria = prob;
	}
	
	@Override
	public void avanza() {		
		if (faulty > 0) {
			this.kmDesdeUltAveria = this.kilometros;
		}
		else if(this.kilometros - this.kmDesdeUltAveria >= resistenciaKm &&
				numAleatorio.nextDouble() < probAveria) {
			super.setTiempoAveria(numAleatorio.nextInt(durationMaxAveria) + 1);
		}
		super.avanza();	
	}
	
	public String getType() {
		return "car";
	}
	
	@Override
	public String completar(IniSection is) {
		is.setValue("type", this.getType());
		return super.completar(is);
	}
}
