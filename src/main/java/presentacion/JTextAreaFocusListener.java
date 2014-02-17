package presentacion;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;

public class JTextAreaFocusListener implements FocusListener {

	JTextArea jTextArea = null;
	
	public JTextAreaFocusListener( JTextArea jTextArea ) {
		this.jTextArea = jTextArea;
	}
	
//	@Override
	public void focusGained( FocusEvent e ) {
		this.jTextArea.setText( "" );
		this.jTextArea.setBorder( ( new JTextArea() ).getBorder() );
		this.jTextArea.setForeground( Color.BLACK );
		
		this.jTextArea.removeFocusListener( this );
	}

//	@Override
	public void focusLost( FocusEvent e ) {
	}
}