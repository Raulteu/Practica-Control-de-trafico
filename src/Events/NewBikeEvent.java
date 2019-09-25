package Events;

import java.util.List;

import Vehiculo.Bike;
import Vehiculo.Vehicle;
import Cruce.GenericJunction;
import Default.RoadMap;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;

public class NewBikeEvent extends NewVehicleEvent{

	public NewBikeEvent(int tiempo, String id, int maxVel, String string) {
		super(tiempo, id, maxVel, string);
	}

	
	@Override
	public void ejecuta(RoadMap map) throws ItinerarioIncorrecto, NoExisteException, YaExisteException, VehiculoNoPosible {
		List<GenericJunction<?>> itinerario = ParseRoad.parseaListaCruces(itinerary, map);
		if ((itinerario == null) || (itinerario.size() < 2)) {
			throw new ItinerarioIncorrecto("El vehiculo " + id + " tiene un itinerario incorrecto");
		}
		else {
			Vehicle v = this.creaVehiculo(itinerario);
			map.addVehiculo(id, v);
			
		}
		
	}
	
	protected Vehicle creaVehiculo(List<GenericJunction<?>> itinerario) throws VehiculoNoPosible {
		return new Bike(id, velMax, itinerario);
	}
	
	@Override
	public String toString() {
		return "Nueva Bici";
	}
	
}
