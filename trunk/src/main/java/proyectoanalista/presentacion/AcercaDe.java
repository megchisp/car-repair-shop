package proyectoanalista.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class AcercaDe extends JDialog {
	private static final long serialVersionUID = 1L;
	JPanel jPanelAcercaDe = null;
	MarqeuePanel marqueuePanel = null;
	String stringTexto = null;
	JButton jButtonCerrar = null;
	MIDI midi = null; // clase que reproduce el MIDI
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	public AcercaDe( JFrame padre, String titulo, String titleBorder ) {
		super( padre, titulo, true );
		
		this.setPreferredSize( new Dimension( 350, 280 ) ); // tamaño de la ventana
		this.getContentPane().setLayout( new BorderLayout() );
		this.setTitle(titulo);
		
		jPanelAcercaDe = new JPanelConFondo (new ImageIcon(resourceLoader.load("/images/abstract-design.jpg")).getImage(), -30, -60, 500, 400);

		jPanelAcercaDe.setLayout(new FlowLayout(FlowLayout.CENTER,0,15) );
		jPanelAcercaDe.setPreferredSize(new Dimension( 290, 295 ));
		jPanelAcercaDe.setBackground(Color.BLACK);
		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder( titleBorder );
		titledBorder.setBorder( BorderFactory.createLineBorder( new Color( 100, 150, 100 ) ) );
		titledBorder.setTitleColor( new Color( 0, 0, 128 ) );
		
		jButtonCerrar = new JButton( "Cerrar" );
		jButtonCerrar.setFont(new Font("IMPACT", Font.PLAIN, 17));
		jButtonCerrar.setPreferredSize( new Dimension( 100, 30 ) );
		jButtonCerrar.setBackground(Color.BLACK);
		jButtonCerrar.setForeground(Color.WHITE);
		jButtonCerrar.setBorderPainted(true);
		jButtonCerrar.setFocusPainted(false);
		jButtonCerrar.setContentAreaFilled(false);
		jButtonCerrar.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		
		jButtonCerrar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				AcercaDe.this.dispose();
				marqueuePanel.StopMarque();
				midi.stop(); // detiene la musica
			}
		});

		// asigna una forma eliptica a la ventana
		setUndecorated(true);  
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		addComponentListener(new ComponentAdapter() {
            // Give the window an elliptical shape.
            // If the window is resized, the shape is recalculated here.
            public void componentResized(ComponentEvent e) {
                setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
            }
        });

		cerrarEsc();
		midi = new MIDI(); // crea instancia de MIDI mientras el constructor reproduce la musica
		
		marqueuePanel = new MarqeuePanel(new Dimension( 260, 200 ));
		jPanelAcercaDe.add(marqueuePanel);
		jPanelAcercaDe.add(jButtonCerrar);
		
		this.getContentPane().add( jPanelAcercaDe, BorderLayout.NORTH );
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setResizable( false );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}
	
	private void cerrarEsc(){
		// esta funcion hace que la ventana AcercaDe se cierre con la tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  AcercaDe.this.dispose();
		    	  marqueuePanel.StopMarque();
		    	  midi.stop(); // detiene la musica
		      }
		    };
		jPanelAcercaDe.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
}

