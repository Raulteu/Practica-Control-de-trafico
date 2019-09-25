
package ConstructorEvento;

import Events.Event;
import Events.NewLanesEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewLanesEvent extends ConstructorEvents {

	public ConstructorNewLanesEvent() {
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length", "lanes", "type"};
		this.valoresPorDefecto = new String[] {"", "", "","","","","","lanes"};
	}
	
	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if (section.getTag().equals(this.etiqueta) &&
				section.getValue("type").equals("lanes"))

			return new NewLanesEvent(ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.identificadorValido(section, "src"),
					ConstructorEvents.identificadorValido(section, "dest"),
					ConstructorEvents.parseaInt(section, "max_speed"),
					ConstructorEvents.parseaInt(section, "length"), 
					ConstructorEvents.parseaIntNoNegativo(section, "lanes", 1));
		else 
			return null;
	}
	
	
	
	public String toString() {
		return "Nueva Autopista";
	}

}
