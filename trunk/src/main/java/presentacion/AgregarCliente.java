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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import negocio.ClienteManager;
import negocio.IClienteManager;
import persistencia.Cliente;


public class AgregarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	
    JPanel jPanelAgregarCliente = null;
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

	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	JCheckBox jCheckBoxFallecido = null;

	public AgregarCliente( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );

		this.setPreferredSize( new Dimension( 480, 534 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);

		jPanelAgregarCliente = new JPanel();

		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder(BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		
		jPanelAgregarCliente.setBorder( titledBorder );
		jPanelAgregarCliente.setPreferredSize(new Dimension( 470, 505 ));
		jPanelAgregarCliente.setLayout(new FlowLayout(FlowLayout.CENTER,0,15) );
		jPanelAgregarCliente.setFocusable( true );

		inicializarVariables();
		inicializarDatosPrimarios();
		inicializarDatosSecundarios();
		inicializarBotones();
		cerrarEsc();
		
		jTabbedPaneTab.setPreferredSize(new Dimension( 450, 400));
		jTabbedPaneTab.add("Datos principales", jPanelDatosPrimarios);
		jTabbedPaneTab.add("Otros", jPanelDatosSecundarios);
		
		jPanelAgregarCliente.add(jTabbedPaneTab);
		jPanelAgregarCliente.add(jPanelBotones);

		this.getContentPane().add( jPanelAgregarCliente, BorderLayout.NORTH );
		
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
				AgregarCliente.this.dispose();
			}
		};
		jPanelAgregarCliente.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	OperationDelegate aceptarAgregarCliente = new OperationDelegate() {
		public void invoke() { 
			aceptarAgregarCliente();
		}
	};

	OperationDelegate cancelarAgregarCliente = new OperationDelegate() {
		public void invoke() {
			cancelarAgregarCliente();
		}
	};
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 140, 25 );
		
		jTabbedPaneTab = new JTabbedPane(); // inicializo pestañas
		
		jLabelCUIT = new JLabel( "CUIT/CUIL: " );
		jLabelCUIT.setPreferredSize( dimensionLabel );
		jTextFieldCUIT = new JTextFieldOfNumbers();
		jTextFieldCUIT.setText( " < 20-31489756-5 >" );
		jTextFieldCUIT.setForeground( Color.GRAY );
		jTextFieldCUIT.setPreferredSize( new Dimension( 120, 25 ) );
		jTextFieldCUIT.addFocusListener( new JTextFieldFocusListener( jTextFieldCUIT ) );
