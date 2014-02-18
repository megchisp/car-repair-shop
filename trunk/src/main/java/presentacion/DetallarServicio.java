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
import java.text.NumberFormat;
import java.text.ParseException;
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

import negocio.IManoDeObraManager;
import negocio.IMecanicoManager;
import negocio.IProveedorManager;
import negocio.IRepuestoManager;
import negocio.ITipoDeServicioManager;
import negocio.ManoDeObraManager;
import negocio.MecanicoManager;
import negocio.ProveedorManager;
import negocio.RepuestoManager;
import negocio.TipoDeServicioManager;

import persistencia.ManoDeObra;
import persistencia.Repuesto;
import persistencia.Servicio;

public class DetallarServicio extends JDialog {
	
	private static final long serialVersionUID = 1L;

	DetallarServicio detallarServicioItSelf;
	DetallarReparacion detallarReparacion;
	private JFrame detallarServicio;
	
	JLabel jLabelNombreServicio = null; // JLabel grande
	JLabel jLabelSubtotalManosDeObra = null;
	JLabel jLabelSubtotalRepuestos = null;
	JLabel jLabelImporteTotal = null;
	
	JButton jButtonAgregarManoDeObra = null;
	JButton jButtonModificarManoDeObra = null;
	JButton jButtonEliminarManoDeObra = null;

	JButton jButtonAgregarRepuesto = null;
	JButton jButtonModificarRepuesto = null;
	JButton jButtonEliminarRepuesto = null;
	JButton jButtonCerrar = null;
	
	JTable jTableManosDeObra = null;
	JTable jTableRepuestos = null;
	
	JScrollPane scrollManosDeObra = null;
	JScrollPane scrollRepuestos = null;
	
