package Paneles;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Opcional.JtextOutput;

public abstract class PanelAreaTexto extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextArea areaTexto;
	
	public PanelAreaTexto(String titulo, boolean editable) {
		this.setLayout(new GridLayout(1,1));
		this.areaTexto = new JTextArea(40,30);
		this.areaTexto.setEditable(editable);
		this.add(new JScrollPane(areaTexto,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		this.setBorde(titulo);
	}

	public void setBorde(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}
	
	public String getTexto() {return areaTexto.getText();}
	public void setTexto(String texto) { this.areaTexto.setText(texto);}
	public void limpiar() {this.areaTexto.setText("");}
	public void inserta(String valor) {
		this.areaTexto.insert(valor, this.areaTexto.getCaretPosition());
	}
	
}
