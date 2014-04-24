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
import java.text.ParseException;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import negocio.IProveedorManager;
import negocio.IRepuestoManager;
import negocio.ProveedorManager;
import negocio.RepuestoManager;

import persistencia.Proveedor;
import persistencia.Repuesto;

public class ModificarRepuesto extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	DetallarServicio detallarServicio = null;
	
	JPanel jPanelModificarRepuesto = null;

	JLabel jLabelNombre = null;
	JLabel jLabelPrecioUnitario = null;
	JLabel jLabelCantidad = null;
	JLabel jLabelObservaciones = null;
	JLabel jLabelProveedor = null;
	
	JTextFieldUpperCased jTextFieldNombre = null;
	JFormattedTextField jTextFieldPrecioUnitario = null;
	JTextFieldOfNumbers jTextFieldCantidad = null;
	JComboBox<String> jComboBoxProveedor = null;
	
	JTextArea jTextAreaObservaciones = null;
	JScrollPane jScrollPaneObservaciones = null;
	
	JScrollBar jscrollbar = null;
	
	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;
	
	Repuesto repuesto = null;
	List<Proveedor> listaProveedores = null;
	IProveedorManager proveedorManager = null;
	
    //Formats to format and parse numbers
    private NumberFormat amountDisplayFormat = null;
    private NumberFormat amountEditFormat = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();

	ModificarRepuesto( JFrame padre, String titulo, String titleBorder, DetallarServicio detallarServicioPadre, Repuesto repuesto ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 418, 348 ) );
		this.getContentPane().setLayout( new BorderLayout() );
		this.repuesto = repuesto;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		detallarServicio = detallarServicioPadre;
		
		jPanelModificarRepuesto = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelModificarRepuesto.setBorder( titledBorder );
		
		jPanelModificarRepuesto.setLayout( new FlowLayout(FlowLayout.RIGHT, 0,10) );
		jPanelModificarRepuesto.setPreferredSize(new Dimension (410, 319));
		
		inicializarVariables();
		cerrarEsc();
			
			JPanel jPanelNombre = new JPanel();
			jPanelNombre.setLayout( new FlowLayout(FlowLayout.CENTER, 0,20) );
			jPanelNombre.setPreferredSize(new Dimension (400,100));
			jPanelNombre.add( jLabelProveedor);
			jPanelNombre.add( jComboBoxProveedor);
			jPanelNombre.add( jLabelNombre);
			jPanelNombre.add( jTextFieldNombre);
		
		jPanelModificarRepuesto.add( jPanelNombre);
		
			JPanel jPanelPrecioCantidadProveedor = new JPanel();
			jPanelPrecioCantidadProveedor.setLayout( new GridBagLayout() );
			jPanelPrecioCantidadProveedor.setPreferredSize(new Dimension (400,30));
			GridBagConstraints b = new GridBagConstraints();
			
			b.gridy = 0;
			b.gridx = 2;
			b.insets = new Insets( 0, -127, 0, 0 );
			jPanelPrecioCantidadProveedor.add( jLabelPrecioUnitario, b );
			b.gridy = 0;
			b.gridx = 3;
			b.insets = new Insets( 0, -50, 0, 0 );
			jPanelPrecioCantidadProveedor.add( jTextFieldPrecioUnitario, b );
			b.gridy = 0;
			b.gridx = 4;
			b.insets = new Insets( 0, 20, 0, 0 );
			jPanelPrecioCantidadProveedor.add( jLabelCantidad, b );
			b.gridy = 0;
			b.gridx = 5;
			b.insets = new Insets( 0, -18, 0, 0 );
			jPanelPrecioCantidadProveedor.add( jTextFieldCantidad, b );
		
		jPanelModificarRepuesto.add( jPanelPrecioCantidadProveedor);
		jPanelModificarRepuesto.add( jLabelObservaciones);
			
			JPanel jPanelObservaciones = new JPanel();
			jPanelObservaciones.setLayout( new FlowLayout(FlowLayout.CENTER,0,0));
			jPanelObservaciones.setPreferredSize(new Dimension(400, 80));
			jPanelObservaciones.add(jLabelObservaciones);
			jPanelObservaciones.add(jScrollPaneObservaciones);
		
		jPanelModificarRepuesto.add( jPanelObservaciones );

			JPanel jPanelBotones = new JPanel();
			jPanelBotones.setLayout( new GridBagLayout() );
			
			GridBagConstraints d = new GridBagConstraints();
			
			d.gridy = 0;
			d.gridx = 0;
			d.insets = new Insets( 0, 0, 0, 0 );
			jPanelBotones.add( jButtonAceptar, d );
			d.gridy = 0;
			d.gridx = 1;
			d.insets = new Insets( 0, 20, 0, 12 );
			jPanelBotones.add( jButtonCancelar, d );

		jPanelModificarRepuesto.add( jPanelBotones);
			
		this.getContentPane().add( jPanelModificarRepuesto, BorderLayout.NORTH );
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
				ModificarRepuesto.this.dispose();
			}
		};
		jPanelModificarRepuesto.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	OperationDelegate modificarAgregarRepuesto = new OperationDelegate() {
		public void invoke() {
			modificarRepuesto();
		}
	};

	OperationDelegate cancelarModificarRepuesto = new OperationDelegate() {
		public void invoke() {
			cancelarModificarRepuesto();
		}
	};
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 75, 25 );
		proveedorManager = new ProveedorManager();
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( new Dimension( 53, 25 ) );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setText( repuesto.getNombre() );
		jTextFieldNombre.setPreferredSize( new Dimension( 327, 25 ) );
		
		setUpFormats();
		jLabelPrecioUnitario = new JLabel( "Precio unitario: " );
		jLabelPrecioUnitario.setPreferredSize( new Dimension( 140, 25 ) );
		jTextFieldPrecioUnitario = new JFormattedTextField(
				new DefaultFormatterFactory(
						new NumberFormatter(amountDisplayFormat),
						new NumberFormatter(amountDisplayFormat),
						new NumberFormatter(amountEditFormat)));
		jTextFieldPrecioUnitario.setValue( repuesto.getPrecioUnitario() );
		jTextFieldPrecioUnitario.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextFieldPrecioUnitario.setPreferredSize( new Dimension( 65, 25 ) );
		jTextFieldPrecioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {  
			// selecciona el boton aceptar cuando se presiona Enter en el jTextFieldPrecio, para que parsee de double a string
			public void keyPressed(java.awt.event.KeyEvent e) {
				int key = e.getKeyCode();
				// System.out.println( "KeyCod: " + key);
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
							jTextFieldPrecioUnitario.setText((jTextFieldPrecioUnitario.getText()).substring(0, jTextFieldPrecioUnitario.getText().length()-1) + ",");
						}
					});
				}
			}
		}); 
		
		jLabelCantidad = new JLabel( "Cantidad: " );
		jLabelCantidad.setPreferredSize( dimensionLabel );
		jTextFieldCantidad = new JTextFieldOfNumbers();
		jTextFieldCantidad.setText( Integer.toString(repuesto.getCantidad()) );
		jTextFieldCantidad.setPreferredSize( new Dimension( 27, 25 ) );
		
		jLabelObservaciones = new JLabel( "Observaciones: " );
		jLabelObservaciones.setPreferredSize( new Dimension( 380, 25) );

		jTextAreaObservaciones = new JTextArea();
		jTextAreaObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		((AbstractDocument) jTextAreaObservaciones.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		jTextAreaObservaciones.setFocusTraversalKeysEnabled(false);  
		jTextAreaObservaciones.setLineWrap(true);
		jTextAreaObservaciones.setWrapStyleWord(true);
		jTextAreaObservaciones.setText( repuesto.getObservaciones() );
		
		jScrollPaneObservaciones = new JScrollPane(jTextAreaObservaciones);
		jScrollPaneObservaciones.setPreferredSize(new Dimension( 380, 50 ));
		
		jLabelProveedor = new JLabel( "Proveedor: " );
		jLabelProveedor.setPreferredSize( new Dimension( 68, 25) );
		
		cargarProveedores();
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar",  imageIconOk);
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new JButtonActionListener( modificarAgregarRepuesto ) );
		jButtonAceptar.addKeyListener( new JButtonKeyListener( modificarAgregarRepuesto ) );
			
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			ModificarRepuesto.this.dispose();
			}
		});
	}
	
    private void setUpFormats() {
        amountDisplayFormat = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
//        amountDisplayFormat.setMinimumFractionDigits(2);
        amountEditFormat = NumberFormat.getNumberInstance(new Locale("es", "AR"));
    }
    
    private boolean characterVaryingExceeded(){
		int cont = 0;

		if(jTextFieldNombre.getText().trim().length() > 25){
			jTextFieldNombre.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
			cont++;
		}
		
		if(jTextFieldPrecioUnitario.getText().trim().length() > 10){
			jTextFieldPrecioUnitario.setBorder( BorderFactory.createLineBorder( Color.RED ) );
			jTextFieldPrecioUnitario.addFocusListener( new JTextFieldFocusListener( jTextFieldPrecioUnitario ) );
			cont++;
		}
		
		if( cont > 0 )
		{
			JOptionPane.showMessageDialog( this, "Uno o más campos se encuentran excediendo el tamaño máximo permitido", "Campo excedido", JOptionPane.ERROR_MESSAGE );
			return false;
		}
		return true;
	
	}
    
    private void modificarRepuesto() {
		
    	int option = 0;
		if( validarDatos() && characterVaryingExceeded() ) {
			double precio_unitario = 0;
			NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
			Number number = null;
			try{
				number = formatter.parse(jTextFieldPrecioUnitario.getText().trim());
			}
			catch (ParseException e)  
			{  
				System.out.print(e);  
			}
			precio_unitario += number.doubleValue();		
			
			// almacena el precio total del repuesto/s una vez presionado el boton Aceptar
			repuesto.setNombre( jTextFieldNombre.getText().trim() );
			repuesto.setPrecioUnitario( precio_unitario );
			repuesto.setCantidad(Integer.parseInt(jTextFieldCantidad.getText().trim()));
			repuesto.setObservaciones(jTextAreaObservaciones.getText());
			
			if(jComboBoxProveedor.getSelectedIndex() == 0)
				repuesto.setId_proveedor(0); // 0 (cero) significa que no se especifica mecanico
			else{
				repuesto.setId_proveedor(listaProveedores.get(jComboBoxProveedor.getSelectedIndex() - 1).getId_proveedor());
			}

			IRepuestoManager repuestoManager = new RepuestoManager();
			try {
				option = repuestoManager.modificar( repuesto );
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
				jTextFieldPrecioUnitario.setBorder( BorderFactory.createLineBorder( Color.RED ) );
				jTextFieldPrecioUnitario.addFocusListener( new JTextFieldFocusListener( jTextFieldPrecioUnitario ) );
				
				break;
			case 3:
				JOptionPane.showMessageDialog( null, "El repuesto no se encuentra en la base de datos", "Campo mal ingresado", JOptionPane.ERROR_MESSAGE );

				break;
			default:
				// este mensaje nunca debería aparecer
				JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		
			}
		}
	}
    
    private void actualizarDetallarServicio(){
		int filaSeleccionada = detallarServicio.jTableRepuestos.getSelectedRow();

		detallarServicio.dtmRepuestos.setValueAt(repuesto.getNombre(), filaSeleccionada, 1);
		detallarServicio.dtmRepuestos.setValueAt(jComboBoxProveedor.getSelectedIndex() == 0 ? "< sin especificar >" : (listaProveedores.get(jComboBoxProveedor.getSelectedIndex() - 1).getNombre() ), filaSeleccionada, 2);
		detallarServicio.dtmRepuestos.setValueAt(amountDisplayFormat.format(repuesto.getPrecioUnitario()), filaSeleccionada, 3);
		detallarServicio.dtmRepuestos.setValueAt(Integer.toString(repuesto.getCantidad()), filaSeleccionada, 4);
		detallarServicio.dtmRepuestos.setValueAt((repuesto.getObservaciones().isEmpty() ? "" : "\u2713"),filaSeleccionada ,5);
		detallarServicio.dtmRepuestos.setValueAt(amountDisplayFormat.format(repuesto.getPrecioUnitario() * repuesto.getCantidad()), filaSeleccionada, 6);
	
	}
	
	private void cancelarModificarRepuesto() {
		cleanAllFields();
		
		this.dispose();
	}
	
	String conObservaciones(){
		if(jTextAreaObservaciones.getText().equals(""))
			return "";
		else{
			int cc = 2713;
			char ccc = (char) Integer.parseInt(String.valueOf(cc), 16);
			return String.valueOf(ccc);
		}
	}
	
	private boolean validarDatos() {
		int cont = 0;
		
		List<JTextField> jTextFields = new ArrayList<JTextField>();
		
		if( jTextFieldNombre.getText().contains( "<" ) ) {
			jTextFieldNombre.setText( "" );
		}
		
		jTextFields.add( jTextFieldNombre );
		jTextFields.add( jTextFieldPrecioUnitario );
		
		for( JTextField jTextField : jTextFields )
			cont += isEmpty( jTextField );
		
		if( jTextAreaObservaciones.getText().contains( "<" ) ) {
			jTextAreaObservaciones.setText( "" );
		}
		
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
		jTextFieldPrecioUnitario.setText( "" );
		jTextAreaObservaciones.setText( "" );
		
	}

	private void cargarProveedores() {
		int index = 0;
		int counter = 0;
		jComboBoxProveedor = new JComboBox<String>();
		jComboBoxProveedor.setFont(new Font("Dialog", Font.BOLD, 11));
		jComboBoxProveedor.setPreferredSize(new Dimension(312,25));
		jComboBoxProveedor.addItem("< sin especificar >");
		
		try {
			listaProveedores = proveedorManager.listaProveedores();
			Iterator<Proveedor> iterator = listaProveedores.iterator();
			Proveedor proveedor = null;
			while (iterator.hasNext()){
				proveedor = iterator.next();
				counter++;
				if(proveedor.getId_proveedor() == repuesto.getId_proveedor())
					index = counter;
				jComboBoxProveedor.addItem( proveedor.getNombre() );
			}
			
			// seleccionando el proveedor correspondiente...
			jComboBoxProveedor.setSelectedIndex(index);
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, "Error al cargar la lista de proveedores", "Error", JOptionPane.ERROR_MESSAGE );
			return;
		}
		
	}
}
