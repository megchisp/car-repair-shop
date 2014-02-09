package proyectoanalista.presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MarqeuePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private boolean IsStoped = false;
	private JPanel jPanelMarquesina = null; // el contenido de este panel se mostrará como marquesina
	private String stringTexto = null;
	private JLabel jLabelTexto = null;
	private JLabel jLabelVacio = null;
	boolean first = true;
	
    public MarqeuePanel(Dimension d)
    {
        super();
        inicializarPanelMarquesina();
        
        add(jPanelMarquesina);
        setPreferredSize(d);
        setOpaque(false);
        setBackground(new Color(0,0,0,0));
    	jPanelMarquesina.setLocation(0,this.getSize().height);
        Thread t = new Thread(this);
        t.start();
    }

//	    @Override
    public void run() 
    {
        while(!IsStoped)
        {
            if(jPanelMarquesina.getLocation().y < -jPanelMarquesina.getSize().height - jLabelVacio.getPreferredSize().height + 180)
            {
            	jPanelMarquesina.setLocation(0,this.getSize().height - jLabelVacio.getPreferredSize().height);
            }
            else
            {
                int y = jPanelMarquesina.getLocation().y - 1;
                jPanelMarquesina.setLocation(0,y);
            }
            try{Thread.sleep(100);}catch(Exception exc){}
        }
    }

    public void StopMarque()
    {
        IsStoped = true;
    }
    
    private void inicializarPanelMarquesina(){
    	// ascii art UTN
    	stringTexto = "<html><font color=#CEF6F5>" +
    			"                    			 _   _      _____      _     _ <br>" +
    			"                   |   |    |   | _      _ |   |    \\ |    |<br>" +
    			"                   |   |    |       |    |        |      \\    |<br>" +
    			"                   \\___/        | _ |         | _ \\ __|</font><br><br><br>"+
    			
    			"<center><font face=\"Impact\" size=5 >SISTEMA DE GESTIÓN AUTOMOTOR</font></center><br>" +
    			"<font face=\"Courier New\">" +
    			"<u>Empresa</u>: Mecánica Integral del Automotor<br><br>" +
				"<u>Integrantes</u>:<br>" +
				"Baravalle, Martín<br>" +
				"Olivera, Mariano<br>" +
				"García Osorio, Mauro<br><br>" +
				
				"<u>Docente</u>:<br>" +
				"Bracalenti, Claudio<br><br>" +
				
				"<center>Este sistema fué presentado como proyecto " +
				"para la cátedra Habilitación Profesional " +
				"de la carrera de Analista Universitario en Sistemas de la " +
				"Universidad Tecnológica Nacional.<br><br>" +
				"<u>MIDI</u>: \"Smells like teen spirit - Nirvana\"<br><br>" +
				"<u>Año</u>: 2014</center>" + 
				"</font></html>";

        jLabelVacio = new JLabel();
        jLabelVacio.setPreferredSize(new Dimension(250,200));

    	jLabelTexto = new JLabel();
        jLabelTexto.setFont(new Font("Dialog", Font.PLAIN, 12));
        jLabelTexto.setForeground(Color.WHITE);
        jLabelTexto.setPreferredSize(new Dimension(250,500));
        jLabelTexto.setText(stringTexto);
        
        jPanelMarquesina = new JPanel();
        jPanelMarquesina.setBackground(Color.BLACK);
        jPanelMarquesina.setOpaque(false);
        jPanelMarquesina.setPreferredSize(new Dimension(250,500 + jLabelVacio.getPreferredSize().height));
        jPanelMarquesina.setLayout( new FlowLayout(2, 0, 0));
        
        jPanelMarquesina.add(jLabelVacio);
        jPanelMarquesina.add(jLabelTexto);
    }
    
}