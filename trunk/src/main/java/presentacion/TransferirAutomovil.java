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
import negocio.ClienteManager;
import negocio.IAutomovilManager;
import negocio.IClienteManager;

import persistencia.Automovil;
import persistencia.Cliente;


public class TransferirAutomovil extends JDialog{
	private static final long serialVersionUID = 1L;

	JFrame transferirAutomovil;
	
	List<Cliente> listaClientes = null;
	Automovil automovil = null; // automovil a transferir
	
	JPanel jPanelTransferirAutomovil = null;
	JLabel jLabelNombre = null;
	JLabel jLabelApellido = null;
	JLabel jLabelCUIT = null;
	JLabel jLabelContador = null;
	
	JTextFieldUpperCased jTextFieldNombre = null;
	JTextFieldUpperCased jTextFieldApellido = null;
	JTextFieldUpperCased jTextFieldCUIT = null;
	
	JRadioButton jRadioButtonPorCliente = null;
	JRadioButton jRadioButtonPorCUIT = null;
	ButtonGroup buttonGroupCriterio = null;
	
	JButton jButtonBuscar = null;
	JButton jButtonCancelar = null;
	JButton jButtonAceptar = null;
	
	JPanel jPanelPorCliente = null; // contiene jLabelNombre, jLabelApellido, jTextFieldNombre y jTextFieldApellido
	JPanel jPanelPorCUIT = null; // contiene jLabelDominio y jTextFieldDominio
	JPanel jPanelCriterioDeBusqueda = null; // contiene jPanelPorCliente, jPanelPorDominio y jButtonBuscar
	JPanel jPanelResultadoDeBusqueda = null; // contiene jTableBuscarCliente
	JPanel jPanelBotones = null; // contiene los botones aceptar y cancelar
	
	JTable jTableBuscarCliente = null;
	JScrollPane jScrollPaneCliente = null;
	
	int rowCount;
	
	ResourceLoader resourceLoader = new ResourceLoader();

	// para que la tabla no sea editable
	DefaultTableModel dtmBuscarCliente = inicializoTableModel();
	
	// variables para realizar el filtrado por apellido y nombre
	TableRowSorter<DefaultTableModel> tableRowSorterNombreApellido = new TableRowSorter<DefaultTableModel>(dtmBuscarCliente);
	ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
	RowFilter<Object,Object> tableRowSorterNombre = null;
	RowFilter<Object,Object> tableRowSorterApellido = null;

	// variable para realizar el filtrado por CUIT/CUIL
	TableRowSorter<DefaultTableModel> tableRowSorterCUIT = new TableRowSorter<DefaultTableModel>(dtmBuscarCliente);

