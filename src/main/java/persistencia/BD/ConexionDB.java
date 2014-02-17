package persistencia.BD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConexionDB {

	protected Connection conexion;
	
	String url;
	String username;
	String password;
	
	public abstract Connection open() throws Exception;
	
	public ResultSet query(String query) throws SQLException{
		Statement st;
		ResultSet rs = null;
		try{
			st = conexion.createStatement();
			rs = st.executeQuery(query);
					
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
		return rs;
	}
	
	public void execute(String query) throws SQLException{
		Statement st = null;
		try{
			st = conexion.createStatement();
			st.executeUpdate(query);
					
		}catch(SQLException e){
//			e.printStackTrace();
			throw e;
		}
	}
	
	public void close() throws Exception{
		try{
			conexion.close();
								
		}catch(Exception e){
//			e.printStackTrace();
			throw e;
		}
	}
}