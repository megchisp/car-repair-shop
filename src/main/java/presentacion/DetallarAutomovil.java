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
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import negocio.AutomovilManager;
import negocio.ClienteManager;
import negocio.IClienteManager;
import negocio.IReparacionManager;
import negocio.ReparacionManager;

import persistencia.Automovil;
import persistencia.Reparacion;

public class DetallarAutomovil extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame detallarAutomovil;
	DetallarAutomovil detallarAutomovilItSelf = this; // tuve que crear detallarAutomovilItSelf xq no me deja pasar this como parametro :/
	
	JPanel jPanelDetalleAutomovil = null;
	
	Automovil automovil = null;
	List<Reparacion> listaReparaciones = null;
	IClienteManager clienteManager = null;
	IReparacionManager reparacionManager = null;
	
	JLabel jLabelDominio = null;
	JLabel jLabelNumeroMotor = null;
	JLabel jLabelNumeroChasis = null;
	JLabel jLabelAnio = null;
	JLabel jLabelColor = null;
	JLabel jLabelTipoAceite = null;
	JLabel jLabelTipoCombustible = null;
	JLabel jLabelNumeroRadio = null;
	JLabel jLabelCodigoLlave = null;
	JLabel jLabelUso = null;
	JLabel jLabelPropietario = null;
	JLabel jLabelMarcaModelo = null;
	
	// todos estos Strings se van a inicializar con los valores de la base de datos
	String stringDominio = null;
	String stringNumeroMotor = null;
	String stringNumeroChasis = null;
	String stringAnio = null;
	String stringColor = null;
	String stringTipoCombustible = null;
	String stringNumeroRadio = null;
	String stringCodigoLlave = null;
	String stringUso = null;
	String stringPropietario = null;
	String stringMarca = null;	
	String stringModelo = null;
	String stringTipoAceite = null;
	String stringGNC = null;
	String stringApellido = null;
	String stringNombre = null;

	JButton jButtonModificarAutomovil = null;
	JButton jButtonEliminarAutomovil = null;
	JButton jButtonAgregarReparacion = null;
	JButton jButtonModificarReparacion = null;
	JButton jButtonEliminarReparacion = null;
	JButton jButtonDetallarReparacion = null;
	JButton jButtonCerrar = null;

	JTable jTableReparaciones = null;
	JScrollPane JScrollReparaciones = null;
	
	// Posibles colores que puede tomar el automovil, el índice se almacena en automovil.getColor()
	Color color[] = {null, Color.black, Color.white, Color.gray, Color.lightGray, Color.darkGray, Color.red, Color.yellow, Color.blue, Color.green, Color.cyan, Color.orange, Color.magenta};
	String nombreColor [] = {null , "Negro", "Blanco","Gris", "Gris claro", "Gris oscuro", "Rojo", "Amarillo", "Azul", "Verde", "Celeste", "Naranja", "Magenta"};
	
	JPanel jPanelReparacionesAutomovil = null; // contiene jTableReparaciones
	ResourceLoader resourceLoader = new ResourceLoader();

	public DetallarAutomovil( JFrame padre, String titulo, String titleBorder, Automovil automovil) {
		super( padre, titulo, true );
		
		// new Dimension( ancho, alto ) 
		this.setPreferredSize( new Dimension( 620, 600 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
	    
	    this.automovil = automovil;
	    clienteManager = new ClienteManager();
	    reparacionManager = new ReparacionManager();

		/* Panel exterior */
		jPanelDetalleAutomovil = new JPanel();
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder(titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelDetalleAutomovil.setBorder( titledBorder );
		jPanelDetalleAutomovil.setPreferredSize(new Dimension(610, 570));
		/* fin panel exterior */
		jPanelDetalleAutomovil.setLayout( new GridBagLayout() );
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		
		inicializarVariables();
		extraerInfoBD();
		enableDisableButtons();
		cerrarEsc();
		
			/* Panel interior donde se muestran los datos del automovil */
			JPanel jPanelDatosAutomovil = new JPanel();
			jPanelDatosAutomovil.setBorder(BorderFactory.createTitledBorder(null, " Datos del automóvil ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelDatosAutomovil.setPreferredSize(new Dimension(590, 245));
			
			jPanelDatosAutomovil.setLayout( new FlowLayout(2, 0, 0));
		
		
				JPanel jPanelMarcaModelo = new JPanel();
				jPanelMarcaModelo.setPreferredSize(new Dimension(570, 45));
				jPanelMarcaModelo.setLayout( new GridBagLayout() );
				
				GridBagConstraints h = new GridBagConstraints();
				h.anchor = GridBagConstraints.NORTH;
				h.gridy = 0;
				h.gridx = 0;
				//h.insets = new Insets( 0, 0, 0, 90 );
				jPanelMarcaModelo.add( jLabelMarcaModelo, h );
				
				/* fin panel interior */
	
			jPanelDatosAutomovil.add( jPanelMarcaModelo );
			
			JPanel jPanelDatosAutomovil2 = new JPanel();
			jPanelDatosAutomovil2.setPreferredSize(new Dimension(570, 130));
//			jPanelDatosAutomovil2.setBorder(BorderFactory.createTitledBorder(null, " Datos del automóvil ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
//					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			
			jPanelDatosAutomovil2.setLayout( new GridBagLayout() );
			GridBagConstraints z = new GridBagConstraints();
			z.ipady = 5;
			z.anchor = GridBagConstraints.WEST;
				z.gridy = 2;
				z.gridx = 0;
				z.insets = new Insets( 0, 0, 0, 170);
				jPanelDatosAutomovil2.add( jLabelDominio , z );
				z.gridy = 2;
				z.gridx = 1;
				z.insets = new Insets( 0, 0, 0, 0);
				jPanelDatosAutomovil2.add( jLabelTipoCombustible, z );
				z.gridy = 4;
				z.gridx = 0;
				jPanelDatosAutomovil2.add( jLabelAnio, z );
				z.gridy = 4;
				z.gridx = 1;
				jPanelDatosAutomovil2.add( jLabelColor, z );
				z.gridy = 5;
				z.gridx = 0;
//				jPanelDatosAutomovil2.add( new JLabel(" "), z );
				z.gridy = 6;
				z.gridx = 0;
				jPanelDatosAutomovil2.add( jLabelNumeroMotor, z );
				z.gridy = 6;
				z.gridx = 1;
				jPanelDatosAutomovil2.add( jLabelNumeroChasis, z );
				z.gridy = 7;
				z.gridx = 0;
//				jPanelDatosAutomovil2.add( new JLabel(" "), z );
				z.gridy = 8;
				z.gridx = 0;
				jPanelDatosAutomovil2.add( jLabelTipoAceite, z );
				z.gridy = 8;
				z.gridx = 1;
				jPanelDatosAutomovil2.add( jLabelCodigoLlave, z );
				z.gridy = 9;
				z.gridx = 0;
//				jPanelDatosAutomovil2.add( new JLabel(" "), z );
				z.gridy = 10;
				z.gridx = 0;
				jPanelDatosAutomovil2.add( jLabelNumeroRadio, z );
				z.gridy = 10;
				z.gridx = 1;
				jPanelDatosAutomovil2.add( jLabelUso, z );
				z.gridy = 11;
				z.gridx = 0;


				jPanelDatosAutomovil.add(jPanelDatosAutomovil2);
			
					// se crea un panel que contiene los botones de Modificar Automovil y Eliminar Automovil
					JPanel jPanelBotonesInterior = new JPanel(new FlowLayout(0, 20, 0));

					jPanelBotonesInterior.setPreferredSize(new Dimension(550, 30));
					jPanelBotonesInterior.add( jButtonModificarAutomovil );
					jPanelBotonesInterior.add( jButtonEliminarAutomovil );
					jPanelBotonesInterior.add( new JLabel(" ") );
				
			z.gridy = 12;
			z.gridx = 0;
			jPanelDatosAutomovil.add( jPanelBotonesInterior, z );
			z.gridy = 13;
			z.gridx = 0;
			jPanelDatosAutomovil.add( new JLabel(" "), z );
			

				jPanelReparacionesAutomovil.setPreferredSize(new Dimension(590, 220));
				
				jPanelReparacionesAutomovil.setLayout( new GridBagLayout() );
				/* fin panel interior */
	
				
				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.WEST;
	
				c.gridy = 0;
				c.gridx = 0;
				jPanelReparacionesAutomovil.add( JScrollReparaciones, c );
				
					// se crea un panel que contiene los botones de Agregar Automóvil y Detallar automóvil
					JPanel jPanelBotones = new JPanel(new GridBagLayout());
					jPanelBotones.setPreferredSize(new Dimension(570, 30));
					GridBagConstraints g = new GridBagConstraints();
					g.anchor = GridBagConstraints.WEST;
					
					g.gridy = 0;
					g.gridx = 0;
					jPanelBotones.add( jButtonAgregarReparacion );
					g.gridy = 0;
					g.gridx = 1;
					g.insets = new Insets( 0, 20, 0, 0 );
					jPanelBotones.add( jButtonModificarReparacion, g );
					g.gridy = 0;
					g.gridx = 2;
					g.insets = new Insets( 0, 20, 0, 0 );
					jPanelBotones.add( jButtonEliminarReparacion, g );
					g.gridy = 0;
					g.gridx = 3;
					g.insets = new Insets( 0, 130, 0, 0 );
					jPanelBotones.add( jButtonDetallarReparacion, g );
					
				c.gridy = 1;
				c.gridx = 0;
				jPanelReparacionesAutomovil.add( new JLabel(" "), c );
				c.gridy = 2;
				c.gridx = 0;
				jPanelReparacionesAutomovil.add( jPanelBotones, c );
				c.gridy = 3;
				c.gridx = 0;
				jPanelReparacionesAutomovil.add( new JLabel(" "), c );
					
					
		a.gridy = 1;
		a.gridx = 0;
		jPanelDetalleAutomovil.add( jPanelDatosAutomovil , a);
		a.gridy = 2;
		a.gridx = 0;
		jPanelDetalleAutomovil.add( new JLabel(" "), a);
		a.gridy = 3;
		a.gridx = 0;
		jPanelDetalleAutomovil.add( jPanelReparacionesAutomovil, a);
		a.gridy = 4;
		a.gridx = 0;
		jPanelDetalleAutomovil.add( new JLabel(" "), a);
		a.gridy = 5;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelDetalleAutomovil.add( jButtonCerrar, a);
		a.gridy = 6;
		a.gridx = 0;
		jPanelDetalleAutomovil.add( new JLabel(" "), a);
					
		this.getContentPane().add( jPanelDetalleAutomovil, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarVariables() {
		stringDominio = automovil.getDominio(); // obligatorio!
		stringNumeroMotor = automovil.getNumeroMotor();
		stringNumeroChasis = automovil.getNumeroChasis();
		stringAnio = Integer.toString(automovil.getAño());
		stringColor = Integer.toString(automovil.getColor());
		stringNumeroRadio = automovil.getNumeroRadio();
		stringCodigoLlave = automovil.getCodigoLlave();
		stringUso = automovil.getUso();
		stringPropietario = stringApellido + ", " + stringNombre;
		stringMarca = automovil.getMarca();
		stringModelo = automovil.getModelo();
		stringTipoAceite = automovil.getTipoAceite();
		if (automovil.isConGNC())
			stringGNC = " con GNC";
		else
			stringGNC = "";
		stringTipoCombustible = automovil.getTipoCombustible() + stringGNC;
		
		jLabelMarcaModelo = new JLabel( stringMarca + " " + stringModelo );
		jLabelMarcaModelo.setFont(new Font("Dialog", Font.BOLD, 20));
		
		jLabelDominio = new JLabel();
		jLabelNumeroChasis = new JLabel();
		jLabelNumeroMotor = new JLabel();
		jLabelNumeroRadio = new JLabel();
		jLabelColor = new JLabel();
		jLabelUso = new JLabel();
		jLabelAnio = new JLabel();
		jLabelTipoAceite = new JLabel();
		jLabelTipoCombustible = new JLabel();
		jLabelCodigoLlave = new JLabel();
		jLabelPropietario = new JLabel();
		
		completarJLabel(jLabelDominio, "Dominio", stringDominio);
		completarJLabel(jLabelNumeroChasis, "Chasis", stringNumeroChasis);
		completarJLabel(jLabelNumeroMotor, "Motor", stringNumeroMotor);
		completarJLabel(jLabelNumeroRadio, "Radio", stringNumeroRadio);
		completarJLabel(jLabelColor, "Color", stringColor.equals("0") ? "" : stringColor);
		
		// si el automovil tiene color, tengo que redefinir el jLabelColor
		if(automovil.getColor() > 0){
			jLabelColor.setText("Color: " + nombreColor[automovil.getColor()] + " ");
			jLabelColor.setIcon(new AnOvalIcon (color[automovil.getColor()]));
			jLabelColor.setVerticalTextPosition(JLabel.CENTER);
			jLabelColor.setHorizontalTextPosition(JLabel.LEFT);
		}
		
		completarJLabel(jLabelUso, "Uso", stringUso);
		completarJLabel(jLabelAnio, "Año", stringAnio.equals("1900") ? "" : stringAnio);
		completarJLabel(jLabelTipoAceite, "Aceite", stringTipoAceite);
		completarJLabel(jLabelTipoCombustible, "Combustible", stringTipoCombustible);
		completarJLabel(jLabelCodigoLlave, "Código llave", stringCodigoLlave);
		
		
		/* Panel interior donde se muestran las reparaciones del automovil */
		jPanelReparacionesAutomovil = new JPanel();
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder(null, " Reparaciones del automóvil ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
		new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
		jPanelReparacionesAutomovil.setBorder( titledBorder );
		
		/********** inicializamos la tabla de las reparaciones de los automoviles ************/
		jTableReparaciones = new JTable(dtmReparacionesAutomovil);
		jTableReparaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableReparaciones.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableReparaciones.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		JScrollReparaciones = new JScrollPane(jTableReparaciones);
		jTableReparaciones.setPreferredScrollableViewportSize(new Dimension(545,95));
		jTableReparaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		JScrollReparaciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Tamaño a las columnas tiempo min y max
		jTableReparaciones.getColumnModel().getColumn(1).setPreferredWidth(30);
		jTableReparaciones.getColumnModel().getColumn(2).setPreferredWidth(30);
		jTableReparaciones.getColumnModel().getColumn(5).setPreferredWidth(30);
		
		// Oculto las columnas id_reparacion y fila
		jTableReparaciones.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableReparaciones.getColumnModel().getColumn(0).setMinWidth(0);
		jTableReparaciones.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableReparaciones.getColumnModel().getColumn(0).setResizable(false);
		jTableReparaciones.getColumnModel().getColumn(6).setMaxWidth(0);
		jTableReparaciones.getColumnModel().getColumn(6).setMinWidth(0);
		jTableReparaciones.getColumnModel().getColumn(6).setPreferredWidth(0);
		jTableReparaciones.getColumnModel().getColumn(6).setResizable(false);
		
		// Justificación a la derecha del contenido de las columnas 1 y 2
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		jTableReparaciones.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		jTableReparaciones.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
		jTableReparaciones.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
		jTableReparaciones.getColumnModel().getColumn(4).setCellRenderer( rightRenderer );
		jTableReparaciones.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );

		/*************************** FIN *************************************************/
		
		// creo el botón modificar con un ícono
		ImageIcon imageIconUpdateCar = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificarAutomovil = new JButton( " Modificar", imageIconUpdateCar );
		jButtonModificarAutomovil.setToolTipText("Modificar el automóvil actual");
		jButtonModificarAutomovil.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificarAutomovil.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new ModificarAutomovil( detallarAutomovil, "Modificar automóvil", "Modificación del automóvil", detallarAutomovilItSelf, automovil);
			}
		});
		
		// creo el botón eliminar con un ícono
		ImageIcon imageIconDeleteCar = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminarAutomovil = new JButton( " Eliminar", imageIconDeleteCar );
		jButtonEliminarAutomovil.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminarAutomovil.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "Si elimina este automóvil, se realizará una eliminación en cascada de sus reparaciones, \nservicios, repuestos y manos de obras asociadas al mismo.\n\n                   ¿Está seguro que desea eliminar este automóvil?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarAutomovil();
					DetallarAutomovil.this.dispose();
				}
			}   
		});
		
		// creo el botón eliminar con un ícono
		ImageIcon imageIconDeleteRepair = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminarReparacion = new JButton( " Eliminar", imageIconDeleteRepair );
		jButtonEliminarReparacion.setToolTipText("Eliminar la reparación seleccionada");
		jButtonEliminarReparacion.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminarReparacion.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "Si elimina la reparación, se realizará una eliminación en cascada de sus \nservicios, repuestos y manos de obras asociadas al mismo.\n\n   ¿Está seguro que desea eliminar la reparación seleccionada?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarReparacion();
					enableDisableButtons();
				}
			}   
		});
		
