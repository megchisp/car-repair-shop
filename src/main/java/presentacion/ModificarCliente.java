package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import negocio.ClienteManager;
import negocio.IClienteManager;

import persistencia.Cliente;


public class ModificarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
    
    DetallarCliente detallarCliente = null;
    
    JPanel jPanelModificarCliente = null;
    JPanel jPanelDatosPrimarios = null;
    JPanel jPanelDatosSecundarios = null;
    JPanel jPanelBotones = null;
    
	JTabbedPane jTabbedPaneTab = null; // pestañas
	
	JLabel jLabelCUIT = null;
	JLabel jLabelFechaDeNacimiento = null;
	JLabel jLabelDe1 = null;
	JLabel jLabelDe2 = null;
	JLabel jLabelFallecido = null;
	JLabel jLabelNombre = null;
	JLabel jLabelApellido = null;
	JLabel jLabelTelefono = null;
	JLabel jLabelCelular = null;
	JLabel jLabelDomicilio = null;
	JLabel jLabelLocalidad = null;
	JLabel jLabelCodigoPostal = null;
	JLabel jLabelEmail = null;
	JLabel jLabelObservaciones = null;
	
	JScrollPane jScrollPaneObservaciones = null;
    JTextArea jTextAreaObservaciones = null;

    JTextFieldUpperCased jTextFieldCUIT = null;
    JTextFieldUpperCased jTextFieldNombre = null;
    JTextFieldUpperCased jTextFieldApellido = null;
    JTextFieldUpperCased jTextFieldTelefono = null;
    JTextFieldUpperCased jTextFieldCelular = null;
    JTextFieldUpperCased jTextFieldDomicilio = null;
    JTextFieldUpperCased jTextFieldLocalidad = null;
    JTextFieldUpperCased jTextFieldCodigoPostal = null;
	JTextField jTextFieldEmail = null;

	JComboBox<String> jComboBoxFechaDeNacimientoDia = null;
	JComboBox<String> jComboBoxFechaDeNacimientoMes = null;
	JComboBox<String> jComboBoxFechaDeNacimientoAnio = null;
	boolean booleanHabilitaFechaDeNacimiento; // indica si la fecha de nacimiento se define en la base de datos

	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	Cliente cliente = null;
	
	JTable jTableBuscarClienteCopia = null;
	DefaultTableModel dtmBuscarClienteCopia = null;
	ResourceLoader resourceLoader = new ResourceLoader();
	
	JCheckBox jCheckBoxFallecido = null;

	public ModificarCliente( JFrame padre, String titulo, String titleBorder, Cliente cliente, DetallarCliente detallarCliente) {
		super( padre, titulo, true );

		this.setPreferredSize( new Dimension( 480, 534 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		this.cliente = cliente;
		this.detallarCliente = detallarCliente;

//		 agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);

		jPanelModificarCliente = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder(BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelModificarCliente.setBorder( titledBorder );

		jPanelModificarCliente.setBorder( titledBorder );
		jPanelModificarCliente.setPreferredSize(new Dimension( 470, 505 ));
		jPanelModificarCliente.setLayout(new FlowLayout(FlowLayout.CENTER,0,15) );
		jPanelModificarCliente.setFocusable( true );

		inicializarDatosSecundarios();
		inicializarVariables();
		inicializarDatosPrimarios();
		inicializarBotones();
		cerrarEsc();
		
		jTabbedPaneTab.setPreferredSize(new Dimension( 450, 400));
		jTabbedPaneTab.add("Datos principales", jPanelDatosPrimarios);
		jTabbedPaneTab.add("Otros", jPanelDatosSecundarios);
		
		jPanelModificarCliente.add(jTabbedPaneTab);
		jPanelModificarCliente.add(jPanelBotones);

		this.getContentPane().add( jPanelModificarCliente, BorderLayout.NORTH );
		
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
				ModificarCliente.this.dispose();
			}
		};
		jPanelModificarCliente.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}


	OperationDelegate aceptarModificarCliente = new OperationDelegate() {
		public void invoke() { 
			aceptarModificarCliente();
		}
	};

	OperationDelegate cancelarModificarCliente = new OperationDelegate() {
		public void invoke() {
			cancelarModificarCliente();
		}
	};
	
	private void inicializarDatosPrimarios(){
		jPanelDatosPrimarios = new JPanel();
		jPanelDatosPrimarios.setLayout( new GridBagLayout() );
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;

		a.gridy = 0;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 1;
		a.gridx = 0;
		jPanelDatosPrimarios.add( jLabelCUIT, a );
		a.gridy = 1;
		a.gridx = 1;
		a.insets = new Insets( 0, -70, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldCUIT, a );
		a.gridy = 1;
		a.gridx = 2;
		a.insets = new Insets( 0, -40, 0, 0 );
		jPanelDatosPrimarios.add( jLabelFallecido, a );
		a.gridy = 1;
		a.gridx = 3;
		a.insets = new Insets( 0, -20, 0, 0 );
		jPanelDatosPrimarios.add( jCheckBoxFallecido, a );
		a.gridy = 2;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );

			JPanel jPanelFechaDeNacimiento = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
	
			jPanelFechaDeNacimiento.add( jLabelFechaDeNacimiento );
			jPanelFechaDeNacimiento.add( jComboBoxFechaDeNacimientoDia );
			jPanelFechaDeNacimiento.add( jLabelDe1 );
			jPanelFechaDeNacimiento.add( jComboBoxFechaDeNacimientoMes );
			jPanelFechaDeNacimiento.add( jLabelDe2 );
			jPanelFechaDeNacimiento.add( jComboBoxFechaDeNacimientoAnio );

		a.gridy = 3;
		a.gridx = 0;
		a.gridwidth = 3;
		a.insets = new Insets( 0, -6, 0, 0 );
		jPanelDatosPrimarios.add( jPanelFechaDeNacimiento, a );
		a.gridy = 4;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelApellido, a );
		a.gridy = 5;
		a.gridx = 1;
		a.insets = new Insets( 0, -82, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldApellido, a );
		a.gridy = 6;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 7;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelNombre, a );
		a.gridy = 7;
		a.gridx = 1;
		a.insets = new Insets( 0, -82, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldNombre, a );
		a.gridy = 8;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );

			JPanel jPanelTelCel = new JPanel();
			jPanelTelCel.setLayout( new GridBagLayout() );
	
			GridBagConstraints b = new GridBagConstraints();
	
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 0, 0, 0 );
			jPanelTelCel.add( jLabelTelefono, b );
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, -82, 0, 0 );
			jPanelTelCel.add( jTextFieldTelefono, b );
			b.gridy = 0;
			b.gridx = 2;
			b.insets = new Insets( 0, 18, 0, 0 );
			jPanelTelCel.add( jLabelCelular, b );
			b.gridy = 0;
			b.gridx = 3;
			b.insets = new Insets( 0, 8, 0, 0 );
			jPanelTelCel.add( jTextFieldCelular, b );

		a.gridy = 9;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jPanelTelCel, a );
		a.gridy = 10;
		a.gridx = 0;
		a.gridwidth = 1;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 11;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelDomicilio, a );
		a.gridy = 11;
		a.gridx = 1;
		a.insets = new Insets( 0, -82, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldDomicilio, a );
		a.gridy = 12;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );

			JPanel jPanelLocCP = new JPanel();
			jPanelLocCP.setLayout(new GridBagLayout());
	
			GridBagConstraints c = new GridBagConstraints();
	
			c.gridy = 0;
			c.gridx = 0;
			c.insets = new Insets( 0, 0, 0, 0 );
			jPanelLocCP.add( jLabelLocalidad, c );
			c.gridy = 0;
			c.gridx = 1;
			c.insets = new Insets(0, -82, 0, 0);
			jPanelLocCP.add( jTextFieldLocalidad, c );
			c.gridy = 0;
			c.gridx = 2;
			c.insets = new Insets(0, 18, 0, 0);
			jPanelLocCP.add( jLabelCodigoPostal, c );
			c.gridy = 0;
			c.gridx = 3;
			c.insets = new Insets( 0, 6, 0, 0 );
			jPanelLocCP.add( jTextFieldCodigoPostal, c );

		a.gridy = 13;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jPanelLocCP, a );
		a.gridy = 14;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 15;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelEmail, a );
		a.gridy = 15;
		a.gridx = 1;
		a.insets = new Insets( 0, -82, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldEmail, a );
		a.gridy = 16;
		a.gridx = 0;
		a.gridwidth = 1;
		a.anchor = GridBagConstraints.EAST;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
		a.gridy = 17;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel( " " ), a );
	}

	private void inicializarDatosSecundarios(){
		jPanelDatosSecundarios = new JPanel();
		jPanelDatosSecundarios.setLayout(new FlowLayout(FlowLayout.CENTER,0,15) );
		jPanelDatosSecundarios.setPreferredSize(new Dimension( 310, 340 ));
		
		jLabelObservaciones = new JLabel( "Observaciones adicionales: " );
		jLabelObservaciones.setBorder(BorderFactory.createEmptyBorder( 10 /*top*/, 0, 0, 0 )); // para dar margen vertical
		jLabelObservaciones.setPreferredSize(new Dimension(410,35)); // aca le doy 410 x 35 para dar margen v
		
		jTextAreaObservaciones = new JTextArea();
		jTextAreaObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		((AbstractDocument) jTextAreaObservaciones.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		jTextAreaObservaciones.setLineWrap(true);
		jTextAreaObservaciones.setWrapStyleWord(true);
		jScrollPaneObservaciones = new JScrollPane(jTextAreaObservaciones);
		jScrollPaneObservaciones.setPreferredSize(new Dimension( 410, 320 ));
		
		jPanelDatosSecundarios.setLayout( new FlowLayout(FlowLayout.CENTER,0,0));
		jPanelDatosSecundarios.setPreferredSize(new Dimension(325, 75));
		
		jPanelDatosSecundarios.add(jLabelObservaciones);
		jPanelDatosSecundarios.add(jScrollPaneObservaciones);
	}
	
	private void inicializarVariables() {
		jTabbedPaneTab = new JTabbedPane(); // inicializo pestañas
		
		Dimension dimensionLabel = new Dimension( 140, 25 );
		
		jLabelCUIT = new JLabel( "CUIT/CUIL: " );
		jLabelCUIT.setPreferredSize( dimensionLabel );
		jTextFieldCUIT = new JTextFieldOfNumbers();
		jTextFieldCUIT.setText( cliente.getCUIT() );
		jTextFieldCUIT.setPreferredSize( new Dimension( 120, 25 ) );
		jTextFieldCUIT.addFocusListener( new JTextFieldFocusListener( jTextFieldCUIT ) );
		jTextFieldCUIT.addKeyListener( new JTextFieldKeyListener( jTextFieldCUIT ) );
		jTextFieldCUIT.addKeyListener(new java.awt.event.KeyAdapter() {  
			public void keyReleased(java.awt.event.KeyEvent evt) {  
				int key = evt.getKeyCode();
				if ((jTextFieldCUIT.getDocument().getLength() == 2) && (key != java.awt.event.KeyEvent.VK_BACK_SPACE))
					jTextFieldCUIT.setText(jTextFieldCUIT.getText() + "-");

				if ((jTextFieldCUIT.getDocument().getLength() == 11) && (key != java.awt.event.KeyEvent.VK_BACK_SPACE))
					jTextFieldCUIT.setText(jTextFieldCUIT.getText() + "-");
			}
		}); 
		
		inicializarFechaNacimiento();

		jLabelFechaDeNacimiento = new JLabel( "Fecha de nacimiento: " );
		jLabelFechaDeNacimiento.setPreferredSize( new Dimension( 130, 25 ) );

		jLabelDe1 = new JLabel( " de " );
		jLabelDe2 = new JLabel( " de " );

		jLabelFallecido = new JLabel( "(\u271D)" );
		jCheckBoxFallecido = new JCheckBox();
		jCheckBoxFallecido.setSelected(cliente.isFallecido());
		jCheckBoxFallecido.setToolTipText("Indicar cliente fallecido");
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldOfLetters();

		jTextFieldNombre.setText( cliente.getNombre() );
		jTextFieldNombre.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
		jTextFieldNombre.addKeyListener( new JTextFieldKeyListener( jTextFieldNombre ) );

		jLabelApellido = new JLabel( "Apellido: " );
		jLabelApellido.setPreferredSize( dimensionLabel );
		jTextFieldApellido = new JTextFieldOfLetters();
		jTextFieldApellido.setText( cliente.getApellido() );
		jTextFieldApellido.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldApellido.addFocusListener( new JTextFieldFocusListener( jTextFieldApellido ) );
		jTextFieldApellido.addKeyListener( new JTextFieldKeyListener( jTextFieldApellido ) );

		jLabelTelefono = new JLabel( "Teléfono: " );
		jLabelTelefono.setPreferredSize( dimensionLabel );
		jTextFieldTelefono = new JTextFieldPhoneNumber();
		jTextFieldTelefono.setText( cliente.getTelefono() );
		jTextFieldTelefono.setPreferredSize( new Dimension( 141, 25 ) );
		jTextFieldTelefono.addFocusListener( new JTextFieldFocusListener( jTextFieldTelefono ) );
		jTextFieldTelefono.addKeyListener( new JTextFieldKeyListener( jTextFieldTelefono ) );
		
		jLabelCelular = new JLabel( "Celular: " );
		jTextFieldCelular = new JTextFieldPhoneNumber();
		jTextFieldCelular.setText( cliente.getCelular() );
		jTextFieldCelular.setPreferredSize( new Dimension( 146, 25 ) );
		jTextFieldCelular.addFocusListener( new JTextFieldFocusListener( jTextFieldCelular ) );
		jTextFieldCelular.addKeyListener( new JTextFieldKeyListener( jTextFieldCelular ) );
		
		jLabelDomicilio = new JLabel( "Domicilio: " );
		jLabelDomicilio.setPreferredSize( dimensionLabel );
		jTextFieldDomicilio = new JTextFieldUpperCased();
		jTextFieldDomicilio.setText( cliente.getDomicilio() );
		jTextFieldDomicilio.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldDomicilio.addFocusListener( new JTextFieldFocusListener( jTextFieldDomicilio ) );
		jTextFieldDomicilio.addKeyListener( new JTextFieldKeyListener( jTextFieldDomicilio ) );

		jLabelLocalidad = new JLabel( "Localidad: " );
		jLabelLocalidad.setPreferredSize( dimensionLabel );
		jTextFieldLocalidad = new JTextFieldUpperCased();
		jTextFieldLocalidad.setText( cliente.getLocalidad() );
		jTextFieldLocalidad.setPreferredSize( new Dimension( 165, 25 ) );
		jTextFieldLocalidad.addFocusListener( new JTextFieldFocusListener( jTextFieldLocalidad ) );
		jTextFieldLocalidad.addKeyListener( new JTextFieldKeyListener( jTextFieldLocalidad ) );

		jLabelCodigoPostal = new JLabel( "Código postal: " );
		jTextFieldCodigoPostal = new JTextFieldUpperCased();
		jTextFieldCodigoPostal.setText( cliente.getCodigoPostal() );
		jTextFieldCodigoPostal.setPreferredSize(new Dimension( 92, 25 ) );
		jTextFieldCodigoPostal.addFocusListener( new JTextFieldFocusListener( jTextFieldCodigoPostal ) );
		jTextFieldCodigoPostal.addKeyListener( new JTextFieldKeyListener( jTextFieldCodigoPostal ) );

		jLabelEmail = new JLabel( "Email: " );
		jLabelEmail.setPreferredSize( dimensionLabel );
		jTextFieldEmail = new JTextField();
		jTextFieldEmail.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextFieldEmail.setText( cliente.getEmail() );
		jTextFieldEmail.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldEmail.addFocusListener( new JTextFieldFocusListener( jTextFieldEmail ) );
		jTextFieldEmail.addKeyListener( new JTextFieldKeyListener( jTextFieldEmail ) );
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( aceptarModificarCliente ) );
//		jButtonAceptar.addKeyListener( new JButtonKeyListener( aceptarModificarCliente ) );

		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new JButtonActionListener( cancelarModificarCliente ) );
		jButtonCancelar.addKeyListener( new JButtonKeyListener( cancelarModificarCliente ) );
		
		jTextAreaObservaciones.setText( cliente.getObservaciones() );
		
	}
		
	private void inicializarBotones(){
		jPanelBotones = new JPanel();
		jPanelBotones.setPreferredSize( new Dimension( 465, 40 ) );
		jPanelBotones.setLayout( new GridBagLayout() );
		GridBagConstraints e = new GridBagConstraints();
		e.anchor = GridBagConstraints.EAST;
		
		e.gridy = 0;
		e.gridx = 0;
		e.insets = new Insets( 0, 220, 0, 0 );
		jPanelBotones.add( jButtonAceptar, e );
		e.gridy = 0;
		e.gridx = 1;
		e.insets = new Insets( 0, 20, 0, 0 );
		jPanelBotones.add( jButtonCancelar, e );
	}


	@SuppressWarnings("deprecation")
	private void inicializarFechaNacimiento() {
		jComboBoxFechaDeNacimientoDia = new JComboBox<String>();
		jComboBoxFechaDeNacimientoDia.setPreferredSize( new Dimension( 52, 25 ) );
		jComboBoxFechaDeNacimientoDia.setFont(new Font("Dialog", Font.BOLD, 11));
		
		jComboBoxFechaDeNacimientoMes = new JComboBox<String>();
		jComboBoxFechaDeNacimientoMes.setPreferredSize( new Dimension( 100, 25 ) );
		jComboBoxFechaDeNacimientoMes.setFont(new Font("Dialog", Font.BOLD, 11));

		
		jComboBoxFechaDeNacimientoAnio = new JComboBox<String>();
		jComboBoxFechaDeNacimientoAnio.setPreferredSize( new Dimension( 64, 25 ) );
		jComboBoxFechaDeNacimientoAnio.setFont(new Font("Dialog", Font.BOLD, 11));

		jComboBoxFechaDeNacimientoDia.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoAnio ) );
		jComboBoxFechaDeNacimientoMes.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoAnio ) );
		jComboBoxFechaDeNacimientoAnio.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaDeNacimientoAnio, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia ) );

		// Recupero el año actual
		Calendar cal1 = Calendar.getInstance();
		int anioActual = cal1.get(Calendar.YEAR);
		
		jComboBoxFechaDeNacimientoDia.addItem( "Día" );
		
		for( int i=1; i<=31; i++ )
			jComboBoxFechaDeNacimientoDia.addItem( String.valueOf( i ) );
		
		jComboBoxFechaDeNacimientoMes.addItem( "Mes" );
		jComboBoxFechaDeNacimientoMes.addItem( "Enero" );
		jComboBoxFechaDeNacimientoMes.addItem( "Febrero" );
		jComboBoxFechaDeNacimientoMes.addItem( "Marzo" );
		jComboBoxFechaDeNacimientoMes.addItem( "Abril" );
		jComboBoxFechaDeNacimientoMes.addItem( "Mayo" );
		jComboBoxFechaDeNacimientoMes.addItem( "Junio" );
		jComboBoxFechaDeNacimientoMes.addItem( "Julio" );
		jComboBoxFechaDeNacimientoMes.addItem( "Agosto" );
		jComboBoxFechaDeNacimientoMes.addItem( "Septiembre" );
		jComboBoxFechaDeNacimientoMes.addItem( "Octubre" );
		jComboBoxFechaDeNacimientoMes.addItem( "Noviembre" );
		jComboBoxFechaDeNacimientoMes.addItem( "Diciembre" );
		
		jComboBoxFechaDeNacimientoAnio.addItem( "Año" );
		for(int i=(anioActual-17); i>=(anioActual-90); i-- ) // el cliente tiene entre 17 y 90 años de edad
			jComboBoxFechaDeNacimientoAnio.addItem( String.valueOf( i ) );
		
		if(cliente.isBooleanHabilitaFechaDeNacimiento()){ // unicamente se ejecuta si tiene una fecha asignada
			Date date = new java.sql.Date(	cliente.getFechaDeNacimiento().getTimeInMillis());		
			jComboBoxFechaDeNacimientoDia.setSelectedIndex(date.getDate());
			jComboBoxFechaDeNacimientoMes.setSelectedIndex(date.getMonth() + 1);
			
			if(date.getYear() != 0)
				try{
				jComboBoxFechaDeNacimientoAnio.setSelectedIndex(anioActual - date.getYear() - 1916);
				}
			catch (Exception e) {
				System.out.println("excepcion de año! " + date.getYear());
				jComboBoxFechaDeNacimientoAnio.setSelectedIndex(0);
			}
		}
	}
	private void aceptarModificarCliente() {
		
		// verifico que los campos obligatorios no se encuentren vacíos y que ningun campo exceda los 25 caracteres
		if( validarDatos() && characterVaryingExceeded()) {
			int option = 0;

			Calendar fechaDeNacimientoCalendar = GregorianCalendar.getInstance();
			fechaDeNacimientoCalendar.setLenient( false );
			
			if(booleanHabilitaFechaDeNacimiento){ // si la fecha de nacimiento está habilitada
				if(jComboBoxFechaDeNacimientoAnio.getSelectedIndex() == 0)
					fechaDeNacimientoCalendar.set( 1900 , jComboBoxFechaDeNacimientoMes.getSelectedIndex() -1, Integer.parseInt( jComboBoxFechaDeNacimientoDia.getSelectedItem().toString() ) );
				else
					fechaDeNacimientoCalendar.set( Integer.parseInt( jComboBoxFechaDeNacimientoAnio.getSelectedItem().toString() ) , jComboBoxFechaDeNacimientoMes.getSelectedIndex() -1, Integer.parseInt( jComboBoxFechaDeNacimientoDia.getSelectedItem().toString() ) );
			}
			else
				fechaDeNacimientoCalendar.set( 1900 , 0, 1); // esta fecha no tiene validez...
			
			fechaDeNacimientoCalendar.set( Calendar.MINUTE, 0 );
			fechaDeNacimientoCalendar.set( Calendar.SECOND, 0 );
			fechaDeNacimientoCalendar.set( Calendar.MILLISECOND, 0 );

			cliente.setFechaDeNacimiento( fechaDeNacimientoCalendar );
			cliente.setCUIT( jTextFieldCUIT.getText().trim()  );
			cliente.setBooleanHabilitaFechaDeNacimiento(booleanHabilitaFechaDeNacimiento);
			cliente.setFallecido(jCheckBoxFallecido.isSelected());
			cliente.setNombre( jTextFieldNombre.getText().trim() );
			cliente.setApellido( jTextFieldApellido.getText().trim() );
			cliente.setTelefono( jTextFieldTelefono.getText().trim() );
			cliente.setCelular( jTextFieldCelular.getText().trim() );
			cliente.setDomicilio( jTextFieldDomicilio.getText().trim() );
			cliente.setLocalidad( jTextFieldLocalidad.getText().trim() );
			cliente.setCodigoPostal( jTextFieldCodigoPostal.getText().trim() );
			cliente.setEmail( jTextFieldEmail.getText().trim() );
			cliente.setObservaciones( jTextAreaObservaciones.getText() );
			
			IClienteManager clienteManager = new ClienteManager();
			try {
				option = clienteManager.modificar( cliente );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}

			switch( option ) {
				case 1:

					if(detallarCliente == null)
						JOptionPane.showMessageDialog( this, "El cliente se modificó con éxito", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE );
					else
						actualizarDetallarCliente();
				
					cleanAllFields();
				
					this.dispose();
					
					break;
				case 2:
					JOptionPane.showMessageDialog( this, "El CUIT/CUIL ingresado pertenece a un cliente existente", "Error", JOptionPane.ERROR_MESSAGE );
					
					jTextFieldCUIT.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldCUIT.addFocusListener( new JTextFieldFocusListener( jTextFieldCUIT ) );
					
					break;
				case 3:
					JOptionPane.showMessageDialog( null, "Ingrese un número válido de CUIT/CUIL", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
				
					jTextFieldCUIT.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldCUIT.addFocusListener( new JTextFieldFocusListener( jTextFieldCUIT ) );
					
					break;
				case 4:
					JOptionPane.showMessageDialog( null, "Ingrese una fecha válida", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					
					jComboBoxFechaDeNacimientoDia.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jComboBoxFechaDeNacimientoDia.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoAnio ) );
					jComboBoxFechaDeNacimientoMes.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jComboBoxFechaDeNacimientoMes.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoAnio ) );
					if(jComboBoxFechaDeNacimientoAnio.getSelectedIndex() != 0){
						jComboBoxFechaDeNacimientoAnio.setBorder( BorderFactory.createLineBorder( Color.RED ) );
						jComboBoxFechaDeNacimientoAnio.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoAnio ) );
					}
					break;
				case 5:
					JOptionPane.showMessageDialog( null, "Ingrese una dirección válida de email", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					
					jTextFieldEmail.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldEmail.addFocusListener( new JTextFieldFocusListener( jTextFieldEmail ) );
					break;
				case 6:
					JOptionPane.showMessageDialog( null, "Si ingresó un código postal debe ingresar una localidad", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					jTextFieldLocalidad.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldLocalidad.addFocusListener( new JTextFieldFocusListener( jTextFieldLocalidad ) );
					break;
				case 7:
					JOptionPane.showMessageDialog( null, "El cliente no se encuentra en la base de datos", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );

					break;
				default:
					// este mensaje nunca debería aparecer
					JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void actualizarDetallarCliente(){
		/** este método actualiza los datos de la ventana detallarCliente **/
		String stringFallecido = cliente.isFallecido() ? " (\u271D)" : "";
		
		// actualizando los campos obligatorios
		detallarCliente.jLabelDomicilio.setText("Domicilio: " + jTextFieldDomicilio.getText());
		detallarCliente.jLabelNombreApellido.setText(jTextFieldApellido.getText() + ", " + jTextFieldNombre.getText() + stringFallecido);
		Date date = new java.sql.Date(	cliente.getFechaDeNacimiento().getTimeInMillis());
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd 'de' MMMMMMMMMM 'de' yyyy", new Locale("es", "ARG"));
		
		String stringNacimientoCliente = sdf.format(date);
		if(date.getYear() == 0) // ojo con esta linea
			stringNacimientoCliente = stringNacimientoCliente.substring(0, stringNacimientoCliente.length()-8);
		detallarCliente.jLabelFechaNacimiento.setText("Nacimiento: " + stringNacimientoCliente);
		
		// actualizando los campos NO obligatorios
		completarJLabel(detallarCliente.jLabelFechaNacimiento, "Nacimiento", cliente.isBooleanHabilitaFechaDeNacimiento() ? stringNacimientoCliente : "");
		completarJLabel(detallarCliente.jLabelCelular, "Celular", jTextFieldCelular.getText());
		completarJLabel(detallarCliente.jLabelCUIT, "CUIT/CUIL", jTextFieldCUIT.getText());
		completarJLabel(detallarCliente.jLabelEmail, "Email", jTextFieldEmail.getText());
		completarJLabel(detallarCliente.jLabelTelefono, "Teléfono", jTextFieldTelefono.getText());
		completarJLabel(detallarCliente.jLabelLocalidad, "Localidad", jTextFieldLocalidad.getText());
		if(!jTextFieldCodigoPostal.getText().isEmpty())
			detallarCliente.jLabelLocalidad.setText(( detallarCliente.jLabelLocalidad.getText() + " ("+ jTextFieldCodigoPostal.getText() + ")"));
		
		detallarCliente.jTextPaneObservaciones.setText(jTextAreaObservaciones.getText());
		detallarCliente.enableDisableObservaciones();
	}
	
	private void completarJLabel(JLabel jLabel, String nombre, String dato){
		// esta funcion completa los JLabels y si el mismo no contiene información los desabilita y muestra <sin especificar>
		if(dato.isEmpty())
		{
			dato = "< sin especificar >";
			jLabel.setEnabled(false);
		}
		else
			jLabel.setEnabled(true);
		jLabel.setText(nombre + ": " + dato);
	}
	
	private void cancelarModificarCliente() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private void cleanAllFields() {
		jTextFieldCUIT.setText( "" );
		jComboBoxFechaDeNacimientoDia.setSelectedIndex( 0 );
		jComboBoxFechaDeNacimientoMes.setSelectedIndex( 0 );
		jComboBoxFechaDeNacimientoAnio.setSelectedIndex( 0 );
		jTextFieldNombre.setText( "" );
		jTextFieldApellido.setText( "" );
		jTextFieldTelefono.setText( "" );
		jTextFieldCelular.setText( "" );
		jTextFieldDomicilio.setText( "" );
		jTextFieldLocalidad.setText( "" );
		jTextFieldCodigoPostal.setText( "" );
		jTextFieldEmail.setText( "" );
	}
	
	private boolean validarDatos() {
		int cont = 0;
		booleanHabilitaFechaDeNacimiento = false;

		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldApellido );
		jTextFields.add( jTextFieldDomicilio );

		for( JTextField jTextField : jTextFields )
			cont += isEmpty( jTextField );
		
		// si los 3 combobox están posicionados en 0 significa que el usuario no define fecha de nacimiento/cumpleaños por lo tanto no se valida
		if(!(jComboBoxFechaDeNacimientoDia.getSelectedIndex() == 0 && jComboBoxFechaDeNacimientoMes.getSelectedIndex() == 0 && jComboBoxFechaDeNacimientoAnio.getSelectedIndex() == 0)){			
			cont += isEmpty( jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoAnio );
			cont += isEmpty( jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoAnio );
	//		cont += isEmpty( jComboBoxFechaDeNacimientoAnio, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia );
			booleanHabilitaFechaDeNacimiento = true;
		}
		
		if( jTextFieldCUIT.getText().contains( "<" ))
			jTextFieldCUIT.setText( "" );
		
		if( jTextFieldCelular.getText().contains( "<" ))
			jTextFieldCelular.setText( "" );

		if( jTextFieldTelefono.getText().contains( "<" ))
			jTextFieldTelefono.setText( "" );

		if( jTextFieldEmail.getText().contains( "<" ))
			jTextFieldEmail.setText( "" );

		if( jTextFieldDomicilio.getText().contains( "<" ))
			jTextFieldDomicilio.setText( "" );

		if( jTextFieldCodigoPostal.getText().contains( "<" ))
			jTextFieldCodigoPostal.setText( "" );
			
		if( jTextFieldLocalidad.getText().contains( "<" ))
			jTextFieldLocalidad.setText( "" );
		
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
	
	private boolean characterVaryingExceeded(){
		int cont = 0;
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldApellido );
		jTextFields.add( jTextFieldDomicilio );
		jTextFields.add( jTextFieldCUIT );
		jTextFields.add( jTextFieldLocalidad );
		jTextFields.add( jTextFieldCodigoPostal );
//		jTextFields.add( jTextFieldEmail ); // el mail no tiene restricción
		jTextFields.add( jTextFieldTelefono );
		jTextFields.add( jTextFieldCelular );
		
		for( JTextField jTextField : jTextFields ){
			if(jTextField.getText().trim().length() > 35){
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
	
	
	private int isEmpty( JComboBox<String> jComboBox1, JComboBox<String> jComboBox2, JComboBox<String> jComboBox3 ) {
		if( jComboBox1.getSelectedItem().toString().equals( "Día" ) || jComboBox1.getSelectedItem().toString().equals( "Mes" ) || jComboBox1.getSelectedItem().toString().equals( "Año" )) {
			jComboBox1.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jComboBox1.addFocusListener( new JComboBoxFocusListener( jComboBox1, jComboBox2, jComboBox3 ) );
			
			return 1;
		}
		
		return 0;
	}
	
}