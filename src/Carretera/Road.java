package Carretera;

import java.util.Comparator;
import java.util.List;

import util.SortedArrayList;
import Cruce.GenericJunction;
import Default.ObjectStruct;
import Vehiculo.Vehicle;
import ini.IniSection;


public class Road extends ObjectStruct implements Comparator<Vehicle> {
	protected int longitud; 							//longitud de la carretera
	protected int maxVel;								//veloci
	protected GenericJunction<?> srcJc;					//cruce del que parte la carretera
	protected GenericJunction<?> dstJc;					//cruce al que llega la carretera
	protected List<Vehicle> listaVehiculos;				//lista ordenada de vehiculos en la carretera ordenada por localizacion

	
	public Road(String id, int lon, int maxvel, GenericJunction<?> src, GenericJunction<?> dst) {
		super(id);
		this.longitud = lon;
		this.maxVel = maxvel;
		this.listaVehiculos = new SortedArrayList<>(this.reversed());
		this.dstJc= dst;
		this.srcJc = src;
		//Se fija el orden entre los vehiculos
		//El vehiculo que esta mas cerca del destino est� en la pos [0]

	}
	
	public void entraVehiculo(Vehicle v) {
		if (!listaVehiculos.contains(v)) {
			listaVehiculos.add(v);
		}	
		//Ordenar lista
		listaVehiculos.sort(this.reversed());
	}
	
	public void saleVehiculo(Vehicle v) {
		listaVehiculos.remove(v);
	}
	

	public void entraVehiculoAlCruce(Vehicle v) {
		this.dstJc.entraVehiculoAlCruce(this.id, v);
		// añade el vehículo al “cruceDestino�? de la carretera�?
		}
	
	protected int calculaVelocidadBase() { //Se redefine en freeway y unpavedRoad
		int m = maxVel;
		int n = listaVehiculos.size(); 
		
		return Math.min(m, (m/Math.max(n, 1))+1);
//		int velocidadBase = maxVel;
//		if (listaVehiculos.size() >= 1) {
//			velocidadBase = maxVel /listaVehiculos.size();
//		}
//		if (maxVel < velocidadBase)
//			velocidadBase = maxVel;
//		
//		return velocidadBase;
	}
	
	protected int calculaFactorReduccion(int obstaculos) { //Se redefine
		int factorReduccion = 1;
		if (obstaculos > 0)
			factorReduccion = 2;
		return factorReduccion;
	}
	
	public void avanza() {
		if(listaVehiculos.size() > 0) {
			int obstaculos = 0;
			for (int i = 0; i < listaVehiculos.size(); i++)
				if (listaVehiculos.get(i).getFaulty() > 0)
					obstaculos++;
			
			int velocidadBase = calculaVelocidadBase();
			int factorReduccion = calculaFactorReduccion(obstaculos);
			
			for (int i = 0 ; i < listaVehiculos.size() ; i++) {
				if (!listaVehiculos.get(i).getEstaEnCruce()) {//Si el vehiculo no esta en el cruce
					if(listaVehiculos.get(i).getPos() < this.ultAveriado())
						listaVehiculos.get(i).setVelocidadActual(velocidadBase);
					else
						listaVehiculos.get(i).setVelocidadActual(velocidadBase / factorReduccion);
					listaVehiculos.get(i).avanza();
				}
			}
			
			//Ordenar la lista
			listaVehiculos.sort(this.reversed());
		}
	}
	
	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public GenericJunction<?> getDstJc() {
		return dstJc;
	}
	

	public ObjectStruct getCruceOrigen() {
		return this.srcJc;
	}
	

	public void setDstJc(GenericJunction<?> dstJc) {
		this.dstJc = dstJc;
	}

	public String getNombreSeccion() {
		return "road_report";
	}
	
	public String completar(IniSection is) { //No se si esta bien
		String str = "";
		if(listaVehiculos.size() > 0) {
			for (int i = 0; i < listaVehiculos.size() ;i++) {
				str = str + "(" + listaVehiculos.get(i).getId() + "," + listaVehiculos.get(i).getPos() + "),";
				if (i==listaVehiculos.size()-1)
					str = str.substring(0,str.length() - 1);
			}
			
		}
		is.setValue("state" , str);
		return is.toString();
	}

	
	public int getTam() {
		return listaVehiculos.size();
	}
	
	public int ultAveriado() {
		int ret = this.longitud;
		for (int i = 0; i < this.listaVehiculos.size()-1; i++) 
			if (this.listaVehiculos.get(i).getFaulty()>0) {
				ret = this.listaVehiculos.get(i).getPos();
			}
		return ret;
	}

	@Override
	public int compare(Vehicle o1, Vehicle o2) {		
		//El vehiculo que esta mas cerca del destino est en la pos [0]
		return o1.getPos() - o2.getPos();
	
	}

	public List<Vehicle> getVehiculos() {
		return listaVehiculos;
	}
	


	public int getMaxVel() {
		return maxVel;
	}

	public GenericJunction<?> getSrcJc() {
		return srcJc;
	}

	public String getListaVehiculos() {
		String s= "[";
		if (listaVehiculos.size() >0) {
			for (Vehicle vehicle : listaVehiculos) {
				s+= vehicle.getId() + ", ";
			}
			s = s.substring(0, s.length()-2);
		}
		s+= "]";
		return s;
	}

	@Override
	public String toString() {
		return id ;
	}
	
}
