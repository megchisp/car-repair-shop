package proyectoanalista.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class ManualDeUsuario extends JDialog {
	
	private static final long serialVersionUID = 1L;

	JTextPane jTextAreaContenido = null;
	JPanel jPanelIntermedio1 = null;
	JPanel jPanelIntermedio2 = null;
	JPanel jPanelContenido = null;
	JPanelConFondo jPanelManualDeUsuario = null;
	
	JButton jButtonCerrar = null;
	
	JTree jTreeOpciones = null;
	JScrollPane jScrollPaneOpciones = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	
	public ManualDeUsuario( JFrame padre, String titulo, String titleBorder1, String titleBorder2 ) {
		super( padre, titulo, true );
		this.setPreferredSize( new Dimension( 550, 420 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/jframe-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
	    
		inicializar(titleBorder1,titleBorder2);
		cerrarEsc();
		
		this.getContentPane().add( jPanelManualDeUsuario, BorderLayout.NORTH );
		this.setResizable( false );
		this.pack();
		this.setLocationRelativeTo( null );
		this.setVisible( true );	
	}
	
	
	private void inicializar(String titleBorder1, String titleBorder2){
		jTreeOpciones = new JTree();
		jTreeOpciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		Image imagenPanel = new ImageIcon(resourceLoader.load("/images/Tree.gif")).getImage();
		jPanelManualDeUsuario = new JPanelConFondo (imagenPanel, -5, 225, 150, 150);
		jScrollPaneOpciones = new JScrollPane()       
		{
			private static final long serialVersionUID = 1L;
				
			Image img = new ImageIcon(resourceLoader.load("/images/TreeSuave.gif")).getImage();
            {setOpaque(false);}

            public void paintComponent(Graphics g)
            {
                g.drawImage(img, -17, 210, 150, 150, this);
                super.paintComponent(g);
            }
        };

  		
		DefaultMutableTreeNode sistema = new DefaultMutableTreeNode("Sistema");
		DefaultTreeModel modelo = new DefaultTreeModel(sistema);		

		DefaultMutableTreeNode archivo = new DefaultMutableTreeNode("Archivo");
		DefaultMutableTreeNode importar_BD = new DefaultMutableTreeNode("Importar BD");
		DefaultMutableTreeNode exportar_BD = new DefaultMutableTreeNode("Exportar BD");
		DefaultMutableTreeNode salir = new DefaultMutableTreeNode("Salir");
	
		modelo.insertNodeInto(archivo, sistema, 0);
	
		modelo.insertNodeInto(importar_BD, archivo, 0);
		modelo.insertNodeInto(exportar_BD, archivo, 1);
		modelo.insertNodeInto(salir, archivo, 2);
		
		DefaultMutableTreeNode herramientas = new DefaultMutableTreeNode("Herramientas");
		DefaultMutableTreeNode mostrar_cumpleanios = new DefaultMutableTreeNode("Mostrar cumpleaños");

		
		modelo.insertNodeInto(herramientas, sistema, 1);
		
		modelo.insertNodeInto(mostrar_cumpleanios, herramientas, 0);
		
		DefaultMutableTreeNode acciones = new DefaultMutableTreeNode("Acciones");
		DefaultMutableTreeNode agregar_cliente = new DefaultMutableTreeNode("Agregar cliente");
		DefaultMutableTreeNode agregar_proveedor = new DefaultMutableTreeNode("Agregar proveedor");
		DefaultMutableTreeNode agregar_mecanico = new DefaultMutableTreeNode("Agregar mecánico");
		DefaultMutableTreeNode agregar_tipo_de_servicio = new DefaultMutableTreeNode("Agregar tipo de servicio");
		DefaultMutableTreeNode buscar_cliente = new DefaultMutableTreeNode("Buscar cliente");
		DefaultMutableTreeNode buscar_automovil = new DefaultMutableTreeNode("Buscar automóvil");
		DefaultMutableTreeNode buscar_proveedor = new DefaultMutableTreeNode("Buscar proveedor");
		DefaultMutableTreeNode buscar_tipo_de_servicio = new DefaultMutableTreeNode("Buscar tipo de servicio");
		
		modelo.insertNodeInto(acciones, sistema, 2);
		
		modelo.insertNodeInto(agregar_cliente, acciones, 0);
		modelo.insertNodeInto(agregar_proveedor, acciones, 1);
		modelo.insertNodeInto(agregar_mecanico, acciones, 2);
		modelo.insertNodeInto(agregar_tipo_de_servicio, acciones, 3);
		modelo.insertNodeInto(buscar_cliente, acciones, 4);
		modelo.insertNodeInto(buscar_automovil, acciones, 5);
		modelo.insertNodeInto(buscar_proveedor, acciones, 6);
		modelo.insertNodeInto(buscar_tipo_de_servicio, acciones, 7);
		
		jTreeOpciones.setModel(modelo);
		
		Icon closedIcon = new ImageIcon(resourceLoader.load("/images/FolderClosed.png"));
		Icon OpenIcon = new ImageIcon(resourceLoader.load("/images/FolderOpened.png"));
		Icon ArrowIcon = new ImageIcon(resourceLoader.load("/images/bullet-black.png"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setClosedIcon(closedIcon);
		renderer.setOpenIcon(OpenIcon);
		renderer.setLeafIcon(ArrowIcon);
		jTreeOpciones.setCellRenderer(renderer);
		
		jPanelIntermedio2 = new JPanel(new BorderLayout());
		jPanelIntermedio2.add(jTreeOpciones);
		jPanelIntermedio2.setBackground(Color.white);
		
		JPanel jPanelContenido = new JPanel();
		jPanelIntermedio1 = new JPanel();
	

		jTextAreaContenido = new JTextPane()
	      {
				private static final long serialVersionUID = 1L;
					
				Image img = new ImageIcon(resourceLoader.load("/images/Info.gif")).getImage();
	            {setOpaque(false);}

	            public void paintComponent(Graphics g)
	            {
	                g.drawImage(img, 100, 90, 155, 170, this);
	                super.paintComponent(g);
	            }
	        };
	    jTextAreaContenido.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    jTextAreaContenido.setText("   Con este sistema el usuario estará habilitado para contar con las herramientas básicas de gestión de un taller mecánico.\n    Además podrá registar todos los datos de los clientes, automóviles y reparaciones, visualizar los cumpleaños de los clientes, realizar copias de seguridad de la base de datos y restaurar la misma en caso de ser necesario.\n    Estas y muchas más ventajas, amplían y facilitan el funcionamiento de la aplicación.");
		jTextAreaContenido.setPreferredSize(new Dimension(240, 269));
		jTextAreaContenido.setEditable(false);
		jPanelIntermedio1.add(jTextAreaContenido);
		jPanelIntermedio1.setBackground(Color.white);
		jPanelIntermedio1.setPreferredSize(new Dimension(240, 269));
		jPanelContenido.add(jPanelIntermedio1);

		TitledBorder rotulo1;
		rotulo1 = BorderFactory.createTitledBorder(titleBorder1);
		rotulo1.setBorder(BorderFactory.createLineBorder(new Color(100,150,100)));
		rotulo1.setTitleColor(new Color(0,0,128));
		jScrollPaneOpciones.setBorder(rotulo1);
		jScrollPaneOpciones.setPreferredSize(new Dimension(250, 300));
		
		TitledBorder rotulo2;
		rotulo2 = BorderFactory.createTitledBorder(titleBorder2);
		rotulo2.setBorder(BorderFactory.createLineBorder(new Color(100,150,100)));
		rotulo2.setTitleColor(new Color(0,0,128));
		jPanelContenido.setBorder(rotulo2);
		jPanelContenido.setPreferredSize(new Dimension(250, 300));

		
		jTreeOpciones.addTreeSelectionListener(new TreeSelectionListener()
		{
		    public void valueChanged(TreeSelectionEvent e)
		    {
		    	/* Sistema */
		    	if(e.getPath().toString().endsWith("Sistema]"))
		    	{
		    		jTextAreaContenido.setText("   Con este sistema el usuario estará habilitado para contar con las herramientas básicas de gestión de un taller mecánico.\n    Además podrá registar todos los datos de los clientes, automóviles y reparaciones, visualizar los cumpleaños de los clientes, realizar copias de seguridad de la base de datos y restaurar la misma en caso de ser necesario.\n    Estas y muchas más ventajas, amplían y facilitan el funcionamiento de la aplicación.");
		    	}
		    	
		    	/* Archivo */
		    	if(e.getPath().toString().endsWith("Importar BD]"))
		    	{
		    		jTextAreaContenido.setText("    Permite importar una base de datos existente al nuevo sistema.\n");
		    	}
		    	if(e.getPath().toString().endsWith("Exportar BD]"))
		    	{
		    		jTextAreaContenido.setText("    Permite exportar la base de datos actual en un archivo.");
		    	}
		    	if(e.getPath().toString().endsWith("Salir]"))
		    	{
		    		jTextAreaContenido.setText("    Cierra  la  aplicación.");
		    	}
		    	
		    	/* Herramientas */
		    	if(e.getPath().toString().endsWith("Mostrar cumpleaños]"))
		    	{
		    		jTextAreaContenido.setText("    Permite  obtener  los cumpleaños de los clientes por día específico, por semana actual y por mes actual.");
		    	}
		    	
		    	/* Acciones */
		    	if(e.getPath().toString().endsWith("Agregar cliente]"))
		    	{
		    		jTextAreaContenido.setText("    Permite agregar un nuevo cliente al sistema.");
		    	}
		    	if(e.getPath().toString().endsWith("Agregar proveedor]"))
		    	{
		    		jTextAreaContenido.setText("    Permite agregar un nuevo proveedor al sistema.");
		    	}
		    	if(e.getPath().toString().endsWith("Agregar mecánico]"))
		    	{
		    		jTextAreaContenido.setText("    Permite agregar un nuevo mecánico al sistema.");
		    	}
		    	if(e.getPath().toString().endsWith("Agregar tipo de servicio]"))
		    	{
		    		jTextAreaContenido.setText("    Permite agregar un nuevo tipo de servicio al sistema.\n    Los tipos de servicio sirven para definir diferentes tipos de servicios que luego se podrán asociar a las reparaciones de los automóviles.\n    Los tiempos se computan en minutos.");
		    	}
		    	
		    	if(e.getPath().toString().endsWith("Buscar cliente]"))
		    	{
		    		jTextAreaContenido.setText("    Permite realizar una búsqueda de un cliente.\n    La búsqueda puede realizarse por Nombre y Apellido o por número de CUIT/CUIL.");
		    	}
		    	if(e.getPath().toString().endsWith("Buscar automóvil]"))
		    	{
		    		jTextAreaContenido.setText("    Permite realizar una búsqueda de un automóvil.\n    La búsqueda puede realizarse por Nombre y Apellido del propietario o por Dominio (chapa patente).");
		    	}
		    	if(e.getPath().toString().endsWith("Buscar proveedor]"))
		    	{
		    		jTextAreaContenido.setText("    Permite realizar una búsqueda de un proveedor.\n    Por defecto se muestran todos los proveedores registrados en el sistema.");
		    	}
		    	if(e.getPath().toString().endsWith("Buscar mecánico]"))
		    	{
		    		jTextAreaContenido.setText("    Permite realizar una búsqueda de un mecánico.\n    Por defecto se muestran todos los mecánicos registrados en el sistema.");
		    	}
		    	if(e.getPath().toString().endsWith("Buscar tipo de servicio]"))
		    	{
		    		jTextAreaContenido.setText("    Permite realizar una búsqueda de un tipo de servicio.\n    Por defecto se muestran todos los tipos de servicios registrados en el sistema.");
		    	}
		    	
		    	if(e.getPath().toString().endsWith("Archivo]") || e.getPath().toString().endsWith("Ayuda]") || e.getPath().toString().endsWith("Consulta]") || e.getPath().toString().endsWith("Final)]"))
		    	{
		    		jTextAreaContenido.setText("");
		    	}
		    }
		});
		
		jTreeOpciones.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		jScrollPaneOpciones.setViewportView(jPanelIntermedio2);
		
		jPanelManualDeUsuario.setLayout(new GridBagLayout());
		jPanelManualDeUsuario.setPreferredSize(new Dimension(530, 390));
		
		// creo el botón cerrar con un ícono
		ImageIcon imageIconLogOut = new ImageIcon(resourceLoader.load("/images/menu/log-out-icon.png"));		
		jButtonCerrar = new JButton( " Cerrar", imageIconLogOut );
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		
		JLabel jLabelEspacio2 = new JLabel(" ");
		
//		System.out.println("BANDERA2!");
		
		c.gridy = 1;
		c.gridx = 0;
		jPanelManualDeUsuario.add(jScrollPaneOpciones, c);
		c.gridy = 1;
		c.gridx = 1;
		jPanelManualDeUsuario.add(jPanelContenido, c);
		c.gridy = 2;
		c.gridx = 0;
		jPanelManualDeUsuario.add(jLabelEspacio2, c);
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.CENTER;
		jPanelManualDeUsuario.add(jButtonCerrar, c);
				
		//Oyente del boton "Aceptar"
		jButtonCerrar.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{				
				ManualDeUsuario.this.dispose();
			}
		});
	}
	private void cerrarEsc(){
		// esta funcion hace que la ventana DetallarAutomovil se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  ManualDeUsuario.this.dispose();
		      }
		    };
		jPanelManualDeUsuario.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
}
