package proyectoanalista.presentacion;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
        fb.insertString(offset, text.toUpperCase(), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String text, javax.swing.text.AttributeSet attrs) throws BadLocationException {

        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}