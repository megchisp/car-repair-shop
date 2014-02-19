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
import javax.swing.JButton;
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
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class MenuPrincipal extends JFrame {
	public static JLabel jLabelLogo = null;
	JPanel jPanelMenuPrincipal = null;
	private Login login;
	private static final long serialVersionUID = 1L;

	private JFrame menuPrincipal;
	
	private DetailPanel detailPanelMenuPrincipal = null;
	private MenuPrincipal menuPrincipalItself = this;
	
	Icon iconTransferirAutomovil = null;
	Icon iconSalir = null;
	Icon iconImportar = null;
	Icon iconExportar = null;
	Icon iconAcercaDe = null;
	Icon iconManual = null;
	
	JMenuItem jMenuItemTransferirAutomovil = null;
	JMenuItem jMenuItemSalir = null;
	JMenuItem jMenuItemImportar = null;
	JMenuItem jMenuItemExportar = null;
	JMenuItem jMenuItemAcercaDe = null;
	JMenuItem jMenuItemManual = null;


	public JCheckBoxMenuItem jCheckBoxMenuItemReferencias = null;
	private JMenuBar jMenuBar = null;
	
	private JMenu jMenuArchivo = null;
	private JMenu jMenuHerramientas = null;
	private JMenu jMenuAyuda = null;

	
	JButton jButtonAgregarCliente = null;
	JButton jButtonAgregarProveedor = null;
	JButton jButtonAgregarMecanico = null;
	JButton jButtonAgregarTipoDeServicio = null;
	JButton jButtonBuscarCliente = null;
	JButton jButtonBuscarAutomovil = null;
	JButton jButtonBuscarProveedor = null;
	JButton jButtonBuscarMecanico = null;
	JButton jButtonBuscarTipoDeServicio = null;
	JButton jButtonMostrarCumpleanios = null;
	JButton jButtonExit = null;
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	JToolBar jToolbar = new JToolBar();
	
	public MenuPrincipal() {
		super();
		
		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(resourceLoader.load("/images/Car-Repair-Blue-2-icon.png"));
	    Image Image = ImageIcon.getImage();
	    this.setIconImage(Image);
	    
		aplicaSkin();
		inicializar();
		
		// login del sistema
		login = new Login(this, true);
        login.setVisible(true);
	}	
	
	private void aplicaSkin(){
		try
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		this.add(jToolbar, BorderLayout.NORTH);
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
		iconSalir = new ImageIcon(resourceLoader.load("/images/menu/exit-icon.png"));
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
	
		
		// inicializo toolbar 
		jButtonAgregarCliente = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/add-client-icon-48x48.png" )));
		jButtonAgregarCliente.setToolTipText("Agregar cliente");
		jButtonAgregarProveedor = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/add-truck-brown-icon-48x48.png" )));
		jButtonAgregarProveedor.setToolTipText("Agregar proveedor");
		jButtonAgregarMecanico = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/add-mechanic-icon-48x48.png" )));
		jButtonAgregarMecanico.setToolTipText("Agregar mecánico");
		jButtonAgregarTipoDeServicio = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/add-tools-icon-48x48.png" )));
		jButtonAgregarTipoDeServicio.setToolTipText("Agregar tipo de servicio");
		jButtonBuscarCliente = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/search-client-icon-48x48.png" )));
		jButtonBuscarCliente.setToolTipText("Buscar cliente (F2)");
		jButtonBuscarAutomovil = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/search-car-icon-48x48.png" )));
		jButtonBuscarAutomovil.setToolTipText("Buscar automóvil (F3)");
		jButtonBuscarProveedor = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/search-truck-brown-icon-48x48.png" )));
		jButtonBuscarProveedor.setToolTipText("Buscar proveedor (F4)");
		jButtonBuscarMecanico = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/search-mechanic-icon-48x48.png" )));
		jButtonBuscarMecanico.setToolTipText("Buscar mecánico (F5)");
		jButtonBuscarTipoDeServicio = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/search-tools-icon-48x48.png" )));
		jButtonBuscarTipoDeServicio.setToolTipText("Buscar tipo de servicio (F6)");
		jButtonMostrarCumpleanios = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/cake-icon-48x48.png" )));
		jButtonMostrarCumpleanios.setToolTipText("Mostrar cumpleaños");
		jButtonExit = new JButton(new ImageIcon( resourceLoader.load("/images/toolbar/logout-icon-48x48.png" )));
		jButtonExit.setToolTipText("Salir");
		
		jToolbar = new JToolBar();
		jToolbar.setEnabled(false);
		jToolbar.add(jButtonAgregarCliente);
		jToolbar.add(jButtonAgregarProveedor);
		jToolbar.add(jButtonAgregarMecanico);
		jToolbar.add(jButtonAgregarTipoDeServicio);
		jToolbar.addSeparator();
		jToolbar.add(jButtonBuscarCliente);
		jToolbar.add(jButtonBuscarAutomovil);
		jToolbar.add(jButtonBuscarProveedor);
		jToolbar.add(jButtonBuscarMecanico);
		jToolbar.add(jButtonBuscarTipoDeServicio);
		jToolbar.addSeparator();
		jToolbar.add(jButtonMostrarCumpleanios);
		jToolbar.addSeparator();
		jToolbar.add(jButtonExit);
		jToolbar.setAlignmentX(0);
		
		// action listeners
		jButtonMostrarCumpleanios.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new MostrarCumpleanios(menuPrincipal, "Mostrar cumpleaños", " Cumpleaños ");
				
			}
		});
		
		jButtonAgregarCliente.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				 new AgregarCliente( menuPrincipal, "Agregar cliente", " Nuevo cliente " );
			}
		});
		
		jButtonAgregarProveedor.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new AgregarProveedor( menuPrincipal, "Agregar proveedor", " Nuevo proveedor " );
			}
		});
		
		jButtonAgregarMecanico.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new AgregarMecanico( menuPrincipal, "Agregar mecánico", " Nuevo mecánico " );
			}
		});
		
		jButtonAgregarTipoDeServicio.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new AgregarTipoDeServicio( menuPrincipal, "Agregar tipo de servicio", " Nuevo tipo de servicio " );
			}
		});
		
		jButtonBuscarCliente.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new BuscarCliente( menuPrincipal, "Buscar cliente", " Búsqueda de cliente " );
			}
		});
		
		jButtonBuscarAutomovil.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new BuscarAutomovil( menuPrincipal, "Buscar automóvil", " Búsqueda de automóvil " );
			}
		});
		
		jButtonBuscarProveedor.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new BuscarProveedor( menuPrincipal, "Buscar proveedor", " Búsqueda de proveedor " );
			}
		});
		
		jButtonBuscarMecanico.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new BuscarMecanico( menuPrincipal, "Buscar mecánico", " Búsqueda de mecánico " );
			}
		});
		
		jButtonBuscarTipoDeServicio.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new BuscarTipoServicio( menuPrincipal, "Buscar tipo de servicio", " Búsqueda de tipo de servicio " );
			}
		});
		
		jButtonExit.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				int option = JOptionPane.showConfirmDialog( menuPrincipal, "¿Está seguro que desea cerrar la aplicación?", "Cerrar aplicación", JOptionPane.YES_NO_OPTION );
			
				if( option == 0 )	
					System.exit( 0 );
			}
		});
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