	public TransferirAutomovil( JFrame padre, String titulo, String titleBorder, Automovil automovil ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 670, 475 ) ); // Tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
	    this.automovil = automovil;
	    
		jPanelTransferirAutomovil = new JPanel();
		jPanelTransferirAutomovil.setFocusable(true);
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelTransferirAutomovil.setBorder( titledBorder );
		jPanelTransferirAutomovil.setPreferredSize(new Dimension(590,445));
		
		jPanelTransferirAutomovil.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		inicializarVariables();
		inicializarBotones();
		extraerInfoBD();
		enableDisableButtons();
		cerrarEsc();
		
		a.gridy = 0;
		a.gridx = 0;
		jPanelTransferirAutomovil.add( new JLabel(" "), a );
		
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
			jPanelCriterioDeBusqueda.add( jRadioButtonPorCUIT, e );
			
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
				jPanelPorCUIT = new JPanel();
				jPanelPorCUIT.setVisible(false);
				jPanelPorCUIT.setLayout( new GridBagLayout() );
			
				GridBagConstraints c = new GridBagConstraints();
				
				c.gridy = 0;
				c.gridx = 0;
				c.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorCUIT.add( jLabelCUIT, c );
				
				c.gridy = 0;
				c.gridx = 1;
				c.insets = new Insets( 0, -10, 0, 0 );
				jPanelPorCUIT.add( jTextFieldCUIT, c );
				
				e.gridy = 2;
				e.gridx = 0;
				e.gridwidth = 2;
				e.insets = new Insets( 0, 0, 41, 0 );
				jPanelCriterioDeBusqueda.add( jPanelPorCUIT, e );
				// Finaliza jPanelPorDominio
				
			e.gridy = 2;
			e.gridx = 1;
			e.insets = new Insets( 0, 195, 38, 0 );
			jPanelCriterioDeBusqueda.add( jButtonBuscar, e );
			
		a.gridy = 1;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelTransferirAutomovil.add( jPanelCriterioDeBusqueda, a );
			
			/**********FIN Generacion de jPanelCriterioDeBusqueda*******/

		
		a.gridy = 2;
		a.gridx = 0;
		jPanelTransferirAutomovil.add( new JLabel(" "), a );

		/**********INICIO Generacion de jPanelResultadoDeBusqueda********/
		
		jPanelResultadoDeBusqueda.setPreferredSize(new Dimension(620, 170)); // Dimensiones del panel
		jPanelResultadoDeBusqueda.setLayout( new GridBagLayout() );
		GridBagConstraints f = new GridBagConstraints();
		f.anchor = GridBagConstraints.WEST;
		
		// Tamaño a las columna Apellido
		jTableBuscarCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		// Oculto la columna ID y Fila para que el usuario no la vea
		jTableBuscarCliente.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableBuscarCliente.getColumnModel().getColumn(0).setMinWidth(0);
		jTableBuscarCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableBuscarCliente.getColumnModel().getColumn(0).setResizable(false);
		jTableBuscarCliente.getColumnModel().getColumn(6).setMaxWidth(0);
		jTableBuscarCliente.getColumnModel().getColumn(6).setMinWidth(0);
		jTableBuscarCliente.getColumnModel().getColumn(6).setPreferredWidth(0);
		jTableBuscarCliente.getColumnModel().getColumn(6).setResizable(false);
		
		jTableBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {  
            // realiza el detalle del cliente seleccionado presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                	if(jTableBuscarCliente.getRowCount() > 0){
                		aceptarTransferirAutomovil();
                	}
                }
            }
        });     
		
		f.gridy = 0;
		f.gridx = 0;
		jPanelResultadoDeBusqueda.add(jScrollPaneCliente, f);
		
		f.gridy = 1;
		f.gridx = 0;
		jPanelResultadoDeBusqueda.add( new JLabel(" "), f );
		
		a.gridy = 3;
		a.gridx = 0;
		jPanelTransferirAutomovil.add(jPanelResultadoDeBusqueda, a); // agregamos la tabla que contiene los clientes
		
		a.gridy = 4;
		a.gridx = 0;
		jPanelTransferirAutomovil.add( new JLabel(" "), a );	
		
		/**********FIN Generacion de jPanelResultadoDeBusqueda********/
		
		a.gridy = 6;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 420 );
		jPanelTransferirAutomovil.add(jLabelContador, a);
		
		a.gridy = 6;
		a.gridx = 1;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelTransferirAutomovil.add( jPanelBotones, a );
		
		a.gridy = 7;
		a.gridx = 0;
		jPanelTransferirAutomovil.add( new JLabel(" "), a );
		
		this.getContentPane().add( jPanelTransferirAutomovil, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarContador(){
		// esta funcion setea el valor del jLabelContador
		if (jTableBuscarCliente.getRowCount() > 1)
			jLabelContador.setText("<html><b> " + jTableBuscarCliente.getRowCount() + " registros encontrados</b></html>");
		else
			if (jTableBuscarCliente.getRowCount() == 1)
				jLabelContador.setText("<html><b> 1 registro encontrado</b></html>");			
			else
				jLabelContador.setText("<html><b><font color=\"#FF0000\">No se han encontrado registros</b></html>");
	}
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 75, 25 );
		// panel que contiene la tabla de clientes
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
				jPanelPorCUIT.setVisible(false);
				jTextFieldApellido.requestFocusInWindow(); // posiciona el cursor en jTextFieldApellido 
			}
		});
		jRadioButtonPorCUIT = new JRadioButton("Por CUIT/CUIL", false);
		jRadioButtonPorCUIT.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPanelPorCliente.setVisible(false);	
				jPanelPorCUIT.setVisible(true);
				jTextFieldCUIT.requestFocusInWindow(); // posiciona el cursor en jTextFieldCUIT
			}
		});
		
		// CREANDO LA TABLA
		jTableBuscarCliente = new JTable(dtmBuscarCliente);
		jTableBuscarCliente.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableBuscarCliente.setAutoCreateRowSorter(false);
		jTableBuscarCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableBuscarCliente.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableBuscarCliente.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		jScrollPaneCliente = new JScrollPane(jTableBuscarCliente);
		jTableBuscarCliente.setPreferredScrollableViewportSize(new Dimension(580,95));
		jScrollPaneCliente.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// configuro el doble click sobre la fila
		jTableBuscarCliente.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			    	  aceptarTransferirAutomovil();
			      }
			   }
			});
		
		buttonGroupCriterio = new ButtonGroup();
		buttonGroupCriterio.add(jRadioButtonPorCliente);
		buttonGroupCriterio.add(jRadioButtonPorCUIT);
		
		jLabelNombre = new JLabel("Nombre: ");
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {              
	         // habilita a realizar la búsqueda presionando el boton Enter
	            public void keyPressed(java.awt.event.KeyEvent e) {
	                int key = e.getKeyCode();
	                if (key == java.awt.event.KeyEvent.VK_ENTER) {
	                	buscarCliente();
	                	jTableBuscarCliente.requestFocus();
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
                	buscarCliente();
                	jTableBuscarCliente.requestFocus();
                }
            }
        });     
		
		jLabelCUIT = new JLabel("CUIT/CUIL: ");
		jLabelCUIT.setPreferredSize( dimensionLabel );
		jTextFieldCUIT = new JTextFieldOfNumbers();
		jTextFieldCUIT.setPreferredSize( new Dimension( 120, 25 ) );
		jTextFieldCUIT.addKeyListener(new java.awt.event.KeyAdapter() { 
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                	buscarCliente();
                	jTableBuscarCliente.requestFocus();
                }
            }
            
        public void keyReleased(java.awt.event.KeyEvent evt) {  
        	//agrega los guiones al escribir el CUIT/CUIL
       	 int key = evt.getKeyCode();
         if ((jTextFieldCUIT.getDocument().getLength() == 2) && (key != java.awt.event.KeyEvent.VK_BACK_SPACE))
         	jTextFieldCUIT.setText(jTextFieldCUIT.getText() + "-");
        if ((jTextFieldCUIT.getDocument().getLength() == 11) && (key != java.awt.event.KeyEvent.VK_BACK_SPACE))
        	jTextFieldCUIT.setText(jTextFieldCUIT.getText() + "-");
       }  
   });  
		
		// inicializo el jLabel que muestra el contador de registros
        jLabelContador = new JLabel();
        jLabelContador.setPreferredSize(new Dimension( 200, 25 ));
		
		// creo el botón buscar con un ícono
		ImageIcon imageIconSearch = new ImageIcon(resourceLoader.load("/images/menu/search-icon.png"));
		jButtonBuscar = new JButton( " Buscar", imageIconSearch );
		jButtonBuscar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonBuscar.setEnabled(true);  
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarCliente();
			}
		});
		
		// creo el botón detallar con un ícono
		ImageIcon imageIconAceptar = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAceptar = new JButton( " Aceptar", imageIconAceptar );
		jButtonAceptar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAceptar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				aceptarTransferirAutomovil();
			}
		});

		// creo el botón cerrar con un ícono
		ImageIcon imageIconCancelar = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelar = new JButton( " Cancelar", imageIconCancelar );
		jButtonCancelar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cerrarTransferirAutomovil();
			}
		});
		
		// habilito a que el usuario ordene ascendente o descendentemente las columnas
		 RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtmBuscarCliente);
		jTableBuscarCliente.setRowSorter(sorter);
	}
		// FIN sección ActionListeners

	private void inicializarBotones(){
		jPanelBotones = new JPanel();
		jPanelBotones.setPreferredSize( new Dimension( 465, 40 ) );
		jPanelBotones.setLayout( new GridBagLayout() );
		GridBagConstraints e = new GridBagConstraints();
		e.anchor = GridBagConstraints.EAST;
		
		e.gridy = 0;
		e.gridx = 0;
		e.insets = new Insets( 0, 220, 0, 0 );
		jPanelBotones.add( jButtonAceptar, e );
		e.gridy = 0;
		e.gridx = 1;
		e.insets = new Insets( 0, 20, 0, 0 );
		jPanelBotones.add( jButtonCancelar, e );
	}
	
	private void enableDisableButtons(){ // habilita o deshabilita los botones según hayan filas en la tabla
		TitledBorder titledBorder;
		if(jTableBuscarCliente.getRowCount() > 0){
			jButtonAceptar.setEnabled(true);
			jTableBuscarCliente.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableBuscarCliente.getTableHeader().setBackground(new Color(236,243,255));
			jTableBuscarCliente.getTableHeader().setForeground(new Color(0,0,0));
			jPanelResultadoDeBusqueda.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Selección del cliente ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
			jScrollPaneCliente.setEnabled(true);
		}
		else{
			jButtonAceptar.setEnabled(false);
			jTableBuscarCliente.getTableHeader().setBackground(new Color(238,238,238));
			jTableBuscarCliente.getTableHeader().setForeground(new Color(153,153,153));
			jPanelResultadoDeBusqueda.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
			jScrollPaneCliente.setEnabled(false);
		}
	}
	
	private void buscarCliente() {
		// elimino cualquier filtro que pudiera tener la tabla
		jTableBuscarCliente.setRowSorter(null);

		if(jRadioButtonPorCliente.getSelectedObjects() == null)
		{
			// creo el filtro segun jTextFieldCUIT
			tableRowSorterCUIT.setRowFilter(RowFilter.regexFilter("(?i).*" + jTextFieldCUIT.getText() +".*",3));
			
			// aplico el filtro
			jTableBuscarCliente.setRowSorter(tableRowSorterCUIT);

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
			jTableBuscarCliente.setRowSorter(tableRowSorterNombreApellido);
		}

		inicializarContador(); // seteo el resultado de la búsqueda
		enableDisableButtons(); // deshabilito botones si no hay resultados
	}
	
	private void extraerInfoBD(){
		IClienteManager clienteManager = new ClienteManager();

		try {
			listaClientes = clienteManager.listaClientesPorNombre(); // obtengo todos los clientes de la BD
			completar_tabla(listaClientes.iterator()); // completa la tabla con la lista de clientes
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void completar_tabla(Iterator<Cliente> iterator){
		// con este método agrego las filas a la tabla
		
		Cliente cliente;
		int numero_fila = 0;
		while (iterator.hasNext()){
			cliente = iterator.next();
			String id_fila = Integer.toString(numero_fila++);
			String id_cliente = Integer.toString(cliente.getId_cliente());
			String apellido = cliente.getApellido();
			String nombre = cliente.getNombre();
			String cuit = cliente.getCUIT();
			String telefono = cliente.getTelefono();
			String celular = cliente.getCelular();
			Object [] fila= {id_cliente, apellido, nombre, cuit, telefono, celular, id_fila};
			if(automovil.getId_cliente() != cliente.getId_cliente())
				dtmBuscarCliente.addRow(fila);
		}
	}
	
	private DefaultTableModel inicializoTableModel(){
		return new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnas[] = {"ID", "Apellido" ,"Nombre","CUIT/CUIL", "Teléfono", "Teléfono celular", "Fila"};
			
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
		       return false;
		    }
		    
		};
	}

