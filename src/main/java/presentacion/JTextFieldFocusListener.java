package presentacion;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class JTextFieldFocusListener implements FocusListener {

	JTextField jTextField = null;
	
	public JTextFieldFocusListener( JTextField jTextField ) {
		this.jTextField = jTextField;
	}
	
//	@Override
	public void focusGained( FocusEvent e ) {
		if(this.jTextField.getText().contains("< ")) // if para que no elimine el textfield si el usuario se equivoca
			this.jTextField.setText( "" );
		this.jTextField.setBorder( ( new JTextField() ).getBorder() );
		this.jTextField.setForeground( Color.BLACK );
		
		this.jTextField.removeFocusListener( this );
	}

//	@Override
	public void focusLost( FocusEvent e ) {
	}
}