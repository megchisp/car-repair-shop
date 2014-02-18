package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import negocio.AutomovilManager;
import negocio.IAutomovilManager;

import persistencia.Automovil;

public class BuscarAutomovil extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	JFrame buscarAutomovil;
	
	List<Automovil> listaAutomoviles = null;
	
	BuscarAutomovil buscarAutomovilItSelf = this;
	JPanel jPanelBuscarAutomovil = null;
	JLabel jLabelNombre = null;
	JLabel jLabelApellido = null;
	JLabel jLabelDominio = null;
	JLabel jLabelContador = null;
	
	JTextFieldUpperCased jTextFieldNombre = null;
	JTextFieldUpperCased jTextFieldApellido = null;
	JTextFieldUpperCased jTextFieldDominio = null;
	
	JRadioButton jRadioButtonPorCliente = null;
	JRadioButton jRadioButtonPorDominio = null;
	ButtonGroup buttonGroupCriterio = null;
	
	JButton jButtonBuscar = null;
	JButton jButtonModificar = null;
	JButton jButtonEliminar = null;
	JButton jButtonCerrar = null;
	JButton jButtonMostrarHistorial = null;
	JButton jButtonDetallar = null;
	
	JPanel jPanelPorCliente = null; // contiene jLabelNombre, jLabelApellido, jTextFieldNombre y jTextFieldApellido
	JPanel jPanelPorDominio = null; // contiene jLabelDominio y jTextFieldDominio
	JPanel jPanelCriterioDeBusqueda = null; // contiene jPanelPorCliente, jPanelPorDominio y jButtonBuscar
	JPanel jPanelResultadoDeBusqueda = null; // contiene jTableAutomovil
	
	JTable jTableAutomovil = null;
	JScrollPane jScrollPaneAutomovil  = null;

	//para que la tabla no sea editable
	DefaultTableModel tableModel = inicializoTableModel();
	
	// variables para realizar el filtrado por apellido y nombre
	TableRowSorter<DefaultTableModel> tableRowSorterNombreApellido = new TableRowSorter<DefaultTableModel>(tableModel);
	ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
	RowFilter<Object,Object> tableRowSorterNombre = null;
	RowFilter<Object,Object> tableRowSorterApellido = null;

	// variable para realizar el filtrado por dominio
	TableRowSorter<DefaultTableModel> tableRowSorterDominio = new TableRowSorter<DefaultTableModel>(tableModel);
	
	ResourceLoader resourceLoader = new ResourceLoader();

	public BuscarAutomovil( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 670, 505 ) ); // Tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);

		jPanelBuscarAutomovil = new JPanel();
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelBuscarAutomovil.setBorder( titledBorder );
		jPanelBuscarAutomovil.setPreferredSize(new Dimension(590,475));

		
		jPanelBuscarAutomovil.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		inicializarVariables();
		extraerInfoBD();
		enableDisableButtons();
		cerrarEsc();
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelBuscarAutomovil.add( new JLabel(" "), a );
		
			/**********INICIO Generacion de jPanelCriterioDeBusqueda********/
			
			jPanelCriterioDeBusqueda = new JPanel();
			jPanelCriterioDeBusqueda.setBorder(BorderFactory.createTitledBorder(null, " Criterio de búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelCriterioDeBusqueda.setPreferredSize(new Dimension(620, 150));
			
			jPanelCriterioDeBusqueda.setLayout( new GridBagLayout() );
			
			GridBagConstraints e = new GridBagConstraints();
			e.anchor = GridBagConstraints.WEST;
				
			e.gridy = 0;
			e.gridx = 0;
			jPanelCriterioDeBusqueda.add( jRadioButtonPorCliente, e );
			
			e.gridy = 0;
			e.gridx = 1;
			e.insets = new Insets( 0, 18, 0, 0 );
			jPanelCriterioDeBusqueda.add( jRadioButtonPorDominio, e );
			
			e.gridy = 1;
			e.gridx = 0;
			jPanelCriterioDeBusqueda.add( new JLabel(" "), e );
		
				// Comienza jPanelPorCliente
				jPanelPorCliente = new JPanel();
				jPanelPorCliente.setLayout( new GridBagLayout() );
			
				GridBagConstraints b = new GridBagConstraints();
				
				b.gridy = 0;
				b.gridx = 0;
				b.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorCliente.add( jLabelApellido, b );
				
				b.gridy = 0;
				b.gridx = 1;
				b.insets = new Insets( 0, -11, 0, 0 );
				jPanelPorCliente.add( jTextFieldApellido, b );
				
				b.gridy = 1;
				b.gridx = 0;
				jPanelPorCliente.add( new JLabel(" "), b );
				
				b.gridy = 2;
				b.gridx = 0;
				b.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorCliente.add( jLabelNombre, b );
				
				b.gridy = 2;
				b.gridx = 1;
				b.insets = new Insets( 0, -11, 0, 0 );
				jPanelPorCliente.add( jTextFieldNombre, b );
				
			e.gridy = 2;
			e.gridx = 0;
			e.gridwidth = 2;
			e.insets = new Insets( 0, 0, 0, 0 );
			jPanelCriterioDeBusqueda.add( jPanelPorCliente, e );
				// Finaliza jPanelPorCliente
	
				
				// Comienza jPanelPorDominio
				jPanelPorDominio = new JPanel();
				jPanelPorDominio.setVisible(false);
				jPanelPorDominio.setLayout( new GridBagLayout() );
			
				GridBagConstraints c = new GridBagConstraints();
				
				c.gridy = 0;
				c.gridx = 0;
				c.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorDominio.add( jLabelDominio, c );
				
				c.gridy = 0;
				c.gridx = 1;
				c.insets = new Insets( 0, -11, 0, 0 );
				jPanelPorDominio.add( jTextFieldDominio, c );
				
				e.gridy = 2;
				e.gridx = 0;
				e.gridwidth = 2;
				e.insets = new Insets( 0, 0, 41, 0 );
				jPanelCriterioDeBusqueda.add( jPanelPorDominio, e );
				// Finaliza jPanelPorDominio
				
			e.gridy = 2;
			e.gridx = 1;
			e.insets = new Insets( 0, 195, 38, 0 );
			jPanelCriterioDeBusqueda.add( jButtonBuscar, e );
			
		a.gridy = 1;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelBuscarAutomovil.add( jPanelCriterioDeBusqueda, a );
			
			/**********FIN Generacion de jPanelCriterioDeBusqueda*******/

		
		a.gridy = 2;
		a.gridx = 0;
		jPanelBuscarAutomovil.add( new JLabel(" "), a );

		/**********INICIO Generacion de jPanelResultadoDeBusqueda********/
		jPanelResultadoDeBusqueda.setPreferredSize(new Dimension(620, 205)); // Dimensiones del panel
		jPanelResultadoDeBusqueda.setLayout( new GridBagLayout() );
		GridBagConstraints f = new GridBagConstraints();
		f.anchor = GridBagConstraints.WEST;
				
		f.gridy = 0;
		f.gridx = 0;
		jPanelResultadoDeBusqueda.add(jScrollPaneAutomovil, f);
		
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
			g.gridy = 0;
			g.gridx = 2;
			g.insets = new Insets( 0, 112, 0, 0 );
			jPanelBotones.add(jButtonMostrarHistorial ,g);
			g.gridy = 0;
			g.gridx = 3;
			g.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotones.add( jButtonDetallar,g);
	
		f.gridy = 2;
		f.gridx = 0;
		jPanelResultadoDeBusqueda.add(jPanelBotones, f);
		
		f.gridy = 3;
		f.gridx = 0;
		jPanelResultadoDeBusqueda.add( new JLabel(" "), f );

		
		a.gridy = 3;
		a.gridx = 0;
		jPanelBuscarAutomovil.add(jPanelResultadoDeBusqueda, a); // agregamos la tabla que contiene los automoviles
		
		a.gridy = 4;
		a.gridx = 0;
		jPanelBuscarAutomovil.add( new JLabel(" "), a );	
		

		
		/**********FIN Generacion de jPanelResultadoDeBusqueda********/
		a.gridy = 6;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 420 );
		jPanelBuscarAutomovil.add(jLabelContador, a);
		
		a.gridy = 6;
		a.gridx = 1;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelBuscarAutomovil.add( jButtonCerrar, a );
		
		a.gridy = 7;
		a.gridx = 0;
		jPanelBuscarAutomovil.add( new JLabel(" "), a );
				
		this.getContentPane().add( jPanelBuscarAutomovil, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );

	}
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 75, 25 );
		// panel que contiene la tabla de automoviles
		jPanelResultadoDeBusqueda = new JPanel();
		
		// hace que el cursor se posicione en el jTextFieldApellido al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextFieldApellido.requestFocusInWindow();
			}
		});
		
		jRadioButtonPorCliente = new JRadioButton("Por cliente", true);
		jRadioButtonPorCliente.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPanelPorCliente.setVisible(true);
				jPanelPorDominio.setVisible(false);	
				jTextFieldApellido.requestFocusInWindow();
			}
		});
		jRadioButtonPorDominio = new JRadioButton("Por dominio", false);
		jRadioButtonPorDominio.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPanelPorCliente.setVisible(false);	
				jPanelPorDominio.setVisible(true);		
				jTextFieldDominio.requestFocusInWindow();
			}
		});
		
		// CREANDO LA TABLA
		jTableAutomovil = new JTable(tableModel);
		jTableAutomovil.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableAutomovil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableAutomovil.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableAutomovil.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		jScrollPaneAutomovil = new JScrollPane(jTableAutomovil);
		jTableAutomovil.setPreferredScrollableViewportSize(new Dimension(580,95));
		jScrollPaneAutomovil.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// configuro el doble click sobre la fila
		jTableAutomovil.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         BuscarAutomovil.this.dispose();
			         new DetallarAutomovil( buscarAutomovil, "Detallar automóvil", " Detalle del automóvil ", listaAutomoviles.get(Integer.parseInt(jTableAutomovil.getValueAt(row, 7).toString())));
			         }
			   }
			});
		
		// Tamaño a columnas
		jTableAutomovil.getColumnModel().getColumn(2).setPreferredWidth(35);
		jTableAutomovil.getColumnModel().getColumn(3).setPreferredWidth(20);
		jTableAutomovil.getColumnModel().getColumn(4).setPreferredWidth(15);
		jTableAutomovil.getColumnModel().getColumn(5).setPreferredWidth(10);
		jTableAutomovil.getColumnModel().getColumn(6).setPreferredWidth(15);

		// Oculto la columna ID y Fila para que el usuario no la vea
		jTableAutomovil.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableAutomovil.getColumnModel().getColumn(0).setMinWidth(0);
		jTableAutomovil.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableAutomovil.getColumnModel().getColumn(0).setResizable(false);
		jTableAutomovil.getColumnModel().getColumn(7).setMaxWidth(0);
		jTableAutomovil.getColumnModel().getColumn(7).setMinWidth(0);
		jTableAutomovil.getColumnModel().getColumn(7).setPreferredWidth(0);
		jTableAutomovil.getColumnModel().getColumn(7).setResizable(false);
		
		buttonGroupCriterio = new ButtonGroup();
		buttonGroupCriterio.add(jRadioButtonPorCliente);
		buttonGroupCriterio.add(jRadioButtonPorDominio);
		
		jLabelNombre = new JLabel("Nombre: ");
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {  
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarAutomovil();
                   }
                }
        }); 
		
		jLabelApellido = new JLabel("Apellido: ");
		jLabelApellido.setPreferredSize( dimensionLabel );
		jTextFieldApellido = new JTextFieldOfLetters();
		jTextFieldApellido.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldApellido.addKeyListener(new java.awt.event.KeyAdapter() {  
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarAutomovil();
                   }
                }
        }); 
		
		jLabelDominio = new JLabel("Dominio: ");
		jLabelDominio.setPreferredSize( dimensionLabel );
		jTextFieldDominio = new JTextFieldUpperCased();
		jTextFieldDominio.setPreferredSize( new Dimension( 75, 25 ) );
		jTextFieldDominio.addKeyListener(new java.awt.event.KeyAdapter() {  
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarAutomovil();
                   }
                }
        }); 
		
		// creo el botón buscar con un ícono
		ImageIcon imageIconSearch = new ImageIcon(resourceLoader.load("/images/menu/search-icon.png"));
		jButtonBuscar = new JButton( " Buscar", imageIconSearch );
		jButtonBuscar.setPreferredSize( new Dimension( 100, 30 ) );
		
		// creo el botón modificar con un ícono
		ImageIcon imageIconUpdate = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificar = new JButton( " Modificar", imageIconUpdate );
		jButtonModificar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificar.setToolTipText("Modificar el automóvil seleccionado");
		jButtonModificar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				BuscarAutomovil.this.dispose();
				int index = Integer.parseInt(jTableAutomovil.getValueAt(jTableAutomovil.getSelectedRow(), 7).toString());
				new ModificarAutomovil( buscarAutomovil, "Modificar automóvil", "Modificación del automóvil", null, listaAutomoviles.get(index));
			}
		});
		
		// creo el botón eliminar con un ícono
		ImageIcon imageIconDelete = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminar = new JButton( " Eliminar", imageIconDelete );
		jButtonEliminar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminar.setToolTipText("Eliminar el automóvil seleccionado");
		jButtonEliminar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "Si elimina el automóvil, se realizará una eliminación en cascada de sus reparaciones, \nservicios, repuestos y manos de obras asociadas al mismo.\n\n              ¿Está seguro que desea eliminar el automóvil seleccionado?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarAutomovil(Integer.parseInt(jTableAutomovil.getValueAt(jTableAutomovil.getSelectedRow(), 7).toString()));
					enableDisableButtons();
				}
			}   
		});
		
		// creo el botón detallar con un ícono
		ImageIcon imageIconHistory = new ImageIcon(resourceLoader.load("/images/menu/history-icon.png"));		
		jButtonMostrarHistorial = new JButton( " Mostrar historial", imageIconHistory );
		jButtonMostrarHistorial.setPreferredSize( new Dimension( 150, 30 ) );
		jButtonMostrarHistorial.setToolTipText("Mostrar el historial de reparaciones del automóvil seleccionado");
		
		// creo el botón detallar con un ícono
		ImageIcon imageIconDetails = new ImageIcon(resourceLoader.load("/images/menu/detail-icon.png"));		
		jButtonDetallar = new JButton( " Detallar", imageIconDetails );
		jButtonDetallar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonDetallar.setToolTipText("Detallar el automóvil seleccionado");
		
		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		
		
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarAutomovil();
			}
		});
		
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				// elimina contenido de jTextFields
				jTextFieldNombre.setText("");
				jTextFieldApellido.setText("");
				jTextFieldDominio.setText("");
				BuscarAutomovil.this.dispose();
			}
		});
		
		jButtonMostrarHistorial.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				BuscarAutomovil.this.dispose();
				mostrarHistorialAutomovil();
			}
		});
		
		jButtonDetallar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				BuscarAutomovil.this.dispose();
				detallarAutomovil();
			}
		});
		
		// FIN sección ActionListeners
		
		
		// habilito a que el usuario ordene ascendente o descendentemente las columnas
		 RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
		jTableAutomovil.setRowSorter(sorter);
		
		// inicializo el jLabel que muestra el contador de registros
        jLabelContador = new JLabel();
        jLabelContador.setPreferredSize(new Dimension( 200, 25 ));
}
	
	private void extraerInfoBD(){
		IAutomovilManager automovilManager = new AutomovilManager();

		try {
			listaAutomoviles = automovilManager.listaAutomoviles(); // obtengo todos los automoviles de la BD
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}

		completar_tabla(listaAutomoviles.iterator()); // completa la tabla con la lista de clientes
	}
	
	private void inicializarContador(){
		// esta funcion setea el valor del jLabelContador
		if (jTableAutomovil.getRowCount() > 1)
			jLabelContador.setText("<html><b> " + jTableAutomovil.getRowCount() + " registros encontrados</b></html>");
		else
			if (jTableAutomovil.getRowCount() == 1)
				jLabelContador.setText("<html><b> 1 registro encontrado</b></html>");			
			else
				jLabelContador.setText("<html><b><font color=\"#FF0000\">No se han encontrado registros</b></html>");
	}
	
	private void buscarAutomovil() {
		// elimino cualquier filtro que pudiera tener la tabla
		jTableAutomovil.setRowSorter(null);

		if(jRadioButtonPorDominio.isSelected())
		{
			// creo el filtro segun jTextFieldCUIT
			tableRowSorterDominio.setRowFilter(RowFilter.regexFilter("(?i).*" + jTextFieldDominio.getText() +".*",5));
			
			// aplico el filtro
			jTableAutomovil.setRowSorter(tableRowSorterDominio);

		}
		else
		{
			// creo los filtros segun jTextFields
			tableRowSorterNombre = RowFilter.regexFilter("(?i)^" + jTextFieldNombre.getText() ,2);
			tableRowSorterApellido = RowFilter.regexFilter("(?i)^" + jTextFieldApellido.getText() ,1);
			
			// agrego los filtros a la lista
			andFilters.add(tableRowSorterNombre);
			andFilters.add(tableRowSorterApellido);
			
			// combino los filtros en uno solo
			tableRowSorterNombreApellido.setRowFilter(RowFilter.andFilter(andFilters));
			
			// reseteo lista de filtros porque ya no se necesita
			andFilters.clear();
			
			// aplico el filtro
			jTableAutomovil.setRowSorter(tableRowSorterNombreApellido);
		}

//		ordenarTabla(); // muestro la tabla en forma ascendente
		inicializarContador(); // seteo el resultado de la búsqueda
		enableDisableButtons(); // deshabilito botones si no hay resultados
	}
	
	private void completar_tabla(Iterator<Automovil> iterator){
		// con este método agrego las filas a la tabla
		
		Automovil automovil;
		int numero_fila = 0;
		while (iterator.hasNext()){
			automovil = iterator.next();
			String id_automovil = Integer.toString(automovil.getId_automovil());
			String id_fila = Integer.toString(numero_fila++);
			String apellido = automovil.getApellidoPropietario();
			String nombre = automovil.getNombrePropietario();
			String marca = automovil.getMarca();
			String modelo = automovil.getModelo();
			String dominio = automovil.getDominio();
			String combustible = automovil.getTipoCombustible();
			String conGNC = automovil.isConGNC() ? " c/ GNC" : "";
			Object [] fila= {id_automovil, apellido, nombre, marca, modelo, dominio, combustible + conGNC, id_fila};
			tableModel.addRow(fila);
		}
	}
	
	private void eliminarAutomovil(int index){
		/** este método elimina el automovil cuyo índice en la tabla se pasa como parámetro **/
		int option = 0;
		IAutomovilManager automovilManager = new AutomovilManager();
		try {
			option = automovilManager.eliminar( listaAutomoviles.get(index) );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}

		switch( option ) {
		case 1:
			// elimino el automovil de la lista de clientes
			listaAutomoviles.remove(index); 
			
			// elimino todos los automoviles de la tabla
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
			
			// una vez eliminado el automovil, completo la tabla con la lista de automoviles
			completar_tabla(listaAutomoviles.iterator());
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El automóvil no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );

			break;
		default:
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void enableDisableButtons(){ // habilita o deshabilita los botones según hayan filas en la tabla
		TitledBorder titledBorder;
		if(jTableAutomovil.getRowCount() > 0){
			jButtonDetallar.setEnabled(true);
			jButtonMostrarHistorial.setEnabled(true);
			jButtonEliminar.setEnabled(true);
			jButtonModificar.setEnabled(true);
			jTableAutomovil.getTableHeader().setBackground(new Color(236,243,255));
			jTableAutomovil.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableAutomovil.getTableHeader().setForeground(new Color(0,0,0));
			jTableAutomovil.setEnabled(true);
			jPanelResultadoDeBusqueda.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
		else{
			jButtonDetallar.setEnabled(false);
			jButtonEliminar.setEnabled(false);
			jButtonModificar.setEnabled(false);
			jButtonMostrarHistorial.setEnabled(false);
			jTableAutomovil.getTableHeader().setBackground(new Color(238,238,238));
			jTableAutomovil.getTableHeader().setForeground(new Color(153,153,153));
			jTableAutomovil.setEnabled(false);
			jPanelResultadoDeBusqueda.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
			
		}
	}
	
	private void mostrarHistorialAutomovil() {
		new MostrarHistorialAutomovil( buscarAutomovil, "Mostrar historial", " Historial del automóvil ", listaAutomoviles.get(Integer.parseInt(jTableAutomovil.getValueAt(jTableAutomovil.getSelectedRow(), 7).toString())) );
	}
	
	private void detallarAutomovil() {
		new DetallarAutomovil( buscarAutomovil, "Detallar automóvil", " Detalle del automóvil ", listaAutomoviles.get(Integer.parseInt(jTableAutomovil.getValueAt(jTableAutomovil.getSelectedRow(), 7).toString())));
	}
	
	private DefaultTableModel inicializoTableModel(){
		return new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnas[] = {"id_automovil", "Apellido", "Nombre", "Marca" , "Modelo", "Dominio", "Combustible", "id_fila"};
			
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
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  BuscarAutomovil.this.dispose();
		      }
		    };
		jPanelBuscarAutomovil.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

}