	JPanel jPanelDetalleServicio = null;
	
	ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();
	Servicio servicio = null;
	List<ManoDeObra> listaManosDeObras = null;
	List<Repuesto> listaRepuestos = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	DetallarServicio( JFrame padre, String titulo, String titleBorder, AgregarServicio agregarServicioPadre, DetallarReparacion detallarReparacionPadre, Servicio servicio ) {
		super( padre, titulo, true );
							// new Dimension( ancho, alto ) 
		this.setPreferredSize( new Dimension( 640, 570 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		detallarServicioItSelf = this;
		detallarReparacion = detallarReparacionPadre;
		this.servicio = servicio;

		/* Panel exterior */
		jPanelDetalleServicio = new JPanel();
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelDetalleServicio.setBorder( titledBorder );
		jPanelDetalleServicio.setPreferredSize(new Dimension(610, 540));
		/* fin panel exterior */

		jPanelDetalleServicio.setLayout( new FlowLayout(FlowLayout.RIGHT, 0,10) );
		
		inicializarVariables();
		extraerInfoBD();
		enableDisableButtons();	
		cerrarEsc();
		
		JPanel jPanelNombreServicio = new JPanel(); // panel que contiene el jLabelNombreServicio
		jPanelNombreServicio.setPreferredSize(new Dimension(620, 25));
		jPanelNombreServicio.setLayout( new FlowLayout(FlowLayout.CENTER,0,0) );
		jPanelNombreServicio.add( jLabelNombreServicio);
		jPanelDetalleServicio.add( jPanelNombreServicio );
		
			/* Panel interior donde se muestran los datos de las manos de obra */
			JPanel jPanelManosDeObra = new JPanel();
			jPanelManosDeObra.setBorder(BorderFactory.createTitledBorder(null, " Lista de manos de obra ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelManosDeObra.setPreferredSize(new Dimension(620, 205));
			
			jPanelManosDeObra.setLayout( new GridBagLayout() );

			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;

			c.gridy = 0;
			c.gridx = 0;
			jPanelManosDeObra.add( scrollManosDeObra, c );
			
				// se crea un panel que contiene los botones de Agregar, Modificar y Eliminar Manos de Obra
				JPanel jPanelBotonesManosDeObra = new JPanel(new GridBagLayout());
				GridBagConstraints g = new GridBagConstraints();
				g.anchor = GridBagConstraints.EAST;
				
				g.gridy = 0;
				g.gridx = 0;
				jPanelBotonesManosDeObra.add( jButtonAgregarManoDeObra, g );
				g.gridy = 0;
				g.gridx = 1;
				g.insets = new Insets( 0, 20, 0, 0 );
				jPanelBotonesManosDeObra.add( jButtonModificarManoDeObra, g );
				g.gridy = 0;
				g.gridx = 2;
				g.insets = new Insets( 0, 20, 0, 0 );
				jPanelBotonesManosDeObra.add( jButtonEliminarManoDeObra, g );
				g.gridy = 0;
				g.gridx = 3;
				g.insets = new Insets( 0, 50, 0, 0 );
				jPanelBotonesManosDeObra.add( jLabelSubtotalManosDeObra, g );
				
			c.gridy = 1;
			c.gridx = 0;
			jPanelManosDeObra.add( new JLabel(" "), c );
			c.gridy = 2;
			c.gridx = 0;
			jPanelManosDeObra.add( jPanelBotonesManosDeObra, c );
			c.gridy = 3;
			c.gridx = 0;
			jPanelManosDeObra.add( new JLabel(" "), c );
				
								/* fin panel interior */
	

			
				/* Panel interior donde se muestran los repuestos del servicio*/
				JPanel jPanelRepuestos = new JPanel();
				jPanelRepuestos.setBorder(BorderFactory.createTitledBorder(null, " Lista de repuestos ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
						new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
				jPanelRepuestos.setPreferredSize(new Dimension(620, 205));
				
				jPanelRepuestos.setLayout( new GridBagLayout() );
				/* fin panel interior */

				GridBagConstraints d = new GridBagConstraints();
				d.anchor = GridBagConstraints.WEST;
	
				d.gridy = 0;
				d.gridx = 0;
				jPanelRepuestos.add( scrollRepuestos, d );
				
					// se crea un panel que contiene los botones de Agregar, Modificar y Eliminar Repuesto
					JPanel jPanelBotonesRepuestos = new JPanel(new GridBagLayout());
					GridBagConstraints h = new GridBagConstraints();
					h.anchor = GridBagConstraints.EAST;
					
					h.gridy = 0;
					h.gridx = 0;
					jPanelBotonesRepuestos.add( jButtonAgregarRepuesto,h );
					h.gridy = 0;
					h.gridx = 1;
					h.insets = new Insets( 0, 20, 0, 0 );
					jPanelBotonesRepuestos.add( jButtonModificarRepuesto, h );
					h.gridy = 0;
					h.gridx = 2;
					h.insets = new Insets( 0, 20, 0, 0 );
					jPanelBotonesRepuestos.add( jButtonEliminarRepuesto, h );
					h.gridy = 0;
					h.gridx = 3;
					h.insets = new Insets( 0, 70, 0, 0 );
					jPanelBotonesRepuestos.add( jLabelSubtotalRepuestos, h );
					
				d.gridy = 1;
				d.gridx = 0;
				jPanelRepuestos.add( new JLabel(" "), d );
				d.gridy = 2;
				d.gridx = 0;
				jPanelRepuestos.add( jPanelBotonesRepuestos, d );
				d.gridy = 3;
				d.gridx = 0;
				jPanelRepuestos.add( new JLabel(" "), d );
					
					

		jPanelDetalleServicio.add( jPanelManosDeObra);

		jPanelDetalleServicio.add( new JLabel(" "));

		jPanelDetalleServicio.add( jPanelRepuestos);

		jPanelDetalleServicio.add( new JLabel(" "));
		
			JPanel jPanelLabelTotal = new JPanel(); // panel que contiene el jLabelTotal
			jPanelLabelTotal.setPreferredSize(new Dimension(516, 25));
			jPanelLabelTotal.setLayout( new FlowLayout(FlowLayout.LEFT,0,0) );
			jPanelLabelTotal.add( jLabelImporteTotal );
		jPanelDetalleServicio.add( jPanelLabelTotal);	
			
		jPanelDetalleServicio.add( jButtonCerrar);
		jPanelDetalleServicio.add( new JLabel(" "));
					
		this.getContentPane().add( jPanelDetalleServicio, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana DetallarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  DetallarServicio.this.dispose();
		      }
		    };
		jPanelDetalleServicio.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void inicializarVariables() {
		Font fontJLabel = new Font("Tahoma", Font.PLAIN, 12);
		
		// recupero el nombre del servicio
		String nombreServicio;
		try {
			nombreServicio = tipoDeServicioManager.getNombre(servicio);
		} catch (Exception e1) {
			nombreServicio = "DESCONOCIDO";
		}
		
		jLabelNombreServicio = new JLabel( nombreServicio );
		jLabelNombreServicio.setFont(new Font("Dialog", Font.BOLD, 20));
		
		jLabelSubtotalManosDeObra = new JLabel();
		jLabelSubtotalManosDeObra.setFont(fontJLabel);
		jLabelSubtotalRepuestos = new JLabel();
		jLabelSubtotalRepuestos.setFont(fontJLabel);
		jLabelImporteTotal = new JLabel();
		jLabelImporteTotal.setFont(fontJLabel);
		
		jButtonEliminarRepuesto = new JButton( "Eliminar" );
		jButtonEliminarRepuesto.setPreferredSize( new Dimension( 100, 30 ) );
		
//		// creo el botón agregar repuesto con un ícono
		ImageIcon imageIconAddReplacement = new ImageIcon(resourceLoader.load("/images/menu/add-icon.png"));
		jButtonAgregarRepuesto = new JButton( " Agregar", imageIconAddReplacement );
		jButtonAgregarRepuesto.setToolTipText("Agregar un nuevo repuesto");
		jButtonAgregarRepuesto.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAgregarRepuesto.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new AgregarRepuesto( detallarServicio, "Agregar repuesto", "Nuevo repuesto", detallarServicioItSelf, servicio );
				enableDisableButtons();
			}
		});
		
		// creo el botón modificar repuesto con un ícono
		ImageIcon imageIconUpdateReplacement = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificarRepuesto = new JButton( " Modificar", imageIconUpdateReplacement );
		jButtonModificarRepuesto.setToolTipText("Modificar el repuesto seleccionado");
		jButtonModificarRepuesto.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificarRepuesto.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new ModificarRepuesto( detallarServicio, "Modificar repuesto", "Modificación del repuesto", detallarServicioItSelf, listaRepuestos.get( jTableRepuestos.getSelectedRow() ) );
				actualizarImportes();
			}
		});
		
		// creo el botón modificar repuesto con un ícono
		ImageIcon imageIconDeleteReplacement = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminarRepuesto = new JButton( " Eliminar", imageIconDeleteReplacement );
		jButtonEliminarRepuesto.setToolTipText("Eliminar el repuesto seleccionado");
		jButtonEliminarRepuesto.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminarRepuesto.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el repuesto seleccionado?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarRepuesto();
					enableDisableButtons();
				}
			}
		});  
		
		// creo el botón modificar mano de obra con un ícono
		ImageIcon imageIconUpdateWorkforce= new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificarManoDeObra = new JButton( " Modificar", imageIconUpdateWorkforce );
		jButtonModificarManoDeObra.setToolTipText("Modificar la mano de obra seleccionada");
		jButtonModificarManoDeObra.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificarManoDeObra.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			new ModificarManoDeObra( detallarServicio, "Modificar mano de obra", "Modificación de mano de obra", detallarServicioItSelf, listaManosDeObras.get( jTableManosDeObra.getSelectedRow() ) );
			actualizarImportes();
		}
	});
		

		// creo el botón modificar mano de obra con un ícono
		ImageIcon imageIconDeleteWorkforce= new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminarManoDeObra = new JButton( " Eliminar", imageIconDeleteWorkforce );
		jButtonEliminarManoDeObra.setToolTipText("Eliminar la mano de obra seleccionada");
		jButtonEliminarManoDeObra.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminarManoDeObra.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la mano de obra seleccionada?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarManoDeObra();
					enableDisableButtons();
				}
			}
		});  
		
