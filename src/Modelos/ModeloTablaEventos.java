package Modelos;

import java.util.List;

import Default.RoadMap;
import Events.Event;
import Exceptions.Excepciones;
import MVC.Controller;

@SuppressWarnings("serial")
public class ModeloTablaEventos extends ModeloTabla<Event> {

	public ModeloTablaEventos(String[] columnIdEventos, Controller ctrl) {
		super(columnIdEventos, ctrl);

	}
	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column) {
		case 0:
			s= row;
			break;
		case 1:
			s = lista.get(row).getTiempo();
			break;
		case 2:
			s= lista.get(row).toString();
			break;
		default:
			assert (false);
		}
		return s;
	}
	
	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.lista = events;
		this.fireTableStructureChanged();
	}
	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.lista = events;
		this.fireTableStructureChanged();
	}
	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.lista = events;
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
