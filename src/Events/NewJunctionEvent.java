package Events;

import Cruce.GenericJunction;
import Cruce.Junction;
import Default.RoadMap;
import Exceptions.YaExisteException;

public class NewJunctionEvent extends Event {
	protected String id;
	
	
	public NewJunctionEvent(int time, String id) {
		this.id = id;
		this.tiempo = time;
	}

	protected GenericJunction<?> creaCruce() {
		return new Junction(this.id);
	}
	
	@Override
	public void ejecuta(RoadMap map) throws YaExisteException {
		//Crea el cruce y lo aniade al mapa;
		Junction j = new Junction(id);
		map.addJunction(j.getId(), j);
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce";
	}
}
