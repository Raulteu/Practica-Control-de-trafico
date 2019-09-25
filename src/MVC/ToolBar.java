package MVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import Default.RoadMap;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;
import Exceptions.ArgumentException;
import Exceptions.ErrorDeSimulacion;
import Exceptions.Excepciones;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar implements TrafficSimulatorObserver {
	
	public JSpinner steps;
	private JTextField time;
	public JSpinner delay;
	private List<JButton> lista;
	
	public ToolBar(View mainWindow, Controller ctrl) {
		super();
		lista = new ArrayList<JButton>();
		ctrl.addObserver(this);
		this.setFloatable(false);

		
		
		//CARGAR FICHERO
		JButton loadButton = new JButton();

		loadButton.setToolTipText("Carga un fichero de eventos");
		loadButton.setIcon(new ImageIcon("src\\MVC\\icons\\open.png"));
		loadButton.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					mainWindow.cargaFichero();
				} catch (ErrorDeSimulacion | ArgumentException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		lista.add(loadButton);
		this.add(loadButton);
		
		//GUARDAR EVENTOS EN FICHERO
		JButton saveEvent = new JButton();
		saveEvent.setToolTipText("Guarda los eventos");
		saveEvent.setIcon(new ImageIcon("src\\MVC\\icons\\save.png"));

		saveEvent.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaEvent();		
			}
		});
		lista.add(saveEvent);
		this.add(saveEvent);
		
		//LIMPIAR EDITOR DE EVENTOS
		JButton clear = new JButton();
		clear.setToolTipText("Limpiar el editor de Eventos");
		clear.setIcon(new ImageIcon("src\\MVC\\icons\\clear.png"));
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.getPanelEvento().setTexto("");
				
			}
		});
		lista.add(clear);
		this.add(clear);
		
		this.addSeparator();
		
		
		//CARGAR EVENTOS AL SIMULADOR
		JButton loadEvent = new JButton();
		loadEvent.setToolTipText("Carga los eventos");
		loadEvent.setIcon(new ImageIcon("src\\MVC\\icons\\events.png"));
		
		loadEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					mainWindow.cargaEvento();
				} catch (ErrorDeSimulacion | ArgumentException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		lista.add(loadEvent);
		this.add(loadEvent);
		
		//EJECUTAR
		JButton executeButton = new JButton();
		executeButton.setToolTipText("Ejecuta los pasos seleccionados");
		executeButton.setIcon(new ImageIcon("src\\MVC\\icons\\jugar.png"));
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.ejecuta();
				for (JButton jButton : lista) {
					jButton.setEnabled(false);
				}
				steps.setEnabled(false);
				delay.setEnabled(false);
				time.setEnabled(false);
			}
		});
		lista.add(executeButton);
		this.add(executeButton);
		
		//Parar
		JButton stopButton = new JButton();
		stopButton.setToolTipText("Detiene la simulacion");
		stopButton.setIcon(new ImageIcon("src\\MVC\\icons\\stop.png"));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.interrupt();
				for (JButton jButton : lista) {
					jButton.setEnabled(true);
				}
				steps.setEnabled(true);
				delay.setEnabled(true);
				time.setEnabled(true);
			}
		});
		
		this.add(stopButton);
		
		
		//RESET DEL SIMULADOR
		JButton resetButton = new JButton();
		resetButton.setToolTipText("Reinicia el simulador");
		resetButton.setIcon(new ImageIcon("src\\MVC\\icons\\reset.png"));

		resetButton.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.reiniciar();
			
			}
		});
		lista.add(resetButton);
		this.add(resetButton);
		
		this.addSeparator();

		//Delay
		this.add(new JLabel("Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 1, 10000, 1));
		this.delay.setToolTipText("Delay");
		this.delay.setMaximumSize(new Dimension(40, 40));
		this.delay.setMinimumSize(new Dimension(40, 40));
		this.delay.setValue(1);
		this.add(delay);
		
		this.addSeparator();		
		
		//SELECTOR DE PASOS
		this.add(new JLabel("Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("Pasos a ejecutar: 1 - 1000");
		this.steps.setMaximumSize(new Dimension(40, 40));
		this.steps.setMinimumSize(new Dimension(40, 40));
		this.steps.setValue(1);
		this.add(steps);
		
		this.addSeparator();

		
		this.add(new JLabel("Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(30, 30));
		this.time.setMinimumSize(new Dimension(30, 30));
		this.time.setEditable(false);
		this.add(time);
		

		this.addSeparator();

		//SELECCIONAR INFORMES A GUARDAR
		JButton dialog = new JButton();
		dialog.setToolTipText("Selecciona los informes");
		dialog.setIcon(new ImageIcon("src\\MVC\\icons\\report.png"));
	
		dialog.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.mostrarInf();
			}
		});
		lista.add(dialog);
		this.add(dialog);
				
				
		//LIMPIA EL PANEL INFORMES
		JButton clearReportsButton = new JButton();
		clearReportsButton.setToolTipText("Limpia el visor de Informes");
		clearReportsButton.setIcon(new ImageIcon("src\\MVC\\icons\\delete_report.png"));
		clearReportsButton.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.getPanelInformes().setTexto("");
			}
		});
		lista.add(clearReportsButton);
		this.add(clearReportsButton);
	
		//GUARDA LSO INFORMES EN ARCHIVO
		JButton save = new JButton();
		save.setToolTipText("Guarda los informes");
		save.setIcon(new ImageIcon("src\\MVC\\icons\\save_report.png"));
	
		save.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaOut();
			}
		});
		lista.add(save);
		this.add(save);
		
		
		this.addSeparator();
		//SALIR
		JButton exit = new JButton();
		exit.setToolTipText("Salir");
		exit.setIcon(new ImageIcon("src\\MVC\\icons\\exit.png"));
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.quit();
			}
		});
		lista.add(exit);
		this.add(exit);
	}
	
	public int getSteps() {
		return (int) steps.getValue();
	}
	
	public int getDelay() {
		return (int) delay.getValue();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		//...
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.steps.setValue(1);
		this.time.setText("0");
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		///...
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.time.setText(""+(++time)); 
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e) {
		//...
	}

	public List<JButton> getLista() {
		return lista;
	}

	public JTextField getTime() {	
		return time;
	}

}
