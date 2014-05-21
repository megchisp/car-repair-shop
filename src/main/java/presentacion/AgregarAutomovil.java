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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import negocio.AutomovilManager;

import persistencia.Automovil;
import persistencia.Cliente;

public class AgregarAutomovil extends JDialog {
	
	private static final long serialVersionUID = 1L;

	JPanel jPanelAgregarAutomovil = null;
	JPanel jPanelDatosPrimarios = null;
	JPanel jPanelDatosSecundarios = null;
	JPanel jPanelBotones = null;
	
	JLabel jLabelDominio = null;
	JLabel jLabelNumMotor = null;
	JLabel jLabelNumChasis = null;
	JLabel jLabelMarca = null;
	JLabel jLabelModelo = null;
	JLabel jLabelAño = null;
	JLabel jLabelColor = null;
	JLabel jLabelTipoAceite = null;
	JLabel jLabelTipoCombustible = null;
	JLabel jLabelNumRadio = null;
	JLabel jLabelCodigoLlave = null;
	JLabel jLabelUso = null;
	
	JTextFieldUpperCased jTextFieldDominio = null;
	JTextFieldUpperCased jTextFieldNumMotor = null;
	JTextFieldUpperCased jTextFieldNumChasis = null;
	JTextFieldUpperCased jTextFieldModelo = null;
	JTextFieldUpperCased jTextFieldTipoAceite = null;
	JTextFieldUpperCased jTextFieldNumRadio = null;
	JTextFieldUpperCased jTextFieldCodigoLlave = null;
	JTextFieldUpperCased jTextFieldUso = null;
	
	JTabbedPane jTabbedPaneTab = null; // pestaña
	
	ButtonGroup buttonGroupNaftaGasoil = null;
	JRadioButton jRadioButtonNafta = null;
	JRadioButton jRadioButtonGasoil = null;
	JCheckBox jCheckBoxGNC = null;
	
