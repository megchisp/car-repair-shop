package presentacion;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;

public class JComboBoxFocusListener implements FocusListener {

	JComboBox<String> jComboBox1 = null;
	JComboBox<String> jComboBox2 = null;
	JComboBox<String> jComboBox3 = null;
	
	public JComboBoxFocusListener( JComboBox<String> jComboBox1, JComboBox<String> jComboBox2, JComboBox<String> jComboBox3 ) {
		this.jComboBox1 = jComboBox1;
		this.jComboBox2 = jComboBox2;
		this.jComboBox3 = jComboBox3;
	}
	
	public void focusGained( FocusEvent e ) {
		this.jComboBox1.setBorder( ( new JComboBox<String>() ).getBorder() );
		this.jComboBox1.setForeground( Color.BLACK );
		this.jComboBox2.setBorder( ( new JComboBox<String>() ).getBorder() );
		this.jComboBox2.setForeground( Color.BLACK );
		this.jComboBox3.setBorder( ( new JComboBox<String>() ).getBorder() );
		this.jComboBox3.setForeground( Color.BLACK );
		
		this.jComboBox1.removeFocusListener( this );
	}

	public void focusLost( FocusEvent e ) {
	}
}