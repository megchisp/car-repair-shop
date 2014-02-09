package proyectoanalista.presentacion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

public class jTextAreaKeyListener implements KeyListener {
	
	JTextArea jTextArea = null;

	public jTextAreaKeyListener( JTextArea jTextArea ) {
		this.jTextArea = jTextArea;
	}
	
	public void keyTyped( KeyEvent e ) {
		if( e.getKeyChar() == KeyEvent.VK_ENTER )
			jTextArea.transferFocus();
			
		if( e.getKeyChar() == KeyEvent.VK_SPACE )
			return;
	}

	public void keyPressed( KeyEvent e ) {
	}

	public void keyReleased( KeyEvent e ) {
	}
}
