package MVC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import Carretera.Road;
import Cruce.GenericJunction;
import Default.RoadMap;
import Default.TrafficSimulator;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;
import Exceptions.ArgumentException;
import Exceptions.ErrorDeSimulacion;
import Exceptions.Excepciones;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;
import Modelos.ModeloTablaCarreteras;
import Modelos.ModeloTablaCruces;
import Modelos.ModeloTablaEventos;
import Modelos.ModeloTablaVehiculos;
import Opcional.DialogoInformes;
import Opcional.JtextOutput;
import Paneles.PanelAreaTexto;
import Paneles.PanelBarraEstado;
import Paneles.PanelEditorEventos;
import Paneles.PanelInformes;
import Paneles.PanelTabla;

import javax.swing.border.Border;
import Vehiculo.Vehicle;

@SuppressWarnings("serial")
public class View extends JFrame implements TrafficSimulatorObserver{
	
	private Controller ctrl; 	//La vista usa el controlador
	@SuppressWarnings("unused")
	private RoadMap map; 		//para los metodos update de Observer
	private int time; 			//para los metodos update de Observer
	private List<Event> listaEventos; //para los metodos update de Observer
	
	private static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);
	
	//Panel Superior
	private static final String[] columnIdEventos = {"#","Tiempo", "Tipo"};	
	
	private PanelAreaTexto panelEditorEventos;
	private PanelAreaTexto panelInformes;
	private PanelTabla<Event> panelColaEventos;
	
	private PanelBarraEstado panelBarraEstado;
	
	//Panel Grafico
	private ComponenteMapa componenteMapa;	
	
	//Panel Inferior
	private static final String[] columnIdVehiculos = {"ID","Carretera","Localizacion","Vel", "Km", "Tiempo Averia", "Itinerario"};
	private static final String[] columnIdCarreteras = {"ID","Origen","Destino","Longitud", "Vel. Max", "Vehiculos",};
	private static final String[] columnIdCruces = {"ID","Verde","Rojo"};
	
	private PanelTabla<Vehicle> panelVehiculos;
	private PanelTabla<Road> panelCarreteras;
	private PanelTabla<GenericJunction<?>> panelCruces;
	
