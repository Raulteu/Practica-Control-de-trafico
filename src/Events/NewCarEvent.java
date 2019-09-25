package Events;

import java.util.List;
import java.util.Random;

import Vehiculo.Car;
import Vehiculo.Vehicle;
import Cruce.GenericJunction;
import Default.RoadMap;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;

public class NewCarEvent extends NewVehicleEvent{
	
	private int resistenciaKm;
	private double probAveria;
	private int durationMaxAveria;
	private Random numAleatorio;

	public NewCarEvent(int tiempo, String id, int maxVel, String string, int resistencia, double probAveria, int duracionMaxAveria, long seed) {
		super(tiempo, id, maxVel, string);
		this.resistenciaKm = resistencia;
		this.probAveria = probAveria;
		this.durationMaxAveria = duracionMaxAveria;
		this.numAleatorio = new Random(seed);
	}
	
	@Override
	public void ejecuta(RoadMap map)throws ItinerarioIncorrecto, NoExisteException, YaExisteException, VehiculoNoPosible {
		List<GenericJunction<?>> itinerario = ParseRoad.parseaListaCruces(itinerary, map);
		
		if ((itinerario == null) || (itinerario.size() < 2)) {
			throw new ItinerarioIncorrecto("El vehiculo " + id + " tiene un itinerario incorrecto");
		}
		else {
			Vehicle v = new Car(this.id, this.velMax, itinerario,this.resistenciaKm ,this.probAveria,this.durationMaxAveria, this.numAleatorio);
			map.addVehiculo(id, v);
		}
	}
	
	public String toString() {
		return "Nuevo Coche";
	}
}
