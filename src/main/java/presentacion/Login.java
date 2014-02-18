package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
    
    ResourceLoader resourceLoader = new ResourceLoader();
    
    public Login() {
        this(null, true);
    }
    
	public Login(final JFrame parent, boolean modal) {
		super(parent, modal);
		
		inicializar();
		
	    JPanel p3 = new JPanel(new GridLayout(2, 1));
	    p3.add(jLabelUsername);
	    p3.add(jLabelPassword);
	
	    JPanel p4 = new JPanel(new GridLayout(2, 1));
	    p4.add(jTextFieldUsername);
	    p4.add(jTextFieldPassword);
	
	    JPanel p1 = new JPanel();
	    p1.add(p3);
	    p1.add(p4);
	
	    JPanel p2 = new JPanel();
	    p2.add(jButtonOk);
	    p2.add(jButtonCancel);
	
	    JPanel p5 = new JPanel(new BorderLayout());
	    p5.add(p2, BorderLayout.CENTER);
	    p5.add(jLabelStatus, BorderLayout.NORTH);
	    jLabelStatus.setForeground(Color.RED);
	    jLabelStatus.setHorizontalAlignment(SwingConstants.CENTER);
	
	    setLayout(new BorderLayout());
	    add(p1, BorderLayout.CENTER);
	    add(p5, BorderLayout.SOUTH);
	    pack();
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	    addWindowListener(new WindowAdapter() {  
	        @Override
	        public void windowClosing(WindowEvent e) {  
	            System.exit(0);  
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
	                jLabelStatus.setText("Nombre de usuario o contraseña inválidos");
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
	}
	
	void inicializar(){
		fontJLabel = new Font("Tahoma", Font.PLAIN, 12);
		
		jLabelUsername = new JLabel("Usuario");
		jLabelPassword = new JLabel("Contraseña");
		
		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setPreferredSize( new Dimension( 130, 25 ) );
		jTextFieldPassword = new JPasswordField();
		
		jLabelStatus = new JLabel(" ");
		
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
	

