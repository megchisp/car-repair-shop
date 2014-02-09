package proyectoanalista.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proyectoanalista.persistencia.BD.ConexionDB;
import proyectoanalista.persistencia.BD.PostgresConexionDB;

public class AutomovilDao implements IAutomovilDao{

	ConexionDB conn;
	int estadoFinal = 1;
	
	public AutomovilDao() {
		this.conn = new PostgresConexionDB();
		
	}

	public int agregar(Automovil automovil) throws Exception {
		String query = "INSERT INTO automovil (id_automovil, id_cliente, dominio, num_motor, num_chasis, marca, modelo, anio, color, aceite, tipo_combustible, con_gnc, num_radio, codigo_llave, uso) " +
				"VALUES ('" + automovil.getId_automovil() + "', '"  + automovil.getId_cliente() + "', '" + automovil.getDominio() + "', '" + automovil.getNumeroMotor().replace("'", "''") + "', '" + automovil.getNumeroChasis().replace("'", "''") + "', '" + automovil.getMarca().replace("'", "''") + "', '" + automovil.getModelo().replace("'", "''") +
				"', '" + automovil.getAño() + "', '" + automovil.getColor() + "', '" + automovil.getTipoAceite().replace("'", "''") + "', '" + automovil.getTipoCombustible() +"', '" + automovil.isConGNC() + "', '" + automovil.getNumeroRadio().replace("'", "''") + "', '" + automovil.getCodigoLlave().replace("'", "''") + "', '" + automovil.getUso().replace("'", "''") + "');";
		
		try{
			if(esValidoParaAgregar(automovil)){
				
				conn.open(); // abre la conexion
				conn.execute(query);
				conn.close(); // cierra la conexión
				estadoFinal = 1;
			} 
			return estadoFinal; 
		}
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(Automovil automovil) throws Exception {
		String queryModificar = "UPDATE AUTOMOVIL SET dominio = '" + automovil.getDominio() + "', num_motor = '" + automovil.getNumeroMotor().replace("'", "''") + "', num_chasis = '" + automovil.getNumeroChasis().replace("'", "''") + "', marca = '" + automovil.getMarca().replace("'", "''") + "', modelo = '" + automovil.getModelo().replace("'", "''") + "', anio = '" + automovil.getAño() + "', color = '" + automovil.getColor() + "', aceite = '" + automovil.getTipoAceite().replace("'", "''") + "', tipo_combustible = '" + automovil.getTipoCombustible() + "', con_gnc = '" + automovil.isConGNC() + "', num_radio = '" + automovil.getNumeroRadio().replace("'", "''") + "', codigo_llave = '" + automovil.getCodigoLlave().replace("'", "''")  + "', uso = '" + automovil.getUso().replace("'", "''") +  "' WHERE id_automovil = '" + automovil.getId_automovil() + "';";
		try{
			if(!existeAutomovil(automovil)) // se verifica que el cliente que se quiere modificar exista en la BD
				return 6;
			if(esValidoParaModificar(automovil)){
				conn.open(); // abre la conexion
				conn.execute(queryModificar);
				conn.close(); // cierra la conexión
				estadoFinal = 1;
			}
			return estadoFinal; 
		}catch (Exception e){
			throw e;
		}
	}

	public int eliminar(Automovil automovil) throws Exception {
		int idAutomovil = automovil.getId_automovil();
		String queryEliminar = "DELETE FROM AUTOMOVIL WHERE id_automovil = '" + idAutomovil + "'";
		try{
			if (esValidoParaEliminar(automovil)){ 
				conn.open(); // abre la conexion
				conn.execute(queryEliminar);
				conn.close(); // cierra la conexión
				return 1;
			}
			return 2;
		}catch(Exception e){
			throw e;
		}

	}



	public List<Automovil> listaAutomoviles(Cliente cliente) throws Exception {
		// este método retorna una lista con todos los automoviles del cliente pasado como parametro
		String query = "SELECT id_automovil, id_cliente, dominio, num_motor, num_chasis, marca, modelo, anio, color, aceite, tipo_combustible, con_gnc, num_radio, codigo_llave, uso " +
				"FROM AUTOMOVIL NATURAL JOIN CLIENTE WHERE id_cliente = '" + cliente.getId_cliente() + "'";
		List<Automovil> listaAutomoviles = new ArrayList<Automovil>();
		ResultSet resultado = null;
		Automovil automovil = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				automovil = new Automovil(resultado.getInt("id_automovil"), resultado.getInt("id_cliente"), resultado.getString("dominio"), resultado.getString("num_motor"), resultado.getString("num_chasis"), resultado.getString("marca"), resultado.getString("modelo"), resultado.getInt("anio"), resultado.getInt("color"), resultado.getString("aceite"), resultado.getString("tipo_combustible"), resultado.getBoolean("con_gnc"), resultado.getString("num_radio"), resultado.getString("codigo_llave"), resultado.getString("uso"), "" ,"" );
				listaAutomoviles.add(automovil);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaAutomoviles;
	}

	public List<Automovil> listaAutomoviles() throws Exception {
		// este método retorna una lista con todos los automoviles de la BD
		String query = "SELECT * FROM AUTOMOVIL NATURAL JOIN CLIENTE ORDER BY apellido, nombre ASC";
		List<Automovil> listaAutomoviles = new ArrayList<Automovil>();
		ResultSet resultado = null;
		Automovil automovil = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				automovil = new Automovil(resultado.getInt("id_automovil"), resultado.getInt("id_cliente"), resultado.getString("dominio"), resultado.getString("num_motor").trim(), resultado.getString("num_chasis").trim(), resultado.getString("marca").trim(), resultado.getString("modelo").trim(), resultado.getInt("anio"), resultado.getInt("color"), resultado.getString("aceite").trim(), resultado.getString("tipo_combustible"), resultado.getBoolean("con_gnc"), resultado.getString("num_radio").trim(), resultado.getString("codigo_llave"), resultado.getString("uso").trim(), resultado.getString("nombre"), resultado.getString("apellido") );
				listaAutomoviles.add(automovil);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaAutomoviles;
	}

	private boolean esValidoParaAgregar(Automovil automovil) throws Exception{
		// valido si ya existe el dominio en la BD
		try {
			if(existeCampo("dominio", automovil.getDominio().trim())){
				estadoFinal = 2;
				return false; // finaliza método
			}
		
		// valido si ya existe el num de motor en la BD
			if(automovil.getNumeroMotor().isEmpty())
				return true;
			else
			if(existeCampo("num_motor", automovil.getNumeroMotor().trim())){
				estadoFinal = 3;
				return false; // finaliza método
			}
		
		// valido si ya existe el num de chasis en la BD
			if(automovil.getNumeroChasis().isEmpty())
				return true;
			else
			if(existeCampo("num_chasis", automovil.getNumeroChasis().trim())){
				estadoFinal = 4;
				return false;
			}
				
		}
		catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	private boolean esValidoParaModificar(Automovil automovil) throws Exception{
		// valido si ya existe el dominio en la BD
		try {
			if(existeCampo("dominio", automovil.getDominio().trim(), automovil.getId_automovil())){
				estadoFinal = 2;
				return false; // finaliza método
			}
		
		// valido si ya existe el num de motor en la BD
			if(automovil.getNumeroMotor().isEmpty())
				return true;
			else
			if(existeCampo("num_motor", automovil.getNumeroMotor().trim(), automovil.getId_automovil())){
				estadoFinal = 3;
				return false; // finaliza método
			}
		
		// valido si ya existe el num de chasis en la BD
			if(automovil.getNumeroChasis().isEmpty())
				return true;
			else
			if(existeCampo("num_chasis", automovil.getNumeroChasis().trim(), automovil.getId_automovil())){
				estadoFinal = 4;
				return false; // finaliza método
			}
				
		}
		catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	private boolean existeCampo(String campo, String dato) throws Exception {
		// este método retorna true si existe un campo con cierto dato en la tabla de automoviles
		// el string campo tomará el valor 'dominio', 'num_motor' y 'num_chasis'.
		// Se utiliza para agregar un nuevo automovil
		if(dato.isEmpty())
			return false;
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM AUTOMOVIL WHERE " + campo  +  "= '"+ dato +"') THEN true ELSE false END";
		ResultSet rs = null;
		boolean existe = false;
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
	
	private boolean existeCampo(String campo, String dato, int id_automovil) throws Exception {
		// este método retorna true si existe un campo con cierto dato en la tabla de automoviles
		// el string campo tomará el valor 'dominio', 'num_motor' y 'num_chasis' que no sea él mismo.
		// Este método se utiliza para modificar un automovil ya existente
		if(dato.isEmpty())
			return false;
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM AUTOMOVIL WHERE " + campo  +  "= '"+ dato + "'AND ID_AUTOMOVIL != " + id_automovil + ") THEN true ELSE false END";
		ResultSet rs = null;
		boolean existe = false;
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
	
	private boolean existeAutomovil(Automovil automovil) throws Exception{
		// este método retorna true si existe el automovil pasado como parametro

		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM AUTOMOVIL WHERE ID_AUTOMOVIL = " + automovil.getId_automovil() + ") THEN true ELSE false END";
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
	
	private boolean esValidoParaEliminar(Automovil automovil) throws Exception{
		// este método retorna true si el automovil a eliminar existe realmente
		return existeAutomovil(automovil);
	}


	public int nextID() throws Exception{
		// este método retorna el siguiente ID del automovil que se quiera agregar
		String query = "SELECT nextval('automovil_id_automovil_seq')";
		ResultSet rs = null;
		int next_id_cliente;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_cliente = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return next_id_cliente;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM automovil_id_automovil_seq";
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

	public Automovil getAutomovil(Reparacion reparacion) throws Exception {
		// este método retorna el automovil correspondiente a la reparacion pasada como parámetro
		Automovil automovil = null;
		ResultSet resultado = null;
		String query = "SELECT * FROM AUTOMOVIL WHERE id_automovil = "+ reparacion.getId_automovil();

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			resultado.next();
			automovil = new Automovil(resultado.getInt("id_automovil"), resultado.getInt("id_cliente"), resultado.getString("dominio"), resultado.getString("num_motor"), resultado.getString("num_chasis"), resultado.getString("marca"), resultado.getString("modelo"), resultado.getInt("anio"), resultado.getInt("color"), resultado.getString("aceite"), resultado.getString("tipo_combustible"), resultado.getBoolean("con_gnc"), resultado.getString("num_radio"), resultado.getString("codigo_llave"), resultado.getString("uso"), "", "" );
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return automovil;
	}

}
