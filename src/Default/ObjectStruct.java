package Default;

import Exceptions.ErrorDeSimulacion;
import ini.IniSection;

public abstract class ObjectStruct {
	protected String id;

	public ObjectStruct(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String generaInforme(int tiempo) { 
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completar(is);
		return is.toString();
	
	}
	
	public abstract void avanza() throws ErrorDeSimulacion;
	
	public abstract String getNombreSeccion();
	
	public abstract String completar(IniSection is);
}
