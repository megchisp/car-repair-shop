package presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Referencias extends JDialog {

	private static final long serialVersionUID = 1L;
	private static boolean instanciado = false; // indica si ya existe el jframe referencias
	
	JPanel jPanelReferencias;
	MenuPrincipal padre2;
	
	JLabel jLabelF1 = null;
	JLabel jLabelF2 = null;
	JLabel jLabelF3 = null;
	JLabel jLabelF4 = null;
	JLabel jLabelF5 = null;
	JLabel jLabelF6 = null;
	
	JLabel jLabelManualDeUsuario = null;
	JLabel jLabelBuscarCliente = null;
	JLabel jLabelBuscarAutomovil = null;
	JLabel jLabelBuscarProveedor = null;
	JLabel jLabelBuscarMecanico = null;
	JLabel jLabelBuscarTipoServicio = null;
	
	Icon iconBullet = null;
	Icon iconManual = null;
	Icon iconBuscarCliente = null;
	Icon iconBuscarAutomovil = null;
	Icon iconBuscarProveedor = null;
	Icon iconBuscarMecanico = null;
	Icon iconBuscarTipoServicio = null;
	ResourceLoader resourceLoader = new ResourceLoader();

	public Referencias(MenuPrincipal padre){
		super( padre);
		padre2 = padre;
		
		// manejo el cierre del frame
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	instanciado = false;
		    	padre2.jCheckBoxMenuItemReferencias.setSelected(false);
		    	Referencias.this.dispose();
		    }
		});


		if(!instanciado){
			instanciado = true;
			this.setTitle( "Referencias" );
			this.getContentPane().setLayout( new BorderLayout() );
			this.setPreferredSize(new Dimension(250, 150));
			this.setType(Type.UTILITY);
			
			inicializarJPanelReferencias();
			cerrarEsc();
			
	        this.getContentPane().add(jPanelReferencias);
	        this.setLocationRelativeTo(padre);
	        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        this.pack();
			this.setResizable( false );
			this.setLocationRelativeTo( null );
			this.setVisible( true );
		}
		else {
			
		}
	}

	public void unCheckMenuItem(){
		
	}
	
	public void inicializarJPanelReferencias(){
	    jPanelReferencias = new JPanel();  
	    jPanelReferencias.setPreferredSize(new Dimension (250,150));
	    jPanelReferencias.setLayout(new GridBagLayout());
	    GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.WEST;

		inicializarLabels();

		a.gridy = 0;
		a.gridx = 0;
	    jPanelReferencias.add(jLabelF1,a);
		a.gridy = 0;
		a.gridx = 1;
		jPanelReferencias.add(jLabelManualDeUsuario,a);
		
		a.gridy = 1;
		a.gridx = 0;
	    jPanelReferencias.add(jLabelF2,a);
		a.gridy = 1;
		a.gridx = 1;
		jPanelReferencias.add(jLabelBuscarCliente,a);
		
		a.gridy = 2;
		a.gridx = 0;
	    jPanelReferencias.add(jLabelF3,a);
		a.gridy = 2;
		a.gridx = 1;
		jPanelReferencias.add(jLabelBuscarAutomovil,a);
		
		a.gridy = 3;
		a.gridx = 0;
	    jPanelReferencias.add(jLabelF4,a);
		a.gridy = 3;
		a.gridx = 1;
		jPanelReferencias.add(jLabelBuscarProveedor,a);
		
		a.gridy = 4;
		a.gridx = 0;
	    jPanelReferencias.add(jLabelF5,a);
		a.gridy = 4;
		a.gridx = 1;
		jPanelReferencias.add(jLabelBuscarMecanico,a);
		
		a.gridy = 5;
		a.gridx = 0;
	    jPanelReferencias.add(jLabelF6,a);
		a.gridy = 5;
		a.gridx = 1;
		jPanelReferencias.add(jLabelBuscarTipoServicio,a);
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana Referencias se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  Referencias.this.dispose();
		      }
		    };
		    jPanelReferencias.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void inicializarLabels(){
	    iconBullet = new ImageIcon( resourceLoader.load("/images/menu/bullet-icon.png" ));
	    
		int cc = 2192; // flecha hacia la izquierda en unicode
		char ccc = (char) Integer.parseInt(String.valueOf(cc), 16);
		
		jLabelF1 = new JLabel();
		jLabelManualDeUsuario = new JLabel();
		jLabelF1.setFont(new Font("Arial", Font.ITALIC, 14));
		jLabelManualDeUsuario.setFont(new Font("Arial", Font.ITALIC, 14));
		iconManual = new ImageIcon( resourceLoader.load("/images/menu/manual-icon.png" ));
		jLabelF1.setIcon(iconBullet);
		jLabelF1.setText("F1 " + String.valueOf(ccc) + " ");
		jLabelManualDeUsuario.setText(" Manual de usuario");
		jLabelManualDeUsuario.setIcon(iconManual);
		
		jLabelF2 = new JLabel();
		jLabelBuscarCliente = new JLabel();
		jLabelF2.setFont(new Font("Arial", Font.ITALIC, 14));
		jLabelBuscarCliente.setFont(new Font("Arial", Font.ITALIC, 14));
		iconBuscarCliente = new ImageIcon( resourceLoader.load("/images/menu/search-client-icon.png" ));
		jLabelF2.setIcon(iconBullet);
		jLabelF2.setText("F2 " + String.valueOf(ccc) + " ");
		jLabelBuscarCliente.setText(" Buscar cliente");
		jLabelBuscarCliente.setIcon(iconBuscarCliente);
		
		jLabelF3 = new JLabel();
		jLabelBuscarAutomovil = new JLabel();
		jLabelF3.setFont(new Font("Arial", Font.ITALIC, 14));
		jLabelBuscarAutomovil.setFont(new Font("Arial", Font.ITALIC, 14));
		iconBuscarAutomovil = new ImageIcon( resourceLoader.load("/images/menu/search-car-icon.png" ));
		jLabelF3.setIcon(iconBullet);
		jLabelF3.setText("F3 " + String.valueOf(ccc) + " ");
		jLabelBuscarAutomovil.setText(" Buscar automóvil");
		jLabelBuscarAutomovil.setIcon(iconBuscarAutomovil);
		
		jLabelF4 = new JLabel();
		jLabelBuscarProveedor = new JLabel();
		jLabelF4.setFont(new Font("Arial", Font.ITALIC, 14));
		jLabelBuscarProveedor.setFont(new Font("Arial", Font.ITALIC, 14));
		iconBuscarProveedor = new ImageIcon( resourceLoader.load("/images/menu/search-truck-icon.png" ));
		jLabelF4.setIcon(iconBullet);
		jLabelF4.setText("F4 " + String.valueOf(ccc) + " ");
		jLabelBuscarProveedor.setText(" Buscar proveedor");
		jLabelBuscarProveedor.setIcon(iconBuscarProveedor);
		
		jLabelF5 = new JLabel();
		jLabelBuscarMecanico = new JLabel();
		jLabelF5.setFont(new Font("Arial", Font.ITALIC, 14));
		jLabelBuscarMecanico.setFont(new Font("Arial", Font.ITALIC, 14));
		iconBuscarMecanico = new ImageIcon( resourceLoader.load("/images/menu/search-mechanic-icon.png" ));
		jLabelF5.setIcon(iconBullet);
		jLabelF5.setText("F5 " + String.valueOf(ccc) + " ");
		jLabelBuscarMecanico.setText(" Buscar mecánico");
		jLabelBuscarMecanico.setIcon(iconBuscarMecanico);
		
		jLabelF6 = new JLabel();
		jLabelBuscarTipoServicio = new JLabel();
		jLabelF6.setFont(new Font("Arial", Font.ITALIC, 14));
		jLabelBuscarTipoServicio.setFont(new Font("Arial", Font.ITALIC, 14));
		iconBuscarTipoServicio = new ImageIcon( resourceLoader.load("/images/menu/search-tools-icon.png" ));
		jLabelF6.setIcon(iconBullet);
		jLabelF6.setText("F6 " + String.valueOf(ccc) + " ");
		jLabelBuscarTipoServicio.setText(" Buscar tipo de servicio");
		jLabelBuscarTipoServicio.setIcon(iconBuscarTipoServicio);
	}
	
	

}


