package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import negocio.IServicioManager;
import negocio.ITipoDeServicioManager;
import negocio.ServicioManager;
import negocio.TipoDeServicioManager;

import persistencia.Reparacion;
import persistencia.Servicio;
import persistencia.TipoDeServicio;

public class AgregarServicio extends JDialog{
	
	private static final long serialVersionUID = 1L;
	
	List<TipoDeServicio> listaTiposDeServicio = null;

	//private static AgregarServicio instance = null;
	JPanel jPanelAgregarServicio = null;
	JButton jButtonCancelarServicio = null;
	JButton jButtonAgregarServicio = null;
	JTable jTableTipoServicio = null;
	JScrollPane scrollServicios = null;
	
	DetallarReparacion detallarReparacion = null;
	Reparacion reparacion = null;
	Vector<String> serviciosAgregadosCopia = null;

	ResourceLoader resourceLoader = new ResourceLoader();


	public AgregarServicio( JFrame padre, String titulo, String titleBorder, DetallarReparacion detallarReparacionPadre,  Reparacion reparacion) {
		super( padre, titulo, true );
		this.setPreferredSize( new Dimension( 250, 300 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
		
		detallarReparacion = detallarReparacionPadre;
		this.reparacion = reparacion;


		/* Panel exterior */
		jPanelAgregarServicio =  new JPanel(new GridBagLayout());
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelAgregarServicio.setBorder( titledBorder );
		jPanelAgregarServicio.setPreferredSize(new Dimension(300, 270));
		/* fin panel exterior */
		
		inicializarVariables();
		extraerInfoBD();
		cerrarEsc();
		
		

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		c.gridy = 0;
		c.gridx = 0;
		jPanelAgregarServicio.add( scrollServicios, c );

			JPanel jPanelBotones = new JPanel(new GridBagLayout());
			GridBagConstraints g = new GridBagConstraints();
			g.anchor = GridBagConstraints.WEST;
			
			g.gridy = 0;
			g.gridx = 0;
			jPanelBotones.add( jButtonAgregarServicio );
			g.gridy = 0;
			g.gridx = 1;
			g.insets = new Insets( 0, 20, 0, 0 );
			jPanelBotones.add( jButtonCancelarServicio, g );
			
		c.gridy = 1;
		c.gridx = 0;
		jPanelAgregarServicio.add( new JLabel(" "), c );
		c.gridy = 2;
		c.gridx = 0;
		jPanelAgregarServicio.add( jPanelBotones, c );
		
		this.getContentPane().add( jPanelAgregarServicio, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
		
	OperationDelegate aceptarAgregarServicio = new OperationDelegate() {
		public void invoke() {
			aceptarAgregarServicio();
		}
	};

	OperationDelegate cancelarAgregarServicio = new OperationDelegate() {
		public void invoke() {
			cancelarAgregarServicio();
		}
	};
	
	private void aceptarAgregarServicio() {
		int option = 0;
		int id_servicio;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		IServicioManager servicioManager = new ServicioManager();
		ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();
		Servicio servicio = new Servicio();
		String nombreServicio = null;
		try {
			id_servicio = servicioManager.nextID();
			servicio.setId_servicio(id_servicio);
			servicio.setId_reparacion(reparacion.getId_reparacion());
			servicio.setId_tipo_de_servicio(Integer.parseInt(jTableTipoServicio.getValueAt(jTableTipoServicio.getSelectedRow(), 0).toString()));
			option = servicioManager.agregar( servicio );
			
			nombreServicio = tipoDeServicioManager.getNombre(servicio);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			return;
		}
		
		switch( option ) {
		case 1:
//			JOptionPane.showMessageDialog( this, "Se agregó con éxito el servicio", "Éxito", JOptionPane.INFORMATION_MESSAGE );
			
			detallarReparacion.listaServicios.add(servicio);
			
			Object [] rowData= {servicio.getId_servicio(), nombreServicio, formatter.format((double)0), Integer.toString(detallarReparacion.dtmServiciosReparacion.getRowCount())};
			detallarReparacion.dtmServiciosReparacion.addRow(rowData);
			detallarReparacion.enableDisableButtons();
			detallarReparacion.servicioAgregado = true;
			this.dispose();
			
			break;
		case 2:
			JOptionPane.showMessageDialog( this, "El servicio ya se encuentra agregado", "Error", JOptionPane.ERROR_MESSAGE );
			
			break;
		default:
			// este mensaje nunca debería aparecer
			JOptionPane.showMessageDialog( this, "Algo salió mal", "Ups!", JOptionPane.ERROR_MESSAGE );
		}
	}

	private void cancelarAgregarServicio() {
		this.dispose();
	}
		
	private void inicializarVariables() {

		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));		
		jButtonAgregarServicio = new JButton( " Aceptar",  imageIconOk);
		jButtonAgregarServicio.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonAgregarServicio.addActionListener( new JButtonActionListener( aceptarAgregarServicio ) );
		jButtonAgregarServicio.addKeyListener( new JButtonKeyListener( cancelarAgregarServicio ) );
		
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancelarServicio = new JButton( " Cancelar", imageIconCancel);
		jButtonCancelarServicio.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelarServicio.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCancelarServicio.addActionListener( new JButtonActionListener( cancelarAgregarServicio ) );
		jButtonCancelarServicio.addKeyListener( new JButtonKeyListener( cancelarAgregarServicio ) );
		
		jTableTipoServicio = new JTable(dtmTipoServicio);
		jTableTipoServicio.setFont(new Font("Dialog", Font.BOLD, 11));
		jTableTipoServicio.setPreferredScrollableViewportSize(new Dimension(200,144));
		
		jTableTipoServicio.getColumnModel().getColumn(0).setMaxWidth(0);
		jTableTipoServicio.getColumnModel().getColumn(0).setMinWidth(0);
		jTableTipoServicio.getColumnModel().getColumn(0).setPreferredWidth(0);
		jTableTipoServicio.getColumnModel().getColumn(0).setResizable(false);
		jTableTipoServicio.getColumnModel().getColumn(1).setPreferredWidth(15);
		jTableTipoServicio.getColumnModel().getColumn(2).setPreferredWidth(200);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		jTableTipoServicio.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		jTableTipoServicio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableTipoServicio.getTableHeader().setReorderingAllowed(false);
		JTableHeader jTableHeaderHeader = jTableTipoServicio.getTableHeader();
		jTableHeaderHeader.setBackground(new Color(236,243,255));
		scrollServicios = new JScrollPane(jTableTipoServicio);
		scrollServicios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana AgregarServicio se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AgregarServicio.this.dispose();
			}
		};
		jPanelAgregarServicio.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void extraerInfoBD(){
		ITipoDeServicioManager tipoDeServicioManager = new TipoDeServicioManager();

		try {
			listaTiposDeServicio = tipoDeServicioManager.listaTiposDeServicio(); // obtengo todos los tipos de servicio de la BD
			completar_tabla(listaTiposDeServicio.iterator()); // completa la tabla con la lista de tipos de servicio
			jTableTipoServicio.setRowSelectionInterval(0, 0); // selecciona la primera fila
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void completar_tabla(Iterator<TipoDeServicio> iterator){
		// con este método agrego las filas a la tabla
		
		TipoDeServicio tipoDeServicio;
		IServicioManager servicioManager = new ServicioManager();
		List<Servicio> listaServicios;
		try {
			listaServicios = servicioManager.listaServicios(reparacion);
			while (iterator.hasNext()){
				tipoDeServicio = iterator.next();
				String id_tipo_de_servicio = Integer.toString(tipoDeServicio.getId_tipo_servicio());
				String nombre = tipoDeServicio.getNombre();
				Object [] fila= {id_tipo_de_servicio, existeServicio(tipoDeServicio, listaServicios.iterator()) ? "\u2713" : "" , nombre};
				dtmTipoServicio.addRow(fila);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private boolean existeServicio(TipoDeServicio tipoDeServicio, Iterator<Servicio> iterator){
		// Este método retorna true si el tipo de servicio ya se encuentra agregado.
		// Se utiliza para decidir si la tabla lleva tilde o no.
		Servicio servicio;
		while (iterator.hasNext()){
			servicio = iterator.next();
			if(servicio.getId_tipo_de_servicio() == tipoDeServicio.getId_tipo_servicio())
				return true;
		}
		return false;
	}


	//para que la tabla no sea editable
			DefaultTableModel dtmTipoServicio = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String nombresColumnas[] = {"ID","", "Tipo de servicio"};
			
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
