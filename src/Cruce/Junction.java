package Cruce;

import java.util.ArrayList;
import java.util.HashMap;

import Vehiculo.Vehicle;
import Carretera.Road;
import Exceptions.ErrorDeSimulacion;
import ini.IniSection;
public class Junction extends GenericJunction<InRoad>{
		
	public Junction(String id){
		super(id);
		this.listaCarreterasIn = new ArrayList<>();
		this.InRoadMap = new HashMap<>();
		this.OutRoad = new HashMap<>();
		indiceSemaforoVerde= -1;
		
	}
	
	public void entraVehiculoAlCruce(String idCarretera, Vehicle vehiculo){
	// añade el vehiculo a la carretera entrante idCarretera
		int i = 0;
		while (i<listaCarreterasIn.size()) {
			if(listaCarreterasIn.get(i).carretera.getId().equals(idCarretera)) {
				listaCarreterasIn.get(i).colaVehiculos.add(vehiculo);
				break;
			}	
			i++;
		}
	}
	
	public void avanza() throws ErrorDeSimulacion {	
		super.avanza();
	}
	
	public void newInRoad(InRoad road) {
		listaCarreterasIn.add(road);
		InRoadMap.put(id, road);
	}
	public void newOutRoad(Road road) {
		OutRoad.put(this, road);
	}
	
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
	
	public Road carreteraHaciaCruce(Junction cruce) {
			return OutRoad.get(cruce); 
		// devuelve la carretera que llega a ese cruce desde â€œthisâ€�
	}
	
	public void addCarreteraEntranteAlCruce(String idCarretera, Road carretera) {
		InRoad aux = new InRoad(carretera);
		listaCarreterasIn.add(aux);
		InRoadMap.put(idCarretera, aux);
		// aniade una carretera entrante al mapaCarreterasEntrantes y
		// a las carreterasEntrantes
	}
	
	public void addCarreteraSalienteAlCruce(Junction destino, Road road) {
		OutRoad.put(destino, road);
	}
	@Override
	protected void actualizaSemaforos(){ //Se redefine
		int enRojo;
		if (indiceSemaforoVerde == -1)
			indiceSemaforoVerde = 0;
		if (indiceSemaforoVerde - 1 >= 0)
			enRojo = indiceSemaforoVerde - 1;
		else
			enRojo = listaCarreterasIn.size() - 1;
		listaCarreterasIn.get(enRojo).ponSemaforo(false);
		listaCarreterasIn.get(indiceSemaforoVerde).ponSemaforo(true);
		indiceSemaforoVerde++;
		if(indiceSemaforoVerde >= listaCarreterasIn.size()) {
			indiceSemaforoVerde = 0;
		}
	}

	@Override
	protected InRoad creaCarreteraEntrante(Road carretera) {	
		return new InRoad(carretera);
	}
	
	public int semOn(){
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if(listaCarreterasIn.get(i).semaforo == true)
				return i;
		}
		return 0;
		
	}
	public String verde() {
		String s ="";
		if (listaCarreterasIn.size() > 0)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", green"  +", " +  listaCarreterasIn.get(indiceSemaforoVerde).getColaVehiculos() + "]";
		}
		return s;
	}
	
	public String rojo() {
		String s ="";
		if (listaCarreterasIn.size() > 1)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (!listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", red, " +  listaCarreterasIn.get(indiceSemaforoVerde).getColaVehiculos() + "]";
		}
		return s;
	}
}