private void aceptarTransferirAutomovil() {
	int choice = JOptionPane.showConfirmDialog(null, "<html>¿Está seguro que desea transferir el automóvil <b>" + automovil.getMarca() + " " + automovil.getModelo() + "</b> del cliente <b>" + automovil.getApellidoPropietario() + ", " + automovil.getNombrePropietario() + "</b> al cliente <b>" + getApellidoNombre(jTableBuscarCliente.getSelectedRow()) + "</b>?</html>", "Confirmación de transferencia", JOptionPane.YES_NO_OPTION);
	if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
		int option = 0;
		automovil.setId_cliente(Integer.parseInt(jTableBuscarCliente.getValueAt(jTableBuscarCliente.getSelectedRow(), 0).toString()));
		
		IAutomovilManager automovilManager = new AutomovilManager();
		try {
			option = automovilManager.modificar( automovil );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			return;
		}

		switch( option ) {
			case 1:
				JOptionPane.showMessageDialog( this, "La transferencia se realizó con éxito", "Transferencia exitosa", JOptionPane.INFORMATION_MESSAGE );
				cleanAllFields();
				this.dispose();

				break;
				
			default:
				// este mensaje nunca debería aparecer
				JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}
}
	
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarCliente se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  TransferirAutomovil.this.dispose();
		      }
		    };
		jPanelTransferirAutomovil.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void cerrarTransferirAutomovil() {
		cleanAllFields();
		this.dispose();
	}
	
	private void cleanAllFields() {
		jTextFieldNombre.setText( "" );
		jTextFieldApellido.setText( "" );
		jTextFieldCUIT.setText( "" );
	}
	
		// función que rescata el nombre y el apellido de la fila seleccionada
	    private String getApellidoNombre(int rowIndex)  
	{  
	    String s = "";  
	    Object o = jTableBuscarCliente.getValueAt(rowIndex, 1);  
	    s += o.toString() + ", ";  
	    o = jTableBuscarCliente.getValueAt(rowIndex, 2);
	    s += o.toString();
	    return s;  
	}  
	    
}
