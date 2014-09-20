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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

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
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import negocio.AutomovilManager;
import negocio.IAutomovilManager;
import negocio.IReparacionManager;
import negocio.IServicioManager;
import negocio.ITipoDeServicioManager;
import negocio.ReparacionManager;
import negocio.ServicioManager;
import negocio.TipoDeServicioManager;

import persistencia.Automovil;
import persistencia.Reparacion;
import persistencia.Servicio;


public class DetallarReparacion extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame detallarReparacion;
	
	AgregarServicio agregarServicio = null;
	AgregarReparacion agregarReparacion = null;
	DetallarAutomovil detallarAutomovil = null;
	DetallarReparacion detallarReparacionItSelf = this;
	
	JLabel jLabelTiempoMaxEstTotal = null;
	JLabel jLabelTiempoMinEstTotal = null;
	JLabel jLabelObservaciones = null;
	JLabel jLabelKilometraje = null;
	JLabel jLabelImporte = null;
	JLabel jLabelFecha = null;

	JButton jButtonModificarReparacion = null;
	JButton jButtonEliminarReparacion = null;
	
	JButton jButtonAgregarServicio = null;
	JButton jButtonEliminarServicio = null;
	JButton jButtonDetallarServicio = null;
	JButton jButtonCerrar = null;
	JButton jButtonDetallarAutomovil = null;
	
	JTable jTableServiciosReparacion = null;
	JScrollPane jScrollPaneServiciosReparacion = null;
	JScrollPane jScrollPaneObservaciones = null;
    JTextArea jTextAreaObservaciones = null;
    
    JPanel jPanelDetalleReparacion = null;
    JPanel jPanelBotonesAbajo = null;
    
    IReparacionManager reparacionManager = null;
    Reparacion reparacion = null;
	Vector<String> serviciosAgregados = null;
	List<Servicio> listaServicios = null;
	ResourceLoader resourceLoader = new ResourceLoader();
	boolean servicioAgregado;

	public DetallarReparacion( JFrame padre, String titulo, String titleBorder, Reparacion reparacion) {
		super( padre, titulo, true );
		this.setPreferredSize( new Dimension( 460, 555 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		serviciosAgregados = new Vector<String>();
		this.reparacion = reparacion;
		
		/* Panel exterior */
		jPanelDetalleReparacion = new JPanel();
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelDetalleReparacion.setBorder( titledBorder );
		jPanelDetalleReparacion.setPreferredSize(new Dimension(450, 525));
		/* fin panel exterior */
		jPanelDetalleReparacion.setLayout(new GridBagLayout());
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		
		inicializarVariables();
		inicializarBotones();
		extraerInfoBD();
		enableDisableButtons();
		
		cerrarEsc();
		
		/* Panel interior donde se muestra el detalle de la reparacion */
		JPanel jPanelDatosReparacionGrande = new JPanel();
		jPanelDatosReparacionGrande.setBorder(BorderFactory.createTitledBorder(null, " Datos de la reparación ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
				new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
		jPanelDatosReparacionGrande.setPreferredSize(new Dimension(423, 200));
		
		jPanelDatosReparacionGrande.setLayout( new FlowLayout(FlowLayout.LEFT,3,0));
		
			JPanel jPanelDatosReparacion = new JPanel(new GridBagLayout());

			jPanelDatosReparacion.setPreferredSize(new Dimension(350, 45));
			GridBagConstraints b = new GridBagConstraints();
			b.anchor = GridBagConstraints.WEST;
			b.ipady = 5;
			b.gridy = 0;
			b.gridx = 0;
			b.insets = new Insets( 0, 30, 0, 20 );
			jPanelDatosReparacion.add( jLabelFecha,b);
			b.gridy = 1;
			b.gridx = 0;
			jPanelDatosReparacion.add(jLabelKilometraje ,b);
			b.gridy = 0;
			b.gridx = 1;
			b.insets = new Insets( 0, 30, 0, 20 );
			jPanelDatosReparacion.add( jLabelTiempoMinEstTotal,b);
			b.gridy = 1;
			b.gridx = 1;
			jPanelDatosReparacion.add( jLabelTiempoMaxEstTotal,b );
		jPanelDatosReparacionGrande.add(jPanelDatosReparacion);
		
			JPanel jPanelObservaciones = new JPanel();
			jPanelObservaciones.setLayout( new FlowLayout(FlowLayout.CENTER,0,0));
			jPanelObservaciones.setPreferredSize(new Dimension(410, 90));
			jPanelObservaciones.add(jLabelObservaciones);
			jPanelObservaciones.add(jScrollPaneObservaciones);
		jPanelDatosReparacionGrande.add(jPanelObservaciones);

			// se crea un panel que contiene los botones de Modificar Reparacion y Eliminar Reparacion
			JPanel jPanelBotonesInterior = new JPanel(new GridBagLayout());
			jPanelBotonesInterior.setPreferredSize(new Dimension(250, 30));
			GridBagConstraints h = new GridBagConstraints();
			h.anchor = GridBagConstraints.EAST;
			
			h.gridy = 0;
			h.gridx = 0;
			jPanelBotonesInterior.add( jButtonModificarReparacion, h );
			h.gridy = 0;
			h.gridx = 1;
			h.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotonesInterior.add( jButtonEliminarReparacion, h );
			
		jPanelDatosReparacionGrande.add( jPanelBotonesInterior);

		
		
			/* Panel interior donde se muestran los servicios de la reparación */
			JPanel jPanelServiciosReparacion = new JPanel();
			jPanelServiciosReparacion.setLayout( new FlowLayout(FlowLayout.LEFT,5,0) );
			jPanelServiciosReparacion.setBorder(BorderFactory.createTitledBorder(null, " Servicios realizados en la reparación ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelServiciosReparacion.setPreferredSize(new Dimension(423, 225));
			

			/* fin panel interior */

			jPanelServiciosReparacion.add( jScrollPaneServiciosReparacion);
			jPanelServiciosReparacion.add(jLabelImporte);
			
				// se crea un panel que contiene los botones de Agregar servicio, Eliminar servicio y Detallar servicio
				JPanel jPanelBotones = new JPanel(new GridBagLayout());
				jPanelBotones.setPreferredSize(new Dimension( 408, 50 ));
				GridBagConstraints g = new GridBagConstraints();
				g.anchor = GridBagConstraints.WEST;
				
				g.gridy = 0;
				g.gridx = 0;
				g.insets = new Insets( 0, -89, 0, 0 );
				jPanelBotones.add( jButtonAgregarServicio,g );
				g.gridy = 0;
				g.gridx = 1;
				g.insets = new Insets( 0, 20, 0, 0 );
				jPanelBotones.add( jButtonEliminarServicio,g );
				g.gridy = 0;
				g.gridx = 2;
				g.insets = new Insets( 0, 20, 0, 0 );
				jPanelBotones.add( jButtonDetallarServicio, g );
				

			jPanelServiciosReparacion.add( jPanelBotones);
					
						
			a.gridy = 1;
			a.gridx = 0;
			jPanelDetalleReparacion.add( jPanelDatosReparacionGrande , a);
			a.gridy = 2;
			a.gridx = 0;
			jPanelDetalleReparacion.add( new JLabel(" "), a);
			a.gridy = 3;
			a.gridx = 0;
			jPanelDetalleReparacion.add( jPanelServiciosReparacion, a);
			a.gridy = 4;
			a.gridx = 0;
			jPanelDetalleReparacion.add( new JLabel(" "), a);
			a.gridy = 5;
			a.gridx = 0;
			jPanelDetalleReparacion.add( jPanelBotonesAbajo, a );
						
		this.getContentPane().add( jPanelDetalleReparacion, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarBotones(){
		jPanelBotonesAbajo = new JPanel();
		jPanelBotonesAbajo.setPreferredSize( new Dimension( 423, 35 ) );
		jPanelBotonesAbajo.setLayout( new GridBagLayout() );
		GridBagConstraints e = new GridBagConstraints();
		e.anchor = GridBagConstraints.EAST;
		
		e.gridy = 0;
		e.gridx = 0;
		e.insets = new Insets( 0, 0, 0, 290 );
		jPanelBotonesAbajo.add( jButtonDetallarAutomovil, e );
		e.gridy = 0;
		e.gridx = 1;
		e.insets = new Insets( 0, 0, 0, 0 );
		jPanelBotonesAbajo.add( jButtonCerrar, e );
	}
	
		
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 350, 25 );
		Font fontJLabel = new Font("Tahoma", Font.PLAIN, 12);
		reparacionManager = new ReparacionManager();
		
		// variables auxiliares
		int tiempo_minimo;
		int tiempo_maximo;
		String tiempo_min_estimado;
		String tiempo_max_estimado;
		try {
			tiempo_minimo = reparacionManager.tiempoMinimo(reparacion);
			tiempo_maximo = reparacionManager.tiempoMaximo(reparacion);
			tiempo_min_estimado = String.format("%dh %02d'", tiempo_minimo / 60, tiempo_minimo % 60);
			tiempo_max_estimado = String.format("%dh %02d'", tiempo_maximo / 60, tiempo_maximo % 60);
		} catch (Exception e) {
			tiempo_min_estimado = "ERROR";
			tiempo_max_estimado = "ERROR";
			JOptionPane.showMessageDialog( this, "No se han podido recuperar los tiempos de reparación", "Error", JOptionPane.ERROR_MESSAGE );
		}

		jLabelTiempoMaxEstTotal = new JLabel();
		jLabelTiempoMaxEstTotal.setText("Tiempo máximo: " + tiempo_max_estimado);
		jLabelTiempoMaxEstTotal.setPreferredSize( dimensionLabel );
		jLabelTiempoMaxEstTotal.setFont(fontJLabel);
		
		jLabelObservaciones = new JLabel( "Observaciones:" );
		jLabelObservaciones.setFont(fontJLabel);
		jLabelObservaciones.setPreferredSize( new Dimension(380,25) );

		// para mostrar la fecha
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/YYYY", new Locale("es", "AR"));
		
		jLabelFecha = new JLabel( "Fecha: " + sdf.format(new java.sql.Date(reparacion.getFechaReparacion().getTimeInMillis())));
		jLabelFecha.setFont(fontJLabel);
		jLabelFecha.setPreferredSize( dimensionLabel );

		// para mostrar separador de miles en el kilometraje
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(new Locale("es", "AR"));
		
		jLabelKilometraje = new JLabel( "Kilometraje: " + formatter.format(reparacion.getKilometraje()) + " kms");
		jLabelKilometraje.setFont(fontJLabel);
		jLabelKilometraje.setPreferredSize( dimensionLabel );
				
		jLabelTiempoMinEstTotal = new JLabel( "Tiempo mínimo: " + tiempo_min_estimado );
		jLabelTiempoMinEstTotal.setFont(fontJLabel);
		jLabelTiempoMinEstTotal.setPreferredSize( dimensionLabel );
		
		jLabelImporte = new JLabel();
		jLabelImporte.setFont(fontJLabel);
		jLabelImporte.setPreferredSize( dimensionLabel );
		
		jTextAreaObservaciones = new JTextArea();
		jTextAreaObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextAreaObservaciones.setText(reparacion.getObservaciones());
		jTextAreaObservaciones.setEditable(false);
		jTextAreaObservaciones.setLineWrap(true);
		jTextAreaObservaciones.setWrapStyleWord(true);
		jScrollPaneObservaciones = new JScrollPane(jTextAreaObservaciones);
		jScrollPaneObservaciones.setPreferredSize(new Dimension( 380, 50 ));
	
		// creo el botón modificar con un ícono
		ImageIcon imageIconUpdateRepair = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificarReparacion = new JButton( " Modificar", imageIconUpdateRepair );
		jButtonModificarReparacion.setToolTipText( "Modificar la reparación actual" );
		jButtonModificarReparacion.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificarReparacion.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new ModificarReparacion( detallarReparacion, "Modificar reparación", "Modificación de la reparacion", reparacion,detallarReparacionItSelf );
			}
		});
		
		// creo el botón eliminar con un ícono
		ImageIcon imageIconDeleteRepair = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminarReparacion = new JButton( " Eliminar", imageIconDeleteRepair );
		jButtonEliminarReparacion.setToolTipText( "Eliminar la reparación actual" );
		jButtonEliminarReparacion.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminarReparacion.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "Si elimina la reparación, se realizará una eliminación en cascada de sus servicios, \nrepuestos y manos de obras asociadas a la misma.\n\n              ¿Está seguro que desea eliminar la reparación seleccionada?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarReparacion();
					DetallarReparacion.this.dispose();
				}
			}
				
		});
		
