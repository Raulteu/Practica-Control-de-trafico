package Cruce;

import java.util.ArrayList;
import java.util.List;

import Vehiculo.Vehicle;
import Carretera.Road;
import Exceptions.ErrorDeSimulacion;

public class InRoad {

		protected Road carretera;
		protected List<Vehicle> colaVehiculos;
		protected boolean semaforo; // true=verde, false=rojo
		
		public InRoad(Road carretera) {
			this.carretera = carretera;
			colaVehiculos = new ArrayList<>();
			semaforo = false;
		// inicia los atributos.
		// el semaforo a rojo
		}
		void ponSemaforo(boolean color) {
			semaforo = color;
		}
		
		public void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
			try {
				if (this.colaVehiculos.size() > 0)
					this.colaVehiculos.get(0).moverASiguienteCarretera();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.colaVehiculos.remove(0);

		// coge el primer vehiculo de la cola, lo elimina,
		// y le manda que se mueva a su siguiente carretera.
		}
		
		public void entraVehiculo(Vehicle v) {
			if (!colaVehiculos.contains(v)) {
				colaVehiculos.add(v);
			}	
			//Ordenar lista
			colaVehiculos.sort(carretera.reversed());
		}
		public Road getCarretera() {
			return this.carretera;
		}
		public boolean tieneSemaforoVerde() {
			return semaforo;
		}
		
	
		public String msgTabla() {
			String s = "";
			for (Vehicle vehicle : colaVehiculos) {
				s+= vehicle.getId() + ",";
			}
			//s.substring(0, s.length()-1);
			s+= "]";
			return s;
		}
		
		public String getColaVehiculos() {
			String s= "[";
			if (this.colaVehiculos.size() >0) {
				for (Vehicle vehicle : colaVehiculos) {
					s+= vehicle.getId() + ", ";
				}
				s = s.substring(0, s.length()-2);
			}
			s+= "]";
			return s;
		}
}
