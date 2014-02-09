package proyectoanalista.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonActionListener implements ActionListener {
	
	OperationDelegate myDelegate;

	public JButtonActionListener( OperationDelegate myDelegate ) {
		this.myDelegate = myDelegate;
	}
	
	public void actionPerformed( ActionEvent e ) {
		myDelegate.invoke();
	}
}