package presentacion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JButtonKeyListener implements KeyListener {
	
	OperationDelegate myDelegate = null;

	public JButtonKeyListener( OperationDelegate myDelegate ) {
		this.myDelegate = myDelegate;
	}
	
	public void keyTyped( KeyEvent e ) {
		/*if( e.getKeyChar() == KeyEvent.VK_ESCAPE && e.getSource().getClass().equals( JPanel.class ) )
			myDelegate.invoke();*/
		
		if( e.getKeyChar() == KeyEvent.VK_ENTER )
			myDelegate.invoke();
			
		if( e.getKeyChar() == KeyEvent.VK_SPACE )
			return;
	}

	public void keyPressed( KeyEvent e ) {
	}

	public void keyReleased( KeyEvent e ) {
	}
}