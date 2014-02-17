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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import negocio.ClienteManager;
import negocio.IClienteManager;

import persistencia.Cliente;

public class MostrarCumpleanios extends JDialog {
	private static final long serialVersionUID = 1L;
	public final static int WEEK_OF_YEAR = 3;
	
	// formatos para mostrar fechas
	DateFormat df = new SimpleDateFormat("dd/MM");
	DateFormat df2 = new SimpleDateFormat("dd/MM/YYYY");

	JPanel jPanelCriterioDeBusqueda = null;
	JPanel jPanelResultadoDeBusqueda = null;
	JPanel jPanelMostrarCumpleanios = null;
	
	JButton jButtonExportar = null;
	JButton jButtonCerrar = null;
	JButton jButtonMostrar = null;
	
	JRadioButton jRadioButtonFechaIndividual = null;
	JRadioButton jRadioButtonSemanaActual = null;
	JRadioButton jRadioButtonMesActual = null;
	
	ButtonGroup buttonGroupCriterio = null;
	
	JLabel jLabelDe = null;
	JLabel jLabelDesdeHasta = null;
	
	JComboBox<String> jComboBoxDia = null;
	JComboBox<String> jComboBoxMes = null;
	
	List<Cliente> listaClientes = null;
	IClienteManager clienteManager = null;

	int semanaDelAnio;
	int mesActual;
	int anioActual;
	Calendar calendarFechaActual; // almacena la fecha actual
	Calendar calendarDiaInicioSemana, calendarDiaFinSemana; // dias de inicio y de finalizacion de la semana actual
	
	JTable jTableCumpleClientes;
	JScrollPane scrollCumpleanios;
	ResourceLoader resourceLoader = new ResourceLoader();

	JPopupMenu jPopupMenu = null;
    JMenuItem jMenuItemLastFix = null;
    JMenuItem jMenuItemSelectAll = null;
    JMenuItem jMenuItemUnselectAll = null;

