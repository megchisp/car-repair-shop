package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import negocio.ProveedorManager;

import persistencia.Proveedor;

public class ModificarProveedor extends JDialog {

	private static final long serialVersionUID = 1L;
	
	Proveedor proveedor = null;
	
	JPanel jPanelModificarProveedor = null;
	
	JLabel jLabelIdProveedor = null;
	JLabel jLabelNombre = null;
	JLabel jLabelTelefono = null;
	JLabel jLabelTelefonoAlternativo = null;
	
	JTextFieldUpperCased jTextFieldNombre = null;
	JTextFieldUpperCased jTextFieldTelefono = null;
	JTextFieldUpperCased jTextFieldTelefonoAlternativo = null;
		
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	
	public ModificarProveedor( JFrame padre, String titulo, String titleBorder, Proveedor proveedor ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 477, 186 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		
		this.proveedor = proveedor;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
				
		jPanelModificarProveedor = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelModificarProveedor.setBorder( titledBorder );
		
		jPanelModificarProveedor.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;
		
		inicializarVariables();
		cerrarEsc();

		a.gridy = 0;
		a.gridx = 0;
		jPanelModificarProveedor.add( new JLabel(" "), a );
		a.gridy = 3;
		a.gridx = 0;
		jPanelModificarProveedor.add( jLabelNombre, a );
		a.gridy = 3;
		a.gridx = 1;
		a.insets = new Insets( 0, -19, 0, 0 );
		jPanelModificarProveedor.add( jTextFieldNombre, a );
		a.gridy = 4;
		a.gridx = 0;
		jPanelModificarProveedor.add( new JLabel(" "), a );		
		
			JPanel jPanelTelCel = new JPanel();
			jPanelTelCel.setLayout( new GridBagLayout() );
			
			GridBagConstraints b = new GridBagConstraints();
			
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 0, 0, 0 );
			jPanelTelCel.add( jLabelTelefono, b );
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, -20, 0, 0 );
			jPanelTelCel.add( jTextFieldTelefono, b );
			b.gridy = 0;
			b.gridx = 2;
			b.insets = new Insets( 0, 18, 0, 0 );
			jPanelTelCel.add( jLabelTelefonoAlternativo, b );
			b.gridy = 0;
			b.gridx = 3;
			b.insets = new Insets( 0, 6, 0, 0 );
			jPanelTelCel.add( jTextFieldTelefonoAlternativo, b );
		
		a.gridy = 5;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelModificarProveedor.add( jPanelTelCel, a );
		a.gridy = 6;
		a.gridx = 0;
		a.gridwidth = 1;
		jPanelModificarProveedor.add( new JLabel(" "), a );
		a.gridy = 7;
		a.gridx = 0;
		jPanelModificarProveedor.add( new JLabel(" "), a );
				
			JPanel jPanelBotones = new JPanel();
			jPanelBotones.setLayout( new GridBagLayout() );
			
			GridBagConstraints d = new GridBagConstraints();
			
			d.gridy = 0;
			d.gridx = 0;
			d.insets = new Insets( 0, 0, 0, 0 );
			jPanelBotones.add( jButtonAceptar, d );
			d.gridy = 0;
			d.gridx = 1;
			d.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotones.add( jButtonCancelar, d );
		
		a.gridy = 7;
		a.gridx = 1;
		a.anchor = GridBagConstraints.EAST;
		jPanelModificarProveedor.add( jPanelBotones, a );
		a.gridy = 8;
		a.gridx = 0;
		jPanelModificarProveedor.add( new JLabel(" "), a );
			
		this.getContentPane().add( jPanelModificarProveedor, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ModificarProveedor.this.dispose();
			}
		};
		jPanelModificarProveedor.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void inicializarVariables() {	
		Dimension dimensionLabel = new Dimension( 75, 25 );
						
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldUpperCased();
		//int filaSeleccionada = buscarProveedor.jTableBuscarProveedor.getSelectedRow();
		jTextFieldNombre.setText(proveedor.getNombre());
		jTextFieldNombre.setPreferredSize( new Dimension( 360, 25 ) );
		
		jLabelTelefono = new JLabel( "Teléfono: " );
		jLabelTelefono.setPreferredSize( dimensionLabel );
		jTextFieldTelefono = new JTextFieldPhoneNumber();
		jTextFieldTelefono.setText(proveedor.getTelefono());
		jTextFieldTelefono.setPreferredSize( new Dimension( 110, 25 ) );
		
		jLabelTelefonoAlternativo = new JLabel( "Teléfono alternativo: " );
		jTextFieldTelefonoAlternativo = new JTextFieldPhoneNumber();
		jTextFieldTelefonoAlternativo.setText(proveedor.getTelefonoAlternativo());
		jTextFieldTelefonoAlternativo.setPreferredSize( new Dimension( 123, 25 ) );
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				aceptarModificarProveedor();
			}
		});
		
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cancelarModificarProveedor();
			}
		});
	}
	
	private boolean characterVaryingExceeded(){
		int cont = 0;
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		// Valido el nombre (hasta 25 caracteres)
		jTextFields.add( jTextFieldNombre );
		for( JTextField jTextField : jTextFields ){
			if(jTextField.getText().trim().length() > 25){
				jTextField.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jTextField.addFocusListener( new JTextFieldFocusListener( jTextField ) );
				cont++;
			}
		}
		
		jTextFields.clear();
		// valido los telefonos
		jTextFields.add( jTextFieldTelefono);
		jTextFields.add( jTextFieldTelefonoAlternativo);
		for( JTextField jTextField : jTextFields ){
			if(jTextField.getText().trim().length() > 25){
				jTextField.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jTextField.addFocusListener( new JTextFieldFocusListener( jTextField ) );
				cont++;
			}
		}
		
		if( cont > 0 )
		{
			JOptionPane.showMessageDialog( this, "Uno o más campos se encuentran excediendo el tamaño máximo permitido", "Campo excedido", JOptionPane.ERROR_MESSAGE );
			return false;
		}
		return true;
	}
	
	private void aceptarModificarProveedor() {
		if( validarDatos() && characterVaryingExceeded()) {
			int option = 0;
			
			proveedor.setNombre( jTextFieldNombre.getText().trim()  );
			proveedor.setTelefono( jTextFieldTelefono.getText().trim() );
			proveedor.setTelefonoAlternativo( jTextFieldTelefonoAlternativo.getText().trim() );
						
			ProveedorManager proveedorManager = new ProveedorManager();
			try{
				option = proveedorManager.modificar(proveedor);
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				e.printStackTrace();
				return;
			}
			//int filaSeleccionada = buscarProveedor.jTableBuscarProveedor.getSelectedRow();
			
			switch( option ) {
			case 1:
				JOptionPane.showMessageDialog( this, "Se modificó con éxito el proveedor", "Éxito", JOptionPane.INFORMATION_MESSAGE );
				cleanAllFields();
				this.dispose();
				
				break;
			case 2:
				JOptionPane.showMessageDialog( this, "Ya existe un proveedor con el nombre ingresado", "Error", JOptionPane.ERROR_MESSAGE );
				
				jTextFieldNombre.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
				break;
			default:
				// este mensaje nunca debería aparecer
				JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	private void cancelarModificarProveedor() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private boolean validarDatos() {
		int cont = 0;
		
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		if( jTextFieldNombre.getText().contains( "<" ) ) {
			jTextFieldNombre.setText( "" );
		}
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldTelefono );
		//jTextFields.add( jTextFieldCelular );
		
		for( JTextField jTextField : jTextFields )
			cont += isEmpty( jTextField );
		
		if( cont > 0 ){
			JOptionPane.showMessageDialog( this, "Uno o más campos obligatorios se encuentran vacíos", "Campo vacío", JOptionPane.ERROR_MESSAGE );
			return false;
		}
		return true;
	}
	
	private int isEmpty( JTextField jTextField ) {
		if( jTextField.getText().isEmpty() || jTextField.getText().contains( "<" ) ) {
			jTextField.setText( "" );
			jTextField.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextField.addFocusListener( new JTextFieldFocusListener( jTextField ) );
			
			return 1;
		}
		
		return 0;
	}
	
		
	private void cleanAllFields() {
		jTextFieldNombre.setText( "" );
		jTextFieldTelefono.setText( "" );
		jTextFieldTelefonoAlternativo.setText( "" );
		
	}
	

}
