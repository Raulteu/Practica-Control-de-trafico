package Events;

import Carretera.Road;
import Cruce.GenericJunction;
import Default.RoadMap;
import Exceptions.NoExisteException;
import Exceptions.YaExisteException;

public class NewRoadEvent extends Event{

	protected String id;
	protected int velMax;
	protected int longitud;
	protected String origen;
	protected String destino;



	public NewRoadEvent(int tiempo, String id, String principio, String fin, int max_speed, int length) {
		
		this.tiempo = tiempo;
		this.origen = principio;
		this.destino = fin;
		this.longitud = length;
		this.velMax = max_speed;
		this.id	= id;
	}

	@Override
	public void ejecuta(RoadMap map) throws YaExisteException, NoExisteException {		
		//crea la carretera
		GenericJunction<?> cruceOrigen = map.getCruce(this.origen);
		GenericJunction<?> cruceDestino = map.getCruce(this.destino);
		Road r = creaCarretera(cruceOrigen, cruceDestino);
		
		//Obten cruce origen y cruce destino del mapa
		//aniade al mapa la carretera 	
		map.addCarretera(r.getId(), map.getCruce(this.origen), r, map.getCruce(this.destino));
	}
	
	protected Road creaCarretera(GenericJunction<?> cruceOrigen, GenericJunction<?> cruceDestino) {
			return new Road(this.id, this.longitud, this.velMax, cruceOrigen, cruceDestino);
		}
	
	public String toString(){
		return "Nueva Carretera";
	}
}
