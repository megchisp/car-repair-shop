package presentacion;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import persistencia.BD.ExportarBD;

public class BackupRapido extends JFrame {

	private static final long serialVersionUID = 1L;
	
	File file = null;
	String nombreArchivo = null;
	String userHomeFolder = null;
	String stringSQLdatabase = null;
	PrintWriter printerWriter = null;

	public BackupRapido() {
		
		nombreArchivo = "backup_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		userHomeFolder = System.getProperty("user.home")+"\\Desktop";
		
		// Obtengo la base de datos SQL completa en forma de String
		try {
			stringSQLdatabase = new ExportarBD().obtenerStringSQLdatabase();
		
		
		// Creo el archivo en la ubicación dada y con el nombre dado
		file = new File(userHomeFolder, nombreArchivo + ".sql");
		
		// Escribo String al archivo
		printerWriter = new PrintWriter(file);
		printerWriter.print(stringSQLdatabase);
		
        JOptionPane.showMessageDialog( this, "La base de datos se ha exportado correctamente en " + userHomeFolder + "." , "Éxito", JOptionPane.INFORMATION_MESSAGE );
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e , "Error", JOptionPane.ERROR_MESSAGE );
		}
		finally{
			// cierro el archivo
			printerWriter.close();
		}
	}

}