//		// creo el botón agregar con un ícono
		ImageIcon imageIconAddService = new ImageIcon(resourceLoader.load("/images/menu/add-icon.png"));
		jButtonAgregarServicio = new JButton( " Agregar", imageIconAddService );
		jButtonAgregarServicio.setPreferredSize( new Dimension( 110, 30 ) );
		jButtonAgregarServicio.setToolTipText( "Agregar un nuevo servicio" );
		jButtonAgregarServicio.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				servicioAgregado = false;
				if(agregarServicio()){
					jTableServiciosReparacion.setRowSelectionInterval(jTableServiciosReparacion.getRowCount() - 1, jTableServiciosReparacion.getRowCount() - 1); // selecciona la ultima fila
					int index = Integer.parseInt(jTableServiciosReparacion.getValueAt(jTableServiciosReparacion.getSelectedRow(), 3).toString());
					new DetallarServicio( detallarReparacion, "Detallar servicio", " Detalle del servicio ", agregarServicio, detallarReparacionItSelf, listaServicios.get(index) );
				}

			}
		});
	
		// creo el botón eliminar con un ícono
		ImageIcon imageIconDeleteService = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminarServicio = new JButton( " Eliminar", imageIconDeleteService );
		jButtonEliminarServicio.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminarServicio.setToolTipText( "Eliminar el servicio seleccionado" );
		jButtonEliminarServicio.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "Si elimina el servicio, se realizará una eliminación en cascada de sus \nrepuestos y manos de obras asociadas al mismo.\n\n    ¿Está seguro que desea eliminar el servicio seleccionado?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					int index = Integer.parseInt(jTableServiciosReparacion.getValueAt(jTableServiciosReparacion.getSelectedRow(), 3).toString());
					eliminarServicio(index);
					enableDisableButtons();
				}
			}   
		});
		
		
		// creo el botón detallar con un ícono
		ImageIcon imageIconDetailsServicio = new ImageIcon(resourceLoader.load("/images/menu/detail-icon.png"));		
		jButtonDetallarServicio = new JButton( " Detallar", imageIconDetailsServicio );
		jButtonDetallarServicio.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonDetallarServicio.setToolTipText( "Detallar el servicio seleccionado" );
		jButtonDetallarServicio.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int index = Integer.parseInt(jTableServiciosReparacion.getValueAt(jTableServiciosReparacion.getSelectedRow(), 3).toString());
				new DetallarServicio( detallarReparacion, "Detallar servicio", " Detalle del servicio ", agregarServicio, detallarReparacionItSelf, listaServicios.get(index) );
			}
		});
		
		// creo el botón detallar automóvil
		ImageIcon imageIconGoBack = new ImageIcon(resourceLoader.load("/images/menu/go-back-icon.png"));		
		jButtonDetallarAutomovil = new JButton( imageIconGoBack );
		jButtonDetallarAutomovil.setToolTipText( "Ir al detalle del automovil" );
		jButtonDetallarAutomovil.setPreferredSize( new Dimension( 30, 30 ));
		jButtonDetallarAutomovil.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				detallarAutomovil();
			}
		});
		
		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ));
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarReparacion.this.dispose();
			}
		});
		
		/********** inicializamos la tabla de los servicios de las reparaciones *********/
		jTableServiciosReparacion = new JTable(dtmServiciosReparacion);
		jTableServiciosReparacion.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableServiciosReparacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableServiciosReparacion.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableServiciosReparacion.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		jScrollPaneServiciosReparacion = new JScrollPane(jTableServiciosReparacion);
		jTableServiciosReparacion.setPreferredScrollableViewportSize(new Dimension(380,95));
		jScrollPaneServiciosReparacion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		jTableServiciosReparacion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			   if (e.getClickCount() == 2) {
				   JTable target = (JTable)e.getSource();
				   int row = target.getSelectedRow();
				   int index = Integer.parseInt(jTableServiciosReparacion.getValueAt(row, 3).toString());
				   new DetallarServicio( detallarReparacion, "Detallar servicio", " Detalle del servicio ", agregarServicio, detallarReparacionItSelf, listaServicios.get(index) );
			   }
			}
		});
		
		jTableServiciosReparacion.getColumnModel().getColumn(0).setPreferredWidth(300);
		jTableServiciosReparacion.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableServiciosReparacion.getColumnModel().getColumn(0).setMinWidth(0);
		jTableServiciosReparacion.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableServiciosReparacion.getColumnModel().getColumn(0).setResizable(false);
		jTableServiciosReparacion.getColumnModel().getColumn(3).setMaxWidth(0);
		jTableServiciosReparacion.getColumnModel().getColumn(3).setMinWidth(0);
		jTableServiciosReparacion.getColumnModel().getColumn(3).setPreferredWidth(0);
		jTableServiciosReparacion.getColumnModel().getColumn(3).setResizable(false);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		jTableServiciosReparacion.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
		jTableServiciosReparacion.getColumnModel().getColumn(2).setMaxWidth(70);
		
		jTableServiciosReparacion.addKeyListener(new java.awt.event.KeyAdapter() {  
            // realiza el detalle del servicio seleccionado presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                	if(jTableServiciosReparacion.getRowCount() > 0){
	    				int index = Integer.parseInt(jTableServiciosReparacion.getValueAt(jTableServiciosReparacion.getSelectedRow(), 3).toString());
	    				new DetallarServicio( detallarReparacion, "Detallar servicio", " Detalle del servicio ", agregarServicio, detallarReparacionItSelf, listaServicios.get(index) );
	    			}
                }
            }
        });     
		
		}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana DetallarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  DetallarReparacion.this.dispose();
		      }
		    };
		jPanelDetalleReparacion.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	

	//para que la tabla no sea editable
	DefaultTableModel dtmServiciosReparacion = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;
		String nombresColumnas[] = {"ID", "Nombre" ,"Importe", "fila"};
		
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
	
	public void detallarAutomovil(){		
		IAutomovilManager automovilManager = new AutomovilManager();
		Automovil automovil;
		try {
			automovil = automovilManager.getAutomovil(reparacion);
			DetallarReparacion.this.dispose();
			new DetallarAutomovil( detallarReparacion, " Detallar automovil", " Detalle del automovil ", automovil);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void eliminarReparacion(){
		/** Esta clase elimina la reparacion que se está detallando **/

		int option = 0;
		IReparacionManager reparacionManager = new ReparacionManager();
		try {
			option = reparacionManager.eliminar( reparacion );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		switch( option ) {
		case 1:
			this.dispose();
			JOptionPane.showMessageDialog( this, "La reparación ha sido eliminada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE );
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "La reparación no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void eliminarServicio(int index){
		/** este método elimina el cliente cuyo índice en la tabla se pasa como parámetro **/
		
		int option = 0;
		IServicioManager servicioManager = new ServicioManager();
		try {
			option = servicioManager.eliminar( listaServicios.get(index) );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}

		switch( option ) {
		case 1:
			// elimino el servicio de la lista de clientes
			listaServicios.remove(index);
			
			// elimino todos los servicios de la tabla
			dtmServiciosReparacion.getDataVector().removeAllElements();
			dtmServiciosReparacion.fireTableDataChanged();
			
			// una vez eliminado el cliente completo la tabla con la lista de clientes
			completar_tabla(listaServicios.iterator());
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El servicio no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			JOptionPane.showMessageDialog( this, "Este mensaje no debería aparecer", "Ups!", JOptionPane.ERROR_MESSAGE );
		}

	}
	
	public void extraerInfoBD(){
		IServicioManager servicioManager = new ServicioManager();
		try {
			listaServicios = servicioManager.listaServicios(reparacion); // obtengo todos los servicios de la reparacion
			completar_tabla(listaServicios.iterator()); // completa la tabla con la lista de servicios
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void completar_tabla(Iterator<Servicio> iterator){
		// con este método agrego las filas a la tabla
		
		Servicio servicio;
		ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();
		IServicioManager servicioManager = new ServicioManager();
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		
		int numero_fila = 0;
		
		while (iterator.hasNext()){
			servicio = iterator.next();
			String id_fila = Integer.toString(numero_fila++);
			String id_servicio = Integer.toString(servicio.getId_servicio());
			String nombre;
			try {
				nombre = tipoDeServicioManager.getNombre(servicio);
			} catch (Exception e) {
				nombre = "ERROR";
				JOptionPane.showMessageDialog( this, "Error al cargar el nombre de un tipo de servicio", "Error", JOptionPane.ERROR_MESSAGE );
			}
			String importe;
			try {
				importe = formatter.format(servicioManager.importe(servicio));
			} catch (Exception e) {
				importe = "ERROR";
				JOptionPane.showMessageDialog( this, "Error al calcular el importe de un servicio", "Error", JOptionPane.ERROR_MESSAGE );
			}
			Object [] fila= {id_servicio, nombre, importe, id_fila};
			dtmServiciosReparacion.addRow(fila);
		}
	}
	
	public void enableDisableButtons(){ 
		if(jTableServiciosReparacion.getRowCount() > 0){
			jButtonDetallarServicio.setEnabled(true);
			jButtonEliminarServicio.setEnabled(true);
			jTableServiciosReparacion.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableServiciosReparacion.getTableHeader().setBackground(new Color(236,243,255));
			jTableServiciosReparacion.getTableHeader().setForeground(new Color(0,0,0));
			jTableServiciosReparacion.setEnabled(true);

			// calcula la suma para luego mostrar el importe
			jLabelImporte.setEnabled(true);
		}
		else{
			jButtonDetallarServicio.setEnabled(false);
			jButtonEliminarServicio.setEnabled(false);
			jTableServiciosReparacion.getTableHeader().setBackground(new Color(238,238,238));
			jTableServiciosReparacion.getTableHeader().setForeground(new Color(153,153,153));
			jTableServiciosReparacion.setEnabled(false);
			jLabelImporte.setEnabled(false);
		}
		jLabelImporte.setText( "Importe final: " + calcula_importe() );
	
	}
	
/* funcion que toma los importes de cada servicio en forma de string, los 
 * convierte a double para sumarlos y los vuelve a convertir a string para mostrar el importe
 * de la reparación */
	String calcula_importe(){
		double importe = 0;
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		Number number = null;
		for (int i = 0, rows = dtmServiciosReparacion.getRowCount(); i < rows; i++)
		{
			try{
			number = formatter.parse(jTableServiciosReparacion.getValueAt(i, 2).toString());
			}
			catch (ParseException e)  
		    {  
		         System.out.print(e);  
		    }
		    importe += number.doubleValue();
		}
		return formatter.format(importe);
	}
	
	@SuppressWarnings("finally")
	private boolean agregarServicio(){
		ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();

		try {
			if(tipoDeServicioManager.listaTiposDeServicio().isEmpty())
				JOptionPane.showMessageDialog( this, "Debe ingresar al menos un tipo de servicio", "No existen tipos de servicio", JOptionPane.ERROR_MESSAGE );
			else
				new AgregarServicio( detallarReparacion, "Agregar servicio", " Nuevo servicio ", detallarReparacionItSelf, reparacion);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
		finally{
			return servicioAgregado;
		}
	
	}
}