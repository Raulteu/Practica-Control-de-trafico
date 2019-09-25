package Carretera;

import Cruce.GenericJunction;
import ini.IniSection;

public class FreewayRoad extends Road{
	private int lanes;

	public FreewayRoad(String id, int lon, int maxvel, GenericJunction<?> src, GenericJunction<?> dst, int lanes) {
		super(id, lon, maxvel, src, dst);
		this.lanes = lanes;
	}
	
	
	
	protected int calculaVelocidadBase() { //Se redefine 

		int velocidadBase = maxVel;
		int div = 1;
		if (listaVehiculos.size() > 1)
			div = listaVehiculos.size();
		velocidadBase = (maxVel * lanes / div) + 1;
		if (maxVel < velocidadBase)
			velocidadBase = maxVel;
		
		return velocidadBase;
	}
	
	@Override
	protected int calculaFactorReduccion(int obstacles) {
		return obstacles < this.lanes ? 1 : 2;
	}

	public String getType() {
		return "lanes";
	}
	
	public String completar(IniSection is) { 
		is.setValue("type", getType());
		return super.completar(is);
	}

	
}
