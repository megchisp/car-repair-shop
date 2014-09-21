package presentacion;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	JPanel jPanelMensaje = null;  
    JLabel jLabelMensaje = null;
    
	ResourceLoader resourceLoader = new ResourceLoader();
    
    public WaitDialog(String stringMensaje){
    	jPanelMensaje = new JPanel(new FlowLayout(0, 10, 30));
    	
    	ImageIcon loading = new ImageIcon(resourceLoader.load("/images/loader.gif"));
    	jLabelMensaje = new JLabel(stringMensaje,loading, JLabel.CENTER);
    	jLabelMensaje.setFont(new Font("Dialog", Font.BOLD, 11));
    	
		jPanelMensaje.add(jLabelMensaje);
	    this.getContentPane().add(jPanelMensaje);  
	    this.setSize(250,90);  
	    this.setLocationRelativeTo(null);  
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);  
	    this.setModal(true);
	    this.setUndecorated(true);
    }
    
    void setStringMensaje(String stringMensaje){
    	jLabelMensaje.setText(stringMensaje);
    }

}
