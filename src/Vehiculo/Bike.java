package Vehiculo;

import java.util.List;

import Cruce.GenericJunction;
import Exceptions.VehiculoNoPosible;
import ini.IniSection;

public class Bike extends Vehicle{

	public Bike(String id, int maxVel, List<GenericJunction<?>> iti) throws VehiculoNoPosible {
		super(id, maxVel, iti);

		
	}
	
	@Override
	public void setTiempoAveria(int n) {
		if (this.vel > this.maxVel/2)
			super.setTiempoAveria(n);
	}
	
	@Override
	public String completar(IniSection is) {
		is.setValue("type", getType());
		return super.completar(is);
	}
	@Override
	public String generaInforme(int tiempo) {
		// TODO Auto-generated method stub
		return super.generaInforme(tiempo);
	}
	
	public String getType() {
		return "bike";
	}
	
	@Override
	public void avanza() {
		super.avanza();
	}

}
