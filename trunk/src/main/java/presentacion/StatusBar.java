package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class StatusBar extends JPanel
{

	private static final long serialVersionUID = 1L;
	private JLabel statusLabel;
	public StatusBar()
	{
		setLayout(new BorderLayout(2, 2)); 
		statusLabel = new JLabel();
		statusLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		statusLabel.setBorder(BorderFactory.createLoweredBevelBorder()); 
		statusLabel.setForeground(Color.black);
		add(BorderLayout.CENTER, statusLabel); 
		JLabel dummyLabel = new JLabel(" Último acceso: ");
		dummyLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		dummyLabel.setBorder(BorderFactory.createLoweredBevelBorder()); 
		add(BorderLayout.EAST, dummyLabel); 
	} 
	public void setStatus(String status) 
	{
		statusLabel.setText(" " + status); 
	} 
	public String getStatus()
	{
		return statusLabel.getText(); 
	}
}