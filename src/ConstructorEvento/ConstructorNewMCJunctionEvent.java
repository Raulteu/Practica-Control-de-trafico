package ConstructorEvento;

import Events.Event;
import Events.NewMostCrowedJunctionEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewMCJunctionEvent extends ConstructorEvents{
	
	public ConstructorNewMCJunctionEvent() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id", "type" };
		this.valoresPorDefecto = new String[] { "","","mc"};
	}

	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if(section.getTag().equals(this.etiqueta) &&
				section.getValue("type").equals("mc"))
			return new NewMostCrowedJunctionEvent(
					//extrae el valor del campo "time" en la seccion
					//0 es el valor por defecto en caso de no specificar el tiempo
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					//extrae el valor del campo "id" de la seccion
					ConstructorEvents.identificadorValido(section, "id"),
					ConstructorEvents.parseaIntNoNegativo(section, "unidadesDeTiempoUsadas",0),
					ConstructorEvents.parseaIntNoNegativo(section, "intervalo", 0));
		else 
			return null;			
	}
	
	

	
	public String toString() {
		return "Nuevo Cruce Congestionado";
	}
}
