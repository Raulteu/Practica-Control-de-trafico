package Default;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Vehiculo.Vehicle;
import Carretera.Road;
import Cruce.GenericJunction;
import Exceptions.*;



public class RoadMap {
	
	private List<Road> carreteras;
	private List<GenericJunction<?>> cruces;
	private List<Vehicle> vehiculos;
	
	private Map<String, Road> RoadMap;
	private Map<String, GenericJunction<?>> JunctionMap;
	private Map<String, Vehicle> VehicleMap;
	
	public RoadMap() {
		this.cruces = new ArrayList<GenericJunction<?>>();
		this.carreteras = new ArrayList<Road>();
		this.vehiculos = new ArrayList<Vehicle>();
		
		this.JunctionMap = new HashMap<>();
		this.RoadMap = new HashMap<>();
		this.VehicleMap = new HashMap<>();
		
		// inicializa los atributos a sus constructoras por defecto.
		// Para carreteras, cruces y vehÃ­culos puede usarse ArrayList.
		// Para los mapas puede usarse HashMap
	}
	
	public void addJunction(String j_id, GenericJunction<?> cruce) throws YaExisteException {
		if(!JunctionMap.containsKey(j_id)) {
			cruces.add(cruce);
			JunctionMap.put(j_id, cruce);
		}
		else
			
			throw new YaExisteException("El cruce ya ha sido aniadido anteriormente");
			
		// comprueba que j_id no existe en el mapa.
		// Si no existe, lo aniade a cruces y a mapaDeCruces.
		// Si existe lanza una excepcion.
	}
	
	
	public void addVehiculo(String idVehiculo, Vehicle vehiculo) throws YaExisteException {
		if (!VehicleMap.containsKey(idVehiculo)) {
			vehiculos.add(vehiculo);
			VehicleMap.put(idVehiculo, vehiculo);
			vehiculo.firstRoad();
		}
		else
			throw new YaExisteException("El vehiculo ya ha sido aÃ±adido anteriormente");
	// comprueba que idVehiculo no existe en el mapa.
	// Si no existe, lo aniade a vehiculos y a mapaDeVehiculos,
	// y posteriormente solicita al vehiculo que se mueva a la siguiente
	// carretera de su itinerario (moverASiguienteCarretera).
	// Si existe lanza una excepcion.
	}
	public void addCarretera(String idCarretera, GenericJunction<?> origen, Road carretera, GenericJunction<?> destino) throws YaExisteException {
		if (!RoadMap.containsKey(idCarretera)) {
			carreteras.add(carretera);
			RoadMap.put(idCarretera, carretera);
			origen.addCarreteraSalienteAlCruce(destino, carretera);
			destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}
		else
			throw new YaExisteException("La carretera ya ha sido aÃ±adida anteriormente");
			// comprueba que idCarretera no existe en el mapa.
			// Si no existe, lo aniade a carreteras y a mapaDeCarreteras
			// y posteriormente actualiza los cruces origen y destino como sigue:
			// - Aniade al cruce origen la carretera, como carretera saliente
			// - Aniade al crude destino la carretera, como carretera entrante
			// Si existe lanza una excepcion.	

	}
	public String generateReport(int time) {
		String report = ""; //"# ******* step " + (time - 1) + " *******\n\n";

		
		for (int i = 0; i < cruces.size(); i++) {
			report += cruces.get(i).generaInforme(time) + '\n';
		}
		for (int i = 0; i < carreteras.size(); i++) {
			report += carreteras.get(i).generaInforme(time)+ '\n';
		}
		for (int i = 0; i < vehiculos.size(); i++) {
			report += vehiculos.get(i).generaInforme(time)+ '\n';
		}
		// genera informe para cruces
		// genera informe para carreteras
		// genera informe para vehiculos
		return report;
		}
	
		public void actualizar() throws ErrorDeSimulacion {
			// llama al metodo avanza de cada carretera
			for (Road road : carreteras) {
				road.avanza();
			}
			// llama al metodo avanza de cada cruce
			for (GenericJunction<?> cruce : cruces) {
				cruce.avanza();
			}	
		}
		public GenericJunction<?> getCruce(String id) throws NoExisteException {
			if(JunctionMap.containsKey(id))
				return JunctionMap.get(id);
			else
				throw new NoExisteException("No existe el cruce");
		// devuelve el cruce con ese idâ utilizando el mapaDeCruces.
		// sino existe el cruce lanza excepcion.
		}
		public Vehicle getVehiculo(String id) throws NoExisteException {
			if(VehicleMap.containsKey(id))
				return VehicleMap.get(id);
			else
				throw new NoExisteException("No existe el coche");
		// devuelve el vehiculo con ese idâ utilizando el mapaDeVehiculos.
		// sino existe el vehiculo lanza excepcion.
		}
		public Road getCarretera(String id) throws NoExisteException {
			if(RoadMap.containsKey(id))
				return RoadMap.get(id);
			else
				throw new NoExisteException("No existe la carretera");
		// devuelve la carretera con ese idâ utilizando el mapaDeCarreteras.
		//
		}
		
		public void makeVehicleFaulty(String idVehicle, int duracion) {
			for (int i = 0; i < vehiculos.size(); i++) {
				if(vehiculos.get(i).id.equals(idVehicle)) 
					vehiculos.get(i).setFaulty(duracion);
				
			}
		}

		public List<GenericJunction<?>> getCruces() {
			return this.cruces;
		}

		public List<Road> getCarreteras() {
			return this.carreteras;
		}

		public List<Vehicle> getVehiculos() {
			return this.vehiculos;
		}
}
