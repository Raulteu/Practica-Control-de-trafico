package Cruce;

import Carretera.Road;
import Exceptions.ErrorDeSimulacion;
import ini.IniSection;

public class RoundRobinJunction extends GenericJunction<IntervalInRoad> {

	private int maxValorIntervalo;
	private int minValorIntervalo;

	
	public RoundRobinJunction(String id, int min, int max) {
		super(id);
		this.minValorIntervalo = min;
		this.maxValorIntervalo = max;
		
		
	}
	
	public String getType() {
		return "rr";
	}
	
	@Override

	public void avanza() throws ErrorDeSimulacion {
		if(listaCarreterasIn.size() != 0) {//Si tiene carreteras de entrada...
			if(indiceSemaforoVerde == -1) {
				indiceSemaforoVerde = 0;
				listaCarreterasIn.get(indiceSemaforoVerde).ponSemaforo(true);
			}
			this.listaCarreterasIn.get(indiceSemaforoVerde).setUsada(false);
			if(listaCarreterasIn.get(indiceSemaforoVerde).colaVehiculos.size() > 0)  //y tiene vehiculos en cola...
				if(listaCarreterasIn.get(indiceSemaforoVerde).colaVehiculos.get(0).getEstaEnCruce()) {
					listaCarreterasIn.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
					this.listaCarreterasIn.get(indiceSemaforoVerde).setUsada(true);
				}

			if (this.listaCarreterasIn.get(indiceSemaforoVerde).getUsada() == false)
				this.listaCarreterasIn.get(indiceSemaforoVerde).setUsoCompleto(false);

		actualizaSemaforos();
		}
	}
	

	@Override
	protected void actualizaSemaforos(){ //Se redefine
		
		if(indiceSemaforoVerde == -1) 
			this.listaCarreterasIn.get(0).ponSemaforo(true);
		
		else {
			this.listaCarreterasIn.get(indiceSemaforoVerde).setUnidades(this.listaCarreterasIn.get(indiceSemaforoVerde).getUnidades() + 1);

			if(this.listaCarreterasIn.get(indiceSemaforoVerde).tiempoConsumido()){				
				this.listaCarreterasIn.get(indiceSemaforoVerde).ponSemaforo(false);
				
				if(listaCarreterasIn.get(indiceSemaforoVerde).usoCompleto()) {
					listaCarreterasIn.get(indiceSemaforoVerde).setIntervalo(listaCarreterasIn.get(indiceSemaforoVerde).getIntervalo() + 1);
					listaCarreterasIn.get(indiceSemaforoVerde).setIntervalo(Math.min(listaCarreterasIn.get(indiceSemaforoVerde).getIntervalo() ,maxValorIntervalo));
				}
				else if(!this.listaCarreterasIn.get(indiceSemaforoVerde).usada()) {
					listaCarreterasIn.get(indiceSemaforoVerde).setIntervalo(listaCarreterasIn.get(indiceSemaforoVerde).getIntervalo() - 1);
					listaCarreterasIn.get(indiceSemaforoVerde).setIntervalo(Math.max(listaCarreterasIn.get(indiceSemaforoVerde).getIntervalo(), minValorIntervalo));
				}
				this.indiceSemaforoVerde++;
				
				if(indiceSemaforoVerde >= listaCarreterasIn.size()) {
					indiceSemaforoVerde = 0;
				}
				
				this.listaCarreterasIn.get(indiceSemaforoVerde).setUnidades(0);
				
				listaCarreterasIn.get(indiceSemaforoVerde).ponSemaforo(true);
			}
			

		}
	}
	
	
	@Override
	public String completar(IniSection is) {
		String report = "";
		String semaforo = "";	
		if(listaCarreterasIn.size() > 0) {
			for(int i = 0; i < listaCarreterasIn.size(); i++){
				if(listaCarreterasIn.get(i).semaforo)
					semaforo = "green";
				else
					semaforo = "red";
				report += "(" + listaCarreterasIn.get(i).carretera.getId() + "," +  semaforo + ":" + (listaCarreterasIn.get(i).getIntervalo() - listaCarreterasIn.get(i).getUnidades())+ ",[";
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
		is.setValue("type", getType());
		return "";
	}

	@Override
	protected IntervalInRoad creaCarreteraEntrante(Road carretera) {
		return new IntervalInRoad(carretera, this.maxValorIntervalo);
	}
	
	public String verde() {
		String s ="";
		if (listaCarreterasIn.size() > 0)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId() + ", green:"+ (this.listaCarreterasIn.get(i).getIntervalo()-this.listaCarreterasIn.get(i).getUnidades() ) +", " +  listaCarreterasIn.get(indiceSemaforoVerde).getColaVehiculos() + "]";
		}
		return s;
	}
	
	public String rojo() {
		String s ="";
		if (listaCarreterasIn.size() > 1)
			s += "[";
		for (int i = 0; i < listaCarreterasIn.size(); i++) {
			if (!listaCarreterasIn.get(i).semaforo) 
				s += listaCarreterasIn.get(i).carretera.getId()+(this.listaCarreterasIn.get(i).getIntervalo()-this.listaCarreterasIn.get(i).getUnidades() ) + ", red:, " +  listaCarreterasIn.get(indiceSemaforoVerde).getColaVehiculos() + "]";
		}
		return s;
	}

}
