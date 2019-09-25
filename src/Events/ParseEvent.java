package Events;

import ConstructorEvento.ConstructorEvents;
import ConstructorEvento.ConstructorNewBikeEvent;
import ConstructorEvento.ConstructorNewCarEvent;
import ConstructorEvento.ConstructorNewDirtRoadEvent;
import ConstructorEvento.ConstructorNewFaultyVehicleEvent;
import ConstructorEvento.ConstructorNewJunctionEvent;
import ConstructorEvento.ConstructorNewLanesEvent;
import ConstructorEvento.ConstructorNewMCJunctionEvent;
import ConstructorEvento.ConstructorNewRRJunctionEvent;
import ConstructorEvento.ConstructorNewRoadEvent;
import ConstructorEvento.ConstuctorNewVehicleEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ParseEvent {

	private static ConstructorEvents[] eventos = {
			new ConstuctorNewVehicleEvent(),
			new ConstructorNewRoadEvent(),
			new ConstructorNewJunctionEvent(),
			new ConstructorNewFaultyVehicleEvent(),
			new ConstructorNewBikeEvent(),
			new ConstructorNewCarEvent(),
			new ConstructorNewDirtRoadEvent(),
			new ConstructorNewLanesEvent(),
			new ConstructorNewMCJunctionEvent(),
			new ConstructorNewRRJunctionEvent()

	};
	
	//bucle de prueba y error
	public static Event parseaEvent(IniSection section) throws ArgumentException {
		int i = 0;
		boolean seguir = true;
		Event e = null;
		while(i < ParseEvent.eventos.length && seguir) {
			//ConstructorEventos contiene el metodo parse(sec)
			e = ParseEvent.eventos[i].parser(section);
			if (e != null)
				seguir = false;
			else
				i++;
		}
		return e;
	}

	public static ConstructorEvents[] getConstructoresEventos() {
		return eventos;
	
	}
	
}
