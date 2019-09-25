package ConstructorEvento;

import Events.Event;
import Exceptions.ArgumentException;
import ini.IniSection;

public abstract class ConstructorEvents {

	protected String etiqueta; //Etiqueta de la entrada (new_road...)
	protected String[] claves; //Campos de la entrada (time, vehicles...)
	protected String[] valoresPorDefecto;
	//...
	
	public ConstructorEvents() {
		this.etiqueta = null;
		this.claves = null;
	}
	
	private static boolean esIdentificadorValido (String id) {
		return id != null && id.matches("[a-z0-9_,]+"); //aqui puse la coma para los itinerarios
	}
	
	protected static String identificadorValido (IniSection section, String key) throws ArgumentException {
		String s = section.getValue(key);
		if (!esIdentificadorValido(s))
			throw new ArgumentException("el valor " + s + " para " + key + " no es un ID valido");
		else
			return s;
	}
	
	protected static int parseaInt (IniSection section, String key) throws ArgumentException {
		String v = section.getValue(key);
		if (v == null)
			throw new ArgumentException("Valor inexistente para la clave " + key);
		else
			return Integer.parseInt(v);
	}
	
	protected static double parseaDouble(IniSection section, String key, int valorPorDefecto) {
		String v = section.getValue(key);
		return (v !=null) ? Double.parseDouble(section.getValue(key)) : valorPorDefecto;
	}
	
	protected static int parseaInt (IniSection section, String key, int valorPorDefecto) {
		String v = section.getValue(key);
		return (v != null) ? Integer.parseInt(section.getValue(key)) :
			valorPorDefecto;
	}
	
	protected static int parseaIntNoNegativo (IniSection section, String key, int valorPorDefecto) throws ArgumentException {
		int i = ConstructorEvents.parseaInt(section, key, valorPorDefecto);
		if ( i < 0)
			throw new ArgumentException("El valor " + i + " para " + key + " no es un ID valido");
		else return i;
	}
	
	protected static long parseaSemilla (IniSection section, String key, int valorPorDefecto) throws ArgumentException {
		String v = section.getValue(key);
		if (v == null)
			throw new ArgumentException("Valor inexistente para la clave " + key);
		else
			return (v != null) ? Long.parseLong(v) : valorPorDefecto;
	}
	
	public abstract Event parser(IniSection section) throws ArgumentException;

	public String template() {
		String s = "[" + this.etiqueta + "]" + '\n' ; 
				for(int i = 0; i< this.claves.length; i ++) {
					s += this.claves[i] + " = " + this.valoresPorDefecto[i] + '\n' ;
				}
		return s;
	}

}
