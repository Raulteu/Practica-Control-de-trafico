package Modelos;

import java.util.List;

import Carretera.Road;
import Default.RoadMap;
import Events.Event;
import Exceptions.Excepciones;
import MVC.Controller;

@SuppressWarnings("serial")
public class ModeloTablaCarreteras extends ModeloTabla<Road> {

	public ModeloTablaCarreteras(String[] columnIdEventos, Controller ctrl) {
		super(columnIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column) {
		case 0:
			s = lista.get(row).getId();
			break;
		case 1:
			s = lista.get(row).getSrcJc().getId();
			break;
		case 2:
			s= lista.get(row).getDstJc().getId();
			break;
		case 3:
			s= lista.get(row).getLongitud();
			break;
		case 4:
			s = lista.get(row).getMaxVel();
			break;
		case 5:
			s = lista.get(row).getListaVehiculos();
			break;
		default:
			assert (false);
		}
		return s;
	}
	
	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.lista = map.getCarreteras();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.lista = map.getCarreteras();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.lista = map.getCarreteras();
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
