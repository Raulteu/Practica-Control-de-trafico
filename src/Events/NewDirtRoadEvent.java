package Events;

import Carretera.DirtRoad;
import Carretera.Road;
import Cruce.GenericJunction;
import Default.RoadMap;
import Exceptions.NoExisteException;
import Exceptions.YaExisteException;

public class NewDirtRoadEvent extends NewRoadEvent{		
		public NewDirtRoadEvent(int tiempo, String id, String principio, String fin, int max_speed, int length) {
			super(tiempo, id, principio, fin, max_speed, length);
		}
		
		@Override
		public void ejecuta(RoadMap map) throws YaExisteException, NoExisteException {		
			//crea la carretera
			Road r = new DirtRoad(id, longitud, velMax, map.getCruce(origen), map.getCruce(destino));
			
			//Obten cruce origen y cruce destino del mapa
			//a�ade al mapa la carretera 	
			map.addCarretera(r.getId(), map.getCruce(this.origen), r, map.getCruce(this.destino));
		}
	
		@Override
		protected Road creaCarretera(GenericJunction<?> cruceOrigen, GenericJunction<?> cruceDestino) {
			return new DirtRoad(id, longitud, velMax, cruceOrigen, cruceDestino);
		}
		
		public String toString() {
			return "Nueva Camino";
		}
}

