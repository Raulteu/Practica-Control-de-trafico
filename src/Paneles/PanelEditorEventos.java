package Paneles;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import MVC.View;
import Opcional.PopUpMenu;


@SuppressWarnings("serial")
public class PanelEditorEventos extends PanelAreaTexto{

	public PanelEditorEventos(String titulo, String texto, boolean editable, View mainWindow) {
		super(titulo,editable);
		this.setTexto(texto);
		//Opcional
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		
		this.areaTexto.add(popUp);

		this.areaTexto.addMouseListener(new MouseListener() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger() && areaTexto.isEnabled()) {
					showPopup(e);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger() && areaTexto.isEnabled()) {
					popUp.show(e.getComponent(), e.getX(), e.getY());
					System.out.println("Mouse P: " + "(" + e.getX() + "," + e.getY() + ")");
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isPopupTrigger() && areaTexto.isEnabled()) {
					popUp.show(e.getComponent(), e.getX(), e.getY());
					System.out.println("Mouse C: " + "(" + e.getX() + ","
						+ e.getY() + ")");
				}
			}
			
			 private void showPopup(MouseEvent e) {
				 if (e.isPopupTrigger()) {
					 popUp.show(e.getComponent(), e.getX(), e.getY());
				 }
			 }	 
		});
	}

}
