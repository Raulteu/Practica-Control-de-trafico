package Cruce;

import Carretera.Road;
import Exceptions.ErrorDeSimulacion;

public class IntervalInRoad extends InRoad{
	
	private int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner
	// el semaforo de la carretera en rojo
	private int unidadesDeTiempoUsadas; // Se incrementa cada vez que
	// avanza un vehiculo
	private boolean usoCompleto; // Controla que en cada paso con el semaforo
	// en verde, ha pasado un vehiculo
	private boolean usadaPorUnVehiculo; // Controla que al menos ha pasado un
	// vehiulo mientras el semaforo estaba
	// en verde.

	protected IntervalInRoad(Road carretera, int intervalTiempo) {
		super(carretera);
		this.intervaloDeTiempo = intervalTiempo;
		this.unidadesDeTiempoUsadas = -1;
		this.usoCompleto = true;
		
	}
	
@Override
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		// Incrementa unidadesDeTiempoUsadas
		this.unidadesDeTiempoUsadas++;
		// Actualiza usoCompleto:
		// - Si  colaVehiculos es vacia, entonces usoCompleto=false
		if(this.colaVehiculos.size() == 0)
			this.usoCompleto = false;
		// - En otro caso saca el primer vehiculo de la colaVehiculos
		// y le mueve a la siguiente carretera (.moverASiguienteCarretera())
		else {	
			this.colaVehiculos.get(0).moverASiguienteCarretera();
			this.colaVehiculos.remove(0);
			// Pone usadaPorUnVehiculo a true.
			this.usadaPorUnVehiculo = true;
		}
	}

	public boolean tiempoConsumido() {
		// comprueba si se ha agotado el intervalo de tiempo.
		// unidadesDeTiempoUsadas >= intervaloDeTiempo
		if(this.unidadesDeTiempoUsadas >= this.intervaloDeTiempo) {
			return true;
		}
		else
			return false;
		}
	
	public boolean usoCompleto() {
		return this.usoCompleto;
	} // metodo get
	
	public boolean usada() {
		return this.usadaPorUnVehiculo;
	} // metodo get
	
	public int getIntervalo() {
		return this.intervaloDeTiempo;
	}
	public int getUnidades() {
		return this.unidadesDeTiempoUsadas;
	}
	public void setIntervalo (int n) {
		this.intervaloDeTiempo = n;
	}
	public void setUnidades (int n) {
		this.unidadesDeTiempoUsadas = n;
	}
	public boolean getUsoCompleto() {
		return this.usoCompleto;
	}
	public boolean getUsada() {
		return this.usadaPorUnVehiculo;
	}
	public void setUsoCompleto(boolean b) {
		this.usoCompleto = b;
	}
	public void setUsada(boolean b) {
		this.usadaPorUnVehiculo = b;
	}
	
	@Override
	public String msgTabla() {
		return "[" + carretera.getId()+ (semaforo ? " ,green" :  " ,red") + ":"+ (this.intervaloDeTiempo - this.unidadesDeTiempoUsadas)+ "," + carretera.toString(); 
		
	}
	
}

