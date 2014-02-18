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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import negocio.IProveedorManager;
import negocio.ProveedorManager;
import persistencia.Proveedor;

public class BuscarProveedor extends JDialog {
	private static final long serialVersionUID = 1L;

	List<Proveedor> listaProveedores = null;

	BuscarProveedor buscarProveedorItSelf;
	private JFrame buscarProveedor;
	
	JLabel jLabelNombre = null;
	JPanel jPanelBuscarProveedor = null;
	JTextFieldUpperCased jTextFieldNombre = null;
	
	JLabel jLabelContador = null;
				
	JButton jButtonBuscar = null;
	JButton jButtonModificar = null;
	JButton jButtonEliminar = null;
	JButton jButtonCerrar = null;
	
	JPanel jPanelResultadoDeBusqueda = null; // contiene jTableProveedor
	JPanel jPanelCriterioDeBusqueda = null; // contiene jLabelNombre, jTextFieldNombre y jButtonEliminar
	
	JTable jTableBuscarProveedor = null;
	JScrollPane jScrollPaneBuscarProveedor = null;
	
	DefaultTableModel dtmBuscarProveedor = inicializoTableModel();
	
	// variables para realizar el filtrado
	TableRowSorter<DefaultTableModel> tableRowSorter= new TableRowSorter<DefaultTableModel>(dtmBuscarProveedor);
	ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
	RowFilter<Object,Object> tableRowSorterNombre = null;

	ResourceLoader resourceLoader = new ResourceLoader();

