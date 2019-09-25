package ConstructorEvento;

import Events.Event;
import Events.NewCarEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewCarEvent extends ConstructorEvents{

	public ConstructorNewCarEvent() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "max_speed", "itinerary", "resistance", "fault_probability", "max_fault_duration", "seed", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", "", "", "car"};
		}

	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if (section.getTag().equals(this.etiqueta) &&
				section.getValue("type").equals("car"))
			return new NewCarEvent(
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.parseaInt(section, "max_speed"),
					ConstructorEvents.identificadorValido(section, "itinerary"),
					ConstructorEvents.parseaIntNoNegativo(section, "resistance", 0),
					ConstructorEvents.parseaDouble(section, "fault_probability", 0),
					ConstructorEvents.parseaIntNoNegativo(section, "max_fault_duration", 0),
					ConstructorEvents.parseaSemilla(section, "seed", (int) System.currentTimeMillis()));
		else
			return null;
		}
	
	
	public String toString() {
		return "Nuevo Coche";
	}
	

}
