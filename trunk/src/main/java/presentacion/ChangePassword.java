package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import negocio.IUsuarioManager;
import negocio.UsuarioManager;

import persistencia.Usuario;


public class ChangePassword extends JDialog {

	private static final long serialVersionUID = 1L;
	
    JPanel jPanelChangePassword = null;
    JPanel jPanelDatosPrimarios = null;
    JPanel jPanelBotones = null;
    
    JLabel jLabelOldPassword = null;
    JLabel jLabelNewPassword = null;
    JLabel jLabelNewPasswordAgain = null;
    
    JPasswordField jPasswordFieldOldPassword = null;
    JPasswordField jPasswordFieldNewPassword = null;
    JPasswordField jPasswordFieldNewPasswordAgain = null;

	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	JCheckBox jCheckBoxFallecido = null;
	
	Usuario usuario = null;
	IUsuarioManager unUsuarioManager = null;

	public ChangePassword( JFrame padre, String titulo, String titleBorder, Usuario usuario ) {
		super( padre, titulo, true );

		this.setPreferredSize( new Dimension( 360, 240 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		this.usuario = usuario;
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);

		jPanelChangePassword = new JPanel();

		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder(BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		
		jPanelChangePassword.setBorder( titledBorder );
		jPanelChangePassword.setPreferredSize(new Dimension( 450, 210 ));
		jPanelChangePassword.setLayout(new FlowLayout(FlowLayout.CENTER,0,15) );
		jPanelChangePassword.setFocusable( true );

		inicializarVariables();
		inicializarBotones();
		cerrarEsc();
		
		jPanelChangePassword.add(jPanelDatosPrimarios);
		jPanelChangePassword.add(jPanelBotones);

		this.getContentPane().add( jPanelChangePassword, BorderLayout.NORTH );
		
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}

	private void cerrarEsc(){
		// esta funcion hace que la ventana ChangePassword se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ChangePassword.this.dispose();
			}
		};
		jPanelChangePassword.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	OperationDelegate aceptarChangePassword = new OperationDelegate() {
		public void invoke() { 
			aceptarChangePassword();
		}
	};

	OperationDelegate cancelarChangePassword = new OperationDelegate() {
		public void invoke() {
			cancelarChangePassword();
		}
	};
	
	private void inicializarVariables() {
		inicializarDatosPrimarios();
		
		// hace que el cursor se posicione en el jPasswordFieldOldPassword al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jPasswordFieldOldPassword.requestFocusInWindow();
			}
		});
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( aceptarChangePassword ) );
		jButtonAceptar.addKeyListener( new JButtonKeyListener( aceptarChangePassword ) );

		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new JButtonActionListener( cancelarChangePassword ) );
		jButtonCancelar.addKeyListener( new JButtonKeyListener( cancelarChangePassword ) );
	}
	
	private void inicializarDatosPrimarios(){
		
		jLabelOldPassword = new JLabel("Contraseña actual: ");
		jLabelNewPassword = new JLabel("Nueva contraseña: ");
		jLabelNewPasswordAgain = new JLabel("Confirmar nueva contraseña: ");
		
		jPasswordFieldOldPassword = new JPasswordField();
		jPasswordFieldNewPassword = new JPasswordField();
		jPasswordFieldNewPasswordAgain = new JPasswordField();
		
		jPasswordFieldOldPassword.setPreferredSize( new Dimension( 150, 25 ) );
		jPasswordFieldNewPassword.setPreferredSize( new Dimension( 150, 25 ) );
		jPasswordFieldNewPasswordAgain.setPreferredSize( new Dimension( 150, 25 ) );
		
		jPanelDatosPrimarios = new JPanel();
		jPanelDatosPrimarios.setLayout( new GridBagLayout() );
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;
		
		a.gridy = 1;
		a.gridx = 0;
		jPanelDatosPrimarios.add( jLabelOldPassword, a );
		a.gridy = 1;
		a.gridx = 1;
		a.insets = new Insets( 0, 10, 0, 0 );
		jPanelDatosPrimarios.add( jPasswordFieldOldPassword, a );
		a.gridy = 2;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 3;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelNewPassword, a );
		a.gridy = 3;
		a.gridx = 1;
		a.insets = new Insets( 0, 10, 0, 0 );
		jPanelDatosPrimarios.add( jPasswordFieldNewPassword, a );
		a.gridy = 4;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelNewPasswordAgain, a );
		a.gridy = 5;
		a.gridx = 1;
		a.insets = new Insets( 0, 10, 0, 0 );
		jPanelDatosPrimarios.add( jPasswordFieldNewPasswordAgain, a );
	}

	private void inicializarBotones(){
		jPanelBotones = new JPanel();
		jPanelBotones.setPreferredSize( new Dimension( 305, 40 ) );
		jPanelBotones.setLayout( new GridBagLayout() );
		GridBagConstraints e = new GridBagConstraints();
		e.anchor = GridBagConstraints.EAST;
		
		e.gridy = 0;
		e.gridx = 0;
		e.insets = new Insets( 0, 85, 0, 0 );
		jPanelBotones.add( jButtonAceptar, e );
		e.gridy = 0;
		e.gridx = 1;
		e.insets = new Insets( 0, 20, 0, 0 );
		jPanelBotones.add( jButtonCancelar, e );
	}
	
	private void cancelarChangePassword() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private void aceptarChangePassword(){
		int option = 0;
		
		if(characterVaryingExceeded() && validaPassword()) {
						
			IUsuarioManager usuarioManager = new UsuarioManager();
			try {
				option = usuarioManager.modificarPassword( usuario , jPasswordFieldOldPassword.getPassword(), jPasswordFieldNewPassword.getPassword());
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			
			switch( option ) {
			case 1:
				JOptionPane.showMessageDialog( this, "La contraseña se modificó con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE );
				cleanAllFields();
				this.dispose();
				
				break;
			case 2:
				JOptionPane.showMessageDialog( this, "La contraseña actual es incorrecta", "Error", JOptionPane.ERROR_MESSAGE );
				jPasswordFieldOldPassword.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jPasswordFieldOldPassword.addFocusListener( new JTextFieldFocusListener( jPasswordFieldOldPassword ) );
				
				break;
			case 3:
				JOptionPane.showMessageDialog( null, "El usuario no se encuentra en la base de datos", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );

				break;
			default:
				// este mensaje nunca debería aparecer
				JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private boolean validaPassword(){
		boolean esValido;
		esValido =  (jPasswordFieldNewPassword.getText().equals(jPasswordFieldNewPasswordAgain.getText()));
		
		if(!esValido){
			JOptionPane.showMessageDialog( this, "La confirmación de la nueva contraseña no coincide", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
			jPasswordFieldNewPasswordAgain.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jPasswordFieldNewPasswordAgain.addFocusListener( new JTextFieldFocusListener( jPasswordFieldNewPasswordAgain ) );
		}
		return esValido;
	}

	private void cleanAllFields() {
		jPasswordFieldOldPassword.setText("");
		jPasswordFieldNewPassword.setText("");
		jPasswordFieldNewPasswordAgain.setText("");
	}
		   
	@SuppressWarnings("deprecation")
	private boolean characterVaryingExceeded(){
		int cont = 0;
		List<JPasswordField> jPasswordFields = new ArrayList<JPasswordField>();
		
		jPasswordFields.add( jPasswordFieldOldPassword );
		jPasswordFields.add( jPasswordFieldNewPassword );
		jPasswordFields.add( jPasswordFieldNewPasswordAgain );
		
		
		for( JPasswordField jPasswordField : jPasswordFields ){
			if(jPasswordField.getText().trim().length() > 15){
				jPasswordField.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jPasswordField.addFocusListener( new JTextFieldFocusListener( jPasswordField ) );
				cont++;
			}
		}
		
		if( cont > 0 )
		{
			JOptionPane.showMessageDialog( this, "La contraseña no puede exceder los 15 caracteres", "Campo excedido", JOptionPane.ERROR_MESSAGE );
			return false;
		}
		return true;
	
	}
	
}