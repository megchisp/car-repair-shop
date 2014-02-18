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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import negocio.AutomovilManager;
import negocio.ClienteManager;
import negocio.IAutomovilManager;

import persistencia.Automovil;
import persistencia.Cliente;

public class DetallarCliente extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame detallarCliente;
	DetallarCliente detallarClienteItSelf = this;
	
	List<Automovil> listaAutomoviles = null;
		
	BuscarCliente buscarCliente = null;

	JLabel jLabelCUIT = null;
	JLabel jLabelNombreApellido = null;
	JLabel jLabelTelefono = null;
	JLabel jLabelCelular = null;
	JLabel jLabelDomicilio = null;
	JLabel jLabelLocalidad = null;
	JLabel jLabelCodigoPostal = null;
	JLabel jLabelFechaNacimiento = null;
	JLabel jLabelEmail = null;

	JButton jButtonModificar = null;
	JButton jButtonEliminar = null;
	JButton jButtonAgregarAutomovil = null;
	JButton jButtonDetallarAutomovil = null;
	JButton jButtonCerrar = null;
	
	JToggleButton jToggleButtonObservaciones = null;
	
	JTable tablaAutomovilesClientes = null;
	JScrollPane scrollAutomovilesClientes = null;
	
	JPanel jPanelAutomovilesCliente = null; // contiene tablaAutomovilesClientes
	JPanel jPanelDetalleCliente = null;
	JPanel jPanelObservaciones = null; // este panel se intercambiará con jPanelDatosPersonales2
	JPanel jPanelDatosPersonales2 = null;
	
	/* Estos strings deben ser obtenidos desde la BD */
	String stringNacimientoCliente = null;
	String stringFallecido = null;
	String stringApellidoCliente = null;
	String stringNombreCliente = null;
	String stringCuitCliente = null;
	String stringTelefonoCliente = null;
	String stringCelularCliente = null;
	String stringDomicilioCliente = null;
	String stringLocalidadCliente = null;
	String stringCodigoPostalCliente = null;
	String stringEmailCliente = null;
	String stringObservacionesCliente = null;
	
	Cliente cliente;
	
	JTextPane jTextPaneObservaciones = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	public DetallarCliente( JFrame padre, String titulo, String titleBorder, Cliente cliente) {
		super( padre, titulo, true );
						
		this.setPreferredSize( new Dimension( 620, 580 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		this.cliente = cliente;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		/* Panel exterior */
		jPanelDetalleCliente = new JPanel();
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelDetalleCliente.setBorder( titledBorder );
		jPanelDetalleCliente.setPreferredSize(new Dimension(610, 550));
		/* fin panel exterior */
		jPanelDetalleCliente.setLayout(new GridBagLayout());
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		
		inicializarVariables();
		inicializarPanelAutomoviles();
		inicializarPanelObservaciones();
		enableDisableObservaciones();
		enableDisableButtons();
		cerrarEsc();

			/* Panel interior donde se muestran los datos personales del cliente */
			JPanel jPanelDatosPersonalesCliente = new JPanel();
			jPanelDatosPersonalesCliente.setBorder(BorderFactory.createTitledBorder(null, "Datos personales del cliente", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelDatosPersonalesCliente.setPreferredSize(new Dimension(590, 225));
			
			jPanelDatosPersonalesCliente.setLayout( new FlowLayout(2, 0, 0));
		
		
				JPanel jPanelApellidoNombre = new JPanel();
				jPanelApellidoNombre.setPreferredSize(new Dimension(570, 45));
				jPanelApellidoNombre.setLayout( new GridBagLayout() );
				
				GridBagConstraints h = new GridBagConstraints();
				h.anchor = GridBagConstraints.NORTH;
				h.gridy = 0;
				h.gridx = 0;
				//h.insets = new Insets( 0, 0, 0, 90 );
				jPanelApellidoNombre.add( jLabelNombreApellido, h );
				
				/* fin panel interior */
	
			jPanelDatosPersonalesCliente.add( jPanelApellidoNombre );
			
			jPanelDatosPersonales2.setPreferredSize(new Dimension(570, 110));
			
			jPanelDatosPersonales2.setLayout( new GridBagLayout() );
			GridBagConstraints z = new GridBagConstraints();
			z.anchor = GridBagConstraints.WEST;
			z.ipady = 5;
				z.gridy = 2;
				z.gridx = 0;
				z.insets = new Insets( 0, 0, 0, 50 );
				jPanelDatosPersonales2.add( jLabelFechaNacimiento, z );
				z.gridy = 2;
				z.gridx = 1;
				jPanelDatosPersonales2.add( jLabelCUIT, z );
				z.gridy = 3;
				z.gridx = 0;
//				jPanelDatosPersonales2.add( new JLabel(" "), z );
				z.gridy = 4;
				z.gridx = 0;
				jPanelDatosPersonales2.add( jLabelTelefono, z );
				z.gridy = 4;
				z.gridx = 1;
				jPanelDatosPersonales2.add( jLabelCelular, z );
				z.gridy = 5;
				z.gridx = 0;
//				jPanelDatosPersonales2.add( new JLabel(" "), z );
				z.gridy = 6;
				z.gridx = 0;
				jPanelDatosPersonales2.add( jLabelDomicilio, z );
				z.gridy = 6;
				z.gridx = 1;
				jPanelDatosPersonales2.add( jLabelLocalidad, z );
				z.gridy = 7;
				z.gridx = 0;
//				jPanelDatosPersonales2.add( new JLabel(" "), z );
				z.gridy = 8;
				z.gridx = 0;
				jPanelDatosPersonales2.add( jLabelEmail, z );

				jPanelDatosPersonalesCliente.add(jPanelDatosPersonales2); // se muestra
				jPanelDatosPersonalesCliente.add(jPanelObservaciones); // uno o el otro, nunca ambos
				
					// se crea un panel que contiene los botones de Modificar Cliente y Eliminar Cliente
					JPanel jPanelBotonesInterior = new JPanel(new FlowLayout(0, 20, 0));
					jPanelBotonesInterior.setPreferredSize(new Dimension(560, 30));
					jPanelBotonesInterior.add( jButtonModificar );
					jPanelBotonesInterior.add( jButtonEliminar );
					jPanelBotonesInterior.add(new JLabel(" "));
					jPanelBotonesInterior.add(new JLabel(" "));
					jPanelBotonesInterior.add( jToggleButtonObservaciones );
				
			z.gridy = 10;
			z.gridx = 0;
			jPanelDatosPersonalesCliente.add( jPanelBotonesInterior, z );
			z.gridy = 11;
			z.gridx = 0;
			jPanelDatosPersonalesCliente.add( new JLabel(" "), z );
					
		a.gridy = 1;
		a.gridx = 0;
		jPanelDetalleCliente.add( jPanelDatosPersonalesCliente , a);
		a.gridy = 2;
		a.gridx = 0;
		jPanelDetalleCliente.add( new JLabel(" "), a);
		a.gridy = 3;
		a.gridx = 0;
		jPanelDetalleCliente.add( jPanelAutomovilesCliente, a);
		a.gridy = 4;
		a.gridx = 0;
		jPanelDetalleCliente.add( new JLabel(" "), a);
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDetalleCliente.add( jButtonCerrar, a);
					
		this.getContentPane().add( jPanelDetalleCliente, BorderLayout.NORTH );
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
				DetallarCliente.this.dispose();
			}
		};
		jPanelDetalleCliente.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
		
	private void inicializarPanelAutomoviles(){
		jPanelAutomovilesCliente = new JPanel();
		
//		/* Panel interior donde se muestran los automóviles que tiene el cliente */
		jPanelAutomovilesCliente.setPreferredSize(new Dimension(590, 220));
		jPanelAutomovilesCliente.setLayout( new GridBagLayout() );
		/* fin panel interior */

		extraerInfoBD();
		enableDisableButtons();
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		c.gridy = 0;
		c.gridx = 0;
		jPanelAutomovilesCliente.add( scrollAutomovilesClientes, c );
		
			// se crea un panel que contiene los botones de Agregar Automóvil y Detallar automóvil
			JPanel jPanelBotones = new JPanel(new GridBagLayout());
			GridBagConstraints g = new GridBagConstraints();
			g.anchor = GridBagConstraints.WEST;
			
			g.gridy = 0;
			g.gridx = 0;
			jPanelBotones.add( jButtonAgregarAutomovil );
			g.gridy = 0;
			g.gridx = 1;
			g.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotones.add( jButtonDetallarAutomovil, g );
			
		c.gridy = 1;
		c.gridx = 0;
		jPanelAutomovilesCliente.add( new JLabel(" "), c );
		c.gridy = 2;
		c.gridx = 0;
		jPanelAutomovilesCliente.add( jPanelBotones, c );
	}
	
	private void inicializarPanelObservaciones(){
		/* INICIO inicializacion panel que muestra las observaciones adicionales */
		jPanelObservaciones = new JPanel();
		jPanelObservaciones.setPreferredSize(new Dimension(570, 110));
		jPanelObservaciones.setLayout(new FlowLayout(FlowLayout.CENTER,0,0) );
		
		JLabel jLabelObservaciones = new JLabel( "Observaciones adicionales: " );
		jLabelObservaciones.setFont(new Font(jLabelObservaciones.getFont().getName(), Font.PLAIN, 12));
		jLabelObservaciones.setPreferredSize(new Dimension(515,20));
		
		jTextPaneObservaciones = new JTextPane();
		jTextPaneObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextPaneObservaciones.setEditable(false); // no se puede editar
		jTextPaneObservaciones.setText(stringObservacionesCliente); // string de ejemplo
		JScrollPane jScrollPaneObservaciones = new JScrollPane(jTextPaneObservaciones);
		jScrollPaneObservaciones.setPreferredSize(new Dimension( 515, 70 ));
		
		jPanelObservaciones.add(jLabelObservaciones);
		jPanelObservaciones.add(jScrollPaneObservaciones);
		
		jPanelObservaciones.setVisible(false); // por que por defecto está oculto
		
		/* FIN inicializacion panel que muestra las observaciones adicionales */
		
	}
	
	void completar_tabla(Iterator<Automovil> iterator){
		/** Este método completa la tabla con la lista de automóviles **/
		Automovil automovil;
		int numero_fila = 0;
		while (iterator.hasNext()){
			automovil = iterator.next();
			String id_fila = Integer.toString(numero_fila++);
			String id_automovil = Integer.toString(automovil.getId_automovil());
			String marca = automovil.getMarca();
			String modelo = automovil.getModelo();
			String dominio = automovil.getDominio();
			String combustible = automovil.getTipoCombustible();
			Object [] fila= {id_automovil, dominio, marca, modelo, combustible, id_fila};
			dtmAutomovilesClientes.addRow(fila);
		}
	}
	
	
	private void extraerInfoBD(){
		IAutomovilManager automovilManager = new AutomovilManager();

		try {
			listaAutomoviles = automovilManager.listaAutomoviles(cliente); // obtengo todos los automoviles de la BD
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		completar_tabla(listaAutomoviles.iterator()); // completa la tabla con la lista de clientes
	}
	
	public void enableDisableObservaciones(){
		// esta funciona habilita o deshabilita el jToggleButtonObservaciones
		// dependiendo si tiene o no contenido para mostrar
		
		if(cliente.getObservaciones().isEmpty()){
			jPanelDatosPersonales2.setVisible(true);
			jPanelObservaciones.setVisible(false);
			jToggleButtonObservaciones.setEnabled(false);
			jToggleButtonObservaciones.setSelected(false); // por si queda presionado
			jToggleButtonObservaciones.setToolTipText("No hay observaciones para mostrar");
		}
		else{
			jToggleButtonObservaciones.setEnabled(true);
			jToggleButtonObservaciones.setToolTipText("Mostrar observaciones");
		}
	}
	
	@SuppressWarnings("deprecation")
	private void inicializarVariables() {
		Date date = new java.sql.Date(	cliente.getFechaDeNacimiento().getTimeInMillis());
	
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd 'de' MMMMMMMMMM 'de' yyyy", new Locale("es", "ARG"));
		stringNacimientoCliente = sdf.format(date);
		if(date.getYear() == 0)
			stringNacimientoCliente = stringNacimientoCliente.substring(0, stringNacimientoCliente.length()-8);
		stringFallecido = cliente.isFallecido() ? " (\u271D)" : "";
		stringApellidoCliente = cliente.getApellido();
		stringNombreCliente = cliente.getNombre();
		stringCuitCliente = cliente.getCUIT();
		stringTelefonoCliente = cliente.getTelefono();
		stringCelularCliente = cliente.getCelular();
		stringDomicilioCliente = cliente.getDomicilio();
		stringLocalidadCliente = cliente.getLocalidad();
		stringCodigoPostalCliente = cliente.getCodigoPostal();
		stringEmailCliente = cliente.getEmail();
		stringObservacionesCliente = cliente.getObservaciones();
		
		jPanelDatosPersonales2 = new JPanel();
		
		// inicializo JLabels
		jLabelFechaNacimiento = new JLabel();
		jLabelNombreApellido = new JLabel();
		jLabelDomicilio = new JLabel();
		jLabelCUIT = new JLabel();
		jLabelTelefono = new JLabel();
		jLabelCelular = new JLabel();
		jLabelLocalidad = new JLabel();
		jLabelEmail = new JLabel();
		
		jLabelNombreApellido.setText( stringApellidoCliente + ", " + stringNombreCliente + stringFallecido );
		jLabelNombreApellido.setFont(new Font("Dialog", Font.BOLD, 20));
		
		completarJLabel(jLabelCUIT, "CUIT/CUIL", stringCuitCliente);
		completarJLabel(jLabelFechaNacimiento, "Nacimiento", stringNacimientoCliente);
		completarJLabel(jLabelTelefono, "Teléfono", stringTelefonoCliente);
		completarJLabel(jLabelCelular, "Celular", stringCelularCliente);
		completarJLabel(jLabelDomicilio, "Domicilio", stringDomicilioCliente);
		completarJLabel(jLabelLocalidad, "Localidad", stringLocalidadCliente);
		if(!cliente.getCodigoPostal().isEmpty())
			jLabelLocalidad.setText(( jLabelLocalidad.getText() + " ("+ stringCodigoPostalCliente + ")"));
		completarJLabel(jLabelEmail, "Email", stringEmailCliente);

		// creo el botón modificar con un ícono
		ImageIcon imageIconUpdate = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificar = new JButton( " Modificar", imageIconUpdate );
		jButtonModificar.setToolTipText( "Modificar el cliente actual" );
		jButtonModificar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new ModificarCliente( detallarCliente, "Modificar cliente", "Modificación de cliente", cliente, detallarClienteItSelf);
			}
		});
		
		/********** inicializamos la tabla de los automóviles de los clientes ************/
		tablaAutomovilesClientes = new JTable(dtmAutomovilesClientes);
		tablaAutomovilesClientes.setFont(new Font("Dialog", Font.BOLD, 11));
		tablaAutomovilesClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaAutomovilesClientes.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = tablaAutomovilesClientes.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		scrollAutomovilesClientes = new JScrollPane(tablaAutomovilesClientes);
		tablaAutomovilesClientes.setPreferredScrollableViewportSize(new Dimension(545,95));
		scrollAutomovilesClientes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// configuro el doble click sobre la fila
		tablaAutomovilesClientes.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         DetallarCliente.this.dispose();
			         new DetallarAutomovil( detallarCliente, "Detallar automóvil", "Detalle del automóvil", listaAutomoviles.get(Integer.parseInt(tablaAutomovilesClientes.getValueAt(row, 5).toString())));
			         }
			   }
			});
		
		// Oculto la columna ID  y fila
		tablaAutomovilesClientes.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaAutomovilesClientes.getColumnModel().getColumn(0).setMinWidth(0);
		tablaAutomovilesClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
		tablaAutomovilesClientes.getColumnModel().getColumn(0).setResizable(false);
		tablaAutomovilesClientes.getColumnModel().getColumn(5).setMaxWidth(0);
		tablaAutomovilesClientes.getColumnModel().getColumn(5).setMinWidth(0);
		tablaAutomovilesClientes.getColumnModel().getColumn(5).setPreferredWidth(0);
		tablaAutomovilesClientes.getColumnModel().getColumn(5).setResizable(false);
		
		// habilito a que el usuario ordene ascendente o descendentemente las columnas
		 RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtmAutomovilesClientes);
		 tablaAutomovilesClientes.setRowSorter(sorter);
		/*************************** FIN *************************************************/
		
		
		// creo el botón eliminar con un ícono
		ImageIcon imageIconDelete = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminar = new JButton( " Eliminar", imageIconDelete );
		jButtonEliminar.setToolTipText( "Eliminar el cliente actual" );
		jButtonEliminar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "Si elimina el cliente, se realizará una eliminación en cascada de sus automóviles, \nreparaciones, servicios, repuestos y manos de obras asociadas al mismo.\n\n¿Está seguro que desea eliminar el cliente '"+ cliente.getApellido()+ ", " + cliente.getNombre() + "'?" , "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ) // si el usuario hace click en SI
					eliminarCliente();
			}   
		});
		
		jToggleButtonObservaciones = new JToggleButton( "Observaciones" );
		jToggleButtonObservaciones.setPreferredSize( new Dimension( 120, 20 ) );
		jToggleButtonObservaciones.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				boolean selected = jToggleButtonObservaciones.getModel().isSelected();
				if (selected){
					jPanelDatosPersonales2.setVisible(false);
					jPanelObservaciones.setVisible(true);
					jToggleButtonObservaciones.setToolTipText("Ocultar observaciones");
				}
				else
				{
					jPanelDatosPersonales2.setVisible(true);
					jPanelObservaciones.setVisible(false);
					jToggleButtonObservaciones.setToolTipText("Mostrar observaciones");
				}
			}   
		});
		
