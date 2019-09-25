package Events;

import Default.RoadMap;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;

public class NewFaultyVehicle extends Event {
	
	protected String []vehiculosAveriados;
	protected int duracion;
	
	public NewFaultyVehicle(int tiempo, String porAveriar, int duracion) {
		this.tiempo = tiempo;
		this.vehiculosAveriados = porAveriar.split(",");
		this.duracion = duracion;
	}
	

	@Override
	public void ejecuta(RoadMap map) throws ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible {
		for (int i = 0; i < vehiculosAveriados.length; i++) {
			map.makeVehicleFaulty(vehiculosAveriados[i], duracion);
		}		
	}

	public String toString() {
		return "Nuevo vehiculo averiado";
	}
}
