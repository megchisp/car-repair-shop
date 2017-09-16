package presentacion;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import persistencia.BD.ImportarBD;

public class Importar extends JFileChooser{

	private static final long serialVersionUID = 1L;
	JFileChooser fileChooser = null;
	FileNameExtensionFilter filter = null;

	JFileChooser jFileChooserThis = this;
	
	public Importar(JFrame padre){
		/*** Este constructor selecciona el archivo .sql mediante un JFileChooser ***/
		
		fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos SQL", "sql", "text");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle(" Importar ");
		int userSelection = fileChooser.showOpenDialog(padre);
		if(userSelection == JFileChooser.APPROVE_OPTION) {
        	final ImportarBD unImportarBD = new ImportarBD();
        	try {
        		final WaitDialog waitDialog = new WaitDialog("Eliminando base de datos actual...");
    			SwingWorker<?,?> worker = new SwingWorker<Void,Void>(){
    				protected Void doInBackground(){
    					try {
    						// elimina todas la tuplas de todas las tablas de la BD
							unImportarBD.eliminarBD();
	    					waitDialog.setStringMensaje("Importando base de datos...");
	    					unImportarBD.importarBD(new BufferedReader(new FileReader(fileChooser.getSelectedFile())));
	    					waitDialog.dispose(); 
	    					JOptionPane.showMessageDialog( jFileChooserThis, "La base de datos se ha importado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE );
    					} catch (Exception e) {
    						waitDialog.dispose(); 
    						JOptionPane.showMessageDialog( jFileChooserThis, "Compruebe que la sintaxis del backup sea el correcto.\n\nDetalles del error:\n" + e, "Error", JOptionPane.ERROR_MESSAGE );
    						return null;
						} 
    		            return null;  
    		          }  
    		  
    		          protected void done(){  
//    		        	  waitDialog.dispose();  
    		          }  
    		        };  
    		        worker.execute();  
    		        waitDialog.setVisible(true);
    		        
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
			}

         }               
     }
	

}
