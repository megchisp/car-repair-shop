package proyectoanalista.persistencia.BD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class ImportarBD{
	
	ConexionDB conn;
	
	public ImportarBD() {
		this.conn = new PostgresConexionDB();
	}	

	public void importarBD(File archivoSQL) throws Exception {
		// Este método ejecuta todas las sentencias contenidas en el archivo SQL
		BufferedReader bufferedReader = new BufferedReader(new FileReader(archivoSQL));
		StringBuilder query = new StringBuilder();
		try{
			conn.open(); // abre la conexion
			String line = bufferedReader.readLine();
	        while (line != null) // almacena todo el archivo en el stringBuilder query
	        {
	        	query.append(line + "\n");
	        	line = bufferedReader.readLine();
	        }
	        conn.execute(query.toString());
			conn.close(); // cierra la conexión
			} 
		catch(Exception e) 
		{ 
			throw e;
		}
		finally {
			bufferedReader.close();
	    }
	}

	public void eliminarBD() throws Exception {
		// Este método elimina TODAS las tuplas de TODAS las tablas de la BD
		
		String queryEliminarBD = "DELETE FROM cliente; DELETE FROM mecanico; DELETE FROM proveedor; DELETE FROM tipo_de_servicio;";
		try{
			conn.open(); // abre la conexion
			conn.execute(queryEliminarBD);
			conn.close(); // cierra la conexión
		}catch(Exception e){
			System.out.println("Error al eliminar");
			throw e;
		}
	}

}
