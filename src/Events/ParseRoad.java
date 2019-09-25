package Events;

import java.util.ArrayList;
import java.util.List;

import Cruce.GenericJunction;
import Default.RoadMap;
import Exceptions.NoExisteException;

public class ParseRoad {

	public static List<GenericJunction<?>> parseaListaCruces(String[] itinerary, RoadMap map) throws NoExisteException {
		List<GenericJunction<?>> aux = new ArrayList<>();
		for (int i = 0; i < itinerary.length; i++) {
			if (map.getCruce(itinerary[i]).getId().equals(itinerary[i])) {
				aux.add(map.getCruce(itinerary[i]));
			}
			else return null;
		}
		return aux;
	}

}
