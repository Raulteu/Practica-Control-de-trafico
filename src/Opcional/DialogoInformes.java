package Opcional;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Carretera.Road;
import Cruce.GenericJunction;
import Default.RoadMap;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;

import Exceptions.Excepciones;
import MVC.Controller;
import MVC.View;
import Paneles.PanelObjSim;
import Vehiculo.Vehicle;

/**
 * @author Joy
 *
 */
@SuppressWarnings("serial")
public class DialogoInformes extends JDialog implements TrafficSimulatorObserver {
	protected static final char TECLALIMPIAR = 'c';
	//private PanelBotones panelBotones;
	private PanelObjSim<Vehicle> panelVehiculos;
	private PanelObjSim<Road> panelCarreteras;
	private PanelObjSim<GenericJunction<?>> panelCruces;
	boolean estado;
	
	public DialogoInformes(View view, Controller ctrl) {
		super(view);
		ctrl.addObserver(this);
		initGUI(view);
		this.setTitle("Selecciona los elementos de simulacion");
	}
	private void initGUI(View view) {
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancel(view);
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.setLayout(new BorderLayout());
		JPanel labels = new JPanel(new FlowLayout());
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel("<html>" +"Selecciona los Objetos de Simulacion de los que quieres generar un informe."+"<br/>" +"Usa 'c' para deseleccionar todos" +"<br/>"+"Usa Ctrl + A para seleccionar todos"+"<html>");
		JPanel panel = new JPanel(new GridLayout(1, 3));

		this.panelVehiculos = new PanelObjSim<Vehicle>("Vehiculos");
		this.panelCarreteras = new PanelObjSim<Road>("Carreteras");
		this.panelCruces = new PanelObjSim<GenericJunction<?>>("Cruces");
		
		JButton cancel = new JButton("Cancelar");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				estado = false;
				cancel(view);
			}
		});
		JButton generate = new JButton("Generar");
		generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				estado = true;
				view.getSelected();
			}
		});
		this.add(panel,BorderLayout.CENTER);
		labels.add(label, BorderLayout.PAGE_START);
		this.add(labels,BorderLayout.PAGE_START);
		this.add(botones, BorderLayout.SOUTH);
		botones.add(cancel);
		botones.add(generate);
		panel.add(panelCarreteras);
		panel.add(panelCruces);
		panel.add(panelVehiculos);
		this.pack();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

	}

		
	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
	}
	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.setMapa(map);		
	}
	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.setMapa(map);		
	}
	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.setMapa(map);		
	}
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e) {
		// TODO Auto-generated method stub
	}
	
	
	public void mostrar(View view) { 
		this.setVisible(true);
		view.setEnabled(false);
	}
	public void cancel(View view) { 
		this.setVisible(false);	
		view.setEnabled(true);
		}
	
	private void setMapa(RoadMap mapa) {
		this.panelVehiculos.setList(mapa.getVehiculos());
		this.panelCarreteras.setList(mapa.getCarreteras());
		this.panelCruces.setList(mapa.getCruces());
	}
	public List<Vehicle> getVehiculosSeleccionados() {
		return this.panelVehiculos.getSelectedItems();
	}
	public List<Road> getCarreterasSeleccionadas() {
		return this.panelCarreteras.getSelectedItems();
	}
	public List<GenericJunction<?>> getCrucesSeleccionados() {
		return this.panelCruces.getSelectedItems();
	}
	
	public boolean open() {
		return estado;
	}
	public static char getTeclalimpiar() {
		return TECLALIMPIAR;
	}
	
	
}