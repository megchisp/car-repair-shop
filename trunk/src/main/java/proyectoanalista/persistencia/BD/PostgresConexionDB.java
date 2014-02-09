package proyectoanalista.persistencia.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class PostgresConexionDB extends ConexionDB {

		
	public PostgresConexionDB() {
		this.url = "jdbc:postgresql://localhost:5432/postgres";
		this.username = "postgres";
		this.password = "admin";
	}

	@Override
	public Connection open() throws Exception {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {

			throw e;
		}

		try {
			this.conexion = DriverManager.getConnection(this.url, this.username, this.password);
		} catch (SQLException e) {

			throw e;
		}
		return this.conexion;
	}
	
	public void close() throws Exception{
		try {
			conexion.close();
		} catch (Exception e) {
			throw e;
		}

	}

}


