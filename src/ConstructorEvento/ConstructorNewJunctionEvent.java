package ConstructorEvento;

import Events.Event;
import Events.NewJunctionEvent;
import Exceptions.ArgumentException;
import ini.IniSection;

public class ConstructorNewJunctionEvent extends ConstructorEvents {
	
	public ConstructorNewJunctionEvent() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		this.valoresPorDefecto = new String[] { "", "" };
	}

	@Override
	public Event parser(IniSection section) throws ArgumentException {
		if(!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null)
			return null;
		else
			return new NewJunctionEvent(
					//extrae el valor del campo "time" en la seccion
					//0 es el valor por defecto en caso de no specificar el tiempo
					ConstructorEvents.parseaIntNoNegativo(section, "time", 0),
					//extrae el valor del campo "id" de la seccion
					ConstructorEvents.identificadorValido(section, "id"));
	}

	

	public String toString() {
		return "Nuevo Cruce";
	}
}
