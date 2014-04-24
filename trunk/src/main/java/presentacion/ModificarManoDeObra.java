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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import negocio.IManoDeObraManager;
import negocio.IMecanicoManager;
import negocio.ManoDeObraManager;
import negocio.MecanicoManager;

import persistencia.ManoDeObra;
import persistencia.Mecanico;
import presentacion.JTextFieldOfLetters;

public class ModificarManoDeObra extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	DetallarServicio detallarServicio = null;
	
	JPanel jPanelModificarManoDeObra = null;

	JLabel jLabelNombre = null;
	JLabel jLabelPrecio = null;
	JLabel jLabelObservaciones = null;
	JLabel jLabelMecanico = null;

	JTextFieldOfLetters jTextFieldNombre = null;
	JFormattedTextField jTextFieldPrecio = null;
	JTextField jTextFieldObservaciones = null;
	JComboBox<String> jComboBoxMecanico = null;
	
	JTextArea jTextAreaObservaciones = null;
	JScrollPane jScrollPaneObservaciones = null;

	DefaultTableModel dtmManosDeObraCopia = null;
	
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	double amount;
	
    //Formats to format and parse numbers
    private NumberFormat amountDisplayFormat = null;
    private NumberFormat amountEditFormat = null;
    
    ManoDeObra manoDeObra = null;
    List<Mecanico> listaMecanicos = null;
	IMecanicoManager mecanicoManager = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	ModificarManoDeObra( JFrame padre, String titulo, String titleBorder, DetallarServicio detallarServicioPadre, ManoDeObra manoDeObra) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 380, 310 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		this.manoDeObra = manoDeObra;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		detallarServicio = detallarServicioPadre;
		
		jPanelModificarManoDeObra = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelModificarManoDeObra.setBorder( titledBorder );
		
		jPanelModificarManoDeObra.setLayout( new FlowLayout(FlowLayout.CENTER,0,15) );

		jPanelModificarManoDeObra.setPreferredSize(new Dimension (380, 280));
	
		inicializarVariables();
		cerrarEsc();
			
		jPanelModificarManoDeObra.add(jLabelMecanico);
		jPanelModificarManoDeObra.add(jComboBoxMecanico);
		
			JPanel jPanelPrecioMecanico = new JPanel();
			jPanelPrecioMecanico.setLayout( new GridBagLayout() );
			
			GridBagConstraints b = new GridBagConstraints();
			
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 0, 0, 0 );
			jPanelPrecioMecanico.add( jLabelNombre, b );
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, -5, 0, 0 );
			jPanelPrecioMecanico.add( jTextFieldNombre, b );
			b.gridy = 0;
			b.gridx = 2;
			b.insets = new Insets( 0, 10, 0, 0 );
			jPanelPrecioMecanico.add( jLabelPrecio, b );
			b.gridy = 0;
			b.gridx = 3;
			b.insets = new Insets( 0, -30, 0, 0 );
			jPanelPrecioMecanico.add( jTextFieldPrecio, b );
			
		jPanelModificarManoDeObra.add( jPanelPrecioMecanico);
			
			JPanel jPanelObservaciones = new JPanel();
			jPanelObservaciones.setLayout( new FlowLayout(FlowLayout.LEFT,0,0));
			jPanelObservaciones.setPreferredSize(new Dimension(342, 80));
			jPanelObservaciones.add(jLabelObservaciones);
			jPanelObservaciones.add(jScrollPaneObservaciones);
			
		jPanelModificarManoDeObra.add( jPanelObservaciones);

			JPanel jPanelBotones = new JPanel();
			jPanelBotones.setLayout( new GridBagLayout() );
			
			
			GridBagConstraints d = new GridBagConstraints();
			d.anchor = GridBagConstraints.EAST;
			
			d.gridy = 0;
			d.gridx = 0;
			d.insets = new Insets( 0, 120, 0, 0 );
			jPanelBotones.add( jButtonAceptar, d );
			d.gridy = 0;
			d.gridx = 1;
			d.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotones.add( jButtonCancelar, d );

		jPanelModificarManoDeObra.add( jPanelBotones );

		jPanelModificarManoDeObra.add( new JLabel(" "));
			
		this.getContentPane().add( jPanelModificarManoDeObra, BorderLayout.NORTH );
		
		this.setResizable( false );
		this.pack();
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ModificarManoDeObra.this.dispose();
			}
		};
		jPanelModificarManoDeObra.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	OperationDelegate modificarAgregarManoDeObra = new OperationDelegate() {
		public void invoke() {
			modificarManoDeObra();
		}
	};

	OperationDelegate cancelarModificarManoDeObra = new OperationDelegate() {
		public void invoke() {
			cancelarModificarManoDeObra();
		}
	};
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 75, 25 );
		mecanicoManager = new MecanicoManager();
		// hace que se seleccione el jComboBoxMecanico al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jComboBoxMecanico.requestFocusInWindow();
			}
		});
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( new Dimension( 57, 25 ) );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setPreferredSize( new Dimension( 170, 25 ) ); // 204, 25
		jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
		jTextFieldNombre.addKeyListener( new JTextFieldKeyListener( jTextFieldNombre ) );
		jTextFieldNombre.setText(manoDeObra.getNombre());
		
		jTextAreaObservaciones = new JTextArea();
		jTextAreaObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		((AbstractDocument) jTextAreaObservaciones.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		jTextAreaObservaciones.setFocusTraversalKeysEnabled(false); 
		jTextAreaObservaciones.setLineWrap(true);
		jTextAreaObservaciones.setWrapStyleWord(true);
		jScrollPaneObservaciones = new JScrollPane(jTextAreaObservaciones);
		jScrollPaneObservaciones.setPreferredSize(new Dimension( 342, 50 ));
		jTextAreaObservaciones.setText(manoDeObra.getObservaciones());
		
		setUpFormats();
		jLabelPrecio = new JLabel( "Precio: " );
		jLabelPrecio.setPreferredSize( dimensionLabel );
		jTextFieldPrecio = new JFormattedTextField(
        				new DefaultFormatterFactory(
        						new NumberFormatter(amountDisplayFormat),
        						new NumberFormatter(amountDisplayFormat),
        						new NumberFormatter(amountEditFormat)));
		
		jTextFieldPrecio.setValue( manoDeObra.getPrecio() );
		jTextFieldPrecio.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextFieldPrecio.setPreferredSize( new Dimension( 65, 25 ) );
		jTextFieldPrecio.addKeyListener(new java.awt.event.KeyAdapter() {  
            // selecciona el boton aceptar cuando se presiona Enter en el jTextFieldPrecio, para que parsee de double a string
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
            		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            			public void run() {
            				jButtonAceptar.requestFocusInWindow();
            			}
            		});
                }
                // cambia el punto (del teclado numerico) por la coma
                if (key == 110) {
            		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            			public void run() {
            				jTextFieldPrecio.setText((jTextFieldPrecio.getText()).substring(0, jTextFieldPrecio.getText().length()-1) + ",");
            			}
            		});
                }
            }
        }); 

		jLabelObservaciones = new JLabel( "Observaciones: " );
		jLabelObservaciones.setPreferredSize( new Dimension( 100, 25) );

		jLabelMecanico = new JLabel( "ID Mecánico: " );
		jLabelMecanico.setPreferredSize( new Dimension( 80, 45 ) );

		cargarMecanicos();
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( modificarAgregarManoDeObra ) );
		jButtonAceptar.addKeyListener( new JButtonKeyListener( modificarAgregarManoDeObra ) );

		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new JButtonActionListener( cancelarModificarManoDeObra ) );
		jButtonCancelar.addKeyListener( new JButtonKeyListener( cancelarModificarManoDeObra ) );
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			ModificarManoDeObra.this.dispose();
			}
		});

	}
	
	   //Create and set up number formats. These objects also
    //parse numbers input by user.
    private void setUpFormats() {
        amountDisplayFormat = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        amountEditFormat = NumberFormat.getNumberInstance(new Locale("es", "AR"));
    }
	
	private void modificarManoDeObra() {
		int option = 0;
		if( validarDatos() && characterVaryingExceeded() ) {
			
			double precio; // almacena el precio de la mano de obra una vez presionado el boton Aceptar
			precio = ((Number)jTextFieldPrecio.getValue()).doubleValue();
			manoDeObra.setNombre( jTextFieldNombre.getText().trim() );
			manoDeObra.setPrecio( precio );
			manoDeObra.setObservaciones(jTextAreaObservaciones.getText());
			if(jComboBoxMecanico.getSelectedIndex() == 0)
				manoDeObra.setId_mecanico(0); // 0 (cero) significa que no se especifica mecanico
			else{
				manoDeObra.setId_mecanico(listaMecanicos.get(jComboBoxMecanico.getSelectedIndex() - 1).getId_mecanico());
			}

			IManoDeObraManager manoDeObraManager = new ManoDeObraManager();
			try {
				option = manoDeObraManager.modificar( manoDeObra );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
	
			switch( option ) {
				case 1:
					actualizarDetallarServicio();
					
					cleanAllFields();
					this.dispose();
					
					break;
				case 2:
					JOptionPane.showMessageDialog( this, "El precio debe ser mayor a cero", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );
					jTextFieldPrecio.setBorder( BorderFactory.createLineBorder( Color.RED ) );
					jTextFieldPrecio.addFocusListener( new JTextFieldFocusListener( jTextFieldPrecio ) );
					
					break;
				case 3:
					JOptionPane.showMessageDialog( null, "La mano de obra no se encuentra en la base de datos", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );

					break;
				default:
					// este mensaje nunca debería aparecer
					JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	
	private void actualizarDetallarServicio(){
		int filaSeleccionada = detallarServicio.jTableManosDeObra.getSelectedRow();

		detallarServicio.dtmManosDeObra.setValueAt(manoDeObra.getNombre(), filaSeleccionada, 1);
		detallarServicio.dtmManosDeObra.setValueAt(jComboBoxMecanico.getSelectedIndex() == 0 ? "< sin especificar >" : (listaMecanicos.get(jComboBoxMecanico.getSelectedIndex() - 1).getNombre() + " " + listaMecanicos.get(jComboBoxMecanico.getSelectedIndex() - 1).getApellido()), filaSeleccionada, 2);
		detallarServicio.dtmManosDeObra.setValueAt((manoDeObra.getObservaciones().isEmpty() ? "" : "\u2713"),filaSeleccionada ,3);
		detallarServicio.dtmManosDeObra.setValueAt(amountDisplayFormat.format(manoDeObra.getPrecio()), filaSeleccionada, 4);
	
	}
	
	private void cancelarModificarManoDeObra() {
		cleanAllFields();
		
		this.dispose();
	}
	
	private boolean characterVaryingExceeded(){
		int cont = 0;

		if(jTextFieldNombre.getText().trim().length() > 25){
			jTextFieldNombre.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
			cont++;
		}
		
		if(jTextFieldPrecio.getText().trim().length() > 10){
			jTextFieldPrecio.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldPrecio.addFocusListener( new JTextFieldFocusListener( jTextFieldPrecio ) );
			cont++;
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
		
		if( jTextFieldNombre.getText().contains( "<" ) ) {
			jTextFieldNombre.setText( "" );
		}
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldPrecio );
		
		for( JTextField jTextField : jTextFields )
			cont += isEmpty( jTextField );
		
		if( jTextAreaObservaciones.getText().contains( "<" ) ) {
			jTextAreaObservaciones.setText( "" );
		}
		
		if( cont > 0 ){
			JOptionPane.showMessageDialog( this, "Uno o más campos se encuentran excediendo el tamaño máximo permitido", "Campo excedido", JOptionPane.ERROR_MESSAGE );
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
		jTextFieldPrecio.setText( "" );
		jTextAreaObservaciones.setText( "" );
		
	}
	
	private void cargarMecanicos() {
		int index = 0;
		int counter = 0;
		jComboBoxMecanico = new JComboBox<String>();
		jComboBoxMecanico.setFont(new Font("Dialog", Font.BOLD, 11));
		jComboBoxMecanico.setPreferredSize(new Dimension(262,25));
		jComboBoxMecanico.addItem("< sin especificar >");
		
		try {
			listaMecanicos = mecanicoManager.listaMecanicos();
			Iterator<Mecanico> iterator = listaMecanicos.iterator();
			Mecanico mecanico = null;
			while (iterator.hasNext()){
				mecanico = iterator.next();
				counter++;
				if(mecanico.getId_mecanico() == manoDeObra.getId_mecanico())
					index = counter;
				jComboBoxMecanico.addItem(String.format("%02d", mecanico.getId_mecanico()) + " " + mecanico.getNombre() + " " + mecanico.getApellido() );
			}
			
			// seleccionando el mecanico correspondiente...
			jComboBoxMecanico.setSelectedIndex(index);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, "Error al cargar la lista de mecánicos", "Error", JOptionPane.ERROR_MESSAGE );
		}		
	}
}