	JComboBox<String> jComboBoxAño = null;
	JComboBox<?> jComboBoxColor = null;
	JComboBox<String> jComboBoxMarca = null;
	ImageListModel ilm = null;
	
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	Cliente cliente = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	public AgregarAutomovil( JFrame padre, String titulo, String titleBorder, Cliente cliente ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 340, 435 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
	    
		this.setTitle(titulo);
		this.cliente = cliente;
		
		jPanelAgregarAutomovil = new JPanel();
		jPanelAgregarAutomovil.setLayout(new FlowLayout(FlowLayout.CENTER,0,15) );
		jPanelAgregarAutomovil.setPreferredSize(new Dimension( 330, 405 ));
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelAgregarAutomovil.setBorder( titledBorder );
		
		inicializarVariables();
		cerrarEsc();
		inicializarDatosPrimarios();
		inicializarDatosSecundarios();
		inicializarBotones();
		
		jTabbedPaneTab.setPreferredSize(new Dimension( 310, 300 ));
		jTabbedPaneTab.add("Datos principales",jPanelDatosPrimarios);
		jTabbedPaneTab.add("Otros",jPanelDatosSecundarios);
	
		jPanelAgregarAutomovil.add(jTabbedPaneTab);
		jPanelAgregarAutomovil.add( jPanelBotones );
		this.getContentPane().add( jPanelAgregarAutomovil, BorderLayout.NORTH );
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
				AgregarAutomovil.this.dispose();
			}
		};
		jPanelAgregarAutomovil.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void inicializarDatosPrimarios(){

		jPanelDatosPrimarios = new JPanel();
		jPanelDatosPrimarios.setPreferredSize(new Dimension( 310, 340 ));
		jPanelDatosPrimarios.setLayout( new GridBagLayout() );
		
		ButtonGroup buttonGroupCriterio = new ButtonGroup();
	
		buttonGroupCriterio.add(jRadioButtonGasoil);
		buttonGroupCriterio.add(jRadioButtonNafta);
	
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );
		
		a.gridy = 1;
		a.gridx = 0;
		//a.gridwidth = 2;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelDominio, a );
		a.gridy = 1;
		a.gridx = 1;
		a.insets = new Insets( 0, -25, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldDominio, a );

		a.gridy = 2;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );

		a.gridy = 3;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelMarca, a );
		a.gridy = 3;
		a.gridx = 1;
		a.insets = new Insets( 0, -115, 0, 0 );
		jPanelDatosPrimarios.add( jComboBoxMarca, a );
		
		a.gridy = 4;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );
		
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelModelo, a );
		a.gridy = 5;
		a.gridx = 1;
		a.insets = new Insets( 0, -110, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldModelo, a );
		
		a.gridy = 6;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );
		
			JPanel jPanelAñoColor = new JPanel();
			jPanelAñoColor.setLayout( new GridBagLayout() );
			
			GridBagConstraints b = new GridBagConstraints();
			
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 0, 0, 0 );
			jPanelAñoColor.add( jLabelAño, b );
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, -200, 0, 0 );
			jPanelAñoColor.add( jComboBoxAño, b );
			b.gridy = 0;
			b.gridx = 2;
			b.insets = new Insets( 0, -40, 0, 0 );
			jPanelAñoColor.add( jLabelColor, b );
			b.gridy = 0;
			b.gridx = 3;
			b.insets = new Insets( 0, -120, 0, 0 );
			jPanelAñoColor.add( jComboBoxColor, b );
		
		a.gridy = 7;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosPrimarios.add( jPanelAñoColor, a );
				
		a.gridy = 8;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );
		
		a.gridy = 9;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelTipoAceite, a );
		a.gridy = 9;
		a.gridx = 1;
		a.insets = new Insets( 0, -72, 0, 0 );
		jPanelDatosPrimarios.add( jTextFieldTipoAceite, a );
		
		a.gridy = 10;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );
		
		a.gridy = 11;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosPrimarios.add( jLabelTipoCombustible, a );
		a.gridy = 11;
		a.gridx = 1;
		a.insets = new Insets( 0, -37, 0, 0 );
		jPanelDatosPrimarios.add( jRadioButtonNafta, a );
		a.gridy = 11;
		a.gridx = 2;
		a.insets = new Insets( 0, -95, 0, 0 );
		jPanelDatosPrimarios.add( jCheckBoxGNC, a );
		
		a.gridy = 12;
		a.gridx = 0;
		a.insets = new Insets( 0, 122, 0, 0 );
		jPanelDatosPrimarios.add( jRadioButtonGasoil, a );

		a.gridy = 11;
		a.gridx = 0;
		jPanelDatosPrimarios.add( new JLabel(" "), a );
	}
	
	private void inicializarDatosSecundarios(){
		jPanelDatosSecundarios = new JPanel();
		jPanelDatosSecundarios.setPreferredSize(new Dimension( 310, 340 ));
		jPanelDatosSecundarios.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelDatosSecundarios.add( new JLabel(" "), a );
		
		a.gridy = 1;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosSecundarios.add( jLabelNumMotor, a );
		a.gridy = 1;
		a.gridx = 1;
		a.insets = new Insets( 0, -50, 0, 0 );
		jPanelDatosSecundarios.add( jTextFieldNumMotor, a );
		
		a.gridy = 2;
		a.gridx = 0;
		jPanelDatosSecundarios.add( new JLabel(" "), a );
		
		a.gridy = 3;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosSecundarios.add( jLabelNumChasis, a );
		a.gridy = 3;
		a.gridx = 1;
		a.insets = new Insets( 0, -50, 0, 0 );
		jPanelDatosSecundarios.add( jTextFieldNumChasis, a );
		
		a.gridy = 4;
		a.gridx = 0;
		jPanelDatosSecundarios.add( new JLabel(" "), a );
		
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosSecundarios.add( jLabelNumRadio, a );
		a.gridy = 5;
		a.gridx = 1;
		a.insets = new Insets( 0, -57, 0, 0 );
		jPanelDatosSecundarios.add( jTextFieldNumRadio, a );
		
		a.gridy = 6;
		a.gridx = 0;
		jPanelDatosSecundarios.add( new JLabel(" "), a );
		
		a.gridy = 7;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosSecundarios.add( jLabelCodigoLlave, a );
		
		a.gridy = 7;
		a.gridx = 1;
		a.insets = new Insets( 0, -83, 0, 0 );
		jPanelDatosSecundarios.add( jTextFieldCodigoLlave, a );
		
		a.gridy = 8;
		a.gridx = 0;
		jPanelDatosSecundarios.add( new JLabel(" "), a );
		
		a.gridy = 9;
		a.gridx = 0;
		a.insets = new Insets( 0, -0, 0, 0 );
		jPanelDatosSecundarios.add( jLabelUso, a );
		a.gridy = 9;
		a.gridx = 1;
		a.insets = new Insets( 0, -130, 0, 0 );
		jPanelDatosSecundarios.add( jTextFieldUso, a );
		
		a.gridy = 10;
		a.gridx = 0;
		jPanelDatosSecundarios.add( new JLabel(" "), a );
	}
	
	private void inicializarBotones(){
		jPanelBotones = new JPanel();
		jPanelBotones.setPreferredSize( new Dimension( 328, 40 ) );
		jPanelBotones.setLayout( new GridBagLayout() );
		
		GridBagConstraints e = new GridBagConstraints();
		e.anchor = GridBagConstraints.EAST;
		
		e.gridy = 0;
		e.gridx = 0;
		e.insets = new Insets( 0, 90, 0, 0 );
		jPanelBotones.add( jButtonAceptar, e );
		e.gridy = 0;
		e.gridx = 1;
		e.insets = new Insets( 0, 20, 0, 0 );
		jPanelBotones.add( jButtonCancelar, e );

	}
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 160, 25 );
		
		jTabbedPaneTab = new JTabbedPane();

		jLabelDominio = new JLabel( "Dominio (chapa patente):" );
		jLabelDominio.setPreferredSize( dimensionLabel );
		jTextFieldDominio = new JTextFieldUpperCased();
		jTextFieldDominio.setText( " < HRE491 >" );
		jTextFieldDominio.setForeground( Color.GRAY );
		jTextFieldDominio.setPreferredSize( new Dimension( 152, 25 ) );
		jTextFieldDominio.addFocusListener( new JTextFieldFocusListener( jTextFieldDominio ) );
		jTextFieldDominio.addKeyListener( new JTextFieldKeyListener( jTextFieldDominio ) );

		jLabelNumMotor = new JLabel( "Número de motor: " );
		jLabelNumMotor.setPreferredSize( new Dimension( 130, 25 ) );
		jTextFieldNumMotor = new JTextFieldUpperCased();
		jTextFieldNumMotor.setText( " < LEGBUV19642 >" );
		jTextFieldNumMotor.setForeground( Color.GRAY );
		jTextFieldNumMotor.setPreferredSize( new Dimension( 176, 25 ) );
		jTextFieldNumMotor.addFocusListener( new JTextFieldFocusListener( jTextFieldNumMotor ) );
		jTextFieldNumMotor.addKeyListener( new JTextFieldKeyListener( jTextFieldNumMotor ) );


		jLabelNumChasis = new JLabel( "Número de chasis: " );
		jLabelNumChasis.setPreferredSize( new Dimension( 140, 25 ) );
		jTextFieldNumChasis = new JTextFieldUpperCased();
		jTextFieldNumChasis.setText( " < MD2HDS4ZXBVC00609 >" );
		jTextFieldNumChasis.setForeground( Color.GRAY );
		jTextFieldNumChasis.setPreferredSize( new Dimension( 176, 25 ) );
		jTextFieldNumChasis.addFocusListener( new JTextFieldFocusListener( jTextFieldNumChasis ) );
		jTextFieldNumChasis.addKeyListener( new JTextFieldKeyListener( jTextFieldNumChasis ) );

		
		inicializarMarca();
		jLabelMarca = new JLabel( "Marca: " );
		jLabelMarca.setPreferredSize( dimensionLabel );
		
		jLabelModelo = new JLabel( "Modelo: " );
		jTextFieldModelo = new JTextFieldUpperCased();
		jTextFieldModelo.setText( " < Sandero >" );
		jTextFieldModelo.setForeground( Color.GRAY );
		jTextFieldModelo.setPreferredSize( new Dimension( 237, 25 ) );
		jTextFieldModelo.addFocusListener( new JTextFieldFocusListener( jTextFieldModelo ) );
		jTextFieldModelo.addKeyListener( new JTextFieldKeyListener( jTextFieldModelo ) );
		
		inicializarAño();
		jLabelAño = new JLabel( "Año: " );
		jLabelAño.setPreferredSize( dimensionLabel );
