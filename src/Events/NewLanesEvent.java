package Events;

import Carretera.FreewayRoad;
import Carretera.Road;
import Cruce.GenericJunction;
import Default.RoadMap;
import Exceptions.NoExisteException;
import Exceptions.YaExisteException;

public class NewLanesEvent extends NewRoadEvent{
	
	private int lanes;
	
	public NewLanesEvent(int tiempo, String id, String principio, String fin, int max_speed, int length, int lanes) {
		super(tiempo, id, principio, fin, max_speed, length);
		this.lanes = lanes;
	}
	
	@Override
	public void ejecuta(RoadMap map) throws YaExisteException, NoExisteException {		
		//crea la carretera
		Road r = this.creaCarretera(map.getCruce(origen), map.getCruce(destino));
		
		//Obten cruce origen y cruce destino del mapa
		//aniade al mapa la carretera 	
		map.addCarretera(r.getId(), map.getCruce(this.origen), r, map.getCruce(this.destino));

	}

	protected Road creaCarretera(GenericJunction<?> cruceOrigen, GenericJunction<?> cruceDestino) {
		return new FreewayRoad(id, longitud, velMax, cruceOrigen, cruceDestino, this.lanes);
	}
	
	@Override
	public String toString() {
		return "Nueva Autopista";
	}
}
