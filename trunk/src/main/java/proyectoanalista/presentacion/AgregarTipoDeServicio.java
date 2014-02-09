package proyectoanalista.presentacion;

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
import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import proyectoanalista.negocio.TipoDeServicioManager;
import proyectoanalista.persistencia.TipoDeServicio;

public class AgregarTipoDeServicio extends JDialog {
	
	private static final long serialVersionUID = 1L;

	JPanel jPanelAgregarTipoServicio = null;
	
	JLabel jLabelNombre = null;
	JLabel jLabelTiempoMinimoReparacion = null;
	JLabel jLabelTiempoMaximoReparacion = null;
	
	JTextField jTextFieldNombre = null;
	JTextField jTextFieldTiempoMinimoReparacion = null;
	JTextField jTextFieldTiempoMaximoReparacion = null;
		
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();

	public AgregarTipoDeServicio( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 357, 224 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		jPanelAgregarTipoServicio = new JPanel();
		jPanelAgregarTipoServicio.setFocusable( true );
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelAgregarTipoServicio.setBorder( titledBorder );
		
		jPanelAgregarTipoServicio.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;
		
		inicializarVariables();
		cerrarEsc();
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelAgregarTipoServicio.add( new JLabel(" "), a );
		a.gridy = 1;
		a.gridx = 0;
		jPanelAgregarTipoServicio.add( jLabelNombre, a );
		a.gridy = 1;
		a.gridx = 1;
		a.insets = new Insets( 0, -178, 0, 0 );
		jPanelAgregarTipoServicio.add( jTextFieldNombre, a );
		a.gridy = 2;
		a.gridx = 0;
		jPanelAgregarTipoServicio.add( new JLabel(" "), a );	
		a.gridy = 3;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelAgregarTipoServicio.add( jLabelTiempoMinimoReparacion, a );
		a.gridy = 3;
		a.gridx = 1;
		a.insets = new Insets( 0, 3, 0, 0 );
		jPanelAgregarTipoServicio.add( jTextFieldTiempoMinimoReparacion, a );
		a.gridy = 4;
		a.gridx = 0;
		jPanelAgregarTipoServicio.add( new JLabel(" "), a );
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelAgregarTipoServicio.add( jLabelTiempoMaximoReparacion, a );
		a.gridy = 5;
		a.gridx = 1;
		a.insets = new Insets( 0, 3, 0, 0 );
		jPanelAgregarTipoServicio.add( jTextFieldTiempoMaximoReparacion, a );
		a.gridy = 6;
		a.gridx = 0;
		jPanelAgregarTipoServicio.add( new JLabel(" "), a );
		
			JPanel jPanelBotones = new JPanel();
			jPanelBotones.setLayout( new GridBagLayout() );
		
			GridBagConstraints b = new GridBagConstraints();
			
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 0, 0, 0 );
			jPanelBotones.add( jButtonAceptar, b );
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotones.add( jButtonCancelar, b );
	
		a.gridy = 7;
		a.gridx = 0;
		a.gridwidth = 2;
		a.anchor = GridBagConstraints.EAST;
		jPanelAgregarTipoServicio.add( jPanelBotones, a );
		a.gridy = 8;
		a.gridx = 0;
		jPanelAgregarTipoServicio.add( new JLabel(" "), a );	
			
