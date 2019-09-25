package ConstructorEvento;

import Events.Event;
import Events.NewRoundRobinJunctionEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewRRJunctionEvent extends ConstructorEvents{
	

	public ConstructorNewRRJunctionEvent() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" ,"type","min_time_slice" ,"max_time_slice"};
		this.valoresPorDefecto = new String[] {"", "", "rr", "", ""};
		}

	@Override
	public Event parser(IniSection section) throws ArgumentException {

		if(section.getTag().equals(this.etiqueta) &&
				section.getValue("type").equals("rr"))
			return new NewRoundRobinJunctionEvent( //Aniade una rotonda
					//extrae el valor del campo "time" en la seccion
					//0 es el valor por defecto en caso de no specificar el tiempo
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					//extrae el valor del campo "id" de la seccion
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.parseaIntNoNegativo(section, "min_time_slice", 0),
					ConstructorEvents.parseaIntNoNegativo(section, "max_time_slice", 0));
		else 
			return null;
	}
	
	

	
	public String toString() {
		return "Nuevo Cruce Circular";
	}
}