//		// creo el botón agregar mano de obra con un ícono
		ImageIcon imageIconAddWorkforce = new ImageIcon(resourceLoader.load("/images/menu/add-icon.png"));
		jButtonAgregarManoDeObra = new JButton( " Agregar", imageIconAddWorkforce );
		jButtonAgregarManoDeObra.setToolTipText("Agregar una nueva mano de obra");
		jButtonAgregarManoDeObra.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAgregarManoDeObra.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new AgregarManoDeObra( detallarServicio, "Agregar mano de obra", "Nueva mano de obra", detallarServicioItSelf, servicio );
				enableDisableButtons();
			}
		});
		
		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				DetallarServicio.this.dispose();
			}
		});
		
		
		/************ inicializamos la tabla de las Manos de Obras *************/
		jTableManosDeObra = new JTable(dtmManosDeObra);
		jTableManosDeObra.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableManosDeObra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableManosDeObra.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeader = jTableManosDeObra.getTableHeader();
		jTableHeader.setBackground(new Color(236,243,255));
		scrollManosDeObra = new JScrollPane(jTableManosDeObra);
		jTableManosDeObra.setPreferredScrollableViewportSize(new Dimension(570,95));
		scrollManosDeObra.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// configuro el doble click sobre la fila
		jTableManosDeObra.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         new ModificarManoDeObra( detallarServicio, "Modificar mano de obra", "Modificación de mano de obra", detallarServicioItSelf, listaManosDeObras.get( row ) );
						actualizarImportes();
			         }
			   }
			});
		
		// Tamaño a las columnas
		jTableManosDeObra.getColumnModel().getColumn(1).setPreferredWidth(150);
		jTableManosDeObra.getColumnModel().getColumn(2).setPreferredWidth(50);
		jTableManosDeObra.getColumnModel().getColumn(3).setMaxWidth(35);
		jTableManosDeObra.getColumnModel().getColumn(4).setPreferredWidth(0);
		
		// Oculto la columna ID para que el usuario no la vea
		jTableManosDeObra.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableManosDeObra.getColumnModel().getColumn(0).setMinWidth(0);
		jTableManosDeObra.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableManosDeObra.getColumnModel().getColumn(0).setResizable(false);
		
		// Justificación a la derecha del contenido de la columna 4
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		jTableManosDeObra.getColumnModel().getColumn(4).setCellRenderer( rightRenderer );
		
		// Justificación al medio del contenido de las columnas 2 y 3
		DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
		centerRenderer1.setHorizontalAlignment( JLabel.CENTER );
		jTableManosDeObra.getColumnModel().getColumn(2).setCellRenderer( centerRenderer1 );
		jTableManosDeObra.getColumnModel().getColumn(3).setCellRenderer( centerRenderer1 );
		/********************** FIN tabla de Manos de Obras ********************/
		
		
		/************ inicializamos la tabla de Repuestos *************/
		jTableRepuestos = new JTable(dtmRepuestos);
		jTableRepuestos.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableRepuestos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableRepuestos.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeader2 = jTableRepuestos.getTableHeader();
		jTableHeader2.setBackground(new Color(236,243,255));
		scrollRepuestos = new JScrollPane(jTableRepuestos);
		jTableRepuestos.setPreferredScrollableViewportSize(new Dimension(570,95));
		scrollRepuestos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// configuro el doble click sobre la fila
		jTableRepuestos.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         new ModificarRepuesto( detallarServicio, "Modificar repuesto", "Modificación del repuesto", detallarServicioItSelf, listaRepuestos.get( row ) );
			         actualizarImportes();
			      }
			   }
			});
		
		// Oculto la columna ID para que el usuario no la vea
		jTableRepuestos.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableRepuestos.getColumnModel().getColumn(0).setMinWidth(0);
		jTableRepuestos.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableRepuestos.getColumnModel().getColumn(0).setResizable(false);
		
		jTableRepuestos.getColumnModel().getColumn(3).setMaxWidth(75);
		jTableRepuestos.getColumnModel().getColumn(4).setMaxWidth(40);
		jTableRepuestos.getColumnModel().getColumn(5).setMaxWidth(35);
		jTableRepuestos.getColumnModel().getColumn(6).setMaxWidth(75);
		
		// Justificación a la derecha del contenido de la columna 6
		jTableRepuestos.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
		jTableRepuestos.getColumnModel().getColumn(6).setCellRenderer( rightRenderer );
		
		// centro columna 2, 4 y 5
		jTableRepuestos.getColumnModel().getColumn(2).setCellRenderer( centerRenderer1 );
		jTableRepuestos.getColumnModel().getColumn(4).setCellRenderer( centerRenderer1 );
		jTableRepuestos.getColumnModel().getColumn(5).setCellRenderer( centerRenderer1 );
		
		/********************** FIN tabla de Repuestos ********************/
		
	}
	

	//para que la tabla no sea editable
			DefaultTableModel dtmRepuestos = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				String nombresColumnasRepuestos[] = {"ID", "Nombre", "Proveedor", "P. unitario", "Cant.", "Obs.", "Precio"};
				
				@Override 
				public int getColumnCount() { 
					return nombresColumnasRepuestos.length; 
				}

				@Override 
				public String getColumnName(int index) { 
					return nombresColumnasRepuestos[index]; 
				} 

				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
				
			};
			
	//para que la tabla no sea editable
		DefaultTableModel dtmManosDeObra = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnasManosDeObra[] = {"ID", "Nombre" ,"Realizada por", "Obs.", "Precio"};
			
			@Override 
			public int getColumnCount() { 
				return nombresColumnasManosDeObra.length; 
			}

			@Override 
			public String getColumnName(int index) { 
				return nombresColumnasManosDeObra[index]; 
			} 

			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
			
		};

	private void enableDisableButtons(){ 
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		double totalServicios = 0;
		
		if(jTableManosDeObra.getRowCount() > 0){
			jButtonModificarManoDeObra.setEnabled(true);
			jButtonEliminarManoDeObra.setEnabled(true);
			jTableManosDeObra.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableManosDeObra.getTableHeader().setBackground(new Color(236,243,255));
			jTableManosDeObra.getTableHeader().setForeground(new Color(0,0,0));
			jTableManosDeObra.setEnabled(true);
			
			// calcula la suma para luego mostrar el importe
			jLabelSubtotalManosDeObra.setText( "Total de mano de obra: " + formatter.format(calcula_importe_ManoDeObra()) );
			jLabelSubtotalManosDeObra.setEnabled(true);
			
		}
		else{
			jButtonModificarManoDeObra.setEnabled(false);
			jButtonEliminarManoDeObra.setEnabled(false);
			jTableManosDeObra.getTableHeader().setBackground(new Color(238,238,238));
			jTableManosDeObra.getTableHeader().setForeground(new Color(153,153,153));
			jTableManosDeObra.setEnabled(false);
			
			jLabelSubtotalManosDeObra.setText( "Total de mano de obra: " + formatter.format(calcula_importe_ManoDeObra()) );
			jLabelSubtotalManosDeObra.setEnabled(false);
		}
		
		if(jTableRepuestos.getRowCount() > 0){
			jButtonModificarRepuesto.setEnabled(true);
			jButtonEliminarRepuesto.setEnabled(true);
			jTableRepuestos.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableRepuestos.getTableHeader().setBackground(new Color(236,243,255));
			jTableRepuestos.getTableHeader().setForeground(new Color(0,0,0));
			jTableRepuestos.setEnabled(true);
			
			// calcula la suma para luego mostrar el importe
			jLabelSubtotalRepuestos.setText( "Total de repuestos: " + formatter.format(calcula_importe_Repuestos()) );
			jLabelSubtotalRepuestos.setEnabled(true);
			
		}
		else{
			jButtonModificarRepuesto.setEnabled(false);
			jButtonEliminarRepuesto.setEnabled(false);
			jTableRepuestos.getTableHeader().setBackground(new Color(238,238,238));
			jTableRepuestos.getTableHeader().setForeground(new Color(153,153,153));
			jTableRepuestos.setEnabled(false);
			
			jLabelSubtotalRepuestos.setText( "Total de repuestos: " + formatter.format(calcula_importe_Repuestos()) );
			jLabelSubtotalRepuestos.setEnabled(false);
		}
		
		// actualizo importe del servicio
		totalServicios = calcula_importe_ManoDeObra()  + calcula_importe_Repuestos();
		jLabelImporteTotal.setText("Importe total del servicio: " + formatter.format(totalServicios) );

		
		// actualiza el importe total en la tabla de detallarReparacion
		int filaSeleccionada = detallarReparacion.jTableServiciosReparacion.getSelectedRow();
		detallarReparacion.jTableServiciosReparacion.setValueAt(formatter.format(totalServicios), filaSeleccionada, 2);
		detallarReparacion.jLabelImporte.setText( "Importe final: " + detallarReparacion.calcula_importe() );
	}
	
	private void actualizarImportes(){
		// este metodo se utiliza solo para cuando se modifica una mano de obra o un repuesto
		// para cuando se agrega o elimina se utiliza enableDisableButtons()
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		double totalServicios = 0;
	
		// calcula la suma para luego mostrar el importe
		jLabelSubtotalManosDeObra.setText( "Total de mano de obra: " + formatter.format(calcula_importe_ManoDeObra()) );
		totalServicios = calcula_importe_ManoDeObra()  + calcula_importe_Repuestos();
		jLabelImporteTotal.setText("Importe total del servicio: " + formatter.format(totalServicios) );
		jLabelSubtotalManosDeObra.setEnabled(true);
	
		// calcula la suma para luego mostrar el importe
		jLabelSubtotalRepuestos.setText( "Total de repuestos: " + formatter.format(calcula_importe_Repuestos()) );
		totalServicios = calcula_importe_ManoDeObra()  + calcula_importe_Repuestos();
		jLabelImporteTotal.setText("Importe total del servicio: " + formatter.format(totalServicios) );
		jLabelSubtotalRepuestos.setEnabled(true);
	

		// actualiza el importe total en la tabla de detallarReparacion
		int filaSeleccionada = detallarReparacion.jTableServiciosReparacion.getSelectedRow();
		detallarReparacion.jTableServiciosReparacion.setValueAt(formatter.format(totalServicios), filaSeleccionada, 2);
		detallarReparacion.jLabelImporte.setText( "Importe final: " + detallarReparacion.calcula_importe() );
	}
	
	private void eliminarManoDeObra(){
		/** Esta clase elimina la mano de obra seleccionada **/
		IManoDeObraManager manoDeObraManager = new ManoDeObraManager();
		
		int option = 0;

		try {
			option = manoDeObraManager.eliminar( listaManosDeObras.get(jTableManosDeObra.getSelectedRow()) );
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		switch( option ) {
		case 1:
			// elimino la mano de obra de la lista de manos de obras
			listaManosDeObras.remove(jTableManosDeObra.getSelectedRow());
			
			// elimino todas las manos de obra de la tabla
			dtmManosDeObra.getDataVector().removeAllElements();
			dtmManosDeObra.fireTableDataChanged();
			
			// una vez eliminada la mano de obra completo la tabla con la lista de manos de obras
			completar_tabla_mano_de_obra((listaManosDeObras.iterator()));
			
			// actualizo el importe final de la reparacion
			detallarReparacion.jLabelImporte.setText( "Importe final: " + detallarReparacion.calcula_importe() );
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "La mano de obra no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			// este mensaje nunca debería aparecer
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
		
	}
	
	private void eliminarRepuesto(){
		/** Esta clase elimina el repuesto seleccionado **/
		IRepuestoManager repuestoManager = new RepuestoManager();
		
		int option = 0;

		try {
			option = repuestoManager.eliminar( listaRepuestos.get(jTableRepuestos.getSelectedRow() ));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		switch( option ) {
		case 1:
			// elimino la mano de obra de la lista de repuestos
			listaRepuestos.remove(jTableRepuestos.getSelectedRow());
			
			// elimino todos los repuestos de la tabla
			dtmRepuestos.getDataVector().removeAllElements();
			dtmRepuestos.fireTableDataChanged();
			
			// una vez eliminado el repuesto completo la tabla con la lista de repuestos
			completar_tabla_repuesto((listaRepuestos.iterator()));
			
			// actualizo el importe final de la reparacion
			detallarReparacion.jLabelImporte.setText( "Importe final: " + detallarReparacion.calcula_importe() );
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El repuesto no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			// este mensaje nunca debería aparecer
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
		
	}
	
	public void extraerInfoBD(){
		IManoDeObraManager manoDeObraManager = new ManoDeObraManager();
		try {
			listaManosDeObras = manoDeObraManager.listaManosDeObras(servicio); // obtengo todas las manos de obras del servicio
			completar_tabla_mano_de_obra(listaManosDeObras.iterator()); // completa la tabla con la lista de manos de obras
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
		
		IRepuestoManager repuestoManager = new RepuestoManager();
		try {
			listaRepuestos = repuestoManager.listaRepuestos(servicio); // obtengo todas las manos de obras del servicio
			completar_tabla_repuesto(listaRepuestos.iterator()); // completa la tabla con la lista de repuestos
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void completar_tabla_mano_de_obra(Iterator<ManoDeObra> iteratorManoDeObra){
		// con este método agrego las filas a la tabla
		
		ManoDeObra manoDeObra;
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		IMecanicoManager mecanicoManager = new MecanicoManager();
		
		while (iteratorManoDeObra.hasNext()){
			manoDeObra = iteratorManoDeObra.next();
			String id_mano_de_obra = Integer.toString(manoDeObra.getId_mano_de_obra());
			String nombre = manoDeObra.getNombre();
			String precio = formatter.format(manoDeObra.getPrecio());
			String conObservaciones = manoDeObra.getObservaciones().isEmpty() ? "" : "\u2713";
			String mecanico;
			try {
				mecanico = manoDeObra.getId_mecanico() == 0 ? "< sin especificar >" : (mecanicoManager.getMecanico(manoDeObra.getId_mecanico()).getNombre() + " " + mecanicoManager.getMecanico(manoDeObra.getId_mecanico()).getApellido());
			} catch (Exception e) {
				// no se deberia entrar a este catch si todo va bien
				JOptionPane.showMessageDialog( this, "Error al cargar mecánico", "Error", JOptionPane.ERROR_MESSAGE );
				mecanico = "ERROR";
			}
			Object [] fila= {id_mano_de_obra, nombre, mecanico, conObservaciones, precio};
			dtmManosDeObra.addRow(fila);
		}
	}
	
	private void completar_tabla_repuesto(Iterator<Repuesto> iteratorRepuesto){
		Repuesto repuesto;
		NumberFormat formatterRepuesto = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		IProveedorManager proveedorManager = new ProveedorManager();
		
		while (iteratorRepuesto.hasNext()){
			repuesto = iteratorRepuesto.next();
			String id_repuesto = Integer.toString(repuesto.getId_repuesto());
			String nombre = repuesto.getNombre();
			String precio_unitario = formatterRepuesto.format(repuesto.getPrecioUnitario());
			String cantidad = Integer.toString(repuesto.getCantidad());
			String precio_total = formatterRepuesto.format(repuesto.getPrecioUnitario() * repuesto.getCantidad());
			String conObservaciones = repuesto.getObservaciones().isEmpty() ? "" : "\u2713";
			String proveedor;
			try {
				proveedor = repuesto.getId_proveedor() == 0 ? "< sin especificar >" : (proveedorManager.getProveedor(repuesto.getId_proveedor()).getNombre());
			} catch (Exception e) {
				// no se deberia entrar a este catch si todo va bien
				JOptionPane.showMessageDialog( this, "Error al cargar proveedor", "Error", JOptionPane.ERROR_MESSAGE );
				proveedor = "ERROR";
			}
			Object [] fila= {id_repuesto, nombre, proveedor, precio_unitario, cantidad, conObservaciones, precio_total};
			dtmRepuestos.addRow(fila);
		}
		
	}

	double calcula_importe_ManoDeObra(){
		/* funcion que toma los importes de cada mano de obra en forma de string, los 
		 * convierte a double para sumarlos y los vuelve a convertir a string para mostrar el importe
		 * de la mano de obra */

		double importe = 0;
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		Number number = null;
		for (int i = 0, rows = dtmManosDeObra.getRowCount(); i < rows; i++)
		{
			try{
				number = formatter.parse(jTableManosDeObra.getValueAt(i, 4).toString());
				importe += number.doubleValue();
			}
			catch (ParseException e)  
			{  
				e.printStackTrace();
			}
		}
		return importe;
	}

	/* funcion que toma los importes de cada repuesto en forma de string, los 
	 * convierte a double para sumarlos y los vuelve a convertir a string para mostrar el importe
	 * de la reparación */
	double calcula_importe_Repuestos(){
		double importe = 0;
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		Number number = null;
		for (int i = 0, rows = dtmRepuestos.getRowCount(); i < rows; i++)
		{
			try{
				number = formatter.parse(jTableRepuestos.getValueAt(i, 6).toString());
				importe += number.doubleValue();
			}
			catch (ParseException e)  
			{  
				System.out.print(e);  
			}
		}
		return importe;
	}


}


