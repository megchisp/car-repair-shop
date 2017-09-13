package presentacion;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import persistencia.BD.ExportarBD;

public class BackupRapido extends JFrame {

	private static final long serialVersionUID = 1L;

	public BackupRapido() throws Exception {
		
		String nombreArchivo = "backup_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String userHomeFolder = System.getProperty("user.home")+"\\Desktop";
		
		
		new ExportarBD().getDatabaseFile(userHomeFolder, nombreArchivo  + ".sql");
        JOptionPane.showMessageDialog( this, "La base de datos se ha exportado correctamente en " + userHomeFolder + "." , "Éxito", JOptionPane.INFORMATION_MESSAGE );


	}
	
}
