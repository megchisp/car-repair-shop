package presentacion;

import java.awt.event.KeyEvent;

// este tipo de JTextField admite solo numeros y letras (para chapas pantetes)
public class JTextFieldOfPlateNumbers extends JTextFieldOfLetters {

	private static final long serialVersionUID = 1L;
	
	final static String goodchars = "abcdefghijklmnñopqrstuvwxyz" +
			"ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789 \b" + (char) KeyEvent.VK_ESCAPE + (char) KeyEvent.VK_ENTER + 
			(char) KeyEvent.VK_LEFT + (char) KeyEvent.VK_RIGHT + (char) KeyEvent.VK_DELETE; 

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