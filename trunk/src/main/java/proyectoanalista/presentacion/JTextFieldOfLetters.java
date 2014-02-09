package proyectoanalista.presentacion;

import java.awt.event.KeyEvent;

public class JTextFieldOfLetters extends JTextFieldUpperCased {

	private static final long serialVersionUID = 1L;
	final static String goodchars = "abcdefghijklmnñopqrstuvwxyz'" +
			"ABCDEFGHIJKLMNÑOPQRSTUVWXYZ \b" + (char) KeyEvent.VK_ESCAPE + (char) KeyEvent.VK_ENTER + 
			 (char) KeyEvent.VK_DELETE; 

    public void processKeyEvent( KeyEvent e ) {
        char c = e.getKeyChar();
        
        if (e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT)
	        if( !( goodchars.indexOf( c ) > -1 ) ) {
	            e.consume();
	            
	            return;
	        }

        super.processKeyEvent( e );
    }
    
}