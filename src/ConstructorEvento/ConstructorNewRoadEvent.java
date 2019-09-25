package ConstructorEvento;

import Events.Event;
import Events.NewRoadEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewRoadEvent extends ConstructorEvents {

	public ConstructorNewRoadEvent() {
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length"};
		this.valoresPorDefecto = new String[] {"", "", "","","","" };
	}
	
		public Event parser(IniSection section) throws ArgumentException {
			if (!section.getTag().equals(this.etiqueta) ||
					section.getValue("type") != null)
				return null;
			else 
				return new NewRoadEvent(ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
						ConstructorEvents.identificadorValido(section, "id"),
						ConstructorEvents.identificadorValido(section, "src"),
						ConstructorEvents.identificadorValido(section, "dest"),
						ConstructorEvents.parseaInt(section, "max_speed"),
						ConstructorEvents.parseaInt(section, "length"));
						
		}

		

		
		public String toString() {
			return "Nueva Carretera";
		}
}

