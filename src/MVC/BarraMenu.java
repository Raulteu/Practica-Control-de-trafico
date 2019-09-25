package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import Exceptions.ArgumentException;
import Exceptions.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class BarraMenu extends JMenuBar {
	

	private JMenu menuFicheros;
	private JMenu menuSimulador;
	private JMenu menuReport;
	
	public BarraMenu(View mainWindow, Controller ctrl){
		super();
		//Ficheros
		menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainWindow);
		
		//Simulador
		menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, ctrl, mainWindow);
		
		//Informes
		menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport, mainWindow);

	}

	private void creaMenuInformes(JMenu menuReport, View mainWindow) {
		JMenuItem generate = new JMenuItem("Generar");
		generate.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				mainWindow.getSelected();				
			}
		});
		// Crea y configura JMenuItem Clear
		JMenuItem clear = new JMenuItem("Limpiar");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clear();
			}
		});
		// Suma los JMenuItems a reportsMenu
		menuReport.add(generate);
		menuReport.add(clear);
		
		
		
	}

	private void creaMenuSimulador(JMenu menuSimulador, Controller ctrl, View mainWindow) {
		JMenuItem run = new JMenuItem("Ejecuta");
		run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.ejecuta();
			}
		});
		JMenuItem reset = new JMenuItem("Reiniciar");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.reiniciar();
			}
		});
		JCheckBox redirect = new JCheckBox("Redireccionar salida");
		redirect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (redirect.isSelected())
					mainWindow.redirect();

				else
					mainWindow.setNull();
					mainWindow.redirect();
			}
		});
		
		menuSimulador.add(run);
		menuSimulador.add(reset);
		menuSimulador.add(redirect);
		
		
	}

	private void creaMenuFicheros(JMenu menu, View mainWindow) {
		JMenuItem cargar = new JMenuItem("Cargar Evento");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.cargaFichero();
				} catch (ErrorDeSimulacion | ArgumentException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		
		JMenuItem guardar = new JMenuItem("Guardar Evento");
		guardar.setMnemonic(KeyEvent.VK_S);
		guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaEvent();
				
			}
		});
		JMenuItem guardarInformes = new JMenuItem("Guardar Informes");
		guardarInformes.setMnemonic(KeyEvent.VK_R);
		guardarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		guardarInformes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaOut();
			}
		});

		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.quit();
				
			}
		});
		menu.add(cargar);
		menu.add(guardar);
		menu.addSeparator();
		menu.add(guardarInformes);
		menu.addSeparator();
		menu.add(salir);
	}

	public JMenu getMenuFicheros() {
		return menuFicheros;
	}

	public JMenu getMenuSimulador() {
		return menuSimulador;
	}

	public JMenu getMenuReport() {
		return menuReport;
	}

	
	
	
}
