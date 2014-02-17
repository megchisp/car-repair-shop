package presentacion;

import java.awt.event.KeyEvent;

public class JTextFieldPhoneNumber extends JTextFieldUpperCased {
	
	private static final long serialVersionUID = 1L;
	
	final static String goodchars = "0123456789()/-\b" + 
			"abcdefghijklmnñopqrstuvwxyz " +
			"ABCDEFGHIJKLMNÑOPQRSTUVWXYZ" +
	(char) KeyEvent.VK_ESCAPE + (char) KeyEvent.VK_ENTER;; // habilito numeros, letras, backspace, enter, escape, parentesis y guion
	
	
    public void processKeyEvent( KeyEvent e ) {
        char c = e.getKeyChar();
        if (e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT)
        if( !( goodchars.indexOf( c ) > -1 )) {
            e.consume();
            
            return;
        }
        
        super.processKeyEvent( e );
    }
}