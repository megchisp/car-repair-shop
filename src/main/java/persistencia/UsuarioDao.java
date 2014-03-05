package persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import persistencia.BD.ConexionDB;
import persistencia.BD.PostgresConexionDB;

public class UsuarioDao implements IUsuarioDao {
	ConexionDB conn = null;

	// en esta clase se escriben las sentencias SQL literalmente

	public UsuarioDao() {
		this.conn = new PostgresConexionDB();
	}	
	
	public int modificarPassword(Usuario usuario, String oldPassword, String newPassword) throws Exception {
		String queryModificar = "UPDATE Usuario SET password = '" + newPassword + "' WHERE id_usuario = '" + usuario.getId_usuario() + "';";
		try{
			if(!existeUsuario(usuario)) // se verifica que el usuario que se quiere modificar exista en la BD
				return 3;

			if(!usuario.getPassword().equals(oldPassword))
				return 2;
					
			conn.open(); // abre la conexion
			conn.execute(queryModificar);
			conn.close(); // cierra la conexión
			
			usuario.setPassword(newPassword); // actualizo password
			return 1; // éxito
			
		}catch (Exception e){
			throw e;
		}
	}

	public int modificar(Usuario usuario) throws Exception {
		String queryModificar = "UPDATE Usuario SET last_login = '" + new java.sql.Date(usuario.getLastLogin().getTimeInMillis()) + "' WHERE id_usuario = '" + usuario.getId_usuario() + "';";
		try{
			if(!existeUsuario(usuario)) // se verifica que el usuario que se quiere modificar exista en la BD
				return 2;
	
			conn.open(); // abre la conexion
			conn.execute(queryModificar);
			conn.close(); // cierra la conexión
			
			return 1; // éxito
			
		}catch (Exception e){
			throw e;
		}
	}
	
	public boolean login(String username, String password) throws Exception {
		// este método retorna true si el login es exitoso, false caso contrario
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM USUARIO WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "') THEN true ELSE false END";
		ResultSet rs = null;
		boolean login;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			login = rs.getBoolean(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return login;
	}

	public Usuario getUsuario(String username) throws Exception {
		// este método retorna el usuario dado su username
		String query = "SELECT * FROM usuario WHERE username = '" + username + "'";
		Usuario usuario = null;
		ResultSet resultado = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			resultado.next();
			usuario = new Usuario(resultado.getInt("id_usuario"), resultado.getString("username"), resultado.getString("password"), resultado.getInt("privilegio") ,resultado.getDate("last_login"));
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return usuario;
	}
	
	private boolean existeUsuario(Usuario usuario) throws Exception{
		// este método retorna true si existe el usuario pasado como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM USUARIO WHERE ID_USUARIO = " + usuario.getId_usuario() + ") THEN true ELSE false END";
		ResultSet rs = null;
		boolean existe;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			existe = rs.getBoolean(1);
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
		return existe;
	}

	public List<Usuario> listaUsuarios() throws Exception{
		// este método retorna una lista con todos los usuarios de la BD
		String query = "SELECT * FROM USUARIO";
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		ResultSet resultado = null;
		Usuario usuario = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				usuario = new Usuario(resultado.getInt("id_usuario"), resultado.getString("username"), resultado.getString("password"), resultado.getInt("privilegio"), resultado.getDate("last_login"));
				listaUsuarios.add(usuario);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaUsuarios;
	}
	
	public int nextID() throws Exception{
		// este método retorna el siguiente ID del cliente que se quiera agregar
		String query = "SELECT nextval('usuario_id_usuario_seq')";
		ResultSet rs = null;
		int next_id_usuario;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_usuario = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return next_id_usuario;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM usuario_id_usuario_seq";
		ResultSet rs = null;
		int last_value;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			last_value = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return last_value;
	}

}
