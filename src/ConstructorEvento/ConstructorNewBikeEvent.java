
package ConstructorEvento;

import Events.Event;
import Events.NewBikeEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewBikeEvent extends ConstructorEvents{

	public ConstructorNewBikeEvent() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "max_speed", "itinerary", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "bike"};
	}

	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if (section.getTag().equals(this.etiqueta) &&
				section.getValue("type").equals("bike"))
			return new NewBikeEvent(
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.parseaInt(section, "max_speed"),
					ConstructorEvents.identificadorValido(section, "itinerary"));
		else
			return null;
	}
	
	
	public String toString() {
		return "Nueva Bicicleta";
	}

}

