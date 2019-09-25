package Paneles;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Modelos.ModeloTabla;

@SuppressWarnings("serial")
public class PanelTabla<T> extends JPanel{
	private ModeloTabla<T> modelo;
	
	public PanelTabla(String bordeId, ModeloTabla<T> modelo) {
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder(bordeId));
		this.modelo = modelo;
		JTable tabla = new JTable(this.modelo);
		this.add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}
	
	public void reset(String bordeId) {
		this.setBorder(BorderFactory.createTitledBorder(bordeId));
	}

	public ModeloTabla<T> getModelo() {
		return modelo;
	}

}
