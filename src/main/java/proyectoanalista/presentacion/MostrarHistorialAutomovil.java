package proyectoanalista.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import proyectoanalista.negocio.ClienteManager;
import proyectoanalista.negocio.IClienteManager;
import proyectoanalista.negocio.IManoDeObraManager;
import proyectoanalista.negocio.IReparacionManager;
import proyectoanalista.negocio.IRepuestoManager;
import proyectoanalista.negocio.IServicioManager;
import proyectoanalista.negocio.ITipoDeServicioManager;
import proyectoanalista.negocio.ManoDeObraManager;
import proyectoanalista.negocio.ReparacionManager;
import proyectoanalista.negocio.RepuestoManager;
import proyectoanalista.negocio.ServicioManager;
import proyectoanalista.negocio.TipoDeServicioManager;
import proyectoanalista.persistencia.Automovil;
import proyectoanalista.persistencia.ManoDeObra;
import proyectoanalista.persistencia.Reparacion;
import proyectoanalista.persistencia.Repuesto;
import proyectoanalista.persistencia.Servicio;

public class MostrarHistorialAutomovil extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private DefaultTreeModel defaultTreeModelModelo = null;
	DefaultMutableTreeNode defaultMutableTreeNodeRaiz = null;
	private JTree jTreeTree = null;
	
	private JPanel jPanelHistorialAutomovil = null;
	private JPanel jPanelArbol = null;
	private JPanel jPanelArbol2 = null;
	private JScrollPane JScrollPaneArbol = null;
	private JLabel jLabelTitulo = null;
	private JButton jButtonExpandir = null;
	private JButton jButtonExportar = null;
	private JButton jButtonContraer = null;
	private JButton jButtonCerrar = null;
	
	// Entidades
	Automovil automovil = null;
	Reparacion reparacion = null;
	Servicio servicio = null;
	ManoDeObra manoDeObra = null;
	Repuesto repuesto = null;
	
	// Managers
	IClienteManager clienteManager = null;
	IReparacionManager reparacionManager = null;
	IServicioManager servicioManager = null;
	ITipoDeServicioManager tipoDeServicioManager = null;
	IManoDeObraManager manoDeObraManager = null;
	IRepuestoManager repuestoManager = null;
	
	// Listas
	List<Reparacion> listaReparaciones = null;
	List<Servicio> listaServicios = null;
	List<ManoDeObra> listaManosDeObras = null;
	List<Repuesto> listaRepuestos = null;
	
	// Iteradores
	Iterator<Reparacion> iteratorReparacion = null;
	Iterator<Servicio> iteratorServicio = null;
	Iterator<ManoDeObra> iteratorManoDeObra = null;
	Iterator<Repuesto> iteratorRepuesto = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	MostrarHistorialAutomovil( JFrame padre, String titulo, String titleBorder, Automovil automovil ) {
		super( padre, titulo, true );
		this.setPreferredSize( new Dimension( 598, 542 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		this.automovil = automovil;
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);

		/* Panel exterior */
		jPanelHistorialAutomovil = new JPanel();
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		jPanelHistorialAutomovil.setBorder( titledBorder );
		jPanelHistorialAutomovil.setPreferredSize(new Dimension(590, 513));
		jPanelHistorialAutomovil.setLayout(new FlowLayout(FlowLayout.CENTER, 0,10));
		/* fin panel exterior */
		
		inicializarVariables();
		cerrarEsc();
		
		JPanel jPanelTitulo = new JPanel();
		jPanelTitulo.setPreferredSize(new Dimension(540, 30));
		jPanelTitulo.add(jLabelTitulo);
		jPanelHistorialAutomovil.add( jPanelTitulo );

		jPanelArbol = new JPanel(); // panel que contiene el arbol jTreeTree
		jPanelArbol2 = new JPanel(); // panel que contiene el panel jPanelArbol
		JScrollPaneArbol = new JScrollPane(jPanelArbol);
		JScrollPaneArbol.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPaneArbol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		jPanelArbol2.setBorder(BorderFactory.createTitledBorder(null, " Árbol de reparaciones ", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, 
				new Font("Dialog", Font.PLAIN, 12), new Color(0, 70, 213)));
		jPanelArbol2.setPreferredSize(new Dimension(560, 385));
		jPanelArbol2.setLayout( new FlowLayout(FlowLayout.LEFT,5,10) );
			JScrollPaneArbol.setPreferredSize(new Dimension(520, 300));
			jPanelArbol.setLayout( new FlowLayout(FlowLayout.LEFT,0,0) );
			JScrollPaneArbol.setBorder(null);
			jPanelArbol.add( jTreeTree);
			jPanelArbol2.add( JScrollPaneArbol );
			jPanelArbol2.add(jButtonExpandir);
			jPanelArbol2.add(jButtonContraer);
			jPanelArbol2.add(new JLabel(" ")); // añade un espacio
			jPanelArbol2.add(jButtonExportar);
			
		jPanelHistorialAutomovil.add(jPanelArbol2);
		
			JPanel jPanelBotonCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			jPanelBotonCerrar.setPreferredSize(new Dimension(556, 30));
			jPanelBotonCerrar.add(jButtonCerrar);
		jPanelHistorialAutomovil.add(jPanelBotonCerrar);
		
		// completo el arbol
		populateJTree();
		
		jTreeTree.setSize(new Dimension( 400, 500 ));
		
		this.getContentPane().add( jPanelHistorialAutomovil, BorderLayout.NORTH );
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo(null);
		this.setVisible( true );
	}
	
		
	private void cerrarEsc(){
		// esta funcion hace que la ventana BuscarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				MostrarHistorialAutomovil.this.dispose();
			}
		};
		jPanelHistorialAutomovil.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}


	private void inicializarVariables() {
		jTreeTree = new JTree();
		jTreeTree.setFont(new Font("Tahoma", Font.PLAIN, 16));
		defaultMutableTreeNodeRaiz = new DefaultMutableTreeNode("Reparaciones");
		defaultTreeModelModelo = new DefaultTreeModel(defaultMutableTreeNodeRaiz);
		
		// customizo el arbol
		Icon closedIcon = new ImageIcon(resourceLoader.load("/images/FolderClosed.png"));
		Icon OpenIcon = new ImageIcon(resourceLoader.load("/images/FolderOpened.png"));
		Icon ArrowIcon = new ImageIcon(resourceLoader.load("/images/bullet-black.png"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setClosedIcon(closedIcon);
		renderer.setOpenIcon(OpenIcon);
		renderer.setLeafIcon(ArrowIcon);
		renderer.setName("Reparaciones");
		renderer.setBackgroundNonSelectionColor(null);
		jTreeTree.setCellRenderer(renderer);
		
		reparacionManager = new ReparacionManager();
		servicioManager = new ServicioManager();
		tipoDeServicioManager = new TipoDeServicioManager();
		manoDeObraManager = new ManoDeObraManager();
		clienteManager = new ClienteManager();
		repuestoManager = new RepuestoManager();
		
		jLabelTitulo = new JLabel( "Historial del autómovil" );
		jLabelTitulo.setFont(new Font("Dialog", Font.BOLD, 20));
		
		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ));
		
		ImageIcon imageIconCollapse = new ImageIcon(resourceLoader.load("/images/menu/collapse-icon.png"));
		jButtonContraer = new JButton( imageIconCollapse );
		jButtonContraer.setToolTipText( "Contraer árbol de reparaciones" );
		jButtonContraer.setPreferredSize( new Dimension( 30, 30 ));
		
		ImageIcon imageIconExpand = new ImageIcon(resourceLoader.load("/images/menu/expand-icon.png"));
		jButtonExpandir = new JButton( imageIconExpand );
		jButtonExpandir.setToolTipText( "Expandir árbol de reparaciones" );
		jButtonExpandir.setPreferredSize( new Dimension( 30, 30 ));
		
		ImageIcon imageIconExport = new ImageIcon(resourceLoader.load("/images/menu/export-icon.png"));		
		jButtonExportar = new JButton( "Exportar", imageIconExport );
		jButtonExportar.setToolTipText("Exportar en archivo de texto");
		jButtonExportar.setPreferredSize( new Dimension( 100, 30 ));
		
		expandAll(jTreeTree);
		// fin de carga de ejemplo del arbol
		
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				MostrarHistorialAutomovil.this.dispose();
			}
		});

		jButtonExportar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				exportarHistorial();
			}
		});
		
		jButtonExpandir.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				expandAll(jTreeTree);
			}
		});

		jButtonContraer.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				collapseAll(jTreeTree);
			}
		});
	}
	
	public void expandAll(JTree JTreeTree) {
	    int row = 0;
	    while (row < JTreeTree.getRowCount()) {
	    	JTreeTree.expandRow(row);
	      row++;
	      }
	    }
	
	public void collapseAll(JTree JTreeTree) {
	    int row = 0;
	    while (row < JTreeTree.getRowCount()) {
	    	JTreeTree.collapseRow(row);
	      row++;
	      }
	    }
	
	void populateJTree(){
		// para mostrar la fecha
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/YYYY", new Locale("es", "AR"));
		int reparacionIndex = 0;
		
		try {
			listaReparaciones = reparacionManager.listaReparaciones(automovil);
			iteratorReparacion = listaReparaciones.iterator();
			
			while(iteratorReparacion.hasNext()){
				// agrego reparaciones
				reparacion = iteratorReparacion.next();
				String fecha = sdf.format(new java.sql.Date(reparacion.getFechaReparacion().getTimeInMillis()));
				DefaultMutableTreeNode reparacionNode = new DefaultMutableTreeNode(fecha);
				defaultTreeModelModelo.insertNodeInto(reparacionNode, defaultMutableTreeNodeRaiz, reparacionIndex++);
				
				insertServicios(reparacionNode);
			}
			
			jTreeTree.setModel(defaultTreeModelModelo);
			jTreeTree.setBackground(null);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog( this, "Error al recuperar información de la base de datos", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	void insertServicios(DefaultMutableTreeNode reparacionNode) throws Exception{
		// agrego servicios por cada reparacion
		listaServicios = servicioManager.listaServicios(reparacion);
		iteratorServicio = listaServicios.iterator();
		while(iteratorServicio.hasNext()){
			int servicioIndex = 0;
			servicio = iteratorServicio.next();
			String nombreServicio = tipoDeServicioManager.getNombre(servicio);
			DefaultMutableTreeNode servicioNode = new DefaultMutableTreeNode(nombreServicio);
			defaultTreeModelModelo.insertNodeInto(servicioNode, reparacionNode, servicioIndex++);
			
			// agrego carpeta manos de obras y repuestos por cada servicio
			DefaultMutableTreeNode manosDeObraRaiz = new DefaultMutableTreeNode("Manos de obra");
			DefaultMutableTreeNode repuestosRaiz = new DefaultMutableTreeNode("Repuestos");
			defaultTreeModelModelo.insertNodeInto(manosDeObraRaiz, servicioNode, 0);
			defaultTreeModelModelo.insertNodeInto(repuestosRaiz, servicioNode, 1);
			
			insertManosDeObras(manosDeObraRaiz);
			insertRepuestos(repuestosRaiz);
		}
	}
	
	void insertManosDeObras(DefaultMutableTreeNode manosDeObraRaiz) throws Exception{
		listaManosDeObras = manoDeObraManager.listaManosDeObras(servicio);
		iteratorManoDeObra = listaManosDeObras.iterator();
		
		if(!iteratorManoDeObra.hasNext()){ // si el servicio NO tiene manos de obra...
			DefaultMutableTreeNode manoDeObraNode = new DefaultMutableTreeNode("< sin manos de obra >");
			defaultTreeModelModelo.insertNodeInto(manoDeObraNode, manosDeObraRaiz, 0);
		}
		else
		while(iteratorManoDeObra.hasNext()){
			int manoDeObraIndex = 0;
			manoDeObra = iteratorManoDeObra.next();
			String nombreManoDeObra = manoDeObra.getNombre();
			DefaultMutableTreeNode manoDeObraNode = new DefaultMutableTreeNode(nombreManoDeObra);
			defaultTreeModelModelo.insertNodeInto(manoDeObraNode, manosDeObraRaiz, manoDeObraIndex++);
		}
	}
	
	void insertRepuestos(DefaultMutableTreeNode respuestosRaiz) throws Exception{
		listaRepuestos = repuestoManager.listaRepuestos(servicio);
		iteratorRepuesto = listaRepuestos.iterator();
		
		if(!iteratorRepuesto.hasNext()){ // si el servicio NO tiene repuestos...
			DefaultMutableTreeNode repuestoNode = new DefaultMutableTreeNode("< sin repuestos >");
			defaultTreeModelModelo.insertNodeInto(repuestoNode, respuestosRaiz, 0);
		}
		else
		while(iteratorRepuesto.hasNext()){
			int repuestoIndex = 0;
			repuesto = iteratorRepuesto.next();
			String nombreRepuesto= repuesto.getNombre();
			DefaultMutableTreeNode repuestoNode = new DefaultMutableTreeNode(nombreRepuesto);
			defaultTreeModelModelo.insertNodeInto(repuestoNode, respuestosRaiz, repuestoIndex++);
		}
	}
	
	private void exportarHistorial(){

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
                printerWriterJTable.println("---------------| HISTORIAL DE REPARACIONES |---------------\r\n");

                // imprime datos del propietario y del automovil
                printerWriterJTable.println("Propietario: " + clienteManager.getApellidoNombre(automovil)[0] + ", " + clienteManager.getApellidoNombre(automovil)[1]);
                printerWriterJTable.println("Automovil: " + automovil.getMarca() + " " + automovil.getModelo() + "\r\n");
                
                // imprime arbol
                printerWriterJTable.println(getTreeText(defaultTreeModelModelo, defaultMutableTreeNodeRaiz, ""));
                
                // cierra archivo
                printerWriterJTable.close();
                JOptionPane.showMessageDialog( this, "El historial se ha exportado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE );
            } catch (IOException e1) {
                JOptionPane.showMessageDialog( this, e1, "Error I/O", JOptionPane.ERROR_MESSAGE );
            }
            catch (Exception e1) {
                JOptionPane.showMessageDialog( this, e1, "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
	}

	private static String getTreeText(DefaultTreeModel model, Object object, String indent) {
	    String myRow = indent + object + "\r\n";
	    for (int i = 0; i < model.getChildCount(object); i++) {
	        myRow += getTreeText(model, model.getChild(object, i), indent + "   ");
	    }
	    return myRow;
	}
	
	
	
}