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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import negocio.ITipoDeServicioManager;
import negocio.TipoDeServicioManager;

import persistencia.TipoDeServicio;

public class BuscarTipoServicio extends JDialog {
	private static final long serialVersionUID = 1L;

	BuscarTipoServicio buscarTipoServicioItSelf;
	
	private JFrame buscarTipoServicio;
	JPanel jPanelBuscarTipoServicio = null;
	JLabel jLabelNombre = null;
	JLabel jLabelContador = null;
		
	JTextField jTextFieldNombre = null;
				
	JButton jButtonBuscar = null;
	JButton jButtonModificar = null;
	JButton jButtonEliminar = null;
	JButton jButtonCerrar = null;
	
	JPanel jPanelResultadoDeBusqueda = null; // contiene jTableProveedor
	JPanel jPanelCriterioDeBusqueda = null; // contiene jLabelNombre, jTextFieldNombre y jButtonEliminar
	
	JTable jTableBuscarTipoServicio = null;
	JScrollPane jScrollPaneBuscarTipoServicio = null;
	
	List<TipoDeServicio> listaTiposDeServicio = null;
	
	//para que la tabla no sea editable
	DefaultTableModel dtmTipoServicio = inicializoTableModel();
	
	// variables para realizar el filtrado
	TableRowSorter<DefaultTableModel> tableRowSorter= new TableRowSorter<DefaultTableModel>(dtmTipoServicio);
	ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
	RowFilter<Object,Object> tableRowSorterNombre = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();

	public BuscarTipoServicio( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 570, 415 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		buscarTipoServicioItSelf = this;
		
		jPanelBuscarTipoServicio = new JPanel();
		jPanelBuscarTipoServicio.setFocusable(true);
		
		
		jPanelBuscarTipoServicio.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		inicializarVariables();
		extraerInfoBD();
		enableDisableButtons();
		cerrarEsc();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelBuscarTipoServicio.setBorder( titledBorder );
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelBuscarTipoServicio.add( new JLabel(" "), a );

		/**********INICIO Generacion de jPanelCriterioDeBusqueda********/
		
		jPanelCriterioDeBusqueda = new JPanel();
		jPanelCriterioDeBusqueda.setLayout( new FlowLayout(FlowLayout.CENTER, 18,5) );
		jPanelCriterioDeBusqueda.setBorder(BorderFactory.createTitledBorder(null, " Criterio de búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
				new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
		jPanelCriterioDeBusqueda.setPreferredSize(new Dimension(520, 70)); // tamaño del panel
		
			jPanelCriterioDeBusqueda.add( jLabelNombre);
			jPanelCriterioDeBusqueda.add( jTextFieldNombre);
			jPanelCriterioDeBusqueda.add( jButtonBuscar);

		
	a.gridy = 1;
	a.gridx = 0;
	a.gridwidth = 2;
	a.insets = new Insets( 0, 0, 0, 0 );
	jPanelBuscarTipoServicio.add( jPanelCriterioDeBusqueda, a );
		
		/**********FIN Generacion de jPanelCriterioDeBusqueda*******/
		
		a.gridy = 2;
		a.gridx = 0;
		jPanelBuscarTipoServicio.add( new JLabel(" "), a );

			/**********INICIO Generacion de jPanelResultadoDeBusqueda********/
			
			jPanelResultadoDeBusqueda.setPreferredSize(new Dimension(520, 209)); // Dimensiones del panel
			jPanelResultadoDeBusqueda.setLayout( new GridBagLayout() );
			GridBagConstraints f = new GridBagConstraints();
			f.anchor = GridBagConstraints.WEST;
	
		
			jTableBuscarTipoServicio.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					jButtonEliminar.setEnabled(true);
					jButtonModificar.setEnabled(true);
				}
			});
			
			
			f.gridy = 0;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add(jScrollPaneBuscarTipoServicio, f);
			
			f.gridy = 1;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add( new JLabel(" "), f );
			
				// se crea un panel que contiene los botones de Modificar y Eliminar
				JPanel jPanelBotones = new JPanel(new GridBagLayout());
				GridBagConstraints g = new GridBagConstraints();
				g.anchor = GridBagConstraints.WEST;
				
				g.gridy = 0;
				g.gridx = 0;
				jPanelBotones.add( jButtonModificar);
				g.gridy = 0;
				g.gridx = 1;
				g.insets = new Insets( 0, 20, 0, 0 );
				jPanelBotones.add( jButtonEliminar,g);
		
			f.gridy = 2;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add(jPanelBotones, f);
			
			f.gridy = 3;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add( new JLabel(" "), f );
	
			
		a.gridy = 3;
		a.gridx = 0;
		jPanelBuscarTipoServicio.add(jPanelResultadoDeBusqueda, a); // agregamos la tabla que contiene los tipo de servicios
			
		a.gridy = 4;
		a.gridx = 0;
		jPanelBuscarTipoServicio.add( new JLabel(" "), a );	
			
		/**********FIN Generacion de jPanelResultadoDeBusqueda********/
		