	public MostrarCumpleanios(JFrame padre, String titulo, String titleBorder) {
		super( padre, titulo, true );
				
		this.setPreferredSize( new Dimension( 620, 480 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
				
		jPanelMostrarCumpleanios = new JPanel();
		
		jPanelMostrarCumpleanios.setLayout( new GridBagLayout() );
		
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
		jPanelMostrarCumpleanios.setBorder( titledBorder );
		jPanelMostrarCumpleanios.setPreferredSize(new Dimension(610,450));

		a.gridy = 0;
		a.gridx = 0;
		jPanelMostrarCumpleanios.add( new JLabel(" "), a );
		
		
		/**********INICIO Generacion de jPanelCriterioDeBusqueda********/
		
			jPanelCriterioDeBusqueda = new JPanel();
			jPanelCriterioDeBusqueda.setLayout( new GridBagLayout() );
			jPanelCriterioDeBusqueda.setBorder(BorderFactory.createTitledBorder(null, " Criterio de búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
			jPanelCriterioDeBusqueda.setPreferredSize(new Dimension(570, 120)); // tamaño del panel
			
			GridBagConstraints b = new GridBagConstraints();
			b.anchor = GridBagConstraints.WEST;
			
			b.gridy = 0;
			b.gridx = 0;
			jPanelCriterioDeBusqueda.add( jRadioButtonFechaIndividual,b);
				JPanel jPanelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
	
				jPanelFecha.add( jComboBoxDia);
				jPanelFecha.add( jLabelDe);
				jPanelFecha.add( jComboBoxMes);
				jPanelFecha.add(jButtonMostrar);
				
				b.gridy = 0;
				b.gridx = 1;
				jPanelCriterioDeBusqueda.add( jPanelFecha, b );
			b.gridy = 1;
			b.gridx = 0;
			jPanelCriterioDeBusqueda.add( jRadioButtonSemanaActual,b);
			b.gridy = 1;
			b.gridx = 1;
			jPanelCriterioDeBusqueda.add( jLabelDesdeHasta,b);
			b.gridy = 2;
			b.gridx = 0;
			b.insets = new Insets( 5, 0, 0, 0 );
			jPanelCriterioDeBusqueda.add( jRadioButtonMesActual,b);

		a.gridy = 1;
		a.gridx = 0;
		a.gridwidth = 2;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelMostrarCumpleanios.add( jPanelCriterioDeBusqueda, a );

		/**********FIN Generacion de jPanelCriterioDeBusqueda*******/
		
		a.gridy = 2;
		a.gridx = 0;
		jPanelMostrarCumpleanios.add( new JLabel(" "), a );

			/**********INICIO Generacion de jPanelResultadoDeBusqueda********/
	
			jPanelResultadoDeBusqueda.setPreferredSize(new Dimension(570, 204)); // Dimensiones del panel
			jPanelResultadoDeBusqueda.setLayout( new GridBagLayout() );
			GridBagConstraints f = new GridBagConstraints();
			f.anchor = GridBagConstraints.WEST;
			
			f.gridy = 0;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add(scrollCumpleanios, f);
			
			f.gridy = 1;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add( new JLabel(" "), f );
			
				// se crea un panel que contiene los botones de Modificar y Eliminar
				JPanel jPanelBotones = new JPanel(new GridBagLayout());
				GridBagConstraints g = new GridBagConstraints();
				g.anchor = GridBagConstraints.WEST;
				
				g.gridy = 0;
				g.gridx = 0;
				jPanelBotones.add( jButtonExportar);
		
			f.gridy = 2;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add(jPanelBotones, f);
			
			f.gridy = 3;
			f.gridx = 0;
			jPanelResultadoDeBusqueda.add( new JLabel(" "), f );

		a.gridy = 3;
		a.gridx = 0;
		jPanelMostrarCumpleanios.add(jPanelResultadoDeBusqueda, a); // agregamos la tabla que contiene los proveedores
			
		a.gridy = 4;
		a.gridx = 0;
		jPanelMostrarCumpleanios.add( new JLabel(" "), a );	
			
			
			/**********FIN Generacion de jPanelResultadoDeBusqueda********/

		
		a.gridy = 6;
		a.gridx = 0;
		a.insets = new Insets( 0, 0, 0, 0 );
		jPanelMostrarCumpleanios.add( jButtonCerrar, a );
		
		a.gridy = 7;
		a.gridx = 0;
		jPanelMostrarCumpleanios.add( new JLabel(" "), a );
		
			
		this.getContentPane().add( jPanelMostrarCumpleanios, BorderLayout.NORTH );
		this.setResizable( false );
		this.pack();
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void inicializarVariables() {
		clienteManager = new ClienteManager();
		
		// panel que contiene la tabla de proveedores
		jPanelResultadoDeBusqueda = new JPanel();
		inicializarFecha();
		
		// right click
		jPopupMenu = new JPopupMenu();
		jMenuItemLastFix = new JMenuItem("");
		jMenuItemSelectAll = new JMenuItem("Seleccionar todo");
		jMenuItemUnselectAll = new JMenuItem("Deseleccionar todo");
		jMenuItemLastFix.setEnabled(false);
		jPopupMenu.add(jMenuItemLastFix);
		jPopupMenu.addSeparator();
		jPopupMenu.add(jMenuItemSelectAll);
		jPopupMenu.add(jMenuItemUnselectAll);
		
		// CREANDO LA TABLA
		jTableCumpleClientes = new JTable(tableModel);
		jTableCumpleClientes.setComponentPopupMenu(jPopupMenu);
		jTableCumpleClientes.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableCumpleClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableCumpleClientes.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableCumpleClientes.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		scrollCumpleanios = new JScrollPane(jTableCumpleClientes);
		jTableCumpleClientes.setPreferredScrollableViewportSize(new Dimension(520,85));
		scrollCumpleanios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//	Tamaño a las columnas
		jTableCumpleClientes.getColumnModel().getColumn(0).setMaxWidth(20);
		jTableCumpleClientes.getColumnModel().getColumn(0).setMinWidth(20);
		jTableCumpleClientes.getColumnModel().getColumn(0).setResizable(false);
		jTableCumpleClientes.getColumnModel().getColumn(1).setMaxWidth(50);
		jTableCumpleClientes.getColumnModel().getColumn(1).setMinWidth(50);
		jTableCumpleClientes.getColumnModel().getColumn(1).setResizable(false);
		jTableCumpleClientes.getColumnModel().getColumn(2).setPreferredWidth(150);
		jTableCumpleClientes.getColumnModel().getColumn(5).setMaxWidth(0);
		jTableCumpleClientes.getColumnModel().getColumn(5).setMinWidth(0);
		jTableCumpleClientes.getColumnModel().getColumn(5).setPreferredWidth(0);
		jTableCumpleClientes.getColumnModel().getColumn(4).setResizable(false);
				
		//centro la columna fecha
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		jTableCumpleClientes.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );

		
		// para que seleccione la fila cuando se hace right click
		jTableCumpleClientes.addMouseListener(new java.awt.event.MouseAdapter(){
		 public void mousePressed(MouseEvent event) {
		        // selects the row at which point the mouse is clicked
		        Point point = event.getPoint();
		        int currentRow = jTableCumpleClientes.rowAtPoint(point);
		        jTableCumpleClientes.setRowSelectionInterval(currentRow, currentRow);
		        seteaJMenuItem();
		    }
		});
		
		// action listener para que seleccione todo
		jMenuItemSelectAll.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				for (int i = 0; i < jTableCumpleClientes.getRowCount(); i++) {
					jTableCumpleClientes.setValueAt(true, i, 0);
			    }
				tableModel.fireTableRowsUpdated(0, jTableCumpleClientes.getRowCount());
			}
		});
		
		// action listener para que deseleccione todo
		jMenuItemUnselectAll.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				for (int i = 0; i < jTableCumpleClientes.getRowCount(); i++) {
					jTableCumpleClientes.setValueAt(false, i, 0);
			    }
				tableModel.fireTableRowsUpdated(0, jTableCumpleClientes.getRowCount());
			}
		});
		
		jRadioButtonFechaIndividual = new JRadioButton( "Fecha individual ", true );
		jRadioButtonFechaIndividual.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonMostrar.setEnabled(true);
				jComboBoxDia.setEnabled(true);
				jComboBoxMes.setEnabled(true);
				jLabelDe.setEnabled(true);
				jLabelDesdeHasta.setEnabled(false);
			}
		});
		
		jRadioButtonSemanaActual = new JRadioButton( "Semana actual", false );
		jRadioButtonSemanaActual.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonMostrar.setEnabled(false);
				jComboBoxDia.setEnabled(false);
				jComboBoxMes.setEnabled(false);
				jLabelDe.setEnabled(false);
				jLabelDesdeHasta.setEnabled(true);
			}
		
		});
		
		jRadioButtonMesActual = new JRadioButton( "Mes actual ", false );
		jRadioButtonMesActual.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonMostrar.setEnabled(false);
				jComboBoxDia.setEnabled(false);
				jComboBoxMes.setEnabled(false);
				jLabelDe.setEnabled(false);
				jLabelDesdeHasta.setEnabled(false);
			}
		});
		
		buttonGroupCriterio = new ButtonGroup();
		buttonGroupCriterio.add(jRadioButtonFechaIndividual);
		buttonGroupCriterio.add(jRadioButtonSemanaActual);
		buttonGroupCriterio.add(jRadioButtonMesActual);
		
		ImageIcon imageIconExport = new ImageIcon(resourceLoader.load("/images/menu/export-icon.png"));
		jButtonExportar = new JButton( " Exportar", imageIconExport );
		jButtonExportar.setToolTipText("Exportar en archivo de texto");
		jButtonExportar.setPreferredSize( new Dimension( 100, 30 ) );
		
		ImageIcon imageIconSearch = new ImageIcon(resourceLoader.load("/images/menu/search-icon.png"));
		jButtonMostrar = new JButton( " Mostrar", imageIconSearch );
		jButtonMostrar.setPreferredSize( new Dimension( 100, 30 ) );
				
		ImageIcon imageIconClose = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));
		jButtonCerrar = new JButton( " Cerrar", imageIconClose );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		
		jLabelDe = new JLabel("de");
		jLabelDesdeHasta = new JLabel();
		jLabelDesdeHasta.setEnabled(false); // porque por defecto se selecciona por fecha individual
		jLabelDesdeHasta.setFont(new Font("Dialog", Font.ITALIC, 12));
		// asignando texto al JLabel jLabelDesdeHasta
		String s = "(del ";
		s += calendarDiaInicioSemana.get(Calendar.DATE);
		s += " de ";
		s += jComboBoxMes.getItemAt(calendarDiaInicioSemana.get(Calendar.MONTH));
		s += " al ";
		s += calendarDiaFinSemana.get(Calendar.DATE);
		s += " de ";
		s += jComboBoxMes.getItemAt(calendarDiaFinSemana.get(Calendar.MONTH));
		s+= ")";
		jLabelDesdeHasta.setText(s);
		
		jButtonExportar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				exportarCumpleanios();
			}
		});
		
		jButtonMostrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				mostrarCumpleanios();
			}
		});
		
		
		jRadioButtonFechaIndividual.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				mostrarCumpleanios();
			}
		});
		
		jRadioButtonMesActual.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				mostrarCumpleanios();
			}
		});
		
		jRadioButtonSemanaActual.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				mostrarCumpleanios();
			}
		});
		
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				MostrarCumpleanios.this.dispose();
		}
	});
	
	}
	
	//para que la tabla no sea editable
	DefaultTableModel tableModel = new DefaultTableModel() {
	private static final long serialVersionUID = 1L;
	String nombresColumnas[] = {"","Fecha", "Cliente" ,"Teléfono","Celular", "fila"};
	
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
       return column == 0;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	 return getValueAt(0, columnIndex).getClass();
    
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == 0) {
		@SuppressWarnings("rawtypes")
		Vector rowData = (Vector)getDataVector().get(row);
        rowData.set(0, aValue);
        fireTableCellUpdated(row, column);
      }
    }
    
	};

	private void completar_tabla(Iterator<Cliente> iterator){
		// con este método agrego los clientes que cumplen año segun criterio seleccionado

		if(jRadioButtonFechaIndividual.isSelected())
			completar_tabla_fecha_individual(listaClientes.iterator());
		else
			if(jRadioButtonSemanaActual.isSelected())
				completar_tabla_semana_actual(listaClientes.iterator());
			else
				completar_tabla_mes_actual(listaClientes.iterator());
	
	}
	
	private void completar_tabla_fecha_individual(Iterator<Cliente> iterator){
		Date date;
		Cliente cliente;
		int num_fila = 0;
		while (iterator.hasNext()){
			cliente = iterator.next();
			date = new java.sql.Date(cliente.getFechaDeNacimiento().getTimeInMillis());
			String fecha = df.format(date);
			if(cliente.getFechaDeNacimiento().get(Calendar.DAY_OF_MONTH) == (jComboBoxDia.getSelectedIndex() + 1) && cliente.getFechaDeNacimiento().get(Calendar.MONTH) == (jComboBoxMes.getSelectedIndex()))
			{
				String apellido = cliente.getApellido();
				String nombre = cliente.getNombre();
				String telefono = cliente.getTelefono();
				String celular = cliente.getCelular();
				Object [] fila= {true,fecha, apellido + ", " + nombre, telefono, celular, Integer.toString(num_fila)};
				tableModel.addRow(fila);
			}
			num_fila++;
		}
	}
	
	private void completar_tabla_semana_actual(Iterator<Cliente> iterator){
		Date date;
		Cliente cliente;
		Calendar fechaModificada;
		int num_fila = 0;
		while (iterator.hasNext()){
			cliente = iterator.next();
			date = new java.sql.Date(cliente.getFechaDeNacimiento().getTimeInMillis());
			String fecha = df.format(date);
			
			fechaModificada = (Calendar) cliente.getFechaDeNacimiento().clone(); // .clone para que no me modifique la lista de clientes (especialmente los años bisiestos)
			fechaModificada.set(Calendar.YEAR, anioActual);

			if(calendarFechaActual.get(Calendar.WEEK_OF_YEAR) == fechaModificada.get(Calendar.WEEK_OF_YEAR))
			{
				String apellido = cliente.getApellido();
				String nombre = cliente.getNombre();
				String telefono = cliente.getTelefono();
				String celular = cliente.getCelular();
				Object [] fila= {true,fecha, apellido + ", " + nombre, telefono, celular, Integer.toString(num_fila)};
				tableModel.addRow(fila);
			}
			num_fila++;
		}
	}
	
	private void completar_tabla_mes_actual(Iterator<Cliente> iterator){
		Date date;
		Cliente cliente;
		int num_fila = 0;
		while (iterator.hasNext()){
			cliente = iterator.next();
			date = new java.sql.Date(cliente.getFechaDeNacimiento().getTimeInMillis());
			String fecha = df.format(date);
			
			if(cliente.getFechaDeNacimiento().get(Calendar.MONTH) == mesActual)
			{
				String apellido = cliente.getApellido();
				String nombre = cliente.getNombre();
				String telefono = cliente.getTelefono();
				String celular = cliente.getCelular();
				Object [] fila= {true,fecha, apellido + ", " + nombre, telefono, celular,Integer.toString(num_fila)};
				tableModel.addRow(fila);
			}
			num_fila++;
		}
	}
	
	private void seteaJMenuItem(){
		/** este método setea el jMenuItem con la última fecha de reparación **/
		Cliente cliente;
		
		cliente = listaClientes.get(Integer.parseInt(jTableCumpleClientes.getValueAt(jTableCumpleClientes.getSelectedRow(), 5).toString()));
		String ultima_vez;
		
		try {
			ultima_vez = df2.format(clienteManager.ultimaVez(cliente));
		} catch (Exception e) {
			ultima_vez = "-----";
		}
		jMenuItemLastFix.setText("Última reparación: " + ultima_vez);
	}
	
	private void inicializarFecha() {
		jComboBoxDia = new JComboBox<String>();
		
		for(int i=1; i<= 31; i++)
			jComboBoxDia.addItem(i+"");
		
		// Recupero la semana y el mes actual
		calendarFechaActual = Calendar.getInstance();
		mesActual = calendarFechaActual.get(Calendar.MONTH); // mes actual
		semanaDelAnio = calendarFechaActual.get(Calendar.WEEK_OF_YEAR); // semana actual
		anioActual = calendarFechaActual.get(Calendar.YEAR);

		jComboBoxMes = new JComboBox<String>();

		jComboBoxMes.addItem("Enero");
		jComboBoxMes.addItem("Febrero");
		jComboBoxMes.addItem("Marzo");
		jComboBoxMes.addItem("Abril");
		jComboBoxMes.addItem("Mayo");
		jComboBoxMes.addItem("Junio");
		jComboBoxMes.addItem("Julio");
		jComboBoxMes.addItem("Agosto");
		jComboBoxMes.addItem("Septiembre");
		jComboBoxMes.addItem("Octubre");
		jComboBoxMes.addItem("Noviembre");
		jComboBoxMes.addItem("Diciembre");
		
		// Seteo los comboBox con la fecha actual
		jComboBoxDia.setSelectedIndex((calendarFechaActual.get(Calendar.DATE))-1);
		jComboBoxMes.setSelectedIndex(mesActual);
		
		// Calculando los días de inicio y fin de la semana actual
		calendarDiaInicioSemana = Calendar.getInstance();
		calendarDiaInicioSemana.clear();
		calendarDiaInicioSemana.set(Calendar.WEEK_OF_YEAR, semanaDelAnio);
		calendarDiaInicioSemana.set(Calendar.YEAR, anioActual);
		calendarDiaFinSemana = Calendar.getInstance();
		calendarDiaFinSemana.clear();
		calendarDiaFinSemana.set(Calendar.WEEK_OF_YEAR, semanaDelAnio);
		calendarDiaFinSemana.set(Calendar.YEAR, anioActual);
		calendarDiaFinSemana.add(Calendar.DATE, 6);
	}
	
	private void enableDisableButtons(){ // habilita o deshabilita los botones según hayan filas en la tabla
		TitledBorder titledBorder;
		if(jTableCumpleClientes.getRowCount() > 0){
			jButtonExportar.setEnabled(true);
			jTableCumpleClientes.getTableHeader().setBackground(new Color(236,243,255));
			jTableCumpleClientes.setRowSelectionInterval(0, 0); // selecciona la primera fila
			jTableCumpleClientes.getTableHeader().setForeground(new Color(0,0,0));
			jTableCumpleClientes.setEnabled(true);
			jPanelResultadoDeBusqueda.setEnabled(true);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 184, 207, 229 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
			

		}
		else{
			jButtonExportar.setEnabled(false);
			jTableCumpleClientes.getTableHeader().setBackground(new Color(238,238,238));
			jTableCumpleClientes.getTableHeader().setForeground(new Color(153,153,153));
			jTableCumpleClientes.setEnabled(false);
			jPanelResultadoDeBusqueda.setEnabled(false);
			titledBorder = BorderFactory.createTitledBorder(null, " Resultado de la búsqueda ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213));
			titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 153, 153, 153 ) ) );
			jPanelResultadoDeBusqueda.setBorder( titledBorder );
			
		}
	}
	
	private void exportarCumpleanios() {
		showSaveFileDialog();
	}
	
	private void extraerInfoBD(){
		IClienteManager clienteManager = new ClienteManager();

		try {
			listaClientes = clienteManager.listaClientesNoFallecidos(); // obtengo todos los clientes de la BD
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
		
		ordenarListaClientes(); // ordeno la lista por fecha de cumpleaños
	}

	private void mostrarCumpleanios(){
		// elimino todos los clientes de la tabla
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		completar_tabla(listaClientes.iterator());		
		enableDisableButtons();
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarMecanico se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  MostrarCumpleanios.this.dispose();
		      }
		    };
		jPanelMostrarCumpleanios.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void showSaveFileDialog() {

		/*****************************************/
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt", "text");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle(" Exportar ");
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File file = new File(fileChooser.getSelectedFile().toString() + (fileChooser.getSelectedFile().toString().endsWith(".txt") ? "" : ".txt"));
                PrintWriter printerWriterJTable = new PrintWriter(file);
                
                // imprime titulo del archivo
                if(jRadioButtonFechaIndividual.isSelected())
                	printerWriterJTable.println("CUMPLEAÑOS DEL DIA");
                else
                	if(jRadioButtonSemanaActual.isSelected())
                		printerWriterJTable.println("CUMPLEAÑOS DE LA SEMANA " + jLabelDesdeHasta.getText());
                	else
                		printerWriterJTable.println("CUMPLEAÑOS DEL MES");
                printerWriterJTable.println("");
                printerWriterJTable.println("");
                
//                 imprime encabezado de la tabla
                    printerWriterJTable.printf("%-6s %-35s %-23s %-24s\n",
                    jTableCumpleClientes.getColumnName(1),
                    jTableCumpleClientes.getColumnName(2),
                    jTableCumpleClientes.getColumnName(3),
                    jTableCumpleClientes.getColumnName(4));
                
//                 imprime contenido de la tabla
                printerWriterJTable.println("");
                for (int i = 0; i < jTableCumpleClientes.getRowCount(); i++){
                	if((Boolean) jTableCumpleClientes.getValueAt(i, 0)){
	                	 printerWriterJTable.printf("%-6s %-35s %-23s %-24s\n",
	                	jTableCumpleClientes.getValueAt(i, 1).toString(),
	                	(jTableCumpleClientes.getValueAt(i, 2).toString().length() > 34) ? jTableCumpleClientes.getValueAt(i, 2).toString().substring(0, 34) : jTableCumpleClientes.getValueAt(i, 2).toString(),
	                	jTableCumpleClientes.getValueAt(i, 3).toString(),
	                	jTableCumpleClientes.getValueAt(i, 4).toString());
	
	                	 printerWriterJTable.println("");
                	}
                }
                
                printerWriterJTable.close();
                JOptionPane.showMessageDialog( this, "Los cumpleaños se han exportado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE );
            } catch (IOException e1) {
                JOptionPane.showMessageDialog( this, e1, "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }
	
	void ordenarListaClientes(){
		// este método ordena la lista cronologicamente por dia y mes unicamente (no contemplo año)
		Collections.sort(listaClientes, new Comparator<Cliente>(){
			public int compare(Cliente o1, Cliente o2) {
				  int returnVal = 0;
				    if(o1.getFechaDeNacimiento().get(Calendar.MONTH) < o2.getFechaDeNacimiento().get(Calendar.MONTH)){
				        returnVal =  -1;
				    }else if(o1.getFechaDeNacimiento().get(Calendar.MONTH) > o2.getFechaDeNacimiento().get(Calendar.MONTH)){
				        returnVal =  1;
				    }else if(o1.getFechaDeNacimiento().get(Calendar.DAY_OF_MONTH) < o2.getFechaDeNacimiento().get(Calendar.DAY_OF_MONTH)){
				        returnVal =  -1;
				    }else if(o1.getFechaDeNacimiento().get(Calendar.DAY_OF_MONTH) > o2.getFechaDeNacimiento().get(Calendar.DAY_OF_MONTH)){
				    	returnVal = 1;
				    }
				    return returnVal; // return 0, quiere decir que el dia y el mes son iguales
			}
		});
	}
}