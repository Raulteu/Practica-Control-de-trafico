/**
 * 
 */
package Modelos;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import Default.TrafficSimulator.TrafficSimulatorObserver;
import MVC.Controller;

@SuppressWarnings("serial")
public abstract class ModeloTabla<T> extends DefaultTableModel implements TrafficSimulatorObserver {


	protected String[] columnId;
	protected List<T> lista;
	public ModeloTabla(String [] columnIdEventos, Controller ctrl) {
		this.lista = null;
		columnId = columnIdEventos;
		ctrl.addObserver(this);
	}
	
	@Override
	public String getColumnName(int column) {
		return this.columnId[column];
	}
	
	@Override
	public int getColumnCount() {
		return this.columnId.length;
	}
	
	@Override
	public int getRowCount() {
		return this.lista == null ? 0 : this.lista.size();
	}
	
}
