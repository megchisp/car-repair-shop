package persistencia.BD;

import java.io.File;
import java.sql.ResultSet;

import presentacion.ResourceLoader;

public class CreateDB {
	// en esta clase si no se detecta la BD se la crea con sus respectivas tablas
	ConexionDB conn = null;
	ResourceLoader resourceLoader = new ResourceLoader();
	
	public CreateDB() throws Exception{
		this.conn = new PostgresConexionDB("jdbc:postgresql://localhost:5432/postgres");
		
		if(!existeBD()) // si no existe la base de datos 'techsoft' se crea una
		{
			System.out.println("creando la bd...");
			crearBD();
			System.out.println("bd creada con exito!");
			crearTablas();
			System.out.println("La BD se creó correctamente");
		}
		else
			System.out.println("La BD ya existe");
	}
	
	public boolean existeBD() throws Exception{
		ResultSet rs = null;
		boolean existeBD;
		try{
			String query = "SELECT CASE WHEN EXISTS (SELECT datname FROM pg_catalog.pg_database WHERE datname = 'techsoft') THEN true ELSE false END";
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			existeBD = rs.getBoolean(1);
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
		return existeBD;
	}
	
	public void crearBD() throws Exception{
		try{
			String query = "CREATE DATABASE techsoft " +
					"WITH OWNER = postgres " +
					"ENCODING = 'UTF8' " +
					"TABLESPACE = pg_default " +
					"LC_COLLATE = 'Spanish_Argentina.1252' " +
					"LC_CTYPE = 'Spanish_Argentina.1252' " +
					"CONNECTION LIMIT = -1;";
			conn.open(); // abre la conexion
			conn.execute(query);
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}
	
	public void crearTablas() throws Exception{
    	ImportarBD unImportarBD = new ImportarBD();
    	try {
			unImportarBD.importarBD(new File(resourceLoader.load("/tablas.sql").toURI()));
		} catch (Exception e) {
			throw e;
		}
	}
}
