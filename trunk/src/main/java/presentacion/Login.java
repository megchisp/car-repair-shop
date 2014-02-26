package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import persistencia.BD.ConexionDB;
import persistencia.BD.CreateDB;
import persistencia.BD.PostgresConexionDB;

public class Login extends JDialog{

	private static final long serialVersionUID = 1L;
	private JLabel jLabelUsername = null;
    private JLabel jLabelPassword = null;

    private JTextField jTextFieldUsername = null;
    private JPasswordField jTextFieldPassword = null;

    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;

    private JLabel jLabelStatus = null;
    private Font fontJLabel = null;
    
    private JDialog login = null;
	ConexionDB conn = null;
	CreateDB createDB;
	
    ResourceLoader resourceLoader = new ResourceLoader();
    
    public Login() {
        this(null, true);
    }
    
	public Login(final JFrame parent, boolean modal) {
		super(parent, modal);
		
	// verifico que el servidor PostgreSQL se encuentre escuchando
	    try {
	        Socket s = new Socket("localhost", 5432);
	        s.close();
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog( this, "Verifique que el servidor PostgreSQL se encuentre escuchando en localhost:5432.", "Servidor no encontrado", JOptionPane.ERROR_MESSAGE );
	    	System.exit( 0 );
	    }
	    
		try {
			createDB = new CreateDB();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog( this, e1, "Error", JOptionPane.ERROR_MESSAGE );
		}
		
		inicializar();
		
	    JPanel p3 = new JPanel(new GridLayout(2, 1,10,10));
	    p3.add(jLabelUsername);
	    p3.add(jLabelPassword);
	
	    JPanel p4 = new JPanel(new GridLayout(2, 1,10,10));
	    p4.add(jTextFieldUsername);
	    p4.add(jTextFieldPassword);
	
	    JPanel p1 = new JPanel();
	    p1.add(p3);
	    p1.add(p4);
	
	    JPanel p2 = new JPanel();
	    p2.setPreferredSize(new Dimension(250,50));
	    p2.add(jButtonOk);
	    p2.add(jButtonCancel);
	
	    JPanel p5 = new JPanel(new BorderLayout());
	    p5.add(p2, BorderLayout.CENTER);
	    p5.add(jLabelStatus, BorderLayout.NORTH);
	
	    setLayout(new BorderLayout());
	    add(p1, BorderLayout.CENTER);
	    add(p5, BorderLayout.SOUTH);
	    pack();
	    setLocationRelativeTo(null);
	    setSize( new Dimension( 280, 175 ) );
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setTitle( "Login" );
	    
	    addWindowListener(new WindowAdapter() {  
	        @Override
	        public void windowClosing(WindowEvent e) {  
	            System.exit(0);  
	        }  
	    });
	    
	    jTextFieldPassword.addKeyListener(new java.awt.event.KeyAdapter() {  
	    	// habilita a realizar la búsqueda presionando el boton Enter
            @SuppressWarnings("deprecation")
			public void keyPressed(java.awt.event.KeyEvent e) {
            	 int key = e.getKeyCode();
                 if (key == java.awt.event.KeyEvent.VK_ENTER) {
                	 if ("admin".equals(jTextFieldPassword.getText())
	                    && "admin".equals(jTextFieldUsername.getText())) {
                		 parent.setVisible(true);
                		 setVisible(false);
                	 } else {
                		 jLabelStatus.setText("Falló autenticación");
                	 }
                 }
	        }
	    });
	
	    jButtonOk.addActionListener(new ActionListener() {
	        @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
	            if ("admin".equals(jTextFieldPassword.getText())
	                    && "admin".equals(jTextFieldUsername.getText())) {
	                parent.setVisible(true);
	                setVisible(false);
	            } else {
	                jLabelStatus.setText("Falló autenticación");
	            }
	        }
	    });
	    jButtonCancel.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            setVisible(false);
	            parent.dispose();
	            System.exit(0);
	        }
	    });
	    
		// esta funcion hace que la ventana MenuPrincipal se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
					int option = JOptionPane.showConfirmDialog( login, "¿Está seguro que desea cerrar la aplicación?", "Cerrar aplicación", JOptionPane.YES_NO_OPTION );
					if( option == 0 )	
						System.exit( 0 );
		      }
		    };
		p5.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
	}
	
	void inicializar(){
		fontJLabel = new Font("Tahoma", Font.PLAIN, 12);
		
		jLabelStatus = new JLabel(" ");
		jLabelStatus.setForeground(Color.RED);
	    jLabelStatus.setHorizontalAlignment(SwingConstants.CENTER);
	    jLabelStatus.setFont(fontJLabel);
		
		jLabelUsername = new JLabel("Usuario:  ");
		jLabelPassword = new JLabel("Contraseña:  ");
		
		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setPreferredSize( new Dimension( 150, 25 ) );
		jTextFieldPassword = new JPasswordField();
		
		// seteo fuentes
		jLabelUsername.setFont(fontJLabel);
		jLabelPassword.setFont(fontJLabel);
		jTextFieldUsername.setFont(new Font("Dialog", Font.BOLD, 11));
		
		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(resourceLoader.load("/images/menu/ok-icon.png"));
		jButtonOk = new JButton("Aceptar", imageIconOk);
		jButtonOk.setPreferredSize( new Dimension( 100, 30 ) );
		
		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(resourceLoader.load("/images/menu/close-icon.png"));		
		jButtonCancel = new JButton( " Cancelar", imageIconCancel);
		jButtonCancel.setPreferredSize( new Dimension( 100, 30 ) );
	}
}
	

