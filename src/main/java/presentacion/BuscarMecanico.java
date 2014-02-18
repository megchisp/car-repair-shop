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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import negocio.IMecanicoManager;
import negocio.MecanicoManager;

import persistencia.Mecanico;

public class BuscarMecanico extends JDialog {
	private static final long serialVersionUID = 1L;
	
	BuscarMecanico buscarMecanicoItSelf;
	
	List<Mecanico> listaMecanicos = null;

	private JFrame buscarMecanico;
	JPanel jPanelBuscarMecanico = null;
	JLabel jLabelNombre = null;
	JLabel jLabelApellido = null;
	JLabel jLabelContador = null;
		
	JTextField jTextFieldNombre = null;
	JTextField jTextFieldApellido = null;
		
	JButton jButtonBuscar = null;
	JButton jButtonModificar = null;
	JButton jButtonEliminar = null;
	JButton jButtonCerrar = null;
		
	JPanel jPanelPorNombreApellido = null; // contiene jLabelNombre, jLabelApellido, jTextFieldNombre y jTextFieldApellido
	JPanel jPanelCriterioDeBusqueda = null; 
	JPanel jPanelResultadoDeBusqueda = null; // contiene jTableMecanico
	
	JTable jTableBuscarMecanico = null;
	JScrollPane jScrollPaneBuscarMecanico = null;
	
	DefaultTableModel dtmBuscarMecanico = inicializoTableModel();
	
	// variables para realizar el filtrado por apellido y nombre
	TableRowSorter<DefaultTableModel> tableRowSorterNombreApellido = new TableRowSorter<DefaultTableModel>(dtmBuscarMecanico);
	ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
	RowFilter<Object,Object> tableRowSorterNombre = null;
	RowFilter<Object,Object> tableRowSorterApellido = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();

