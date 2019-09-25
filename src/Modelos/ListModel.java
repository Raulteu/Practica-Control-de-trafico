package Modelos;

import java.util.List;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class ListModel<T> extends DefaultListModel<T> {
	private List<T> lista;
	
	public ListModel() { this.lista = null; }
	
	public void setList(List<T> lista) {
		this.lista = lista;
		fireContentsChanged(this, 0, this.lista.size());
	}
	
	@Override
	public T getElementAt(int index) { 
		return this.lista.get(index);
	}
	

	@Override
	public int getSize() {
		return this.lista == null ? 0 : this.lista.size();
	}

}