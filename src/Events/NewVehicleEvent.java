package Events;

import java.util.List;

import Vehiculo.Vehicle;
import Cruce.GenericJunction;
import Default.*;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;

public class NewVehicleEvent extends Event{

	protected String id;
	protected int velMax;
	protected String[] itinerary;

	public NewVehicleEvent(int tiempo, String v_id, int maxVel, String string) {

		this.tiempo = tiempo;
		this.velMax = maxVel;
		this.id = v_id;
		itinerary = string.split(",");
		}


	@Override
	public void ejecuta(RoadMap map) throws ItinerarioIncorrecto, NoExisteException, YaExisteException, VehiculoNoPosible {	
		List<GenericJunction<?>> itinerario = ParseRoad.parseaListaCruces(this.itinerary, map);
		//Si es null o tiene menos de dos cruces se lanza una excepcion
		if ((itinerario == null) || (itinerario.size() < 2)) {
			throw new ItinerarioIncorrecto("El vehiculo " + id + " tiene un itinerario incorrecto");
		}
		//en otro caso, crear el vehiculo y aniadirlo al mapa
		else {
			
			Vehicle v = new Vehicle(id, velMax, itinerario);
			
			//Aniadirlo al mapa
			map.addVehiculo(v.getId(), v);
		}		
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "Nuevo Vehiculo";
	}
	
}