		a.gridy = 6;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 315 );
		jPanelBuscarTipoServicio.add(jLabelContador, a);
		
		a.gridy = 6;
		a.gridx = 1;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelBuscarTipoServicio.add( jButtonCerrar, a );
		
		a.gridy = 7;
		a.gridx = 0;
		jPanelBuscarTipoServicio.add( new JLabel(" "), a );
		
			
		this.getContentPane().add( jPanelBuscarTipoServicio, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarVariables() {	
		// panel que contiene la tabla de tipos de servicios
		jPanelResultadoDeBusqueda = new JPanel();
		
		// hace que el cursor se posicione en el jTextFieldNombre al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextFieldNombre.requestFocusInWindow();
			}
		});
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( new Dimension( 53, 25 ) );
		jTextFieldNombre = new JTextField();
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
		jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {              
	         // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarTipoServicio();
                   }
                }
        });

		// inicializo el jLabel que muestra el contador de registros
        jLabelContador = new JLabel();
        jLabelContador.setPreferredSize(new Dimension( 200, 25 ));

		jTableBuscarTipoServicio = new JTable(dtmTipoServicio);
		jTableBuscarTipoServicio.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableBuscarTipoServicio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableBuscarTipoServicio.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableBuscarTipoServicio.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		jScrollPaneBuscarTipoServicio = new JScrollPane(jTableBuscarTipoServicio);
		jTableBuscarTipoServicio.setPreferredScrollableViewportSize(new Dimension(465,95));
		jScrollPaneBuscarTipoServicio.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// configuro el doble click sobre la fila
		jTableBuscarTipoServicio.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         BuscarTipoServicio.this.dispose();
			         new ModificarTipoDeServicio( buscarTipoServicio, "Modificar tipo de servicio", "Modificación del tipo de servicio", listaTiposDeServicio.get(Integer.parseInt(jTableBuscarTipoServicio.getValueAt(row, 4).toString())) );			      }
			   }
			});
		
		// Tamaño de la columna Nombre
		jTableBuscarTipoServicio.getColumnModel().getColumn(0).setPreferredWidth(125);

		// Justificación a la derecha del contenido de las columnas 2 y 3
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		jTableBuscarTipoServicio.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
		jTableBuscarTipoServicio.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );

		// Oculto la columna ID y Fila para que el usuario no la vea
		jTableBuscarTipoServicio.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableBuscarTipoServicio.getColumnModel().getColumn(0).setMinWidth(0);
		jTableBuscarTipoServicio.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableBuscarTipoServicio.getColumnModel().getColumn(0).setResizable(false);
		jTableBuscarTipoServicio.getColumnModel().getColumn(4).setMaxWidth(0);
		jTableBuscarTipoServicio.getColumnModel().getColumn(4).setMinWidth(0);
		jTableBuscarTipoServicio.getColumnModel().getColumn(4).setPreferredWidth(0);
		jTableBuscarTipoServicio.getColumnModel().getColumn(4).setResizable(false);
		
		ImageIcon imageIconSearch = new ImageIcon(resourceLoader.load("/images/menu/search-icon.png"));
		jButtonBuscar = new JButton( "Buscar", imageIconSearch );
		jButtonBuscar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarTipoServicio();
			}
		});
		
		ImageIcon imageIconUpdate = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificar = new JButton( " Modificar", imageIconUpdate );
		jButtonModificar.setToolTipText("Modificar el tipo de servicio seleccionado");
		jButtonModificar.setEnabled(false);
		jButtonModificar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cleanAllFields();
				BuscarTipoServicio.this.dispose();
				new ModificarTipoDeServicio( buscarTipoServicio, "Modificar tipo de servicio", "Modificación del tipo de servicio", listaTiposDeServicio.get(Integer.parseInt(jTableBuscarTipoServicio.getValueAt(jTableBuscarTipoServicio.getSelectedRow(), 4).toString())) );
			}
		});
		
		ImageIcon imageIconDelete = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminar = new JButton( "Eliminar", imageIconDelete );
		jButtonEliminar.setToolTipText("Eliminar el tipo de servicio seleccionado");
		jButtonEliminar.setEnabled(false);
		jButtonEliminar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el tipo de servicio '" + getNombreTipoServicio(jTableBuscarTipoServicio.getSelectedRow()) + "'?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarTipoDeServicio(Integer.parseInt(jTableBuscarTipoServicio.getValueAt(jTableBuscarTipoServicio.getSelectedRow(), 4).toString()));
					enableDisableButtons();
				}

			}   
		});
		
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cerrarBuscarTipoServicio();
		}
	});
		
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarTipoServicio();
			}
		});
			
		
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarCliente se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  BuscarTipoServicio.this.dispose();
		      }
		    };
		jPanelBuscarTipoServicio.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void eliminarTipoDeServicio(int index){
		/** este método elimina el tipo de servicio cuyo índice en la tabla se pasa como parámetro **/
		
		int option = 0;
		ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();
		try {
			option = tipoDeServicioManager.eliminar( listaTiposDeServicio.get(index) );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}

		switch( option ) {
		case 1:
			// elimino el tipo de servicio de la lista de tipos de servicio
			listaTiposDeServicio.remove(index);
			
			// elimino todos los tipos de servicio de la tabla
			dtmTipoServicio.getDataVector().removeAllElements();
			dtmTipoServicio.fireTableDataChanged();
			
			// una vez eliminado el tipo de servicio, completo la tabla con la lista de tipos de servicio
			completar_tabla(listaTiposDeServicio.iterator());
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El tipo de servicio no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		case 3:
			JOptionPane.showMessageDialog( this, "El tipo de servicio se encuentra siendo utilizado en al menos una reparación", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			JOptionPane.showMessageDialog( this, "Este mensaje no debería aparecer", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void enableDisableButtons(){ // habilita o deshabilita los botones según hayan filas en la tabla
		TitledBorder titledBorder;
		if(jTableBuscarTipoServicio.getRowCount() > 0){
			jButtonEliminar.setEnabled(true);
			jButtonModificar.setEnabled(true);
			jTableBuscarTipoServicio.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableBuscarTipoServicio.getTableHeader().setBackground(new Color(236,243,255));
			jTableBuscarTipoServicio.getTableHeader().setForeground(new Color(0,0,0));
			jPanelResultadoDeBusqueda.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Lista de tipos de servicios ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
		else{
			jButtonEliminar.setEnabled(false);
			jButtonModificar.setEnabled(false);
			jTableBuscarTipoServicio.getTableHeader().setBackground(new Color(238,238,238));
			jTableBuscarTipoServicio.getTableHeader().setForeground(new Color(153,153,153));
			jPanelResultadoDeBusqueda.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Lista de tipos de servicios ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
	}

	// función que rescata el nombre del tipo de servicio de la fila seleccionada
    private String getNombreTipoServicio(int rowIndex)  
	{  
	    String s = "";  
	    Object o = jTableBuscarTipoServicio.getValueAt(rowIndex, 1);  
	    s += o;
	    return s;  
	}  
    
    private void extraerInfoBD(){
    	ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();
    	try {
			listaTiposDeServicio = tipoDeServicioManager.listaTiposDeServicio(); // obtengo todos los tipos de servicio de la BD
			completar_tabla(listaTiposDeServicio.iterator()); // completa la tabla con la lista de tipos de servicio
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
    }
	
    private void completar_tabla(Iterator<TipoDeServicio> iterator){
    	// con este método agrego las filas a la tabla
    	TipoDeServicio tipoDeServicio;
    	int numero_fila = 0;
    	while (iterator.hasNext()){
    		tipoDeServicio = iterator.next();
    		String id_fila = Integer.toString(numero_fila++);
    		String id_tipo_de_servicio = Integer.toString(tipoDeServicio.getId_tipo_servicio());
    		String nombre = tipoDeServicio.getNombre();
    		String min = String.format("%dh %02d'", tipoDeServicio.getTiempoMinimoReparacion() / 60, tipoDeServicio.getTiempoMinimoReparacion() % 60);
    		String max = String.format("%dh %02d'", tipoDeServicio.getTiempoMaximoReparacion() / 60, tipoDeServicio.getTiempoMaximoReparacion() % 60);
    		Object [] fila= {id_tipo_de_servicio, nombre, min, max, id_fila};
    		dtmTipoServicio.addRow(fila);
    	}
    }
    
    private DefaultTableModel inicializoTableModel(){
    	return new DefaultTableModel() {
    			private static final long serialVersionUID = 1L;
    			String nombresColumnas[] = {"id_tipo_de_servicio", "Nombre" ,"Tiempo mín. reparación","Tiempo máx. reparación", "fila"};

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
    }
    
	private void buscarTipoServicio() {
		// elimino cualquier filtro que pudiera tener la tabla
		jTableBuscarTipoServicio.setRowSorter(null);
		
		// creo el filtro segun jTextFieldNombre
		tableRowSorterNombre = RowFilter.regexFilter("(?i)^" + jTextFieldNombre.getText(), 1);

		// agrego los filtros a la lista
		andFilters.add(tableRowSorterNombre);

		// combino los filtros en uno solo
		tableRowSorter.setRowFilter(RowFilter.andFilter(andFilters));

		// reseteo lista de filtros porque ya no se necesita
		andFilters.clear();

		// aplico el filtro
		jTableBuscarTipoServicio.setRowSorter(tableRowSorter);
		
		inicializarContador();
		enableDisableButtons();
	}
	
	private void inicializarContador(){
		// esta funcion setea el valor del jLabelContador
		if (jTableBuscarTipoServicio.getRowCount() > 1)
			jLabelContador.setText("<html><b> " + jTableBuscarTipoServicio.getRowCount() + " registros encontrados</b></html>");
		else
			if (jTableBuscarTipoServicio.getRowCount() == 1)
				jLabelContador.setText("<html><b> 1 registro encontrado</b></html>");			
			else
				jLabelContador.setText("<html><b><font color=\"#FF0000\">No se han encontrado registros</b></html>");
	}
	
	private void cerrarBuscarTipoServicio() {
		cleanAllFields();
		BuscarTipoServicio.this.dispose();
	}
	
	private void cleanAllFields() {
		jTextFieldNombre.setText( "" );
	}

}


