package Paneles;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Default.RoadMap;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;
import Exceptions.Excepciones;
import MVC.Controller;

@SuppressWarnings("serial")
public class PanelBarraEstado extends JPanel implements TrafficSimulatorObserver {

	private JLabel infoEjecucion;
	
	public PanelBarraEstado(String msg, Controller ctrl) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.infoEjecucion = new JLabel(msg);
		this.add(this.infoEjecucion);
		this.setBorder(BorderFactory.createBevelBorder(1));
		ctrl.addObserver(this);
	}

	public void setMensaje(String msg) {
		this.infoEjecucion.setText(msg);
	}
	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.infoEjecucion.setText("El simulador ha sido reseteado");
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.infoEjecucion.setText("Eventos aniadidos al simulador");
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.infoEjecucion.setText("Paso: " + (time + 1) + " del Simulador");
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e) {
		this.infoEjecucion.setText("ERROR");;
	}

}
