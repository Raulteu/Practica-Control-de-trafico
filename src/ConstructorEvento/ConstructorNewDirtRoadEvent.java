
package ConstructorEvento;

import Events.Event;
import Events.NewDirtRoadEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewDirtRoadEvent extends ConstructorEvents{

	public ConstructorNewDirtRoadEvent() {
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length", "type"};
		this.valoresPorDefecto = new String[] {"", "","","","","","dirt" };
	}

	@Override
	public Event parser(IniSection section) throws ArgumentException {

		if (section.getTag().equals(this.etiqueta) &&
				section.getValue("type").equals("dirt"))

			return new NewDirtRoadEvent(ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.identificadorValido(section, "src"),
					ConstructorEvents.identificadorValido(section, "dest"),
					ConstructorEvents.parseaInt(section, "max_speed"),
					ConstructorEvents.parseaInt(section, "length"));
		else
			return null;
		
		
	}
	
	

	public String toString() {
		return "Nuevo Camino";
	}
}
