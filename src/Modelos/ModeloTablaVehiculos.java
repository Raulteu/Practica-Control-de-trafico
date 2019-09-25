package Modelos;

import java.util.List;

import Default.RoadMap;
import Events.Event;
import Exceptions.Excepciones;
import MVC.Controller;
import Vehiculo.Vehicle;

@SuppressWarnings("serial")
public class ModeloTablaVehiculos extends ModeloTabla<Vehicle> {
	
	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column) {
		case 0:
			s = lista.get(row).getId();
			break;
		case 1:
			if (lista.get(row).getLlegado())
				s = "arrived";
			else
				s = lista.get(row).getRoad().getId();
			break;
		case 2:
			s= lista.get(row).getPos();
			break;
		case 3:
			s= lista.get(row).getVel();
			break;
		case 4:
			s= lista.get(row).getKilometros();
			break;
		case 5:
			s= lista.get(row).getFaulty();
			break;
		case 6:
			s= lista.get(row).getItinerario();
			break;
		default:
			assert (false);
		}
		return s;
	}
	
	public ModeloTablaVehiculos(String[] columnIdEventos, Controller ctrl) {
		super(columnIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}
	 @Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.lista = map.getVehiculos();
		this.fireTableStructureChanged();
	}
	 
	 @Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.lista = map.getVehiculos();
		this.fireTableStructureChanged();
	}
	 
	 @Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.lista = map.getVehiculos();
		this.fireTableStructureChanged();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e) {
		// TODO Auto-generated method stub
	}
}
