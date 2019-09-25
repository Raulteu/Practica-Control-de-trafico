package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 1L;
	
	private Comparator<E> cmp;
	

	public SortedArrayList(Comparator<E> cmp) {
		this.cmp = cmp; 
	}
	@Override
	public boolean add(E e) { //Devuelve false si ya existe el elemento y entonces, no se aniade
        boolean notFound = true;
        int i = 0;
        if(this.size() == 0)
        	super.add(e);
        else
        {
	        while(this.size() > i && cmp.compare(this.get(i), e) <= 0)
	        {
	        	if (this.get(i).equals(e))
	        		notFound = false; //Si se encuentra el elemento, habria repetidos, y creo que no se puede
	        						  //Si da igual que se repitan, habria que quitar eso y solo hacer i++
	        	i++;
	        }
	        @SuppressWarnings("unused")
			int j = this.size();
	        if (notFound)
	        	super.add(i, e); //Aniadimos
        }
        return notFound;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection<? extends E> c) { //Devuelve false si algun elemento ya existe, y ese no se añade
		boolean ok = true;
		for (int i = 0; i < c.size(); i++)
		{
			if (!add((E) c))
				ok = false;
			
		}
		return ok;
	// programar insercion ordenada (invocando a add)
	}	
}