	public BuscarMecanico( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 570, 490 ) ); // Tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		buscarMecanicoItSelf = this;
		
		jPanelBuscarMecanico = new JPanel();
		jPanelBuscarMecanico.setFocusable(true);
		
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelBuscarMecanico.setBorder( titledBorder );
		
		jPanelBuscarMecanico.setLayout( new GridBagLayout() );
		
		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.EAST;
		
		inicializarVariables();
		extraerInfoBD();
		enableDisableButtons();
		cerrarEsc();
				
		a.gridy = 0;
		a.gridx = 0;
		jPanelBuscarMecanico.add( new JLabel(" "), a );
		
			/**********INICIO Generacion de jPanelCriterioDeBusqueda********/
			
			jPanelCriterioDeBusqueda = new JPanel();
			jPanelCriterioDeBusqueda.setBorder(BorderFactory.createTitledBorder(null, " Criterio de búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelCriterioDeBusqueda.setPreferredSize(new Dimension(520, 135));
			jPanelCriterioDeBusqueda.setLayout( new GridBagLayout() );
			
			GridBagConstraints e = new GridBagConstraints();
			e.anchor = GridBagConstraints.WEST;
				
			e.gridy = 0;
			e.gridx = 0;
			jPanelCriterioDeBusqueda.add( new JLabel(" "), e );
		
				// Comienza jPanelPorNombreApellido
				jPanelPorNombreApellido = new JPanel();
				jPanelPorNombreApellido.setLayout( new GridBagLayout() );
			
				GridBagConstraints b = new GridBagConstraints();
				//b.anchor = GridBagConstraints.EAST;
				
				b.gridy = 0;
				b.gridx = 0;
				b.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorNombreApellido.add( jLabelApellido, b );
				
				b.gridy = 0;
				b.gridx = 1;
				b.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorNombreApellido.add( jTextFieldApellido, b );
				
				b.gridy = 1;
				b.gridx = 0;
				jPanelPorNombreApellido.add( new JLabel(" "), b );
				
				b.gridy = 2;
				b.gridx = 0;
				b.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorNombreApellido.add( jLabelNombre, b );
				
				b.gridy = 2;
				b.gridx = 1;
				b.insets = new Insets( 0, 0, 0, 0 );
				jPanelPorNombreApellido.add( jTextFieldNombre, b );
				
			e.gridy = 1;
			e.gridx = 0;
			e.gridwidth = 2;
			e.insets = new Insets( 0, -10, 0, 0 );
			jPanelCriterioDeBusqueda.add( jPanelPorNombreApellido, e );
			// Finaliza jPanelPorCliente
			
			e.gridy = 1;
			e.gridx = 1;
			e.insets = new Insets( 0, 290, 38, 0 );
			jPanelCriterioDeBusqueda.add( jButtonBuscar, e );
			
		a.gridy = 1;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelBuscarMecanico.add( jPanelCriterioDeBusqueda, a );
		
		a.gridy = 2;
		a.gridx = 0;
		jPanelCriterioDeBusqueda.add( new JLabel(" "), a );
			
		/**********FIN Generacion de jPanelCriterioDeBusqueda*******/
		
		a.gridy = 2;
		a.gridx = 0;
		jPanelBuscarMecanico.add( new JLabel(" "), a );

		/**********INICIO Generacion de jPanelResultadoDeBusqueda********/
		
		jPanelResultadoDeBusqueda.setPreferredSize(new Dimension(520, 219)); // Dimensiones del panel
		jPanelResultadoDeBusqueda.setLayout( new GridBagLayout() );
		GridBagConstraints f = new GridBagConstraints();
		f.anchor = GridBagConstraints.WEST;

		f.gridy = 0;
		f.gridx = 0;
		jPanelResultadoDeBusqueda.add(jScrollPaneBuscarMecanico, f);
		
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
		jPanelBuscarMecanico.add(jPanelResultadoDeBusqueda, a); // agregamos la tabla que contiene los mecanicos
		
		a.gridy = 4;
		a.gridx = 0;
		jPanelBuscarMecanico.add( new JLabel(" "), a );	
		
		/**********FIN Generacion de jPanelResultadoDeBusqueda********/
		
		a.gridy = 6;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 320 );
		jPanelBuscarMecanico.add(jLabelContador, a);
		
		a.gridy = 6;
		a.gridx = 1;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelBuscarMecanico.add( jButtonCerrar, a );
		
		a.gridy = 7;
		a.gridx = 0;
		jPanelBuscarMecanico.add( new JLabel(" "), a );
			
		this.getContentPane().add( jPanelBuscarMecanico, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}

	OperationDelegate cerrarBuscarMecanico = new OperationDelegate() {
		public void invoke() {
			cerrarBuscarMecanico();
		}
	};
	
	
	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension( 75, 25 );
		// panel que contiene la tabla de mecanicos
		jPanelResultadoDeBusqueda = new JPanel();
		
		// hace que el cursor se posicione en el jTextFieldApellido al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextFieldApellido.requestFocusInWindow();
			}
		});
		
		jLabelNombre = new JLabel("Nombre: ");
		jLabelNombre.setPreferredSize( dimensionLabel );
		jTextFieldNombre = new JTextField();
		jTextFieldNombre = new JTextFieldOfLetters();
		jTextFieldNombre.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {  
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarMecanico();
                   }
                }
        }); 
		
		jLabelApellido = new JLabel("Apellido: ");
		jLabelApellido.setPreferredSize( dimensionLabel );
		jTextFieldApellido = new JTextField();
		jTextFieldApellido = new JTextFieldOfLetters();
		jTextFieldApellido.setPreferredSize( new Dimension( 200, 25 ) );
		jTextFieldApellido.addKeyListener(new java.awt.event.KeyAdapter() {  
            // habilita a realizar la búsqueda presionando el boton Enter
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                   buscarMecanico();
                   }
                }
        }); 
		
		// inicializo el jLabel que muestra el contador de registros
        jLabelContador = new JLabel();
        jLabelContador.setPreferredSize(new Dimension( 200, 25 ));
		
		ImageIcon imageIconSearch = new ImageIcon(resourceLoader.load("/images/menu/search-icon.png"));
		jButtonBuscar = new JButton( " Buscar", imageIconSearch );
		jButtonBuscar.setEnabled(true);  
		jButtonBuscar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarMecanico();
			}
		});
		
		ImageIcon imageIconUpdate = new ImageIcon(resourceLoader.load("/images/menu/update-icon.png"));
		jButtonModificar = new JButton( " Modificar", imageIconUpdate );
		jButtonModificar.setToolTipText("Modificar el mecánico seleccionado");
		jButtonModificar.setEnabled(false);
		jButtonModificar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonModificar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				BuscarMecanico.this.dispose();
				modificarMecanico();
			}
		});
		
		ImageIcon imageIconDelete = new ImageIcon(resourceLoader.load("/images/menu/delete-icon.png"));
		jButtonEliminar = new JButton( " Eliminar", imageIconDelete );
		jButtonEliminar.setEnabled(false);
		jButtonEliminar.setToolTipText("Eliminar el mecánico seleccionado");
		jButtonEliminar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonEliminar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int choice = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el mecánico seleccionado?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
					eliminarMecanico(Integer.parseInt(jTableBuscarMecanico.getValueAt(jTableBuscarMecanico.getSelectedRow(), 5).toString()));
					enableDisableButtons();
				}
			}   
		});
		
		ImageIcon imageIconClose = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));
		jButtonCerrar = new JButton( " Cerrar", imageIconClose  );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cerrarBuscarMecanico();
			}
		});
		
		
		jButtonBuscar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				buscarMecanico();
			}
		});

		
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				// elimina contenido de jTextFields
				jTextFieldNombre.setText("");
				jTextFieldApellido.setText("");
				BuscarMecanico.this.dispose();
			}
		});
		
		jTableBuscarMecanico = new JTable(dtmBuscarMecanico);
		jTableBuscarMecanico.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableBuscarMecanico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableBuscarMecanico.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableBuscarMecanico.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		jScrollPaneBuscarMecanico = new JScrollPane(jTableBuscarMecanico);
		jTableBuscarMecanico.setPreferredScrollableViewportSize(new Dimension(470,95));
		jScrollPaneBuscarMecanico.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// configuro el doble click sobre la fila
		jTableBuscarMecanico.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         BuscarMecanico.this.dispose();
			         new ModificarMecanico ( buscarMecanico, "Modificar mecánico", " Modificación de mecánico ", buscarMecanicoItSelf, listaMecanicos.get(Integer.parseInt(jTableBuscarMecanico.getValueAt(row, 5).toString())) );
			      }
			   }
			});
		
		// Tamaño a las columnas
		jTableBuscarMecanico.getColumnModel().getColumn(0).setMaxWidth(25);
		jTableBuscarMecanico.getColumnModel().getColumn(1).setPreferredWidth(50);
		jTableBuscarMecanico.getColumnModel().getColumn(2).setPreferredWidth(50);
		jTableBuscarMecanico.getColumnModel().getColumn(5).setMaxWidth(0);
		jTableBuscarMecanico.getColumnModel().getColumn(5).setMinWidth(0);
		jTableBuscarMecanico.getColumnModel().getColumn(5).setPreferredWidth(0);
		jTableBuscarMecanico.getColumnModel().getColumn(5).setResizable(false);;
		
		// FIN sección ActionListeners
		
		
		}
	
	
	
	private DefaultTableModel inicializoTableModel(){
		return new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnas[] = {"ID", "Apellido", "Nombre", "Teléfono", "Celular", "fila"};
			
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
		
	private void extraerInfoBD(){
		IMecanicoManager mecanicoManager = new MecanicoManager();

		try {
			listaMecanicos = mecanicoManager.listaMecanicos(); // obtengo todos los mecanicos de la BD
			completar_tabla(listaMecanicos.iterator()); // completa la tabla con la lista de mecanicos
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void completar_tabla(Iterator<Mecanico> iterator){
		// con este método agrego las filas a la tabla
		
		Mecanico mecanico;
		int numero_fila = 0;
		while (iterator.hasNext()){
			mecanico = iterator.next();
			String id_fila = Integer.toString(numero_fila++);
			String id_mecanico = String.format("%02d", mecanico.getId_mecanico());  
			String apellido = mecanico.getApellido();
			String nombre = mecanico.getNombre();
			String telefono = mecanico.getTelefono();
			String celular = mecanico.getCelular();
			Object [] fila= {id_mecanico, apellido, nombre, telefono, celular, id_fila};
			dtmBuscarMecanico.addRow(fila);
		}
	}	

	private void enableDisableButtons(){ // habilita o deshabilita los botones según hayan filas en la tabla
		TitledBorder titledBorder;
		if(jTableBuscarMecanico.getRowCount() > 0){
			jButtonEliminar.setEnabled(true);
			jButtonModificar.setEnabled(true);
			jTableBuscarMecanico.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableBuscarMecanico.getTableHeader().setBackground(new Color(236,243,255));
			jTableBuscarMecanico.getTableHeader().setForeground(new Color(0,0,0));
			jPanelResultadoDeBusqueda.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
		else{
			jButtonEliminar.setEnabled(false);
			jButtonModificar.setEnabled(false);
			jTableBuscarMecanico.getTableHeader().setBackground(new Color(238,238,238));
			jTableBuscarMecanico.getTableHeader().setForeground(new Color(153,153,153));
			jPanelResultadoDeBusqueda.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
		}
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarMecanico se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  BuscarMecanico.this.dispose();
		      }
		    };
		jPanelBuscarMecanico.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void buscarMecanico() {
		// elimino cualquier filtro que pudiera tener la tabla
		jTableBuscarMecanico.setRowSorter(null);

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
		jTableBuscarMecanico.setRowSorter(tableRowSorterNombreApellido);
	

		inicializarContador(); // seteo el resultado de la búsqueda
		enableDisableButtons(); // deshabilito botones si no hay resultados
	}
	
	private void inicializarContador(){
		// esta funcion setea el valor del jLabelContador
		if (jTableBuscarMecanico.getRowCount() > 1)
			jLabelContador.setText("<html><b> " + jTableBuscarMecanico.getRowCount() + " registros encontrados</b></html>");
		else
			if (jTableBuscarMecanico.getRowCount() == 1)
				jLabelContador.setText("<html><b> 1 registro encontrado</b></html>");			
			else
				jLabelContador.setText("<html><b><font color=\"#FF0000\">No se han encontrado registros</b></html>");
	}
	
	private void modificarMecanico() {
		new ModificarMecanico ( buscarMecanico, "Modificar mecánico", " Modificación de mecánico ", buscarMecanicoItSelf, listaMecanicos.get(Integer.parseInt(jTableBuscarMecanico.getValueAt(jTableBuscarMecanico.getSelectedRow(), 5).toString())) );
	}
	
	private void eliminarMecanico(int index){
		/** este método elimina el mecánico cuyo índice en la tabla se pasa como parámetro **/
		
		int option = 0;
		IMecanicoManager mecanicoManager = new MecanicoManager();
		try {
			option = mecanicoManager.eliminar( listaMecanicos.get(index) );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}

		switch( option ) {
		case 1:
			// elimino el mecanico de la lista de mecanicos
			listaMecanicos.remove(index);
			
			// elimino todos los mecanicos de la tabla
			dtmBuscarMecanico.getDataVector().removeAllElements();
			dtmBuscarMecanico.fireTableDataChanged();
			
			// una vez eliminado el mecanico completo la tabla con la lista de mecanicos
			completar_tabla(listaMecanicos.iterator());
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El mecánico no se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE );
			
			break;
		case 3:
			
			JOptionPane.showMessageDialog( this, "El mecánico posee al menos una mano de obra asociada", "Error", JOptionPane.ERROR_MESSAGE );
			break;
		default:
			// este mensaje nunca debería aparecer
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void cerrarBuscarMecanico() {
		cleanAllFields();
		BuscarMecanico.this.dispose();
	}
	
	private void cleanAllFields() {
		jTextFieldNombre.setText( "" );
		jTextFieldApellido.setText( "" );
	}
}
