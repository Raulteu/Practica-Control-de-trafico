package Events;

import Cruce.GenericJunction;
import Cruce.RoundRobinJunction;
import Default.RoadMap;
import Exceptions.YaExisteException;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {
	
	protected int min;
	protected int max;

	public NewRoundRobinJunctionEvent(int time, String id, int minIntervalo, int maxIntervalo) {
		super(time, id);
		this.min = minIntervalo;
		this.max = maxIntervalo;
	}
	
	@Override
	public void ejecuta(RoadMap map) throws YaExisteException {
		//Crea el cruce y lo aniade al mapa;
		GenericJunction<?> j = new RoundRobinJunction(id, this.min, this.max);
		map.addJunction(j.getId(), j);
	}

	protected GenericJunction<?> creaCruce() {
		return new RoundRobinJunction(this.id, this.min, this.max);
		}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Circular";
	}
}