//		jTextFieldAño = new JTextField();
//		jTextFieldAño.setPreferredSize( new Dimension( 160, 25 ) );
		
		inicializarColor();
		jLabelColor = new JLabel( "Color: " );
		jLabelColor.setPreferredSize( dimensionLabel );
		
		jLabelTipoAceite = new JLabel( "Tipo de aceite: " );
		jTextFieldTipoAceite = new JTextFieldUpperCased();
		jTextFieldTipoAceite.setText( " < Rimula >" );
		jTextFieldTipoAceite.setForeground( Color.GRAY );
		jTextFieldTipoAceite.setPreferredSize( new Dimension( 199, 25 ) );
		jTextFieldTipoAceite.addFocusListener( new JTextFieldFocusListener( jTextFieldTipoAceite ) );
		jTextFieldTipoAceite.addKeyListener( new JTextFieldKeyListener( jTextFieldTipoAceite ) );
		
		jLabelTipoCombustible = new JLabel( "Tipo de combustible: " );
		jLabelTipoCombustible.setPreferredSize( dimensionLabel );
		jRadioButtonNafta = new JRadioButton("Nafta", true);
		jRadioButtonGasoil = new JRadioButton("Gasoil");
		jCheckBoxGNC = new JCheckBox("con GNC");
		//jTextFieldTipoCombustible.setPreferredSize( new Dimension( 114, 25 ) );
		
		jLabelNumRadio = new JLabel( "Número de radio: " );
		jLabelNumRadio.setPreferredSize( dimensionLabel );
		jTextFieldNumRadio = new JTextFieldUpperCased();
		jTextFieldNumRadio.setText( " < 6584 >" );
		jTextFieldNumRadio.setForeground( Color.GRAY );
		jTextFieldNumRadio.setPreferredSize( new Dimension( 183, 25 ) );
		jTextFieldNumRadio.addFocusListener( new JTextFieldFocusListener( jTextFieldNumRadio ) );
		jTextFieldNumRadio.addKeyListener( new JTextFieldKeyListener( jTextFieldNumRadio ) );
		
		
		jLabelCodigoLlave = new JLabel( "Código llave: " );
		jLabelCodigoLlave.setPreferredSize( dimensionLabel );
		jTextFieldCodigoLlave = new JTextFieldUpperCased();
		jTextFieldCodigoLlave.setText( " < 325147884554 >" );
		jTextFieldCodigoLlave.setForeground( Color.GRAY );
		jTextFieldCodigoLlave.setPreferredSize( new Dimension( 208, 25 ) );
		jTextFieldCodigoLlave.addFocusListener( new JTextFieldFocusListener( jTextFieldCodigoLlave ) );
		jTextFieldCodigoLlave.addKeyListener( new JTextFieldKeyListener( jTextFieldCodigoLlave ) );
		
		
		jLabelUso = new JLabel( "Uso: " );
		jLabelUso.setPreferredSize( dimensionLabel );
		jTextFieldUso = new JTextFieldUpperCased();
		jTextFieldUso.setText( " < Remis >" );
		jTextFieldUso.setForeground( Color.GRAY );
		jTextFieldUso.setPreferredSize( new Dimension( 255, 25 ) );
		jTextFieldUso.addFocusListener( new JTextFieldFocusListener( jTextFieldUso ) );
		jTextFieldUso.addKeyListener( new JTextFieldKeyListener( jTextFieldUso ) );
		
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				aceptarAgregarAutomovil();
			}
		});
		
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cancelarAgregarAutomovil();
			}
		});
		
	}
	
	
	private void inicializarMarca() {
		jComboBoxMarca = new JComboBox<String>();
		jComboBoxMarca.setFont(new Font("Dialog", Font.BOLD, 11));
		jComboBoxMarca.addItem("----");
		jComboBoxMarca.addItem("ALFA ROMEO");
		jComboBoxMarca.addItem("ASTON MARTIN");
		jComboBoxMarca.addItem("AUDI");
		jComboBoxMarca.addItem("BENTLEY");
		jComboBoxMarca.addItem("BMW");
		jComboBoxMarca.addItem("CADILLAC");
		jComboBoxMarca.addItem("CITROEN");
		jComboBoxMarca.addItem("CHEVROLET");
		jComboBoxMarca.addItem("MERCEDES BENZ");
		jComboBoxMarca.addItem("MAZDA");
		jComboBoxMarca.addItem("MAYBACH");
		jComboBoxMarca.addItem("MASERATI");
		jComboBoxMarca.addItem("MAHINDRA");
		jComboBoxMarca.addItem("LOTUS");
		jComboBoxMarca.addItem("LEXUS");
		jComboBoxMarca.addItem("LAND ROVER");
		jComboBoxMarca.addItem("LANCIA");
		jComboBoxMarca.addItem("LADA");
		jComboBoxMarca.addItem("KIA");
		jComboBoxMarca.addItem("JEEP");
		jComboBoxMarca.addItem("JAGUAR");
		jComboBoxMarca.addItem("IVECO DAILY");
		jComboBoxMarca.addItem("ISUZU");
		jComboBoxMarca.addItem("INFINITI");
		jComboBoxMarca.addItem("HYUNDAI");
		jComboBoxMarca.addItem("HUMMER");
		jComboBoxMarca.addItem("HONDA");
		jComboBoxMarca.addItem("FORD");
		jComboBoxMarca.addItem("FIAT");
		jComboBoxMarca.addItem("FERRARI");
		jComboBoxMarca.addItem("DAIMLER");
		jComboBoxMarca.addItem("DAIHATSU");
		jComboBoxMarca.addItem("DACIA");
		jComboBoxMarca.addItem("CORVETTE");
		jComboBoxMarca.addItem("SANTANA");
		jComboBoxMarca.addItem("SAAB");
		jComboBoxMarca.addItem("ROVER");
		jComboBoxMarca.addItem("ROLLS ROYCE");
		jComboBoxMarca.addItem("RENAULT");
		jComboBoxMarca.addItem("PORSCHE");
		jComboBoxMarca.addItem("PIAGGIO");
		jComboBoxMarca.addItem("PEUGEOT");
		jComboBoxMarca.addItem("PARTNER");
		jComboBoxMarca.addItem("OPEL");
		jComboBoxMarca.addItem("NISSAN");
		jComboBoxMarca.addItem("MORGAN");
		jComboBoxMarca.addItem("MITSUBISHI");
		jComboBoxMarca.addItem("MINI");
		jComboBoxMarca.addItem("TOYOTA");
		jComboBoxMarca.addItem("TATA");
		jComboBoxMarca.addItem("SUZUKI");
		jComboBoxMarca.addItem("SUBARU");
		jComboBoxMarca.addItem("SSANGYONG");
		jComboBoxMarca.addItem("SMART");
		jComboBoxMarca.addItem("SKODA");
		jComboBoxMarca.addItem("SEAT");
		jComboBoxMarca.addItem("VOLKSWAGEN");
		jComboBoxMarca.addItem("VOLVO");
}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void inicializarColor() {
		// doy color a jComboBoxColor
		ilm = new ImageListModel();
		jComboBoxColor = new JComboBox(ilm);
		jComboBoxColor.setRenderer(new ImageCellRenderer());
		jComboBoxColor.setSelectedIndex(0);
		jComboBoxColor.setPreferredSize(new Dimension(120,20));
		jComboBoxColor.setMaximumRowCount(20); // muestra los 13 colores sin scroll
		
	}

	private void inicializarAño() {
		// Recupero el año acual
		Calendar cal1 = Calendar.getInstance();
		int anioActual = cal1.get(Calendar.YEAR);
		jComboBoxAño = new JComboBox<String>();
		jComboBoxAño.setFont(new Font("Dialog", Font.BOLD, 11));
			
		jComboBoxAño.addItem("----");
		for(int i = anioActual; i >= 1930; i--)
			jComboBoxAño.addItem(i + "");
	}


	private void aceptarAgregarAutomovil() {
		
		// verifico que los campos obligatorios no se encuentren vacíos y que ningun campo exceda los 25 caracteres
		if( validarDatos() && characterVaryingExceeded()) {

			int option = 0;
			int id_automovil;
			Automovil automovil = new Automovil();
			automovil.setId_cliente(cliente.getId_cliente());
			automovil.setDominio( jTextFieldDominio.getText().trim() );
			automovil.setNumeroMotor( jTextFieldNumMotor.getText().trim() );
			automovil.setNumeroChasis( jTextFieldNumChasis.getText().trim() );
			automovil.setModelo( jTextFieldModelo.getText().trim() );
			if(jRadioButtonGasoil.isSelected()){
				automovil.setTipoCombustible("Gasoil" );
				}
				else{
					automovil.setTipoCombustible("Nafta" );
				}
			automovil.setConGNC(jCheckBoxGNC.isSelected());
			automovil.setNumeroRadio( jTextFieldNumRadio.getText().trim() );
			automovil.setCodigoLlave( jTextFieldCodigoLlave.getText().trim() );
			if( jComboBoxMarca.getSelectedIndex() != 0 )
				automovil.setMarca( jComboBoxMarca.getSelectedItem().toString() );
			else
				automovil.setMarca( "" );
			
			if( jComboBoxAño.getSelectedIndex() != 0 )
				automovil.setAnio( Integer.parseInt(jComboBoxAño.getSelectedItem().toString()) );
			else
				automovil.setAnio( 1900 );
			automovil.setColor( jComboBoxColor.getSelectedIndex() );
			automovil.setTipoAceite( jTextFieldTipoAceite.getText().trim() );
			automovil.setUso( jTextFieldUso.getText().trim() );
			

			AutomovilManager automovilManager = new AutomovilManager();
			try {
				id_automovil = automovilManager.nextID();
				automovil.setId_automovil(id_automovil);
				option = automovilManager.agregar( automovil );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			switch( option ) {
				case 1:

					cleanAllFields();
					this.dispose();
					new DetallarAutomovil( null, "Detallar automóvil", " Detalle del automóvil ", automovil);
					
					break;
				case 2:
					JOptionPane.showMessageDialog( this, "El dominio ingresado pertenece a un automóvil existente", "Error", JOptionPane.ERROR_MESSAGE );
					
					jTextFieldDominio.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldDominio.addFocusListener( new JTextFieldFocusListener( jTextFieldDominio ) );
					
					break;
				case 3:
					JOptionPane.showMessageDialog( null, "El número de motor pertenence a un automóvil existente", "Error", JOptionPane.ERROR_MESSAGE );
				
					jTextFieldNumMotor.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldNumMotor.addFocusListener( new JTextFieldFocusListener( jTextFieldNumMotor ) );
					
					break;
				case 4:
					JOptionPane.showMessageDialog( null, "El número de chasis pertenence a un automóvil existente", "Error", JOptionPane.ERROR_MESSAGE );
				
					jTextFieldNumMotor.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldNumMotor.addFocusListener( new JTextFieldFocusListener( jTextFieldNumMotor ) );
					
					break;
				case 5:
					JOptionPane.showMessageDialog( this, "Ingrese un dominio válido", "Error", JOptionPane.ERROR_MESSAGE );
					
					jTextFieldDominio.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldDominio.addFocusListener( new JTextFieldFocusListener( jTextFieldDominio ) );
					break;
				default:
					// este mensaje nunca debería aparecer
					JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		} 
	}
	
	private void cancelarAgregarAutomovil() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private void cleanAllFields() {
		jTextFieldDominio.setText( "" );
		jComboBoxAño.setSelectedIndex( 0 );
		jComboBoxColor.setSelectedIndex( 0 );
		jComboBoxMarca.setSelectedIndex( 0 );
		jCheckBoxGNC.setSelected( false );
		jTextFieldNumMotor.setText( "" );
		jTextFieldNumChasis.setText( "" );
		jTextFieldModelo.setText( "" );
		jTextFieldTipoAceite.setText( "" );
		jTextFieldNumRadio.setText( "" );
		jTextFieldCodigoLlave.setText( "" );
		jTextFieldUso.setText( "" );
		
	}
	
	private boolean characterVaryingExceeded(){
		int cont = 0;
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		jTextFields.add( jTextFieldDominio );
		jTextFields.add( jTextFieldModelo );
		jTextFields.add( jTextFieldTipoAceite );
		jTextFields.add( jTextFieldNumMotor );
		jTextFields.add( jTextFieldNumChasis );
		jTextFields.add( jTextFieldNumRadio );
		jTextFields.add( jTextFieldCodigoLlave );
		jTextFields.add( jTextFieldUso );

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
	
	private boolean validarDatos() {
		int cont = 0;
	
		cont += isEmpty( jTextFieldDominio );
		cont += isEmpty( jTextFieldModelo );
		if( jComboBoxMarca.getSelectedIndex() == 0){
			jComboBoxMarca.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jComboBoxMarca.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {

				}
				public void focusGained( FocusEvent e ) {
					jComboBoxMarca.setBorder( ( new JComboBox<Object>() ).getBorder() );
					jComboBoxMarca.setForeground( Color.BLACK );
                }
            });
			cont++;
		}
		
		if( jTextFieldDominio.getText().contains( "<" ) )
			jTextFieldDominio.setText( "" );
		
		if( jTextFieldModelo.getText().contains( "<" ) )
			jTextFieldModelo.setText( "" );
		
		if( jTextFieldNumMotor.getText().contains( "<" ) )
			jTextFieldNumMotor.setText( "" );
		
		if( jTextFieldTipoAceite.getText().contains( "<" ) )
			jTextFieldTipoAceite.setText( "" );
		
		if( jTextFieldNumChasis.getText().contains( "<" ) )
			jTextFieldNumChasis.setText( "" );
		
		if( jTextFieldNumRadio.getText().contains( "<" ) )
			jTextFieldNumRadio.setText( "" );
		
		if( jTextFieldCodigoLlave.getText().contains( "<" ) )
			jTextFieldCodigoLlave.setText( "" );
		
		if( jTextFieldUso.getText().contains( "<" ) )
			jTextFieldUso.setText( "" );
				
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