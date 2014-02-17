package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class MenuPrincipal extends JFrame {
	public static JLabel jLabelLogo = null;
	JPanel jPanelMenuPrincipal = null;

	private static final long serialVersionUID = 1L;

	private JFrame menuPrincipal;
	
	private DetailPanel detailPanelMenuPrincipal = null;
	private MenuPrincipal menuPrincipalItself = this;
	
	Icon iconMostrarCumpleanios = null;
	Icon iconTransferirAutomovil = null;
	Icon iconSalir = null;
	Icon iconImportar = null;
	Icon iconExportar = null;
	Icon iconAcercaDe = null;
	Icon iconManual = null;
	Icon iconBuscar = null;
	Icon iconAgregar = null;
	Icon iconBuscarAutomovil = null;
	Icon iconBuscarCliente = null;
	Icon iconBuscarMecanico = null;
	Icon iconBuscarProveedor = null;
	Icon iconBuscarTipoServicio = null;
	Icon iconAgregarCliente = null;
	Icon iconAgregarMecanico = null;
	Icon iconAgregarProveedor = null;
	Icon iconAgregarTipoServicio = null;
	
	JMenuItem jMenuItemTransferirAutomovil = null;
	JMenuItem jMenuItemMostrarCumpleanios = null;
	JMenuItem jMenuItemSalir = null;
	JMenuItem jMenuItemImportar = null;
	JMenuItem jMenuItemExportar = null;
	JMenuItem jMenuItemAcercaDe = null;
	JMenuItem jMenuItemManual = null;
	JMenuItem jMenuItemBuscarAutomovil = null;
	JMenuItem jMenuItemBuscarCliente = null;
	JMenuItem jMenuItemBuscarMecanico = null;
	JMenuItem jMenuItemBuscarProveedor = null;
	JMenuItem jMenuItemBuscarTipoServicio = null;
	JMenuItem jMenuItemAgregarCliente = null;
	JMenuItem jMenuItemAgregarMecanico = null;
	JMenuItem jMenuItemAgregarProveedor = null;
	JMenuItem jMenuItemAgregarTipoServicio = null;

	public JCheckBoxMenuItem jCheckBoxMenuItemReferencias = null;
	private JMenuBar jMenuBar = null;
	
	private JMenu jMenuArchivo = null;
	private JMenu jMenuHerramientas = null;
	private JMenu jMenuAcciones = null;
	private JMenu jMenuAyuda = null;
	private JMenu jMenuBuscar = null;
	private JMenu jMenuAgregar = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	public MenuPrincipal() {
		super();
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/Car-Repair-Blue-2-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
	    
		aplicaSkin();

		inicializar();

	}	
	
	private void aplicaSkin(){
		try
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e)
		{
		 	e.printStackTrace();
		}
	}
	
	private void inicializar() {
		menuPrincipal = this;
		
		inicializarIconos();

		this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		this.pack();
		this.setResizable( false );
		this.setSize( new Dimension( 800, 600 ) );
		this.setLocationRelativeTo( null );
		this.setContentPane( getjPanelMenuPrincipal() );
		this.setJMenuBar( getjMenuBar() );
		this.setTitle( "Sistema de gestión automotor" );
		
		shortcuts(); // inicializa los atajos de teclado
		
		this.addWindowListener( new java.awt.event.WindowAdapter() { 
	        public void windowClosing( WindowEvent e ) {
				int option = 0; 
				option = JOptionPane.showConfirmDialog( menuPrincipal, "¿Está seguro que desea cerrar la aplicación?", "Cerrar aplicación", JOptionPane.YES_NO_OPTION );
				if( option == 0 )	
					System.exit( 0 );
	        }
	    });
	}
	
	private JMenuBar getjMenuBar() {
		if( jMenuBar == null ) {
			jMenuBar = new JMenuBar();
			jMenuBar.add( getjMenuArchivo() );
			jMenuBar.add( getjMenuHerramientas() );
			jMenuBar.add( getjMenuAcciones() );
			jMenuBar.add( getjMenuAyuda() );
		}
		
		return jMenuBar;
	}

	private JMenu getjMenuArchivo() {
		if( jMenuArchivo == null ) {
			jMenuArchivo = new JMenu();
			jMenuArchivo.setText( "Archivo" );
			jMenuArchivo.setMnemonic(KeyEvent.VK_A);
			
			jMenuArchivo.add( jMenuItemImportar ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					int choice = JOptionPane.showConfirmDialog(null, "¡IMPORTANTE! Al importar una base de datos se reemplazarán los datos existentes haciendolos irrecuperables.\n ¿Está seguro que desea continuar?" , "Advertencia", JOptionPane.YES_NO_OPTION);
					if( choice == JOptionPane.YES_OPTION ){ // si el usuario hace click en SI
						new Importar(menuPrincipal);
						
					}
				}
			});
			jMenuArchivo.add( jMenuItemExportar ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new Exportar(menuPrincipal);
				}
			});
			jMenuArchivo.addSeparator();
			jMenuArchivo.add( jMenuItemSalir ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					int option = JOptionPane.showConfirmDialog( menuPrincipal, "¿Está seguro que desea cerrar la aplicación?", "Cerrar aplicación", JOptionPane.YES_NO_OPTION );
				
					if( option == 0 )	
						System.exit( 0 );
				}
			});
		}
		
		return jMenuArchivo;
	}
	
	private void inicializarIconos(){
		// Menu Archivo
		iconSalir = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));
		jMenuItemSalir = new JMenuItem("Salir", iconSalir);
		jMenuItemSalir.setToolTipText("Salir");
		jMenuItemSalir.setMnemonic(KeyEvent.VK_S);
		
		iconExportar = new ImageIcon(resourceLoader.load("/images/menu/export-icon.png"));
		jMenuItemExportar = new JMenuItem("Exportar", iconExportar);
		jMenuItemExportar.setToolTipText("Exportar");
		jMenuItemExportar.setMnemonic(KeyEvent.VK_E);
		
		iconImportar = new ImageIcon(resourceLoader.load("/images/menu/import-icon.png"));
		jMenuItemImportar = new JMenuItem("Importar", iconImportar);
		jMenuItemImportar.setToolTipText("Importar");
		jMenuItemImportar.setMnemonic(KeyEvent.VK_I);
		
		// Menu Herramientas
		iconMostrarCumpleanios = new ImageIcon(resourceLoader.load("/images/menu/cake-icon.png"));
		jMenuItemMostrarCumpleanios = new JMenuItem("Mostrar cumpleaños", iconMostrarCumpleanios);
		jMenuItemMostrarCumpleanios.setToolTipText("Mostrar cumpleaños");
		jMenuItemMostrarCumpleanios.setMnemonic(KeyEvent.VK_M);
		
		iconTransferirAutomovil = new ImageIcon(resourceLoader.load("/images/menu/transfer-icon.png"));
		jMenuItemTransferirAutomovil = new JMenuItem("Transferir automóvil", iconTransferirAutomovil);
		jMenuItemTransferirAutomovil.setToolTipText("Transferir automovil");
		
		jCheckBoxMenuItemReferencias = new JCheckBoxMenuItem("Referencias");
		jCheckBoxMenuItemReferencias.setMnemonic(KeyEvent.VK_R);
		// Menu Ayuda
		iconAcercaDe = new ImageIcon(resourceLoader.load("/images/menu/about-icon.png"));
		jMenuItemAcercaDe = new JMenuItem("Acerca de", iconAcercaDe);
		jMenuItemAcercaDe.setToolTipText( "Acerca de" );
		jMenuItemAcercaDe.setMnemonic(KeyEvent.VK_A);
		
		iconManual = new ImageIcon( resourceLoader.load("/images/menu/manual-icon.png" ));
		jMenuItemManual = new JMenuItem( "Manual de usuario" , iconManual );
		jMenuItemManual.setToolTipText( "Manual de usuario" );
		jMenuItemManual.setMnemonic(KeyEvent.VK_M);
		
		// Menu Acciones
		iconBuscar = new ImageIcon( resourceLoader.load("/images/menu/search-icon.png" ));
		jMenuBuscar = new JMenu( "Buscar..." );
		jMenuBuscar.setIcon( iconBuscar );
		jMenuBuscar.setMnemonic(KeyEvent.VK_B);
		
		iconAgregar = new ImageIcon( resourceLoader.load("/images/menu/add-icon.png" ));
		jMenuAgregar = new JMenu( "Agregar..." );
		jMenuAgregar.setIcon( iconAgregar );
		jMenuAgregar.setMnemonic(KeyEvent.VK_A);
		
		iconBuscarAutomovil = new ImageIcon( resourceLoader.load("/images/menu/search-car-icon.png" ));
		jMenuItemBuscarAutomovil = new JMenuItem( "Automóvil" , iconBuscarAutomovil );
		jMenuItemBuscarAutomovil.setToolTipText( "Buscar automóvil" );
		jMenuItemBuscarAutomovil.setMnemonic(KeyEvent.VK_A);
		
		iconBuscarMecanico = new ImageIcon( resourceLoader.load("/images/menu/search-mechanic-icon.png" ));
		jMenuItemBuscarMecanico = new JMenuItem( "Mecánico" , iconBuscarMecanico );
		jMenuItemBuscarMecanico.setToolTipText( "Buscar mecánico" );
		jMenuItemBuscarMecanico.setMnemonic(KeyEvent.VK_M);
		
		iconBuscarProveedor = new ImageIcon( resourceLoader.load("/images/menu/search-truck-icon.png" ));
		jMenuItemBuscarProveedor = new JMenuItem( "Proveedor" , iconBuscarProveedor );
		jMenuItemBuscarProveedor.setToolTipText( "Buscar proveedor" );
		jMenuItemBuscarProveedor.setMnemonic(KeyEvent.VK_P);
		
		iconBuscarCliente = new ImageIcon( resourceLoader.load("/images/menu/search-client-icon.png" ));
		jMenuItemBuscarCliente = new JMenuItem( "Cliente" , iconBuscarCliente );
		jMenuItemBuscarCliente.setToolTipText( "Buscar cliente" );
		jMenuItemBuscarCliente.setMnemonic(KeyEvent.VK_C);
		
		iconBuscarTipoServicio = new ImageIcon( resourceLoader.load("/images/menu/search-tools-icon.png" ));
		jMenuItemBuscarTipoServicio = new JMenuItem( "Tipo de servicio" , iconBuscarTipoServicio );
		jMenuItemBuscarTipoServicio.setToolTipText( "Buscar tipo de servicio" );
		jMenuItemBuscarTipoServicio.setMnemonic(KeyEvent.VK_T);
		
		iconAgregarCliente = new ImageIcon( resourceLoader.load("/images/menu/add-client-icon.png" ));
		jMenuItemAgregarCliente = new JMenuItem( "Cliente" , iconAgregarCliente );
		jMenuItemAgregarCliente.setToolTipText( "Agregar cliente" );
		
		iconAgregarMecanico = new ImageIcon( resourceLoader.load("/images/menu/add-mechanic-icon.png" ));
		jMenuItemAgregarMecanico = new JMenuItem( "Mecánico" , iconAgregarMecanico );
		jMenuItemAgregarMecanico.setToolTipText( "Agregar mecánico" );
		jMenuItemAgregarMecanico.setMnemonic(KeyEvent.VK_M);
		
		iconAgregarProveedor = new ImageIcon( resourceLoader.load("/images/menu/add-truck-icon.png" ));
		jMenuItemAgregarProveedor = new JMenuItem( "Proveedor" , iconAgregarProveedor );
		jMenuItemAgregarProveedor.setToolTipText( "Agregar proveedor" );
		jMenuItemAgregarProveedor.setMnemonic(KeyEvent.VK_P);

		iconAgregarTipoServicio = new ImageIcon( resourceLoader.load("/images/menu/add-tools-icon.png" ));
		jMenuItemAgregarTipoServicio = new JMenuItem( "Tipo de servicio" , iconAgregarTipoServicio );
		jMenuItemAgregarTipoServicio.setToolTipText( "Agregar tipo de servicio" );
		jMenuItemAgregarTipoServicio.setMnemonic(KeyEvent.VK_T);
	}
	
	private void shortcuts(){
		// esta funcion hace que la ventana MenuPrincipal se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
					int option = JOptionPane.showConfirmDialog( menuPrincipal, "¿Está seguro que desea cerrar la aplicación?", "Cerrar aplicación", JOptionPane.YES_NO_OPTION );
					if( option == 0 )	
						System.exit( 0 );
		      }
		    };
		jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		
		// F1 --> ManualDeUsuario
				keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false);
				actionListener = new ActionListener() {
				      public void actionPerformed(ActionEvent actionEvent) {
				    	  new ManualDeUsuario( menuPrincipal, "Manual de usuario", " Selección de opciones " , " Descripción ");
				      }
				    };
				jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);
				
		
		// F2 --> BuscarCliente
		keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false);
		actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  new BuscarCliente( menuPrincipal, "Buscar cliente", " Búsqueda de cliente " );
		      }
		    };
		jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);
		
		// F3 --> BuscarAutomovil
		keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false);
		actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  new BuscarAutomovil( menuPrincipal, "Buscar automóvil", " Búsqueda de automóvil " );
		      }
		    };
		    
		jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);
		
		// F4 --> BuscarProveedor
		keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false);
		actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  new BuscarProveedor( menuPrincipal, "Buscar proveedor", " Búsqueda de proveedor " );
		      }
		    };
		
		    jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);
		
		    // F5 --> BuscarMecanico
		keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false);
		actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  new BuscarMecanico( menuPrincipal, "Buscar mecánico", " Búsqueda de mecánico " );
		      }
		    };
		jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);
		
		// F6 --> BuscarTipoDeServicio
				keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false);
				actionListener = new ActionListener() {
				      public void actionPerformed(ActionEvent actionEvent) {
				    	  new BuscarTipoServicio( menuPrincipal, "Buscar tipo de servicio", " Búsqueda de tipo de servicio " );
				      }
				    };
				jMenuBar.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);
				
	}
	
	private JMenu getjMenuHerramientas() {
		if( jMenuHerramientas == null ) {
			jMenuHerramientas = new JMenu();
			jMenuHerramientas.setText( "Herramientas" );
			jMenuHerramientas.setMnemonic(KeyEvent.VK_H);
			jMenuHerramientas.add(jMenuItemMostrarCumpleanios).addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					new MostrarCumpleanios(menuPrincipal, "Mostrar cumpleaños", " Cumpleaños ");
					
				}
			});
			
			jMenuHerramientas.add(jMenuItemTransferirAutomovil).addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					
				}
			});
			jMenuHerramientas.addSeparator();
			jMenuHerramientas.add(jCheckBoxMenuItemReferencias).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new Referencias(menuPrincipalItself);
					jCheckBoxMenuItemReferencias.setSelected(true);
				}
			});
			
		}
		
		return jMenuHerramientas;
	}
	
	private JMenu getjMenuAcciones() {
		if( jMenuAcciones == null ) {
			jMenuAcciones = new JMenu();
			jMenuAcciones.setText( "Acciones" );
			jMenuAcciones.setMnemonic(KeyEvent.VK_C);
			
			jMenuAcciones.add (jMenuAgregar);
			
			jMenuAgregar.add( jMenuItemAgregarCliente ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					 new AgregarCliente( menuPrincipal, "Agregar cliente", " Nuevo cliente " );
				}
			});
			
		
			jMenuAgregar.add( jMenuItemAgregarProveedor ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new AgregarProveedor( menuPrincipal, "Agregar proveedor", " Nuevo proveedor " );
				}
			});
			
			
			jMenuAgregar.add( jMenuItemAgregarMecanico ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new AgregarMecanico( menuPrincipal, "Agregar mecánico", " Nuevo mecánico " );
				}
			});
			
			jMenuAgregar.add( jMenuItemAgregarTipoServicio ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new AgregarTipoDeServicio( menuPrincipal, "Agregar tipo de servicio", " Nuevo tipo de servicio " );
				}
			});
			
			jMenuAcciones.add (jMenuBuscar);
			
			jMenuBuscar.add( jMenuItemBuscarCliente ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new BuscarCliente( menuPrincipal, "Buscar cliente", " Búsqueda de cliente " );
				}
			});

			jMenuBuscar.add( jMenuItemBuscarAutomovil ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new BuscarAutomovil( menuPrincipal, "Buscar automóvil", " Búsqueda de automóvil " );
				}
			});
	
			jMenuBuscar.add( jMenuItemBuscarProveedor ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new BuscarProveedor( menuPrincipal, "Buscar proveedor", " Búsqueda de proveedor " );
				}
			});
			
			jMenuBuscar.add( jMenuItemBuscarMecanico ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new BuscarMecanico( menuPrincipal, "Buscar mecánico", " Búsqueda de mecánico " );
				}
			});
			
			jMenuBuscar.add( jMenuItemBuscarTipoServicio ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new BuscarTipoServicio( menuPrincipal, "Buscar tipo de servicio", " Búsqueda de tipo de servicio " );
				}
			});
		}
		
		return jMenuAcciones;
	}
	
	private JMenu getjMenuAyuda() {
		if( jMenuAyuda == null ) {
			jMenuAyuda = new JMenu();
			jMenuAyuda.setText( "Ayuda" );
			jMenuAyuda.setMnemonic(KeyEvent.VK_Y);
			jMenuAyuda.add( jMenuItemAcercaDe ).addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					new AcercaDe( menuPrincipal, "Acerca de...", " Información " );
				}
			});
			
			jMenuAyuda.add( jMenuItemManual ).addActionListener( new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			    	  new ManualDeUsuario( menuPrincipal, "Manual de usuario", " Selección de opciones " , " Descripción ");
			      }
			});
		}
		
		return jMenuAyuda;
	}
	
	private JPanel getjPanelMenuPrincipal() {
		if( jPanelMenuPrincipal == null ) {
			jPanelMenuPrincipal = new JPanel();
			jPanelMenuPrincipal.setLayout( new BorderLayout() );
			jPanelMenuPrincipal.setBackground( Color.GRAY );
			
			detailPanelMenuPrincipal = new DetailPanel();
			detailPanelMenuPrincipal.setLayout( new BorderLayout() );
			detailPanelMenuPrincipal.setBorder( BorderFactory.createEmptyBorder( 20, 20, 20, 20 ) );
			
			jLabelLogo = new JLabel();
			jLabelLogo.setIcon( new ImageIcon( resourceLoader.load("/images/Logo.jpg")  ) );
			detailPanelMenuPrincipal.add( jLabelLogo, BorderLayout.CENTER );

			jPanelMenuPrincipal.add( detailPanelMenuPrincipal, BorderLayout.CENTER );
		}
		
		return jPanelMenuPrincipal;
	}
}