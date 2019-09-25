package Opcional;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class JtextOutput extends OutputStream {
		
	private JTextArea areaTexto;
	private OutputStream output;
	public JtextOutput(JTextArea ja, OutputStream o) {
		areaTexto = ja;
		output = o;
	}
		

	@Override
	public void write(byte [] b) throws IOException {
		areaTexto.setText(new String(b));			
	}


	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
