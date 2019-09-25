package Events;

import Cruce.GenericJunction;
import Cruce.MostCrowedJunction;
import Default.RoadMap;
import Exceptions.YaExisteException;

public class NewMostCrowedJunctionEvent extends NewJunctionEvent{

	@SuppressWarnings("unused")
	private int unidadesDeTiempoUsadas;
	@SuppressWarnings("unused")
	private int intervalo;
	
	public NewMostCrowedJunctionEvent(int time, String id, int unidadesDeTiempo, int intervalo) {
		super(time, id);
		this.unidadesDeTiempoUsadas =unidadesDeTiempo;
		this.intervalo = intervalo;
	}

	@Override
	public void ejecuta(RoadMap map) throws YaExisteException {
		//Crea el cruce y lo aniade al mapa;
		GenericJunction<?> j = new MostCrowedJunction(id);
		map.addJunction(j.getId(), j);
	}

	@Override
	protected GenericJunction<?> creaCruce() {
		return new MostCrowedJunction(this.id);
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Congestionado";
	}
}
