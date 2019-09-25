package Paneles;

import java.util.List;

import Default.RoadMap;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;
import Exceptions.Excepciones;
import MVC.Controller;

@SuppressWarnings("serial")
public class PanelInformes extends PanelAreaTexto implements TrafficSimulatorObserver{
	
	public PanelInformes(String titulo, boolean editable, Controller ctrl) {
		super(titulo, editable);
		ctrl.addObserver(this);
	}
	
	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e) {
		// TODO Auto-generated method stub
	}

}
