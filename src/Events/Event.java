package Events;

import Default.RoadMap;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;

public abstract class Event {
	protected int tiempo;

	public int getTiempo() {
		return this.tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public abstract void ejecuta(RoadMap map) throws ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible;

}
