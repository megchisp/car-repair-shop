package proyectoanalista.presentacion;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;

public class JTextFieldUpperCased extends JTextField {

	private static final long serialVersionUID = 1L;

	public JTextFieldUpperCased(){
		this.setFont(new Font("Dialog", Font.BOLD, 11));
		DocumentFilter filter = new UppercaseDocumentFilter();
		((AbstractDocument) this.getDocument()).setDocumentFilter(filter);
	}
}

