package proyectoanalista.presentacion;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;


import proyectoanalista.negocio.ReparacionManager;
import proyectoanalista.presentacion.JTextFieldOfNumbers;
import proyectoanalista.persistencia.Reparacion;

public class AgregarReparacion extends JDialog{
	
	private static final long serialVersionUID = 1L;

	private JFrame agregarReparacion;
	private DetallarAutomovil detallarAutomovil = null;
	
	java.util.Date dateFecha = new Date();
	
	JLabel jLabelFechaReparacion = null;
	JLabel jLabelDe1 = null;
	JLabel jLabelDe2 = null;
	JLabel jLabelImporte = null;
	JLabel jLabelKilometraje = null;
	JLabel jLabelObservaciones = null;
	
	JTextField jTextFieldImporte = null;
	JTextFieldOfNumbers jTextFieldKilometraje = null;
		
	JScrollPane jScrollPaneObservaciones = null;
    JTextArea jTextAreaObservaciones = null;
		
	JComboBox<String> jComboBoxFechaReparacionDia  = null;
	JComboBox<String> jComboBoxFechaReparacionMes  = null;
	JComboBox<String> jComboBoxFechaReparacionAnio = null;
	
	DefaultTableModel dtmReparacionesAutomoviles = null;
	
	TitledBorder titledBorder2 = null;
	
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	JPanel jPanelAgregarReparacion = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();

	public AgregarReparacion( JFrame padre, String titulo, String titleBorder, DetallarAutomovil detallarAutomovil) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 350, 315 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		this.detallarAutomovil = detallarAutomovil;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		jPanelAgregarReparacion = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelAgregarReparacion.setBorder( titledBorder );
		jPanelAgregarReparacion.setPreferredSize( new Dimension( 200, 285 ) );
		

		jPanelAgregarReparacion.setLayout( new FlowLayout(FlowLayout.RIGHT, 0, 20) );
		
		inicializarVariables();
		cerrarEsc();
		 
		jPanelAgregarReparacion.add( new JLabel(" "));
			
			JPanel jPanelFechaReparacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jPanelFechaReparacion.setPreferredSize(new Dimension( 325, 30 ));

			jPanelFechaReparacion.add( jLabelFechaReparacion);
			jPanelFechaReparacion.add( jComboBoxFechaReparacionDia);
			jPanelFechaReparacion.add( jLabelDe1);
			jPanelFechaReparacion.add( jComboBoxFechaReparacionMes);
			jPanelFechaReparacion.add( jLabelDe2);
			jPanelFechaReparacion.add( jComboBoxFechaReparacionAnio);
			
		jPanelAgregarReparacion.add( jPanelFechaReparacion);
		
			JPanel jPanelTiempoEstimado = new JPanel();
			jPanelTiempoEstimado.setLayout( new GridBagLayout() );
			
		
			JPanel jPanelImporteKilometraje = new JPanel();
			jPanelImporteKilometraje.setPreferredSize(new Dimension(325,30));
			jPanelImporteKilometraje.setLayout( new GridBagLayout() );
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridy = 0;
			c.gridx = 0;
			c.insets = new Insets( 0, 0, 0, 6 );
			jPanelImporteKilometraje.add( jLabelKilometraje, c );
			c.gridy = 0;
			c.gridx = 1;
			c.insets = new Insets( 0, -5, 0, 15 );
			jPanelImporteKilometraje.add( jTextFieldKilometraje, c );
			c.gridy = 0;
			c.gridx = 2;
			c.insets = new Insets( 0, 0, 0, 0 );
			jPanelImporteKilometraje.add( jLabelImporte, c );
			c.gridy = 0;
			c.gridx = 3;
			c.insets = new Insets( 0, 1, 0, 0 );
			jPanelImporteKilometraje.add( jTextFieldImporte, c );
		
		jPanelAgregarReparacion.add( jPanelImporteKilometraje);
		jPanelAgregarReparacion.add( new JLabel(" ") );
		
