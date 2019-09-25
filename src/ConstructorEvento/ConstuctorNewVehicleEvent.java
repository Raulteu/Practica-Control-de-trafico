package ConstructorEvento;

import Events.Event;
import Events.NewVehicleEvent;
import Exceptions.ArgumentException;
import ini.*;

public class ConstuctorNewVehicleEvent extends ConstructorEvents{
	
	public ConstuctorNewVehicleEvent() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "max_speed", "itinerary" };
		this.valoresPorDefecto = new String[] {"", "", "", ""};
		
	}

	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null)
				return null;
		else
			return new NewVehicleEvent(
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.parseaInt(section, "max_speed"),
					ConstructorEvents.identificadorValido(section, "itinerary"));

	}
	
	
	
	public String toString() {
		return "Nuevo Vehiculo";
	}
}
