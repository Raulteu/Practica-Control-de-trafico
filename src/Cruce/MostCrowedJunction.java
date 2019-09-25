package Cruce;

import Carretera.Road;
import ini.IniSection;

public class MostCrowedJunction extends GenericJunction<IntervalInRoad>{

	
	public MostCrowedJunction(String id) {
		super(id);
	}

	@Override
	protected void actualizaSemaforos() { //Se redefine
		
		if(this.indiceSemaforoVerde == -1) {
			this.indiceSemaforoVerde = 0;
			this.listaCarreterasIn.get(this.carreteraConMasVehiculos()).ponSemaforo(true);
		}
		else {
			this.listaCarreterasIn.get(indiceSemaforoVerde).tiempoConsumido();
			this.listaCarreterasIn.get(indiceSemaforoVerde).ponSemaforo(false);
			indiceSemaforoVerde = carreteraConMasVehiculos(this.listaCarreterasIn.get(indiceSemaforoVerde));
			this.listaCarreterasIn.get(indiceSemaforoVerde).ponSemaforo(true);
			this.listaCarreterasIn.get(indiceSemaforoVerde).setIntervalo(Math.max(this.listaCarreterasIn.get(indiceSemaforoVerde).colaVehiculos.size(),1));
		}
	}
	
	public int carreteraConMasVehiculos() {
		int masVehiculos = this.listaCarreterasIn.get(0).colaVehiculos.size();
		for (int i = 0; i < this.listaCarreterasIn.size(); i++) {
			if(this.listaCarreterasIn.get(i).colaVehiculos.size() > masVehiculos)
				masVehiculos = listaCarreterasIn.indexOf(listaCarreterasIn.get(i));
		}
		return masVehiculos;
	}
	
	public int carreteraConMasVehiculos(IntervalInRoad iir) {
		int masVehiculos = this.listaCarreterasIn.get(0).colaVehiculos.size();
		for (int i = 0; i < this.listaCarreterasIn.size(); i++) {
			if(!this.listaCarreterasIn.get(i).equals(iir))
				if(this.listaCarreterasIn.get(i).colaVehiculos.size() >= masVehiculos)
					masVehiculos = listaCarreterasIn.indexOf(listaCarreterasIn.get(i));
		}
		return masVehiculos;
	}

	@Override
	protected IntervalInRoad creaCarreteraEntrante(Road carretera) {
		return new IntervalInRoad(carretera, 0);
	}
	@Override
	public String completar(IniSection is) {
		String report = "";
		String semaforo = "";	
		if(listaCarreterasIn.size() > 0) {
			for(int i = 0; i < listaCarreterasIn.size(); i++){
				if(listaCarreterasIn.get(i).semaforo) {
					semaforo = "green";
					report += "(" + listaCarreterasIn.get(i).carretera.getId() + "," +  semaforo + ":1"+ ",[";
				}
				else {
					semaforo = "red";
					report += "(" + listaCarreterasIn.get(i).carretera.getId() + "," +  semaforo + ",[";
				}
				for (int j = 0; j < listaCarreterasIn.get(i).colaVehiculos.size(); j++) {				
					report += listaCarreterasIn.get(i).colaVehiculos.get(j).getId() + ",";
					if(j == listaCarreterasIn.get(i).colaVehiculos.size()-1)
						report = report.substring(0,report.length() - 1);
				}
				
				report += "]),";
				
			}
			report = report.substring(0, report.length() - 1);
		}
		is.setValue("queues", report);
		is.setValue("type", getType());
		return "";
	}
	
	public String getType() {
		return "mc";
	}
	
	public String verde() {
		String s ="";
		if (listaCarreterasIn.size() > 0)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", green:1"  +", " +  listaCarreterasIn.get(indiceSemaforoVerde).getColaVehiculos() + "]";
		}
		return s;
	}
	
	public String rojo() {
		String s ="";
		if (listaCarreterasIn.size() > 1)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (!listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", red:1, " +  listaCarreterasIn.get(indiceSemaforoVerde).getColaVehiculos() + "]";
		}
		return s;
	}
}
