package presentacion;

import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import persistencia.BD.ImportarBD;

public class Importar extends JFileChooser{

	private static final long serialVersionUID = 1L;
	JFileChooser fileChooser = null;
	FileNameExtensionFilter filter = null;
	Component parent = null;
	
	public Importar(JFrame padre){
		/*** Este constructor selecciona el archivo .sql mediante un JFileChooser ***/
		
		fileChooser = new JFileChooser();
//		fileChooser.showOpenDialog(padre); // setea icono al fileChooser
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos SQL", "sql", "text");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle(" Importar ");
		int userSelection = fileChooser.showOpenDialog(parent);
		if(userSelection == JFileChooser.APPROVE_OPTION) {
        	ImportarBD unImportarBD = new ImportarBD();
        	try {
				unImportarBD.eliminarBD(); // elimina todas la tuplas de BD
				unImportarBD.importarBD(fileChooser.getSelectedFile());
				
				JOptionPane.showMessageDialog( this, "La base de datos se ha importado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			}
        	finally{	
        	}
         }               
     }
	

}
