package ConstructorEvento;

import Events.Event;
import Events.NewFaultyVehicle;
import Exceptions.ArgumentException;
import ini.*;

public class ConstructorNewFaultyVehicleEvent extends ConstructorEvents {
	
	public ConstructorNewFaultyVehicleEvent() {
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] {"time", "vehicles", "duration"};
		this.valoresPorDefecto = new String[] {"", "", ""};
		
	}

	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if (!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null)
				return null;
		else
			return new NewFaultyVehicle(
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					ConstructorEvents.identificadorValido(section, "vehicles"),
					ConstructorEvents.parseaIntNoNegativo(section, "duration", 0));
	}
	
	
	
	public String toString() {
		return "Nueva Averia";
	}
}
