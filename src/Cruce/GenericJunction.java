package Cruce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Vehiculo.Vehicle;
import Carretera.Road;
import Default.ObjectStruct;
import Exceptions.ErrorDeSimulacion;
import ini.IniSection;

public abstract class GenericJunction<T extends InRoad> extends ObjectStruct {

	
	protected int indiceSemaforoVerde;

	protected List<T> listaCarreterasIn;
	protected Map<String, T> InRoadMap;
	protected Map<GenericJunction<?>, Road> OutRoad;
	
	public GenericJunction(String id) {
		super(id);
		this.listaCarreterasIn = new ArrayList<>();
		this.InRoadMap = new HashMap<>();
		this.OutRoad = new HashMap<>();
		indiceSemaforoVerde= -1;
	}
	
	@Override
	public void avanza() throws ErrorDeSimulacion {
		
		if(listaCarreterasIn.size() != 0) {//Si tiene carreteras de entrada...
			if(indiceSemaforoVerde == -1) {
				listaCarreterasIn.get(indiceSemaforoVerde + 1).ponSemaforo(true);
			}
			int semOn = semOn();
			if(listaCarreterasIn.get(semOn).colaVehiculos.size() > 0)  //y tiene vehiculos en cola...
				if(listaCarreterasIn.get(semOn).colaVehiculos.get(0).getEstaEnCruce())
					listaCarreterasIn.get(semOn).avanzaPrimerVehiculo();
		this.actualizaSemaforos();
		}
	}
	
	public Road carreteraHaciaCruce(GenericJunction<?> cruce) {
		return OutRoad.get(cruce); 
		// devuelve la carretera que llega a ese cruce desde this		
	}
	
	public void addCarreteraEntranteAlCruce(String idCarretera, Road carretera) {
		T ri = creaCarreteraEntrante(carretera);
		listaCarreterasIn.add(ri);
		InRoadMap.put(idCarretera, ri);
	}
	
	public void entraVehiculoAlCruce(String idCarretera, Vehicle vehiculo){
		int i = 0;
		while (i<listaCarreterasIn.size()) {
			if(listaCarreterasIn.get(i).carretera.getId().equals(idCarretera)) {
				listaCarreterasIn.get(i).colaVehiculos.add(vehiculo);
				break;
			}	
			i++;
		}
	}
	
	public void addCarreteraSalienteAlCruce(GenericJunction<?> destino, Road carr) {
		OutRoad.put(destino, carr);
	}
	
	abstract protected void actualizaSemaforos();
	
	abstract protected T creaCarreteraEntrante(Road carretera);
	
	public String getNombreSeccion() {
		return "junction_report";
	}
	
	public String completar(IniSection is) {
		String report = "";
		String semaforo = "";
		if(listaCarreterasIn.size() > 0) {
			for(int i = 0; i < listaCarreterasIn.size(); i++){
				if(listaCarreterasIn.get(i).semaforo)
					semaforo = "green";
				else
					semaforo = "red";
				report += "(" + listaCarreterasIn.get(i).carretera.getId() + "," +  semaforo + ",[";
				for (int j = 0; j < listaCarreterasIn.get(i).colaVehiculos.size(); j++) {				
					report += listaCarreterasIn.get(i).colaVehiculos.get(j).getId() + ",";
					if(j == listaCarreterasIn.get(i).colaVehiculos.size()-1)
						report = report.substring(0,report.length() - 1);
				}
				
				report += "]),";
				
			}
			report = report.substring(0, report.length() - 1);
		}
		//Hay que generar la seccion queues del report que contiene las carreteras el semaforo y los coches
		is.setValue("queues", report);
		return "";
	}
	
	public int semOn(){
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if(listaCarreterasIn.get(i).semaforo == true)
				return i;
		}
		return -1;
		
	}

	public List<T> getCarreteras() {
		return this.listaCarreterasIn;
	}

	public String verde() {
		String s ="";
		if (listaCarreterasIn.size() > 0)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", green:"  +", " +  listaCarreterasIn.get(indiceSemaforoVerde).carretera.getListaVehiculos() + "]";
		}
		return s;
	}
	
	public String rojo() {
		String s ="";
		if (listaCarreterasIn.size() > 1)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (!listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", red:, " +  listaCarreterasIn.get(indiceSemaforoVerde).carretera.getListaVehiculos() + "]";
		}
		return s;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