		this.getContentPane().add( jPanelAgregarTipoServicio, BorderLayout.NORTH );
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
				AgregarTipoDeServicio.this.dispose();
			}
		};
		jPanelAgregarTipoServicio.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	OperationDelegate aceptarAgregarTipoDeServicio = new OperationDelegate() {
		public void invoke() {
			aceptarAgregarTipoDeServicio();
		}
	};

	OperationDelegate cancelarAgregarTipoDeServicio = new OperationDelegate() {
		public void invoke() {
			cancelarAgregarTipoDeServicio();
		}
	};
	
	private void inicializarVariables() {	
		Dimension dimensionLabel = new Dimension( 231, 25 );
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setPreferredSize( new Dimension( 253, 25 ) );
		jTextFieldNombre.setText( " < Electricidad >" );
		jTextFieldNombre.setForeground( Color.GRAY );
		jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
		jTextFieldNombre.addKeyListener( new JTextFieldKeyListener( jTextFieldNombre ) );
		
		jLabelTiempoMinimoReparacion = new JLabel( "Tiempo mínimo de reparación estimado: " );
		jLabelTiempoMinimoReparacion.setPreferredSize( dimensionLabel );
		jTextFieldTiempoMinimoReparacion = new JTextFieldOfNumbers();
		jTextFieldTiempoMinimoReparacion.setPreferredSize( new Dimension( 72, 25 ) );
		jTextFieldTiempoMinimoReparacion.setText( " < 30 min >" );
		jTextFieldTiempoMinimoReparacion.setForeground( Color.GRAY );
		jTextFieldTiempoMinimoReparacion.addFocusListener( new JTextFieldFocusListener( jTextFieldTiempoMinimoReparacion ) );
		jTextFieldTiempoMinimoReparacion.addKeyListener( new JTextFieldKeyListener( jTextFieldTiempoMinimoReparacion ) );
		
		jLabelTiempoMaximoReparacion = new JLabel( "Tiempo máximo de reparación estimado: " );
		jTextFieldTiempoMaximoReparacion = new JTextField();
		jTextFieldTiempoMaximoReparacion.setPreferredSize( new Dimension( 69, 25 ) );
		jTextFieldTiempoMaximoReparacion = new JTextFieldOfNumbers();
		jTextFieldTiempoMaximoReparacion.setPreferredSize( new Dimension( 72, 25 ) );
		jTextFieldTiempoMaximoReparacion.setText( " < 50 min >" );
		jTextFieldTiempoMaximoReparacion.setForeground( Color.GRAY );
		jTextFieldTiempoMaximoReparacion.addFocusListener( new JTextFieldFocusListener( jTextFieldTiempoMaximoReparacion ) );
		jTextFieldTiempoMaximoReparacion.addKeyListener( new JTextFieldKeyListener( jTextFieldTiempoMaximoReparacion ) );
		
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));
		jButtonAceptar = new JButton( " Aceptar", imageIconOk );
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( aceptarAgregarTipoDeServicio ) );
		jButtonAceptar.addKeyListener( new JButtonKeyListener( aceptarAgregarTipoDeServicio ) );
		
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel );
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new JButtonActionListener( cancelarAgregarTipoDeServicio ) );
		jButtonCancelar.addKeyListener( new JButtonKeyListener( cancelarAgregarTipoDeServicio ) );
	}

	private void aceptarAgregarTipoDeServicio() {
		if( validarDatos() && characterVaryingExceeded()) {
			int option = 0;
			int id_tipo_de_servicio;
			TipoDeServicio tipoDeServicio = new TipoDeServicio();
	
			tipoDeServicio.setNombre( jTextFieldNombre.getText().trim()  );
			tipoDeServicio.setTiempoMinimoReparacion( Integer.parseInt(jTextFieldTiempoMinimoReparacion.getText().trim() ));
			tipoDeServicio.setTiempoMaximoReparacion( Integer.parseInt(jTextFieldTiempoMaximoReparacion.getText().trim() ));

			TipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();
			try{
				id_tipo_de_servicio = tipoDeServicioManager.nextID();
				tipoDeServicio.setId_tipo_servicio(id_tipo_de_servicio);
				option = tipoDeServicioManager.agregar(tipoDeServicio);
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				e.printStackTrace();
				return;
			}
			
			switch( option) {
				case 1:
					JOptionPane.showMessageDialog( this, "Se agregó con éxito el nuevo tipo de servicio", "Éxito", JOptionPane.INFORMATION_MESSAGE );
					cleanAllFields();
					this.dispose();
					
					break;
				case 2:
					JOptionPane.showMessageDialog( this, "Ya existe un tipo de servicio con el nombre ingresado", "Error", JOptionPane.ERROR_MESSAGE );
					
					jTextFieldNombre.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldTiempoMinimoReparacion ) );
					break;
				case 3:
					JOptionPane.showMessageDialog( this, "El tiempo mínimo de reparación debe ser mayor o igual que el tiempo máximo de reparación", "Error", JOptionPane.ERROR_MESSAGE );
					
					jTextFieldTiempoMinimoReparacion.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldTiempoMinimoReparacion.addFocusListener( new JTextFieldFocusListener( jTextFieldTiempoMinimoReparacion ) );
					
					break;
				default:
					// este mensaje nunca debería aparecer
					JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	private boolean characterVaryingExceeded(){
		int cont = 0;

		if(jTextFieldNombre.getText().trim().length() > 25){
			jTextFieldNombre.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
			cont++;
		}
		
		if(jTextFieldTiempoMaximoReparacion.getText().trim().length() > 4){
			jTextFieldTiempoMaximoReparacion.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldTiempoMaximoReparacion.addFocusListener( new JTextFieldFocusListener( jTextFieldTiempoMaximoReparacion ) );
			cont++;
		}
		
		if(jTextFieldTiempoMinimoReparacion.getText().trim().length() > 4){
			jTextFieldTiempoMinimoReparacion.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldTiempoMinimoReparacion.addFocusListener( new JTextFieldFocusListener( jTextFieldTiempoMinimoReparacion ) );
			cont++;
		}
		
		if( cont > 0 )
		{
			JOptionPane.showMessageDialog( this, "Uno o más campos se encuentran excediendo el tamaño máximo permitido", "Campo excedido", JOptionPane.ERROR_MESSAGE );
			return false;
		}
		return true;
	}
	
	private void cancelarAgregarTipoDeServicio() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private void cleanAllFields() {
		jTextFieldNombre.setText( "" );
		jTextFieldTiempoMinimoReparacion.setText( "" );
		jTextFieldTiempoMaximoReparacion.setText( "" );
	}
	
	private boolean validarDatos() {
		int cont = 0;
		
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldTiempoMinimoReparacion );
		jTextFields.add( jTextFieldTiempoMaximoReparacion );

		for( JTextField jTextField : jTextFields ) // recorre los campos obligatorios
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
}