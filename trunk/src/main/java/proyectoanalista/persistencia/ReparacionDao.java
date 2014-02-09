package proyectoanalista.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proyectoanalista.persistencia.BD.ConexionDB;
import proyectoanalista.persistencia.BD.PostgresConexionDB;


public class ReparacionDao implements IReparacionDao {

	ConexionDB conn;
	
	public ReparacionDao(){
		this.conn = new PostgresConexionDB();
	}
	
	public int agregar(Reparacion reparacion) throws Exception {
		String query = "INSERT INTO reparacion (id_reparacion, id_automovil,fecha,km,observaciones) VALUES " +
				"('" + reparacion.getId_reparacion() + "', '" + reparacion.getId_automovil() + "', '" + new java.sql.Date(reparacion.getFechaReparacion().getTimeInMillis()) +  "', '" + reparacion.getKilometraje() + "', '" + reparacion.getObservaciones().replace("'", "''") + "');";
		
		try{
			if (esValidoParaAgregar(reparacion)){ 
				conn.open(); // abre la conexion
				conn.execute(query);
				conn.close(); // cierra la conexión
				return 1;
			} 
			return 2;
		}
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(Reparacion reparacionModificada) throws Exception {
		String queryModificar = "UPDATE REPARACION SET fecha = '" + new java.sql.Date(reparacionModificada.getFechaReparacion().getTimeInMillis()) + "', km = '" + reparacionModificada.getKilometraje()+ "', observaciones = '" + reparacionModificada.getObservaciones().replace("'", "''") + "' WHERE id_reparacion = '" + reparacionModificada.getId_reparacion() + "';";
		try{
			 // se verifica que la reparacion que se quiere modificar exista en la BD

			if (esValidoParaModificar(reparacionModificada)){ 
				
				conn.open(); // abre la conexion
				conn.execute(queryModificar);
				conn.close(); // cierra la conexión
				
				return 1;
			}
			return 4;
			
		}catch (Exception e){
			throw e;
		}
	}

	public int eliminar(Reparacion reparacion) throws Exception{ 
		String queryEliminar = "DELETE FROM REPARACION WHERE id_reparacion = '" + reparacion.getId_reparacion() + "'";
		try{
			if (esValidoParaEliminar(reparacion)){ 
				conn.open(); // abre la conexion
				conn.execute(queryEliminar);
				conn.close(); // cierra la conexión
				return 1;
			}
			return 2;
		}
	
		catch(Exception e){
			throw e;
		}
	}

	public List<Reparacion> listaReparaciones(Automovil automovil) throws Exception {
		// este método retorna una lista con todas las reparaciones del automovil pasado como parametro
		String query = "SELECT id_reparacion, id_automovil, fecha,km,observaciones " +
		"FROM AUTOMOVIL NATURAL JOIN REPARACION WHERE id_automovil = '" + automovil.getId_automovil() + "' ORDER BY fecha";
		List<Reparacion> listaReparaciones = new ArrayList<Reparacion>();
		ResultSet resultado = null;
		Reparacion reparacion = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			while(resultado.next()){
				reparacion = new Reparacion(resultado.getInt("id_reparacion"), resultado.getInt("id_automovil"), resultado.getDate("fecha"), resultado.getInt("km") , resultado.getString("observaciones") );
				listaReparaciones.add(reparacion);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaReparaciones;
	}
	
	public List<Reparacion> listaReparaciones() throws Exception {
		// este método retorna una lista con todas las reparaciones
		// es utilizado para exportar la BD
		String query = "SELECT id_reparacion, id_automovil, fecha,km,observaciones FROM REPARACION";
		List<Reparacion> listaReparaciones = new ArrayList<Reparacion>();
		ResultSet resultado = null;
		Reparacion reparacion = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				reparacion = new Reparacion(resultado.getInt("id_reparacion"), resultado.getInt("id_automovil"), resultado.getDate("fecha"), resultado.getInt("km") , resultado.getString("observaciones") );
				listaReparaciones.add(reparacion);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaReparaciones;
	}
	
	private boolean esValidoParaModificar(Reparacion reparacion) throws Exception{
		return existeReparacion(reparacion);
	}
	
	private boolean esValidoParaEliminar(Reparacion reparacion) throws Exception{
		// este método retorna true si la reparacion a eliminar existe realmente
		return existeReparacion(reparacion);
		
	}
	
	private boolean existeReparacion(Reparacion reparacion) throws Exception{
		// este método retorna true si existe la reparacion pasada como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM REPARACION WHERE ID_REPARACION = " + reparacion.getId_reparacion() + ") THEN true ELSE false END";
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
	
	private boolean esValidoParaAgregar (Reparacion reparacion){
		return true; // por ahora no hay restricciones al agregar
	}
	
	public int nextID() throws Exception{
		// este método retorna el siguiente ID de la reparacion que se quiera agregar
		String query = "SELECT nextval('reparacion_id_reparacion_seq')";
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
		String query = "SELECT last_value FROM reparacion_id_reparacion_seq";
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

	public double importe(Reparacion reparacion) throws Exception {
		// este método devuelve la suma de los importes de los servicios de la reparacion pasada como parametro
		String query = "SELECT SUM(SUMA_MANO_DE_OBRA +  SUMA_REPUESTO) AS SUMA_REPARACION FROM( " +
				"SELECT COALESCE(SUM((PRECIO_MANO_DE_OBRA)), 0) AS SUMA_MANO_DE_OBRA " +
				"FROM SERVICIO NATURAL JOIN MANO_DE_OBRA " +
				"WHERE ID_REPARACION = '" + reparacion.getId_reparacion() + "') SUMA_MANO_DE_OBRA, " +
				"(SELECT COALESCE(SUM((PRECIO_UNITARIO * CANTIDAD)), 0) AS SUMA_REPUESTO " +
				"FROM SERVICIO NATURAL JOIN REPUESTO " +
				"WHERE ID_REPARACION = '" + reparacion.getId_reparacion() + "') SUMA_REPUESTO";
		ResultSet rs = null;
		double suma;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			suma = rs.getDouble(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return suma;
	}

	public int tiempoMinimo(Reparacion reparacion) throws Exception {
		// este método devuelve la suma de los tiempos mínimos de los servicios de la reparacion pasada como parametro
		String query = "SELECT SUM(T.TIEMPO_MIN_REPARACION_ESTIMADO) " +
				"FROM REPARACION R NATURAL JOIN SERVICIO S NATURAL JOIN TIPO_DE_SERVICIO T " +
				"WHERE R.ID_REPARACION = " + reparacion.getId_reparacion() + "";
		ResultSet rs = null;
		int suma;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			suma = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return suma;
	}

	public int tiempoMaximo(Reparacion reparacion) throws Exception {
		// este método devuelve la suma de los tiempos máximos de los servicios de la reparacion pasada como parametro
		String query = "SELECT SUM(T.TIEMPO_MAX_REPARACION_ESTIMADO) " +
				"FROM REPARACION R NATURAL JOIN SERVICIO S NATURAL JOIN TIPO_DE_SERVICIO T " +
				"WHERE R.ID_REPARACION = " + reparacion.getId_reparacion() + "";
		ResultSet rs = null;
		int suma;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			suma = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return suma;
	}

}
