package presentacion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextFieldKeyListener implements KeyListener {
	
	JTextField jTextField = null;

	public JTextFieldKeyListener( JTextField jTextField ) {
		this.jTextField = jTextField;
	}
	
	public void keyTyped( KeyEvent e ) {
		if( e.getKeyChar() == KeyEvent.VK_ENTER )
			jTextField.transferFocus();
			
		if( e.getKeyChar() == KeyEvent.VK_SPACE )
			return;
	}

	public void keyPressed( KeyEvent e ) {
	}

	public void keyReleased( KeyEvent e ) {
	}
}
