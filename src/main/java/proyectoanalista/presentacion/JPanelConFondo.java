package proyectoanalista.presentacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class JPanelConFondo extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Image imagen;
	private int x;
	private int y;
	private int ancho;
	private int alto;

	public JPanelConFondo(Image img, int x, int y, int ancho, int alto)
	{
		super();
		imagen = img;
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto ;
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(imagen, x, y, ancho, alto, this);
		
		setBackground(Color.lightGray);
		setOpaque(false);

		
		super.paint(g);
	}
}