//		jTextFieldCUIT.addKeyListener( new JTextFieldKeyListener( jTextFieldCUIT ) );
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
		jCheckBoxFallecido.setToolTipText("Indicar cliente fallecido");
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setText( " < Juan Jose >" );
		jTextFieldNombre.setForeground( Color.GRAY );
		jTextFieldNombre.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
		jTextFieldNombre.addKeyListener( new JTextFieldKeyListener( jTextFieldNombre ) );

		jLabelApellido = new JLabel( "Apellido: " );
		jLabelApellido.setPreferredSize( dimensionLabel );
		jTextFieldApellido = new JTextFieldOfLetters();
		jTextFieldApellido.setText( " < Lopez >" );
		jTextFieldApellido.setForeground( Color.GRAY );
		jTextFieldApellido.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldApellido.addFocusListener( new JTextFieldFocusListener( jTextFieldApellido ) );
		jTextFieldApellido.addKeyListener( new JTextFieldKeyListener( jTextFieldApellido ) );

		jLabelTelefono = new JLabel( "Teléfono: " );
		jLabelTelefono.setPreferredSize( dimensionLabel );
		jTextFieldTelefono = new JTextFieldPhoneNumber();
		jTextFieldTelefono.setText( " < 0342 - 4891546 >" );
		jTextFieldTelefono.setForeground( Color.GRAY );
		jTextFieldTelefono.setPreferredSize( new Dimension( 141, 25 ) );
		jTextFieldTelefono.addFocusListener( new JTextFieldFocusListener( jTextFieldTelefono ) );
		jTextFieldTelefono.addKeyListener( new JTextFieldKeyListener( jTextFieldTelefono ) );

		jLabelCelular = new JLabel( "Celular: " );
		jTextFieldCelular = new JTextFieldPhoneNumber();
		jTextFieldCelular.setText( " < 0342 - 155293576 >" );
		jTextFieldCelular.setForeground( Color.GRAY );
		jTextFieldCelular.setPreferredSize( new Dimension( 146, 25 ) );
		jTextFieldCelular.addFocusListener( new JTextFieldFocusListener( jTextFieldCelular ) );
		jTextFieldCelular.addKeyListener( new JTextFieldKeyListener( jTextFieldCelular ) );

		jLabelDomicilio = new JLabel( "Domicilio: " );
		jLabelDomicilio.setPreferredSize( dimensionLabel );
		jTextFieldDomicilio = new JTextFieldUpperCased();
		jTextFieldDomicilio.setText( " < Santiago de Chile 2546 >" );
		jTextFieldDomicilio.setForeground( Color.GRAY );
		jTextFieldDomicilio.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldDomicilio.addFocusListener( new JTextFieldFocusListener( jTextFieldDomicilio ) );
		jTextFieldDomicilio.addKeyListener( new JTextFieldKeyListener( jTextFieldDomicilio ) );

		jLabelLocalidad = new JLabel( "Localidad: " );
		jLabelLocalidad.setPreferredSize( dimensionLabel );
		jTextFieldLocalidad = new JTextFieldUpperCased();
		jTextFieldLocalidad.setText( " < Santa Fe - Capital >" );
		jTextFieldLocalidad.setForeground( Color.GRAY );
		jTextFieldLocalidad.setPreferredSize( new Dimension( 165, 25 ) );
		jTextFieldLocalidad.addFocusListener( new JTextFieldFocusListener( jTextFieldLocalidad ) );
		jTextFieldLocalidad.addKeyListener( new JTextFieldKeyListener( jTextFieldLocalidad ) );

		jLabelCodigoPostal = new JLabel( "Código postal: " );
		jTextFieldCodigoPostal = new JTextFieldUpperCased();
		jTextFieldCodigoPostal.setText( " < 3000 >" );
		jTextFieldCodigoPostal.setForeground( Color.GRAY );
		jTextFieldCodigoPostal.setPreferredSize(new Dimension( 92, 25 ) );
		jTextFieldCodigoPostal.addFocusListener( new JTextFieldFocusListener( jTextFieldCodigoPostal ) );
		jTextFieldCodigoPostal.addKeyListener( new JTextFieldKeyListener( jTextFieldCodigoPostal ) );

		jLabelEmail = new JLabel( "Email: " );
		jLabelEmail.setPreferredSize( dimensionLabel );
		jTextFieldEmail = new JTextField();
		jTextFieldEmail.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextFieldEmail.setText( " < juanjose_lopez@gmail.com >" );
		jTextFieldEmail.setForeground( Color.GRAY );
		jTextFieldEmail.setPreferredSize( new Dimension( 354, 25 ) );
		jTextFieldEmail.addFocusListener( new JTextFieldFocusListener( jTextFieldEmail ) );
		jTextFieldEmail.addKeyListener( new JTextFieldKeyListener( jTextFieldEmail ) );

		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( aceptarAgregarCliente ) );
		jButtonAceptar.addKeyListener( new JButtonKeyListener( aceptarAgregarCliente ) );

		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new JButtonActionListener( cancelarAgregarCliente ) );
		jButtonCancelar.addKeyListener( new JButtonKeyListener( cancelarAgregarCliente ) );
	}
	
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
		jPanelDatosSecundarios.setLayout(new FlowLayout(FlowLayout.CENTER,0,0) );
		jPanelDatosSecundarios.setPreferredSize(new Dimension( 325, 75 ));
		
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
		
		jPanelDatosSecundarios.add(jLabelObservaciones);
		jPanelDatosSecundarios.add(jScrollPaneObservaciones);
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
		
		// Recupero el año actual
		Calendar cal1 = Calendar.getInstance();
		int anioActual = cal1.get(Calendar.YEAR);

		for(int i=(anioActual-17); i>=(anioActual-90); i-- ) // el cliente tiene entre 17 y 90 años de edad
			jComboBoxFechaDeNacimientoAnio.addItem( String.valueOf( i ) );
	}
	 
	private void aceptarAgregarCliente() {
		int option = 0;
		int id_cliente;
		// verifico que los campos obligatorios no se encuentren vacíos y que ningun campo exceda los 25 caracteres
		if( validarDatos() && characterVaryingExceeded()) {
			
			// almaceno la fecha en fechaDeNacimientoCalendar
			Calendar fechaDeNacimientoCalendar = GregorianCalendar.getInstance();
			fechaDeNacimientoCalendar.setLenient( false );
			if(jComboBoxFechaDeNacimientoAnio.getSelectedIndex() == 0)
				fechaDeNacimientoCalendar.set( 1900 , jComboBoxFechaDeNacimientoMes.getSelectedIndex() - 1, Integer.parseInt( jComboBoxFechaDeNacimientoDia.getSelectedItem().toString() ) );
			else
				fechaDeNacimientoCalendar.set( Integer.parseInt( jComboBoxFechaDeNacimientoAnio.getSelectedItem().toString() ) , jComboBoxFechaDeNacimientoMes.getSelectedIndex() - 1, Integer.parseInt( jComboBoxFechaDeNacimientoDia.getSelectedItem().toString() ) );
			fechaDeNacimientoCalendar.set( Calendar.MINUTE, 0 );
			fechaDeNacimientoCalendar.set( Calendar.SECOND, 0 );
			fechaDeNacimientoCalendar.set( Calendar.MILLISECOND, 0 );

			Cliente cliente = new Cliente();
			cliente.setCUIT( jTextFieldCUIT.getText().trim()  );
			cliente.setFallecido(jCheckBoxFallecido.isSelected());
			cliente.setNombre( jTextFieldNombre.getText().trim() );
			cliente.setApellido( jTextFieldApellido.getText().trim() );
			cliente.setFechaDeNacimiento( fechaDeNacimientoCalendar );
			cliente.setTelefono( jTextFieldTelefono.getText().trim() );
			cliente.setCelular( jTextFieldCelular.getText().trim() );
			cliente.setDomicilio( jTextFieldDomicilio.getText().trim() );
			cliente.setLocalidad( jTextFieldLocalidad.getText().trim() );
			cliente.setCodigoPostal( jTextFieldCodigoPostal.getText().trim() );
			cliente.setEmail( jTextFieldEmail.getText().trim() );
			cliente.setObservaciones( jTextAreaObservaciones.getText() );
			
			IClienteManager clienteManager = new ClienteManager();
			try {
				id_cliente = clienteManager.nextID();
				cliente.setId_cliente(id_cliente);
				option = clienteManager.agregar( cliente );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}

			switch( option ) {
				case 1:
					JOptionPane.showMessageDialog( this, "Se agregó con éxito el nuevo cliente", "Éxito", JOptionPane.INFORMATION_MESSAGE );
					cleanAllFields();
					this.dispose();
					
					break;
				case 2:
					JOptionPane.showMessageDialog( this, "El CUIT/CUIL ingresado pertenece a un cliente existente", "Error", JOptionPane.ERROR_MESSAGE );
					jTextFieldCUIT.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldCUIT.addFocusListener( new JTextFieldFocusListener( jTextFieldCUIT ) );
					
					break;
				case 3:
					JOptionPane.showMessageDialog( this, "Ingrese un número válido de CUIT/CUIL", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					jTextFieldCUIT.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldCUIT.addFocusListener( new JTextFieldFocusListener( jTextFieldCUIT ) );
					
					break;
				case 4:
					JOptionPane.showMessageDialog( this, "Ingrese una fecha válida", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
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
					JOptionPane.showMessageDialog( this, "Ingrese una dirección válida de email", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					jTextFieldEmail.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldEmail.addFocusListener( new JTextFieldFocusListener( jTextFieldEmail ) );
					break;
				case 6:
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

	
	private void cancelarAgregarCliente() {
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
	
	
	private boolean validarDatos() {
		int cont = 0;
		
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldApellido );
		jTextFields.add( jTextFieldDomicilio );

		for( JTextField jTextField : jTextFields )
			cont += isEmpty( jTextField );
		
		cont += isEmpty( jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoAnio );
		cont += isEmpty( jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia, jComboBoxFechaDeNacimientoAnio );
//		cont += isEmpty( jComboBoxFechaDeNacimientoAnio, jComboBoxFechaDeNacimientoMes, jComboBoxFechaDeNacimientoDia );
		
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
	
	private int isEmpty( JComboBox<String> jComboBox1, JComboBox<String> jComboBox2, JComboBox<String> jComboBox3 ) {
		if( jComboBox1.getSelectedItem().toString().equals( "Día" ) || jComboBox1.getSelectedItem().toString().equals( "Mes" ) || jComboBox1.getSelectedItem().toString().equals( "Año" )) {
			jComboBox1.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jComboBox1.addFocusListener( new JComboBoxFocusListener( jComboBox1, jComboBox2, jComboBox3 ) );
			
			return 1;
		}
		
		return 0;
	}
	
}