			JPanel jPanelObservaciones = new JPanel();
			jPanelObservaciones.setLayout( new FlowLayout(FlowLayout.CENTER,0,0));
			jPanelObservaciones.setPreferredSize(new Dimension(325, 75));
			jTextAreaObservaciones = new JTextArea();
			jTextAreaObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
			((AbstractDocument) jTextAreaObservaciones.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
			jTextAreaObservaciones.setLineWrap(true);
			jTextAreaObservaciones.setWrapStyleWord(true);
			jScrollPaneObservaciones = new JScrollPane(jTextAreaObservaciones);
			jScrollPaneObservaciones.setPreferredSize(new Dimension( 309, 50 ));
			jPanelObservaciones.add(jLabelObservaciones);
			jPanelObservaciones.add(jScrollPaneObservaciones);
		jPanelAgregarReparacion.add( jPanelObservaciones);
			
				
			JPanel jPanelBotones = new JPanel();
			//jPanelBotones.setLayout( new FlowLayout(FlowLayout.RIGHT, 15, 0) );
			
			//jPanelBotones.setPreferredSize( new Dimension( 230, 30 ) );
			jPanelBotones.setLayout( new GridBagLayout() );
			
			GridBagConstraints e = new GridBagConstraints();
			e.anchor = GridBagConstraints.EAST;
			
			e.gridy = 0;
			e.gridx = 0;
			e.insets = new Insets( 0, 0, 0, 0 );
			jPanelBotones.add( jButtonAceptar, e );
			e.gridy = 0;
			e.gridx = 1;
			e.insets = new Insets( 0, 20, 0, 8 );
			jPanelBotones.add( jButtonCancelar, e );

		jPanelAgregarReparacion.add( jPanelBotones);
		
		jPanelAgregarReparacion.add( new JLabel(" ") );
			
		this.getContentPane().add( jPanelAgregarReparacion, BorderLayout.NORTH );
		this.setResizable( false );
		this.pack();
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarFecha() {
		jComboBoxFechaReparacionDia = new JComboBox<String>();
		jComboBoxFechaReparacionAnio = new JComboBox<String>();
		jComboBoxFechaReparacionDia.setFont(new Font("Dialog", Font.BOLD, 11));
		jComboBoxFechaReparacionAnio.setFont(new Font("Dialog", Font.BOLD, 11));
		
		// Recupero el día, mes y año acual
		Calendar cal1 = Calendar.getInstance();
		int anioActual = cal1.get(Calendar.YEAR);
		int mesActual = cal1.get(Calendar.MONTH);
		int diaActual = cal1.get(Calendar.DATE);
		
		for(int i=1; i<= 31; i++)
			jComboBoxFechaReparacionDia.addItem(i+"");
		
		for(int i=anioActual; i>=1990; i--)
			jComboBoxFechaReparacionAnio.addItem(i+"");

		jComboBoxFechaReparacionMes = new JComboBox<String>();
		jComboBoxFechaReparacionMes.setPreferredSize(new Dimension(94,25));
		jComboBoxFechaReparacionMes.setFont(new Font("Dialog", Font.BOLD, 11));
		
		jComboBoxFechaReparacionMes.addItem("Enero");
		jComboBoxFechaReparacionMes.addItem("Febrero");
		jComboBoxFechaReparacionMes.addItem("Marzo");
		jComboBoxFechaReparacionMes.addItem("Abril");
		jComboBoxFechaReparacionMes.addItem("Mayo");
		jComboBoxFechaReparacionMes.addItem("Junio");
		jComboBoxFechaReparacionMes.addItem("Julio");
		jComboBoxFechaReparacionMes.addItem("Agosto");
		jComboBoxFechaReparacionMes.addItem("Septiembre");
		jComboBoxFechaReparacionMes.addItem("Octubre");
		jComboBoxFechaReparacionMes.addItem("Noviembre");
		jComboBoxFechaReparacionMes.addItem("Diciembre");
		
		// Seteo los comboBox con la fecha actual
		jComboBoxFechaReparacionDia.setSelectedIndex(diaActual-1);
		jComboBoxFechaReparacionMes.setSelectedIndex(mesActual);
	}
	
	
	OperationDelegate aceptarAgregarReparacion = new OperationDelegate() {
		public void invoke() {
			aceptarAgregarReparacion();
		}
	};

	OperationDelegate cancelarAgregarReparacion = new OperationDelegate() {
		public void invoke() {
			cancelarAgregarReparacion();
		}
	};
	
	private void inicializarVariables() {	
		//Dimension dimensionLabel = new Dimension( 130, 25 );
		
		inicializarFecha();
		
		jLabelFechaReparacion = new JLabel( "Fecha: " );
		jLabelFechaReparacion.setPreferredSize( new Dimension (50, 25)); 
		
		jLabelDe1 = new JLabel( " de " );
		jLabelDe2 = new JLabel( " de " );
		
		jLabelImporte = new JLabel( "Importe: $ " );
		jLabelImporte.setEnabled(false);
		jTextFieldImporte = new JTextField("0,00");
		jTextFieldImporte.setEnabled(false);
		jTextFieldImporte.setPreferredSize( new Dimension( 80, 25 ) );
		jTextFieldImporte.setFont(new Font("Dialog", Font.BOLD, 11));
		
		jLabelKilometraje = new JLabel( "Kilometraje: " );
		jTextFieldKilometraje = new JTextFieldOfNumbers();
		jTextFieldKilometraje.setTransferHandler(null); // deshabilita copy/paste
		jTextFieldKilometraje.setText( " < 65000 >" );
		jTextFieldKilometraje.setForeground( Color.GRAY );
		jTextFieldKilometraje.setPreferredSize( new Dimension( 80, 25 ) );
		jTextFieldKilometraje.addFocusListener( new JTextFieldFocusListener( jTextFieldKilometraje ) );
		jTextFieldKilometraje.addKeyListener(new java.awt.event.KeyAdapter() {  
            // agrega la reparacion presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER)
                	aceptarAgregarReparacion();
                }
        }); 
		
		jLabelObservaciones = new JLabel( "Observaciones: " );
		jLabelObservaciones.setPreferredSize(new Dimension(309,25));

		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( aceptarAgregarReparacion ) );
		jButtonAceptar.addKeyListener( new JButtonKeyListener( aceptarAgregarReparacion ) );
		
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension(100,30) );
		jButtonCancelar.addActionListener( new JButtonActionListener( cancelarAgregarReparacion ) );
		jButtonCancelar.addKeyListener( new JButtonKeyListener( cancelarAgregarReparacion ) );
		
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			AgregarReparacion.this.dispose();
			}
		});
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana DetallarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  AgregarReparacion.this.dispose();
		      }
		    };
		jPanelAgregarReparacion.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void aceptarAgregarReparacion() {
		if( validarDatos() ) {
			int option = 0;
			int id_reparacion;
			Reparacion reparacion = new Reparacion();
			
			Calendar fechaReparacion = GregorianCalendar.getInstance();
			fechaReparacion.setLenient( false );
			fechaReparacion.set( Integer.parseInt( jComboBoxFechaReparacionAnio.getSelectedItem().toString() ) , jComboBoxFechaReparacionMes.getSelectedIndex(), Integer.parseInt( jComboBoxFechaReparacionDia.getSelectedItem().toString() ) );
			fechaReparacion.set( Calendar.MINUTE, 0 );
			fechaReparacion.set( Calendar.SECOND, 0 );
			fechaReparacion.set( Calendar.MILLISECOND, 0 );
			
			reparacion.setId_automovil(detallarAutomovil.automovil.getId_automovil());
			reparacion.setFechaReparacion(fechaReparacion );
			reparacion.setKilometraje(Integer.parseInt(jTextFieldKilometraje.getText().trim()));
			reparacion.setObservaciones(jTextAreaObservaciones.getText());
			
			ReparacionManager reparacionManager = new ReparacionManager();
			try {
				id_reparacion = reparacionManager.nextID();
				reparacion.setId_reparacion(id_reparacion);
				option = reparacionManager.agregar( reparacion );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return; // finaliza método
			}

			switch( option ) {
				case 1:
					cleanAllFields();
					this.dispose();
					
					detallarAutomovil.dispose(); // cierro la ventana detallarAutomovil
					// se debe detallar la reparación automáticamente luego de ser agregada por el usuario
					new DetallarReparacion( agregarReparacion, "Detallar reparación", " Detalle de la reparación ", reparacion);

					break;
				case 2:
					JOptionPane.showMessageDialog( null, "Ingrese una fecha válida", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					jComboBoxFechaReparacionDia.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jComboBoxFechaReparacionDia.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaReparacionDia, jComboBoxFechaReparacionMes, jComboBoxFechaReparacionAnio ) );
					jComboBoxFechaReparacionMes.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jComboBoxFechaReparacionMes.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaReparacionMes, jComboBoxFechaReparacionDia, jComboBoxFechaReparacionAnio ) );
					jComboBoxFechaReparacionAnio.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jComboBoxFechaReparacionAnio.addFocusListener( new JComboBoxFocusListener( jComboBoxFechaReparacionAnio, jComboBoxFechaReparacionMes, jComboBoxFechaReparacionDia ) );
					break;
				case 3:
					JOptionPane.showMessageDialog( null, "El kilometraje debe ser menor a 999.999", "Kilometraje no válido", JOptionPane.ERROR_MESSAGE );
					jTextFieldKilometraje.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldKilometraje.addFocusListener( new JTextFieldFocusListener( jTextFieldKilometraje ) );
					break;
				default:
					// este mensaje nunca debería aparecer
					JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	private void cancelarAgregarReparacion() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private boolean validarDatos() {
		int cont = 0;
		
		cont += isEmpty( jTextFieldKilometraje );
		
		cont += isEmpty( jComboBoxFechaReparacionDia, jComboBoxFechaReparacionMes, jComboBoxFechaReparacionAnio );
		cont += isEmpty( jComboBoxFechaReparacionMes, jComboBoxFechaReparacionDia, jComboBoxFechaReparacionAnio );
		cont += isEmpty( jComboBoxFechaReparacionAnio, jComboBoxFechaReparacionMes, jComboBoxFechaReparacionDia );
		
		if( jTextAreaObservaciones.getText().contains( "<" ) ) {
			jTextAreaObservaciones.setText( "" );
		}
		
		if( cont > 0 ){
			JOptionPane.showMessageDialog( this, "Uno o más campos obligatorios se encuentran vacíos", "Campo vacío", JOptionPane.ERROR_MESSAGE );
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
		jTextFieldKilometraje.setText( "" );
		jTextAreaObservaciones.setText( "" );
		
	}
}