//		// creo el botón agregar con un ícono
		ImageIcon imageIconAddRepair = new ImageIcon(resourceLoader.load("/images/menu/add-icon.png"));
		jButtonAgregarReparacion = new JButton( " Agregar", imageIconAddRepair );
		jButtonAgregarReparacion.setToolTipText("Agregar una nueva reparación");
		jButtonAgregarReparacion.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAgregarReparacion.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new AgregarReparacion( detallarAutomovil, "Agregar reparación", " Nueva reparación ", detallarAutomovilItSelf);
			}
		});
		
		// creo el botón modificar con un ícono
		ImageIcon imageIconUpdateRepair = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificarReparacion = new JButton( " Modificar", imageIconUpdateRepair );
		jButtonModificarReparacion.setToolTipText("Modificar la reparación seleccionada");
		jButtonModificarReparacion.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificarReparacion.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarAutomovil.this.dispose();
				new ModificarReparacion( detallarAutomovil, "Modificar reparación", " Modificación de la reparación ", listaReparaciones.get(Integer.parseInt(jTableReparaciones.getValueAt(jTableReparaciones.getSelectedRow(), 6).toString())), null );
			}
		});
		
		// creo el botón detallar con un ícono
		ImageIcon imageIconDetailsRepair = new ImageIcon(resourceLoader.load("/images/menu/detail-icon.png"));		
		jButtonDetallarReparacion = new JButton( " Detallar", imageIconDetailsRepair );
		jButtonDetallarReparacion.setToolTipText("Detallar la reparación seleccionada");
		jButtonDetallarReparacion.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonDetallarReparacion.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarAutomovil.this.dispose();
				new DetallarReparacion( detallarAutomovil, "Detallar reparación", " Detalle de la reparación ", listaReparaciones.get(Integer.parseInt(jTableReparaciones.getValueAt(jTableReparaciones.getSelectedRow(), 6).toString())));
			}
		});
		

		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarAutomovil.this.dispose();
			}
		});
	}
	
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
	
	private void extraerInfoBD(){
		IReparacionManager reparacionManager = new ReparacionManager();

		try {
			// obtengo las reparaciones del automovil pasado como parametro
			listaReparaciones = reparacionManager.listaReparaciones(automovil);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		completar_tabla(listaReparaciones.iterator()); // completa la tabla con la lista de reparaciones
	}
	
	void completar_tabla(Iterator<Reparacion> iterator){
		/** Este método completa la tabla con la lista de reparaciones **/
		Reparacion reparacion;
		int numero_fila = 0;
		int tiempo_minimo;
		int tiempo_maximo;
		
		// para mostrar el importe
		NumberFormat numberFormatMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		
		// para mostrar la fecha
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/YYYY", new Locale("es", "AR"));
		
		// para mostrar separador de miles en el kilometraje
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(new Locale("es", "AR"));
		
		while (iterator.hasNext()){
			reparacion = iterator.next();
			String id_fila = Integer.toString(numero_fila++);
			String id_reparacion = Integer.toString(reparacion.getId_reparacion());
			String fecha = sdf.format(new java.sql.Date(reparacion.getFechaReparacion().getTimeInMillis()));
			String km = formatter.format(reparacion.getKilometraje());
			String tiempo_min_estimado;
			String tiempo_max_estimado;
			String importe;
			try {
				importe = numberFormatMoneda.format(reparacionManager.importe(reparacion));
				tiempo_minimo = reparacionManager.tiempoMinimo(reparacion);
				tiempo_maximo = reparacionManager.tiempoMaximo(reparacion);
				tiempo_min_estimado = String.format("%dh %02d'", tiempo_minimo / 60, tiempo_minimo % 60);
				tiempo_max_estimado = String.format("%dh %02d'", tiempo_maximo / 60, tiempo_maximo % 60);
			} catch (Exception e) {
				importe = "ERROR";
				tiempo_min_estimado = "ERROR";
				tiempo_max_estimado = "ERROR";
				JOptionPane.showMessageDialog( this, "Algo salió mal", "Error", JOptionPane.ERROR_MESSAGE );
			}
			Object [] fila= {id_reparacion, fecha, km, tiempo_min_estimado, tiempo_max_estimado, importe, id_fila};
			dtmReparacionesAutomovil.addRow(fila);
		}
	}
	
	private void eliminarReparacion(){
		/** Esta clase elimina la reparacion seleccionada **/
		
		int option = 0;
		IReparacionManager reparacionManager = new ReparacionManager();
		try {
			option = reparacionManager.eliminar( listaReparaciones.get(Integer.parseInt(jTableReparaciones.getValueAt(jTableReparaciones.getSelectedRow(), 6).toString())) );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		switch( option ) {
		case 1:
			// elimino la reparacion de la lista de reparaciones
			listaReparaciones.remove(jTableReparaciones.getSelectedRow());
			
			// elimino todas las reparaciones de la tabla
			dtmReparacionesAutomovil.getDataVector().removeAllElements();
			dtmReparacionesAutomovil.fireTableDataChanged();
			
			// una vez eliminada la reparacion completo la tabla con la lista de reparaciones
			completar_tabla(listaReparaciones.iterator());
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "La reparación no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			// este mensaje nunca debería aparecer
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
		
	}
	
	private void eliminarAutomovil(){
		/** Esta clase elimina el automovil que se está detallando **/

		int option = 0;
		AutomovilManager automovilManager = new AutomovilManager();
		try {
			option = automovilManager.eliminar( automovil );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		switch( option ) {
		case 1:
			this.dispose();
			JOptionPane.showMessageDialog( this, "El automovil ha sido eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE );
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El automovil no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
		
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana DetallarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  DetallarAutomovil.this.dispose();
		      }
		    };
		jPanelDetalleAutomovil.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	//para que la tabla no sea editable
		DefaultTableModel dtmReparacionesAutomovil = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnas[] = {"id_reparacion", "Fecha" ,"Kilometraje","Tiempo mín. reparación","Tiempo máx. reparación", "Importe", "Fila"};
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
	
	/* Habilita o deshabilita los botones según hayan filas en la tabla */
	public void enableDisableButtons(){ 
//		TitledBorder titledBorder;
		if(jTableReparaciones.getRowCount() > 0){
			jButtonDetallarReparacion.setEnabled(true);
			jButtonEliminarReparacion.setEnabled(true);
//			jButtonAgregarReparacion.setEnabled(true);
			jButtonModificarReparacion.setEnabled(true);
			jTableReparaciones.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableReparaciones.getTableHeader().setBackground(new Color(236,243,255));
			jTableReparaciones.getTableHeader().setForeground(new Color(0,0,0));
//			jPanelReparacionesAutomovil.setEnabled(true);
			jTableReparaciones.setEnabled(true);
//			titledBorder = BorderFactory.createTitledBorder(null, " Reparaciones del automóvil ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
//					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
//			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
//			jPanelReparacionesAutomovil.setBorder( titledBorder );
		}
		else{
			jButtonDetallarReparacion.setEnabled(false);
			jButtonEliminarReparacion.setEnabled(false);
//			jButtonAgregarReparacion.setEnabled(false);
			jButtonModificarReparacion.setEnabled(false);
			jTableReparaciones.getTableHeader().setBackground(new Color(238,238,238));
			jTableReparaciones.getTableHeader().setForeground(new Color(153,153,153));
			jTableReparaciones.setEnabled(false);
//			jPanelReparacionesAutomovil.setEnabled(false);
//			titledBorder = BorderFactory.createTitledBorder(null, " Reparaciones del automóvil ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
//					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
//			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
//			jPanelReparacionesAutomovil.setBorder( titledBorder );
		}
	}
	
}


