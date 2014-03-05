package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import negocio.IUsuarioManager;
import negocio.UsuarioManager;

import persistencia.Usuario;

class StatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel statusLabel;
	Usuario usuario = null;
	public StatusBar(Usuario usuario)
	{
		this.usuario = usuario;
		setLayout(new BorderLayout(2, 2)); 
		statusLabel = new JLabel();
		statusLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		statusLabel.setBorder(BorderFactory.createLoweredBevelBorder()); 
		statusLabel.setForeground(Color.black);
		statusLabel.setText(" Usuario: " + usuario.getUsername());
		add(BorderLayout.CENTER, statusLabel); 
		JLabel dummyLabel = new JLabel(" Último acceso: " + formatDate() + " ");
		dummyLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		dummyLabel.setBorder(BorderFactory.createLoweredBevelBorder()); 
		add(BorderLayout.EAST, dummyLabel); 
	}
	
	@SuppressWarnings("deprecation")
	public String formatDate(){
		// este metodo muestra la fecha de forma elegante
		Date date = new java.sql.Date( usuario.getLastLogin().getTimeInMillis());
		usuario.setLastLogin(Calendar.getInstance()); // asigno fecha actual

		IUsuarioManager unUsuarioManager = new UsuarioManager();
		try {
			unUsuarioManager.modificar(usuario);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
		
		if(date.getYear() == 0)
			return "----"; // si tiene año 1900 quiere decir que es su primer acceso al sistema
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd 'de' MMMMMMMMMM 'de' yyyy", new Locale("es", "ARG"));
		return sdf.format(date);
	}
	
}