package Carretera;

import Cruce.GenericJunction;
import ini.IniSection;

public class DirtRoad extends Road {

	public DirtRoad(String id, int lon, int maxvel, GenericJunction<?> src, GenericJunction<?> dst) {
		super(id, lon, maxvel, src, dst);
	}

	protected int calculaVelocidadBase() { //Se redefine 
		return maxVel;
	}
	
	protected int calculaFactorReduccion(int obstaculos) { //Se redefine
		return 1 + obstaculos;
	}

	public String getType() {
		return "dirt";
	}
	
	public String completar(IniSection is) { 
		
		is.setValue("type", getType());
		return super.completar(is);
	}
}