	public BuscarProveedor( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 570, 415 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
	
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		buscarProveedorItSelf = this;
		
		jPanelBuscarProveedor = new JPanel();
		jPanelBuscarProveedor.setFocusable(true);
			
		jPanelBuscarProveedor.setLayout( new GridBagLayout() );
		
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
		jPanelBuscarProveedor.setBorder( titledBorder );
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelBuscarProveedor.add( new JLabel(" "), a );

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
	jPanelBuscarProveedor.add( jPanelCriterioDeBusqueda, a );
		
	/********** FIN Generacion de jPanelCriterioDeBusqueda *******/
		
		a.gridy = 2;
		a.gridx = 0;
		jPanelBuscarProveedor.add( new JLabel(" "), a );

			/**********INICIO Generacion de jPanelResultadoDeBusqueda********/
			
			jPanelResultadoDeBusqueda.setPreferredSize(new Dimension(520, 209)); // Dimensiones del panel
			jPanelResultadoDeBusqueda.setLayout( new GridBagLayout() );
			GridBagConstraints f = new GridBagConstraints();
			f.anchor = GridBagConstraints.WEST;
	
			jTableBuscarProveedor.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					jButtonEliminar.setEnabled(true);
					jButtonModificar.setEnabled(true);
				}
			});
			
			f.gridy = 0;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add(jScrollPaneBuscarProveedor, f);
			
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
		jPanelBuscarProveedor.add(jPanelResultadoDeBusqueda, a); // agregamos la tabla que contiene los proveedores
			
		a.gridy = 4;
		a.gridx = 0;
		jPanelBuscarProveedor.add( new JLabel(" "), a );	
			
		/**********FIN Generacion de jPanelResultadoDeBusqueda********/

		a.gridy = 6;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 315 );
		jPanelBuscarProveedor.add(jLabelContador, a);
		
		a.gridy = 6;
		a.gridx = 1;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelBuscarProveedor.add( jButtonCerrar, a );
		
		a.gridy = 7;
		a.gridx = 0;
		jPanelBuscarProveedor.add( new JLabel(" "), a );
		
			
		this.getContentPane().add( jPanelBuscarProveedor, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarContador(){
		// esta funcion setea el valor del jLabelContador
		if (jTableBuscarProveedor.getRowCount() > 1)
			jLabelContador.setText("<html><b> " + jTableBuscarProveedor.getRowCount() + " registros encontrados</b></html>");
		else
			if (jTableBuscarProveedor.getRowCount() == 1)
				jLabelContador.setText("<html><b> 1 registro encontrado</b></html>");			
			else
				jLabelContador.setText("<html><b><font color=\"#FF0000\">No se han encontrado registros</b></html>");
	}
	
	private void inicializarVariables() {	
		// panel que contiene la tabla de proveedores
		jPanelResultadoDeBusqueda = new JPanel();
		// hace que el cursor se posicione en el jTextFieldNombre al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextFieldNombre.requestFocusInWindow();
			}
		});
		
		jLabelNombre = new JLabel( "Nombre: " );
		jLabelNombre.setPreferredSize( new Dimension( 70, 25 ) );
		jTextFieldNombre = new JTextFieldUpperCased();
		jTextFieldNombre.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldNombre.addFocusListener( new JTextFieldFocusListener( jTextFieldNombre ) );
		jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {  
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarProveedor();
                   }
                }
        });  
		
		// inicializo el jLabel que muestra el contador de registros
        jLabelContador = new JLabel();
        jLabelContador.setPreferredSize(new Dimension( 200, 25 ));
		
		ImageIcon imageIconSearch = new ImageIcon(resourceLoader.load("/images/menu/search-icon.png"));		
		jButtonBuscar = new JButton( " Buscar", imageIconSearch );
		jButtonBuscar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarProveedor();
			}
		});
		
		ImageIcon imageIconUpdate = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));		
		jButtonModificar = new JButton( " Modificar", imageIconUpdate );
		jButtonModificar.setToolTipText("Modificar el proveedor seleccionado");
		jButtonModificar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificar.setEnabled(false);
		jButtonModificar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				BuscarProveedor.this.dispose();
				new ModificarProveedor( buscarProveedor, "Modificar proveedor", "Modificación del proveedor", listaProveedores.get(Integer.parseInt(jTableBuscarProveedor.getValueAt(jTableBuscarProveedor.getSelectedRow(), 4).toString())));
			}
		});
		
		ImageIcon imageIconDelete = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminar = new JButton( " Eliminar", imageIconDelete );
		jButtonEliminar.setToolTipText("Eliminar el proveedor seleccionado");
		jButtonEliminar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminar.setEnabled(false);
		jButtonEliminar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el proveedor seleccionado?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarProveedor(Integer.parseInt(jTableBuscarProveedor.getValueAt(jTableBuscarProveedor.getSelectedRow(), 4).toString()));
					enableDisableButtons();
				}
			}
		});
		
		ImageIcon imageIconClose = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));
		jButtonCerrar = new JButton( "Cerrar", imageIconClose );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				// elimina contenido de jTextFields
				jTextFieldNombre.setText("");
				BuscarProveedor.this.dispose();
		}
	});
		
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarProveedor();
			}
		});
		
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				// elimina contenido de jTextFields
				jTextFieldNombre.setText("");
				BuscarProveedor.this.dispose();
		}
	});
		
	jTableBuscarProveedor = new JTable(dtmBuscarProveedor);
	jTableBuscarProveedor.setFont(new Font("Dialog", Font.BOLD, 11));
	jTableBuscarProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	jTableBuscarProveedor.getTableHeader().setReorderingAllowed(false);
	JTableHeader jTableHeaderHeader = jTableBuscarProveedor.getTableHeader();
	jTableHeaderHeader.setBackground(new Color(236,243,255));
	jScrollPaneBuscarProveedor = new JScrollPane(jTableBuscarProveedor);
	jTableBuscarProveedor.setPreferredScrollableViewportSize(new Dimension(470,95));
	jScrollPaneBuscarProveedor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	// configuro el doble click sobre la fila
	jTableBuscarProveedor.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() == 2) {
		         JTable target = (JTable)e.getSource();
		         int row = target.getSelectedRow();
		         BuscarProveedor.this.dispose();
		         new ModificarProveedor( buscarProveedor, "Modificar proveedor", "Modificación del proveedor", listaProveedores.get(Integer.parseInt(jTableBuscarProveedor.getValueAt(row, 4).toString())));
		      }
		   }
		});
	
	// Tamaño a las columnas
	jTableBuscarProveedor.getColumnModel().getColumn(0).setPreferredWidth(100);
	jTableBuscarProveedor.getColumnModel().getColumn(0).setMaxWidth(0);
	jTableBuscarProveedor.getColumnModel().getColumn(0).setMinWidth(0);
	jTableBuscarProveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
	jTableBuscarProveedor.getColumnModel().getColumn(0).setResizable(false);
	jTableBuscarProveedor.getColumnModel().getColumn(4).setMaxWidth(0);
	jTableBuscarProveedor.getColumnModel().getColumn(4).setMinWidth(0);
	jTableBuscarProveedor.getColumnModel().getColumn(4).setPreferredWidth(0);
	jTableBuscarProveedor.getColumnModel().getColumn(4).setResizable(false);

	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarProveedor se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  BuscarProveedor.this.dispose();
		      }
		    };
		jPanelBuscarProveedor.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void enableDisableButtons(){ // habilita o deshabilita los botones según hayan filas en la tabla
		TitledBorder titledBorder;
		if(jTableBuscarProveedor.getRowCount() > 0){
			jButtonEliminar.setEnabled(true);
			jButtonModificar.setEnabled(true);
			jTableBuscarProveedor.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableBuscarProveedor.getTableHeader().setBackground(new Color(236,243,255));
			jTableBuscarProveedor.getTableHeader().setForeground(new Color(0,0,0));
			jPanelResultadoDeBusqueda.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Lista de proveedores ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
		else{
			jButtonEliminar.setEnabled(false);
			jButtonModificar.setEnabled(false);
			jTableBuscarProveedor.getTableHeader().setBackground(new Color(238,238,238));
			jTableBuscarProveedor.getTableHeader().setForeground(new Color(153,153,153));
			jPanelResultadoDeBusqueda.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Lista de proveedores ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
	}
	
	private void eliminarProveedor(int index){
		/** este método elimina el proveedor cuyo índice en la tabla se pasa como parámetro **/
		
		int option = 0;
		IProveedorManager proveedorManager = new ProveedorManager();
		try {
			option = proveedorManager.eliminar( listaProveedores.get(index) );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}

		switch( option ) {
		case 1:
			// elimino el proveedor de la lista de clientes
			listaProveedores.remove(index);
			
			// elimino todos los proveedores de la tabla
			dtmBuscarProveedor.getDataVector().removeAllElements();
			dtmBuscarProveedor.fireTableDataChanged();
			
			// una vez eliminado el proveedor completo la tabla con la lista de proveedores
			completar_tabla(listaProveedores.iterator());
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El cliente no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			// este mensaje nunca debería aparecer
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void extraerInfoBD(){
		IProveedorManager proveedorManager = new ProveedorManager();

		try {
			listaProveedores = proveedorManager.listaProveedores(); // obtengo todos los proveedores de la BD
			completar_tabla(listaProveedores.iterator()); // completa la tabla con la lista de proveedores
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void completar_tabla(Iterator<Proveedor> iterator){
		// con este método agrego las filas a la tabla
		
		Proveedor proveedor;
		int numero_fila = 0;
		while (iterator.hasNext()){
			proveedor = iterator.next();
			String id_fila = Integer.toString(numero_fila++);
			String id_proveedor = Integer.toString(proveedor.getId_proveedor());
			String nombre = proveedor.getNombre();
			String telefono = proveedor.getTelefono();
			String telefonoAlternativo = proveedor.getTelefonoAlternativo();
			Object [] fila= {id_proveedor, nombre, telefono, telefonoAlternativo, id_fila};
			dtmBuscarProveedor.addRow(fila);
		}
	}

	private DefaultTableModel inicializoTableModel(){
		//para que la tabla no sea editable
		return new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnas[] = {"id_proveedor", "Nombre" ,"Teléfono","Teléfono alternativo", "id_fila"};

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
	
	private void buscarProveedor() {
		// elimino cualquier filtro que pudiera tener la tabla
		jTableBuscarProveedor.setRowSorter(null);

		// creo el filtro segun jTextFieldNombre
		tableRowSorterNombre = RowFilter.regexFilter("(?i)^" + jTextFieldNombre.getText(), 1);

		// agrego los filtros a la lista
		andFilters.add(tableRowSorterNombre);
		
		// combino los filtros en uno solo
		tableRowSorter.setRowFilter(RowFilter.andFilter(andFilters));
		
		// reseteo lista de filtros porque ya no se necesita
		andFilters.clear();

		// aplico el filtro
		jTableBuscarProveedor.setRowSorter(tableRowSorter);

		inicializarContador();
		enableDisableButtons();
	}

}


