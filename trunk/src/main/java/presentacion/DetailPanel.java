package presentacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class DetailPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public DetailPanel() {
		setOpaque( false );
	}

	protected void paintComponent( Graphics g ) {
	    int x = 15;
	    int y = 15;
	    int w = getWidth() - 33;
	    int h = getHeight() - 33;
	    int arc = 42;

	    Graphics2D g2 = ( Graphics2D ) g.create();
	    
	    g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

	    g2.setColor( Color.BLACK );
	    g2.fillRoundRect( x, y, w, h, arc, arc );

	    g2.setStroke( new BasicStroke( 3f ) );
	    g2.setColor( Color.BLACK );
	    g2.drawRoundRect( x, y, w, h, arc, arc ); 
	 
	    g2.dispose();
	}
}