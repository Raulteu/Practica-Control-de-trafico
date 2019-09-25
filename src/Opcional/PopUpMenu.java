package Opcional;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


import ConstructorEvento.ConstructorEvents;
import Default.RoadMap;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;
import Events.ParseEvent;
import Exceptions.ArgumentException;
import Exceptions.ErrorDeSimulacion;
import Exceptions.Excepciones;
import MVC.View;

public class PopUpMenu extends JPopupMenu implements TrafficSimulatorObserver {


	private static final long serialVersionUID = 1L;
public PopUpMenu(View mainWindow) {

	this.creaMenuPlantillas(mainWindow);
	this.addSeparator();
	this.creaMenuFicheros(mainWindow);
	
	pack();
}
	private void creaMenuPlantillas(View mainWindow) {
		JMenu plantillas = new JMenu("Nueva Plantilla");
		this.add(plantillas);
		
		for(ConstructorEvents ce : ParseEvent.getConstructoresEventos()) {
			 JMenuItem mi = new JMenuItem(ce.toString());
			 mi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.inserta(ce.template());
					
				}
			});
			plantillas.add(mi);
		 }
	
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
	
	private void creaMenuFicheros(View mainWindow) {
		JMenuItem cargar = new JMenuItem("Cargar");
		cargar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.cargaFichero();
				} catch (ErrorDeSimulacion | ArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JMenuItem guardar = new JMenuItem("Guardar");
		guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaEvent();
				
			}
		});
		JMenuItem clear = new JMenuItem("Limpiar");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clear();
			}
		});

		this.add(cargar);
		this.add(guardar);
		this.add(clear);
	}

}