//		// creo el botón agregar con un ícono
		ImageIcon imageIconAddAutomovil = new ImageIcon(resourceLoader.load("/images/menu/add-icon.png"));
		jButtonAgregarAutomovil = new JButton( " Agregar", imageIconAddAutomovil );
		jButtonAgregarAutomovil.setToolTipText( "Agregar un nuevo automóvil" );
		jButtonAgregarAutomovil.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAgregarAutomovil.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarCliente.this.dispose();
				new AgregarAutomovil( detallarCliente, "Agregar automóvil", "Nuevo automóvil", cliente );
			}
		});
		
		// creo el botón detallar con un ícono
		ImageIcon imageIconDetailsAutomovil = new ImageIcon(resourceLoader.load("/images/menu/detail-icon.png"));		
		jButtonDetallarAutomovil = new JButton( " Detallar", imageIconDetailsAutomovil );
		jButtonDetallarAutomovil.setToolTipText( "Detallar el automóvil seleccionado" );
		jButtonDetallarAutomovil.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonDetallarAutomovil.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarCliente.this.dispose();
				new DetallarAutomovil( detallarCliente, "Detallar automóvil", "Detalle del automóvil", listaAutomoviles.get(Integer.parseInt(tablaAutomovilesClientes.getValueAt(tablaAutomovilesClientes.getSelectedRow(), 5).toString())));
			}
		});
		
		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			DetallarCliente.this.dispose();
			}
		});
	}
		
	//para que la tabla no sea editable
	DefaultTableModel dtmAutomovilesClientes = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;
		String nombresColumnas[] = {"id_automovil", "Dominio" ,"Marca","Modelo", "Combustible", "id_fila"};
		//final DefaultTableModel dtmAutosCliente = new DefaultTableModel(nombresColumnas, 0);

		@Override 
		public int getColumnCount() { 
			return nombresColumnas.length; 
		}

		@Override 
		public String getColumnName(int index) { 
			return nombresColumnas[index]; 
		} 

		@Override
		public boolean isCellEditable(int row, int column) {
			//all cells false
			return false;
		}
	};
	
	public void completarJLabel(JLabel jLabel, String nombre, String dato){
		// esta funcion completa los JLabels y si el mismo no contiene información los desabilita y muestra <sin especificar>
		Dimension dimensionLabel = new Dimension( 350, 25 );
		jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 12));
		jLabel.setPreferredSize( dimensionLabel );
		if(dato.isEmpty())
		{
			dato = "< sin especificar >";
			jLabel.setEnabled(false);
		}
		jLabel.setText(nombre + ": " + dato);
	}
	
	private void eliminarCliente(){
		/** Esta clase elimina el cliente que se está detallando **/
		
		int option = 0;
		ClienteManager clienteManager = new ClienteManager();
		try {
			option = clienteManager.eliminar( cliente );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		switch( option ) {
		case 1:
			this.dispose();
			JOptionPane.showMessageDialog( this, "El cliente ha sido eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE );
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El cliente no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
		
	}
	
	
	/* Habilita o deshabilita los botones según hayan filas en la tabla */
	private void enableDisableButtons(){ 
		TitledBorder titledBorder;
		if(tablaAutomovilesClientes.getRowCount() > 0){
			jButtonDetallarAutomovil.setEnabled(true);
			jButtonAgregarAutomovil.setEnabled(true);
			tablaAutomovilesClientes.setRowSelectionInterval(0, 0); // selecciona la primera fila
			tablaAutomovilesClientes.getTableHeader().setBackground(new Color(236,243,255));
			tablaAutomovilesClientes.getTableHeader().setForeground(new Color(0,0,0));
			jPanelAutomovilesCliente.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Automóviles del cliente ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelAutomovilesCliente.setBorder( titledBorder );
		}
		else{
			jButtonDetallarAutomovil.setEnabled(false);
			tablaAutomovilesClientes.getTableHeader().setBackground(new Color(238,238,238));
			tablaAutomovilesClientes.getTableHeader().setForeground(new Color(153,153,153));
			jPanelAutomovilesCliente.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Automóviles del cliente ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelAutomovilesCliente.setBorder( titledBorder );
		}
	}
}