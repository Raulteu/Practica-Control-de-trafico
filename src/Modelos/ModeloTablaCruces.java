package Modelos;


import java.util.List;

import Cruce.GenericJunction;
import Default.RoadMap;
import Events.Event;
import Exceptions.Excepciones;
import MVC.Controller;

public class ModeloTablaCruces extends ModeloTabla<GenericJunction<?>> {

	private static final long serialVersionUID = 1L;
	public ModeloTablaCruces(String[] columnIdEventos, Controller ctrl) {
		super(columnIdEventos, ctrl);
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column) {
		case 0:
			s = lista.get(row).getId();
			break;
		case 1:
			int aux = lista.get(row).semOn();
			if(aux == -1)
				s = "[]";
			else
				s = lista.get(row).verde();
			break;
		case 2:
			if(lista.get(row).semOn() == -1)
				s = "[]";
			else
				s = lista.get(row).rojo();
			break;
		default:
			assert (false);
		}
		return s;
	}
	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
			this.lista = map.getCruces();
			this.fireTableStructureChanged();
	}
	
	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.lista = map.getCruces();
		this.fireTableStructureChanged();
	}
	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.lista = map.getCruces();
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
