package MVC;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Default.TrafficSimulator;
import Default.TrafficSimulator.TrafficSimulatorObserver;
import Events.Event;
import Events.ParseEvent;
import Exceptions.*;
import Paneles.PanelAreaTexto;
import ini.Ini;
import ini.IniSection;


public class Controller {
	
	private TrafficSimulator simulador;
	private int pasos;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private Thread thread;
	
	
	public Controller(TrafficSimulator sim, int pasos, InputStream is, OutputStream os) throws FileNotFoundException {
		this.simulador = sim;
		this.pasos = pasos; 
		this.ficheroEntrada = is;
		this.ficheroSalida  = os;
	}		
	public void run() throws ArgumentException, ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible, ErrorDeSimulacion, IOException {
		try {
			this.cargaEventos(this.ficheroEntrada);
			this.simulador.ejecuta(pasos, this.ficheroSalida);
		} catch(Excepciones e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void cargaEventos(InputStream entrada) throws ErrorDeSimulacion, ArgumentException {
		Ini ini;
		try {
			//lee el archivo y carga su atributo iniSections
			ini = new Ini(entrada);
		} catch (IOException e) {
			throw new ErrorDeSimulacion ("Error en la lectura de eventos: " + e);
		}
		//Recorremos todos los elementos de iniSections para generar el evento
		for (IniSection section : ini.getSections()){
			//Parseamos la seccion para ver a que evento corresponde
			Event e = ParseEvent.parseaEvent(section);
			if (e != null)
				this.simulador.insertaEvento(e);
			else
				throw new ErrorDeSimulacion("Evento desconocido: " + section.getTag());
		}
	}
	
	public void ejecuta(int pasos) throws ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible, ErrorDeSimulacion, IOException {
		this.simulador.ejecuta(pasos, ficheroSalida);
	}
	
	public void reinicia() {
		this.simulador.reinicia();
	}
	
	public void addObserver(TrafficSimulatorObserver o) {
		this.simulador.addObservador(o);
	}
	
	public void removeObserver(TrafficSimulatorObserver o) {
		this.simulador.removeObservador(o);
	}
	public int getPasos() {
		return pasos;
	}
	
	public String getInforme(String str) {
		return simulador.generaReport(str);		
	}
	public InputStream getFicheroEntrada() {
		return ficheroEntrada;
	}
	public void setOutput(OutputStream output) {
		this.ficheroSalida = output;
		
	}
	public void redirect(OutputStream output) {
		simulador.redirect(output);
	}
	
}

