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
import java.sql.SQLException;
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

import negocio.IMecanicoManager;
import negocio.MecanicoManager;

import persistencia.Mecanico;

public class ModificarMecanico extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	BuscarMecanico buscarMecanico = null;
	
	JPanel jPanelModificarMecanico = null;
	
	JLabel jLabelID = null;
	JLabel jLabelNombre = null;
	JLabel jLabelApellido = null;
	JLabel jLabelTelefono = null;
	JLabel jLabelCelular = null;
	JLabel jLabelDomicilio = null;
	JLabel jLabelLocalidad = null;
	JLabel jLabelCodigoPostal = null;
	
	JTextFieldUpperCased jTextFieldID = null;
	JTextFieldUpperCased jTextFieldNombre = null;
	JTextFieldUpperCased jTextFieldApellido = null;
	JTextFieldUpperCased jTextFieldTelefono = null;
	JTextFieldUpperCased jTextFieldCelular = null;
	JTextFieldUpperCased jTextFieldDomicilio = null;
	JTextFieldUpperCased jTextFieldLocalidad = null;
	JTextFieldUpperCased jTextFieldCodigoPostal = null;
	
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	Mecanico mecanico = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	ModificarMecanico( JFrame padre, String titulo, String titleBorder, BuscarMecanico buscarMecanicoPadre, Mecanico mecanico ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 426, 356 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		this.mecanico = mecanico;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		buscarMecanico = buscarMecanicoPadre;
		
		jPanelModificarMecanico = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelModificarMecanico.setBorder( titledBorder );
		
		jPanelModificarMecanico.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;
		
		inicializarVariables();
		cerrarEsc();

		a.gridy = 0;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		a.gridy = 1;
		a.gridx = 0;
		jPanelModificarMecanico.add( jLabelID, a );
		a.gridy = 1;
		a.gridx = 1;
		a.insets = new Insets( 0, -53, 0, 0 );
		jPanelModificarMecanico.add( jTextFieldID, a );
		a.gridy = 2;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );		
		a.gridy = 3;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelModificarMecanico.add( jLabelNombre, a );
		a.gridy = 3;
		a.gridx = 1;
		a.insets = new Insets( 0, -18, 0, 0 );
		jPanelModificarMecanico.add( jTextFieldNombre, a );
		a.gridy = 4;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelModificarMecanico.add( jLabelApellido, a );
		a.gridy = 5;
		a.gridx = 1;
		a.insets = new Insets( 0, -18, 0, 0 );
		jPanelModificarMecanico.add( jTextFieldApellido, a );
		a.gridy = 6;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		
			JPanel jPanelTelCel = new JPanel();
			jPanelTelCel.setLayout( new GridBagLayout() );
			
			GridBagConstraints b = new GridBagConstraints();
			
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 0, 0, 0 );
			jPanelTelCel.add( jLabelTelefono, b );
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, -18, 0, 0 );
			jPanelTelCel.add( jTextFieldTelefono, b );
			b.gridy = 0;
			b.gridx = 2;
			b.insets = new Insets( 0, 18, 0, 0 );
			jPanelTelCel.add( jLabelCelular, b );
			b.gridy = 0;
			b.gridx = 3;
			b.insets = new Insets( 0, 5, 0, 0 );
			jPanelTelCel.add( jTextFieldCelular, b );
		
		a.gridy = 7;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelModificarMecanico.add( jPanelTelCel, a );
		a.gridy = 8;
		a.gridx = 0;
		a.gridwidth = 1;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		a.gridy = 9;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelModificarMecanico.add( jLabelDomicilio, a );
		a.gridy = 9;
		a.gridx = 1;
		a.insets = new Insets( 0, -18, 0, 0 );
		jPanelModificarMecanico.add( jTextFieldDomicilio, a );
		a.gridy = 10;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		
			JPanel jPanelLocCP = new JPanel();
			jPanelLocCP.setLayout( new GridBagLayout() );
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridy = 0;
			c.gridx = 0;
			c.insets = new Insets( 0, 0, 0, 0 );
			jPanelLocCP.add( jLabelLocalidad, c );
			c.gridy = 0;
			c.gridx = 1;
			c.insets = new Insets( 0, -18, 0, 0 );
			jPanelLocCP.add( jTextFieldLocalidad, c );
			c.gridy = 0;
			c.gridx = 2;
			c.insets = new Insets( 0, 18, 0, 0 );
			jPanelLocCP.add( jLabelCodigoPostal, c );
			c.gridy = 0;
			c.gridx = 3;
			c.insets = new Insets( 0, 6, 0, 0 );
			jPanelLocCP.add( jTextFieldCodigoPostal, c );
		
		a.gridy = 11;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelModificarMecanico.add( jPanelLocCP, a );
		a.gridy = 12;
		a.gridx = 0;
		a.gridwidth = 1;
		a.anchor = GridBagConstraints.EAST;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		a.gridy = 13;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );
		
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
		
		a.gridy = 14;
		a.gridx = 1;
		jPanelModificarMecanico.add( jPanelBotones, a );
		a.gridy = 15;
		a.gridx = 0;
		jPanelModificarMecanico.add( new JLabel(" "), a );
			
		this.getContentPane().add( jPanelModificarMecanico, BorderLayout.NORTH );
		this.getRootPane().setDefaultButton(jButtonAceptar);
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
				ModificarMecanico.this.dispose();
			}
		};
		jPanelModificarMecanico.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 75, 25 );
		
		jLabelID = new JLabel( "ID: " );
		jLabelID.setPreferredSize( dimensionLabel );
		jLabelID.setEnabled(false);
		jTextFieldID = new JTextFieldOfNumbers();
		jTextFieldID.setPreferredSize( new Dimension( 50, 25 ) );
		jTextFieldID.setEnabled(false);
		jTextFieldID.setText(String.format("%02d", mecanico.getId_mecanico()));
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setText(mecanico.getNombre());
		jTextFieldNombre.setPreferredSize( new Dimension( 322, 25 ) );
		
		jLabelApellido = new JLabel( "Apellido: " );
		jLabelApellido.setPreferredSize( dimensionLabel );
		jTextFieldApellido = new JTextFieldOfLetters();
		jTextFieldApellido.setText(mecanico.getApellido());
		jTextFieldApellido.setPreferredSize( new Dimension( 322, 25 ) );
		
		jLabelTelefono = new JLabel( "Teléfono: " );
		jLabelTelefono.setPreferredSize( dimensionLabel );
		jTextFieldTelefono = new JTextFieldPhoneNumber();
		jTextFieldTelefono.setText(mecanico.getTelefono());
		jTextFieldTelefono.setPreferredSize( new Dimension( 120, 25 ) );
		
		jLabelCelular = new JLabel( "Celular: " );
		jTextFieldCelular = new JTextFieldPhoneNumber();
		jTextFieldCelular.setText(mecanico.getCelular());
		jTextFieldCelular.setPreferredSize( new Dimension( 139, 25 ) );
		
		jLabelDomicilio = new JLabel( "Domicilio: " );
		jLabelDomicilio.setPreferredSize( dimensionLabel );
		jTextFieldDomicilio = new JTextFieldUpperCased();
		jTextFieldDomicilio.setPreferredSize( new Dimension( 322, 25 ) );
		jTextFieldDomicilio.setText(mecanico.getDomicilio());
		
		jLabelLocalidad = new JLabel( "Localidad: " );
		jLabelLocalidad.setPreferredSize( dimensionLabel );
		jTextFieldLocalidad = new JTextFieldUpperCased();
		jTextFieldLocalidad.setPreferredSize( new Dimension( 150, 25 ) );
		jTextFieldLocalidad.setText(mecanico.getLocalidad());
		
		jLabelCodigoPostal = new JLabel( "Código postal: " );
		jTextFieldCodigoPostal = new JTextFieldUpperCased();
		jTextFieldCodigoPostal.setPreferredSize( new Dimension( 75, 25 ) );
		jTextFieldCodigoPostal.setText(mecanico.getCodigoPostal());
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				aceptarModificarMecanico();
			}
		});
		
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				ModificarMecanico.this.dispose();
			}
		});
	}
	
	private void aceptarModificarMecanico() {
		// verifico que los campos obligatorios no se encuentren vacíos y que ningun campo exceda los 25 caracteres
		int option = 0;
		
		if( validarDatos() && characterVaryingExceeded() ) {
			
			mecanico.setId_mecanico( Integer.parseInt(jTextFieldID.getText().trim() ));
			mecanico.setNombre( jTextFieldNombre.getText().trim() );
			mecanico.setApellido( jTextFieldApellido.getText().trim() );
			mecanico.setTelefono( jTextFieldTelefono.getText().trim() );
			mecanico.setCelular( jTextFieldCelular.getText().trim() );
			mecanico.setDomicilio( jTextFieldDomicilio.getText().trim() );
			mecanico.setLocalidad( jTextFieldLocalidad.getText().trim() );
			mecanico.setCodigoPostal( jTextFieldCodigoPostal.getText().trim() );
			
			IMecanicoManager mecanicoManager = new MecanicoManager();
			try {
				option = mecanicoManager.modificar( mecanico );
			} 
			catch (SQLException e)
			{
				if(e.getSQLState().equals("23505"));
					option = 2;
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			switch( option ) {
				case 1:
					
					JOptionPane.showMessageDialog( this, "Se modificó con éxito el nuevo mecánico", "Éxito", JOptionPane.INFORMATION_MESSAGE );
					cleanAllFields();
					this.dispose();

					break;
				case 2:
					JOptionPane.showMessageDialog( this, "El ID ingresado pertenece a un mecánico existente", "Error", JOptionPane.ERROR_MESSAGE );
					jTextFieldID.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldID.addFocusListener( new JTextFieldFocusListener( jTextFieldID ) );
					
					break;
				case 3:
					JOptionPane.showMessageDialog( this, "Si ingresó un código postal debe ingresar una localidad", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					jTextFieldLocalidad.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldLocalidad.addFocusListener( new JTextFieldFocusListener( jTextFieldLocalidad ) );
					
					break;
				default:
					// este mensaje nunca debería aparecer
					JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
				}
		}
	}


	private boolean validarDatos() {
		int cont = 0;
		
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldID );
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldApellido );
		jTextFields.add( jTextFieldDomicilio );

		for( JTextField jTextField : jTextFields )
			cont += isEmpty( jTextField );
		
		if( jTextFieldCelular.getText().contains( "<" ))
			jTextFieldCelular.setText( "" );

		if( jTextFieldTelefono.getText().contains( "<" ))
			jTextFieldTelefono.setText( "" );

		if( jTextFieldDomicilio.getText().contains( "<" ))
			jTextFieldDomicilio.setText( "" );

		if( jTextFieldCodigoPostal.getText().contains( "<" ))
			jTextFieldCodigoPostal.setText( "" );
			
		if( jTextFieldLocalidad.getText().contains( "<" ))
			jTextFieldLocalidad.setText( "" );
		
		if( cont > 0 )
		{
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

	private boolean characterVaryingExceeded(){
		int cont = 0;
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldApellido );
		jTextFields.add( jTextFieldDomicilio );
		jTextFields.add( jTextFieldLocalidad );
		jTextFields.add( jTextFieldCodigoPostal );
		jTextFields.add( jTextFieldTelefono );
		jTextFields.add( jTextFieldCelular );
		
		for( JTextField jTextField : jTextFields ){
			if(jTextField.getText().trim().length() > 25){
				jTextField.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jTextField.addFocusListener( new JTextFieldFocusListener( jTextField ) );
				cont++;
			}
		}
		
		if(jTextFieldID.getText().trim().length() > 2){
			jTextFieldID.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldID.addFocusListener( new JTextFieldFocusListener( jTextFieldID ) );
			cont++;
		}
		
		if( cont > 0 )
		{
			JOptionPane.showMessageDialog( this, "Uno o más campos se encuentran excediendo el tamaño máximo permitido", "Campo excedido", JOptionPane.ERROR_MESSAGE );
			return false;
		}
		
		return true;
	}
	
	private void cleanAllFields() {
		jTextFieldNombre.setText( "" );
		jTextFieldTelefono.setText( "" );
		jTextFieldCelular.setText( "" );
	
	}
}