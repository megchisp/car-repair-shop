package proyectoanalista.presentacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.util.Hashtable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class ImageListModel extends DefaultComboBoxModel<Object> {
	/** Esta clase sirve para dar color a los items de un JComboBox **/
	
	private static final long serialVersionUID = 1L;
	
	// ¡IMPORTANTE!
	// ¡NO ALTERAR EL ORDEN DE LOS COLORES! (porque la BD almacena el índice donde se ubica el color)
	private static final Color color[] = 
      {null, Color.black, Color.white, Color.gray, Color.lightGray, Color.darkGray, Color.red, Color.yellow, Color.blue, Color.green, Color.cyan, Color.orange, Color.magenta};
    private static final String label [] = 
      {"-------------", "Negro", "Blanco","Gris", "Gris claro", "Gris oscuro", "Rojo", "Amarillo", "Azul", "Verde", "Celeste", "Naranja", "Magenta"};
    public ImageListModel () {
      Icon icon;
      for (int i=0, n=label.length; i<n ;i++) {
        icon = new AnOvalIcon (color[i]);
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        result.put ("label", label[i]);
        result.put ("icon",  icon);
        addElement(result);
      }
    }
  }

class AnOvalIcon implements Icon {
  Color color;
  public AnOvalIcon (Color c) {
    color = c;
  }
  public void paintIcon (Component c, Graphics g, 
      int x, int y) {
    g.setColor(color);
    g.fillOval (x, y, 
      getIconWidth(), getIconHeight());
  }
  public int getIconWidth() {
    return 20;
  }
  public int getIconHeight() { 
    return 10;
  }
}

class ImageCellRenderer implements ListCellRenderer<Object> {
    private JLabel renderer;
    public ImageCellRenderer () {
      renderer = new JLabel();
      renderer.setOpaque (true);
    }
    public Component getListCellRendererComponent(
        JList<?> list, Object value, int index, 
        boolean isSelected, boolean cellHasFocus) {
      if (value == null) {
        renderer.setText("");
        renderer.setIcon(null);
      } else {
        Hashtable<?, ?> h = (Hashtable<?, ?>) value;
        renderer.setText((String)h.get ("label"));
        renderer.setIcon((Icon)h.get ("icon"));
      }
      renderer.setFont(new Font("Dialog", Font.BOLD, 11));
      renderer.setBackground (isSelected ? 
        SystemColor.textHighlight : 
        SystemColor.text);
      renderer.setForeground (isSelected ? 
        SystemColor.textHighlightText : 
        SystemColor.textText);
      return renderer;
    }
  }