//	// REPORT DIALOG
	private DialogoInformes dialogoInformes; // opcional

	
	private JPanel mainPanel;
	
	//Menu and ToolBar
	private BarraMenu menubar;
	private JToolBar toolBar;
	private JFileChooser fc;
	
	// MODEL PART - VIEW CONTROLLER MODEL

	private File currentFile;
	
	private OutputStream output;
	
	private Thread thread;

	

	public View(TrafficSimulator model, String inFileName, Controller ctrl) throws FileNotFoundException{
		super("Traffic Simulator");
		this.ctrl = ctrl;
		currentFile = inFileName != null ? new File(inFileName) : null;
		initGUI();
		output = new JtextOutput(panelInformes.areaTexto, null);
		ctrl.setOutput(output);
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				quit(); 
			}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowOpened(WindowEvent e) {
				hello();
			}
			
		});
		
		mainPanel = this.createPrincipalPanel();
		this.add(mainPanel);
		this.setContentPane(mainPanel);

		this.addStatusBar(mainPanel);
		
		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior)
		JPanel panelCentral  = this.createPanelCentral();
		mainPanel.add(panelCentral, BorderLayout.CENTER);
		
		//Menu
		menubar = new BarraMenu(this, this.ctrl);
		this.setJMenuBar(menubar);
		this.addToolBar(mainPanel);
		
		//File Chooser
		fc = new JFileChooser();
		
		this.dialogoInformes = new DialogoInformes(this,this.ctrl); //opcional
		
		this.pack();	
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "¿Seguro que quieres salir?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (n == 0) {// finalize your app
			System.exit(0);
		}
	}
	
	public void hello() {
		
		JOptionPane.showMessageDialog(new JFrame(), "Bienvenido al simulador de tráfico de Jose Márquez García y Raúl Vindel Loeches", "Bienvenido", JOptionPane.PLAIN_MESSAGE);
	}

	private void addToolBar(JPanel panelPrincipal) {
		this.toolBar = new ToolBar(this, this.ctrl);
		panelPrincipal.add(this.toolBar, BorderLayout.NORTH);
	}

	private void createPanelSuperior(JPanel panelcentral) {
		//Si el comando lo pide, leer texto:
		String result = "";
		InputStream is = this.ctrl.getFicheroEntrada();
		if (is !=(null)) {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(is).useDelimiter("\\A");
			result += s.hasNext() ? s.next() : "";		
		
		}
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		this.panelEditorEventos = new PanelEditorEventos("Eventos: ", result, true,this);
		this.panelColaEventos = new PanelTabla<Event>("Cola Eventos: ", new ModeloTablaEventos(View.columnIdEventos,this.ctrl));
		this.panelInformes = new PanelInformes("Informes: ", false, this.ctrl);

		panel.add(panelEditorEventos);
		panel.add(panelColaEventos);
		panel.add(panelInformes);
				
		panelcentral.add(panel);
	}
	
	private JPanel createPrincipalPanel() {
		JPanel panelPrincipal = new JPanel();
		this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		panelPrincipal.setLayout(new BorderLayout());
		return panelPrincipal;
	}

	private void createPanelInferior(JPanel panelcentral) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JPanel tablas = new JPanel();
		tablas.setLayout(new GridLayout(3, 1));
		JPanel grafico = new JPanel();
		grafico.setLayout(new GridLayout(1, 1));
		this.panelVehiculos = new PanelTabla<Vehicle>("Vehiculos", new ModeloTablaVehiculos(View.columnIdVehiculos,this.ctrl));
		this.panelCarreteras = new PanelTabla<Road>("Carreteras", new ModeloTablaCarreteras(View.columnIdCarreteras,this.ctrl));
		this.panelCruces = new PanelTabla<GenericJunction<?>>("Cruces", new ModeloTablaCruces(View.columnIdCruces,this.ctrl));
	
		
		tablas.add(panelVehiculos);
		tablas.add(panelCarreteras);
		tablas.add(panelCruces);

		this.componenteMapa = new ComponenteMapa(this.ctrl);

		panel.add(tablas);
		grafico.add(new JScrollPane(componenteMapa,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		panel.add(grafico);
		
		panelcentral.add(panel);
	}

	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(2, 1));
		this.createPanelSuperior(panelCentral);
		this.createPanelInferior(panelCentral);
	
		return panelCentral;
	}
	


	private void addStatusBar(JPanel panelPrincipal) {		
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador!", this.ctrl);
		panelPrincipal.add(this.panelBarraEstado,BorderLayout.PAGE_END);
	}
	
	@SuppressWarnings("resource")
	public static String readFile(File file) {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}


	@Override
	public void registered(int time, RoadMap map, List<Event> events) {

		
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		//Limpiar los textos
		panelEditorEventos.limpiar();
		panelInformes.limpiar();

		//panelColaEventos
		this.limpiarColaEventos();
		
		componenteMapa.reset(time, map, listaEventos);
		((ToolBar) toolBar).reset(time, map, listaEventos);
		this.panelBarraEstado.reset(time, map, null);
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {

		
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {

		
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e) {
		JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	public void cargaFichero() throws ErrorDeSimulacion, ArgumentException {
		fc.setDialogTitle("Cargar Fichero:");
		int returnVal = fc.showOpenDialog(null);
		fc.setDialogTitle("Cargar Fichero:");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s = readFile(file);
			panelEditorEventos.areaTexto.setText(s);
			panelEditorEventos.setBorde("Eventos: "+ fc.getName(fc.getSelectedFile()));
		}
	}
	
	public void guardaFichero() {
		fc.setDialogTitle("Guardar Eventos:");
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			escribeFichero(file, panelEditorEventos.getTexto());
		}
	}
	
	public static void escribeFichero(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		panelInformes.setTexto("");
		currentFile = null;		
	}


	public int getSteps() {
		return ((ToolBar) toolBar).getSteps();		
	}
	
	private int getDelay() {
		return ((ToolBar) toolBar).getDelay();
	}

	public void reiniciar() {
		ctrl.reinicia();
		panelEditorEventos.setBorde("Eventos: ");
	}

	public PanelAreaTexto getPanelEvento() {
		return panelEditorEventos;
	}

	public void ejecuta() {
		int pasos = this.getSteps();
		int delay = this.getDelay();
		thread = new Thread() {
			public void run() {
				try {
					int i = 0;
					while(!thread.isInterrupted() && i < pasos) {
						//Interrumpir todos los botones menos stop
						
						menubar.getMenuFicheros().setEnabled(false);
						menubar.getMenuReport().setEnabled(false);
						menubar.getMenuSimulador().setEnabled(false);

						
						ctrl.ejecuta(1);
						sleep(delay);
						i++;
					}
				menubar.getMenuFicheros().setEnabled(true);
				menubar.getMenuReport().setEnabled(true);
				menubar.getMenuSimulador().setEnabled(true);
					
				List<JButton> bots = ((ToolBar) toolBar).getLista();
				for (JButton jButton : bots) {
					jButton.setEnabled(true);
				}
				
				((ToolBar)toolBar).steps.setEnabled(true);
				((ToolBar)toolBar).delay.setEnabled(true);
				((ToolBar)toolBar).getTime().setEnabled(true);
				} catch (ItinerarioIncorrecto | YaExisteException | NoExisteException | VehiculoNoPosible | ErrorDeSimulacion
						| IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				} catch (InterruptedException e) {}
			}
		};
		thread.start();
		//ctrl.ejecuta(pasos);
		time+=pasos;			
	}

	public void guardaOut() {
		fc.setDialogTitle("Guardar Informes:");
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			escribeFichero(file, panelInformes.getTexto());
		}
	}
	public void mostrarInf() {
		dialogoInformes.mostrar(this);
	}
	
	public PanelAreaTexto getPanelInformes() {
		return panelInformes;
	}

	public void guardaEvent() {
		fc.setDialogTitle("Guardar Eventos:");
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			escribeFichero(file, panelEditorEventos.getTexto());
		}
	}

	public void cargaEvento() throws ErrorDeSimulacion, ArgumentException {
		
		InputStream is = new ByteArrayInputStream(this.getPanelEvento().getTexto().getBytes());
		ctrl.cargaEventos(is);

	}
	
	public PanelTabla<Event> getPanelColaEvento() {
		return panelColaEventos;

	}

	public void limpiarColaEventos() {
		panelColaEventos.getModelo().setRowCount(0);
		panelColaEventos.getModelo().fireTableStructureChanged();
		panelColaEventos.reset("Cola Eventos: ");
	}

	public void inserta(String string) {
		panelEditorEventos.areaTexto.append("\n" + string);
		
	}

	public void getSelected() {
		
		if(dialogoInformes.open() == true) {
			
			
			String s= "";
			
			for (GenericJunction<?> cruce : dialogoInformes.getCrucesSeleccionados()) {
				s += cruce.generaInforme(time) + "\n";
			}
			
			for (Road carretera : dialogoInformes.getCarreterasSeleccionadas()) {
				s += carretera.generaInforme(time)+ "\n";
			}
			
			for (Vehicle vehiculo : dialogoInformes.getVehiculosSeleccionados()) {
				s += vehiculo.generaInforme(time)+ "\n";
			}
			
			panelInformes.setTexto(s);
		}
	}

	public void redirect() {
		ctrl.redirect(output);
	}

	public void setNull() {
		output = null;
		
	}

	public void interrupt() {
		// TODO Auto-generated method stub
		if (thread != null || thread.isAlive())
			thread.interrupt();
	}

	
}
