package Default;

import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Default.TrafficSimulator.TrafficSimulatorObserver;
import util.SortedArrayList;
import Events.Event;
import Exceptions.ErrorDeSimulacion;
import Exceptions.Excepciones;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;
import MVC.*;
import Paneles.PanelAreaTexto;



public class TrafficSimulator implements Observador<TrafficSimulatorObserver>{

	public interface TrafficSimulatorObserver {
		public void registered(int time, RoadMap map, List<Event> events);
		public void reset(int time, RoadMap map, List<Event> events);
		public void eventAdded(int time, RoadMap map, List<Event> events);
		public void advanced(int time, RoadMap map, List<Event> events);
		public void simulatorError(int time, RoadMap map, List<Event> events, Excepciones e);
	}
	
	private RoadMap map;
	private int tiempo;
	private SortedArrayList<Event> listaEventos;
	private List<TrafficSimulatorObserver> observers;
	private OutputStream output;

	
	public TrafficSimulator() {
		this.map = new RoadMap();
		this.tiempo = 0;
		Comparator<Event> cmp = new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return o1.getTiempo() - o2.getTiempo(); 
			}
		};
		this.listaEventos = new SortedArrayList<>(cmp);
		this.observers = new ArrayList<>();
	
	}
	
	public void insertaEvento(Event evento) throws ErrorDeSimulacion { 		
		if(evento != null) {
			if (evento.getTiempo() >= tiempo) {
				listaEventos.add(evento);
				notifyEventAdded();
			}
			else {
				ErrorDeSimulacion err = new ErrorDeSimulacion("El tiempo es invalido");
				notifyError(err);
				throw err;
			}
		}
		else {
			//excepcion;
			ErrorDeSimulacion err = new ErrorDeSimulacion("El evento es invalido");
			notifyError(err);
			throw err;
		}
	}
	
	public void ejecuta(int pasosSimulacion, OutputStream salida) throws ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible, ErrorDeSimulacion, IOException {
		int limiteTiempo = this.tiempo + pasosSimulacion - 1;
		while(this.tiempo <= limiteTiempo) {

			for (int j = 0; j < listaEventos.size(); j++) 
				if (listaEventos.get(j).getTiempo() ==  this.tiempo) {
					listaEventos.get(j).ejecuta(this.map);
					listaEventos.remove(j);
					j--;
				}
					
			notifyAdvanced();
			map.actualizar();// Avanza todo
			this.tiempo++;
			
			//Escribir en outputstream llamando a genera informe de cada objeto simulado
			String str = generaReport(""); 
			if (output == null) {
				System.out.println(str);
			}
			else
				output.write(str.getBytes());
		}
	}
	
	public String generaReport(String str) {
		str += map.generateReport(this.tiempo);	
		return str;
	}
		@SuppressWarnings("unused")
		private void notifyRegistered(TrafficSimulatorObserver o) { //Usar cuando un observador se registre
			o.registered(this.tiempo, map, listaEventos);
		}
		private void notifyReset() { 
			for(TrafficSimulatorObserver o : this.observers) {
				o.reset(this.tiempo, map, listaEventos);
			}
		}
		private void notifyEventAdded() { 

			for(TrafficSimulatorObserver o : this.observers) {
				o.eventAdded(this.tiempo, map, listaEventos);
			}
		}
		private void notifyAdvanced() { 
			for(TrafficSimulatorObserver o : this.observers) { 
				o.advanced(this.tiempo, map, listaEventos);
			}
		}
		private void notifyError(ErrorDeSimulacion e) { 
			for(TrafficSimulatorObserver o : this.observers) {
				o.simulatorError(this.tiempo, map, listaEventos, e);
			}
		}
		
		public void reinicia() {
			Comparator<Event> cmp = new Comparator<Event>() {

				@Override
				public int compare(Event o1, Event o2) {
					return o1.getTiempo() - o2.getTiempo(); 
				}
			};
			this.listaEventos = new SortedArrayList<>(cmp);
			this.map = new RoadMap();
			this.tiempo = 0;
			notifyReset();
		}

		@Override
		public void addObservador(TrafficSimulatorObserver o) {
			if( o != null && !this.observers.contains(o))
				observers.add(o);
		}

		@Override
		public void removeObservador(TrafficSimulatorObserver o) {
			if( o != null && this.observers.contains(o))
				observers.remove(o);			
		}
		
		public void printReports() {
			try {
				if(output != null)
					output.write(map.generateReport(tiempo).getBytes());
			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void redirect(OutputStream output2) {
			output = output2;
			
		}

		/*public void redirect(OutputStream output2) {
			if (output == null) {
				output = output	
					public void write(byte[] b) throws IOException {
						output2.setTexto(new String(b));
					}
					@Override
					public void write(int b) throws IOException {
						// TODO Auto-generated method stub
						
					}	
				};		
			}
			else {
				output = null;
			}			
		}*/
		
}
