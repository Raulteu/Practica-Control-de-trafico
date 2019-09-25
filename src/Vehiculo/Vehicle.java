package Vehiculo;
import java.util.List;

import Carretera.Road;
import Cruce.GenericJunction;
import Default.ObjectStruct;
import Exceptions.ErrorDeSimulacion;
import Exceptions.VehiculoNoPosible;
import ini.IniSection;

public class Vehicle extends ObjectStruct{
	
	protected Road road; 					//Carretera en la que esta el vehiculo
	protected int maxVel;					//Velocidad Maxima
	protected int vel;					//Velocidad Actual
	protected int pos;					//localizacion en la carretera
	protected int kilometros;				//Distacia recorrida
	protected int faulty;					//Tiempo que le queda estando averiado
	protected List<GenericJunction<?>> itinerario;	//Itinerario a recorrer(minimo 2)
	protected boolean llegado;
	protected boolean estaEnCruce; 
	
	
	public Vehicle(String id, int maxVel, List<GenericJunction<?>> iti) throws VehiculoNoPosible {
		super(id);
		
		if(maxVel >= 0){
			if(iti.size() > 1) {
				vel = 0;
				kilometros = 0;
				faulty = 0;
				llegado = false;
				this.maxVel = maxVel;
				this.itinerario = iti;
				road=null;
			}
			else
				throw new VehiculoNoPosible("El itinerario no es valido");
			}
		else {
			throw new VehiculoNoPosible("La velocidad maxima del vehiculo no es valida");
		}

		
	}
	
	public void avanza() {
		if (faulty > 0) {
			faulty--;
			this.vel = 0;
		}
		else {
			pos = pos + vel;
			kilometros += vel;
			if (pos >= road.getLongitud() ) {
				kilometros =kilometros - (pos - road.getLongitud());
				pos = road.getLongitud();
				this.road.entraVehiculoAlCruce(this);
				vel = 0;
				estaEnCruce = true;	
			}
		}
	}
	
	public void moverASiguienteCarretera() throws ErrorDeSimulacion { 
		if(!this.road.equals(null)) {

			this.road.saleVehiculo(this);
			if(road.getDstJc().equals(itinerario.get(itinerario.size()-1))) {
				this.llegado = true;
				this.road = null;
				this.vel = 0;
				this.pos = 0;
				this.estaEnCruce = true;
			}
			else {
				this.pos = 0;
				road = road.getDstJc().carreteraHaciaCruce(this.itinerario.get(this.itinerario.indexOf(road.getDstJc())+1));
				road.entraVehiculo(this);
				this.vel = 0;
				this.estaEnCruce = false;
			}
		}
		else
			throw new ErrorDeSimulacion("Error");
		
	}
	
	public boolean isEstaEnCruce() {
		return estaEnCruce;
	}

	public void setEstaEnCruce(boolean estaEnCruce) {
		this.estaEnCruce = estaEnCruce;
	}
	public void setFaulty(int faulty) {
		this.faulty = faulty;
	}

	public boolean getEstaEnCruce() {
		return this.estaEnCruce;
	}
	public void setTiempoAveria(int n){
		if(!this.road.equals(null)) {
			faulty += n;
			if (faulty > 0)
				vel = 0;
		}
	}
	
	public void setVelocidadActual(int velocidad) {
		
		if (velocidad < 0) {
			velocidad = 0;
		}
		else if(velocidad > maxVel)
			vel = maxVel;
		else
			vel = velocidad;
	}
	
	public String getNombreSeccion() {
		return "vehicle_report";
	}
	
	public String completar(IniSection is) {
		is.setValue("speed", vel);
		is.setValue("kilometrage", kilometros);
		is.setValue("faulty", faulty);
		is.setValue("location", this.llegado ? "arrived" :
					'(' + this.road.getId() + "," + this.pos + ')');
		return is.toString();
	}
	
	
	
	public int getFaulty() {
		return this.faulty;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getPos() {
		return this.pos;
	}

	public void firstRoad() {
		road = this.itinerario.get(0).carreteraHaciaCruce(this.itinerario.get(1));	
		road.entraVehiculo(this);
	}

	public int getTiempoDeInfraccion() {
		return faulty;
	}

	public Road getRoad() {
		return road;
	}

	public int getVel() {
		return vel;
	}

	public String getItinerario() {
		String s = "[";
		for (GenericJunction<?> genericJunction : itinerario) {
			s+= genericJunction.getId() + ", ";
		}
		s = s.substring(0, s.length()-2);
		s+= "]";
		return s;
	}

	public int getKilometros() {
		return kilometros;
	}

	public boolean getLlegado() {
		return llegado;
	}
	
	@Override
	public String toString() {
		return id;
	}

}
