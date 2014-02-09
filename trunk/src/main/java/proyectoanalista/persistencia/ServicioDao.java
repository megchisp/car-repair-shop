package proyectoanalista.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proyectoanalista.persistencia.BD.ConexionDB;
import proyectoanalista.persistencia.BD.PostgresConexionDB;

public class ServicioDao implements IServicioDao {
	
	ConexionDB conn;
	
	public ServicioDao(){
		this.conn = new PostgresConexionDB();
	}

	public int agregar(Servicio servicio) throws Exception {
		try{
			if (esValidoParaAgregar(servicio)){ 
				String query = "INSERT INTO servicio (id_servicio, id_tipo_de_servicio, id_reparacion) VALUES ('"+ servicio.getId_servicio() + "','"+ servicio.getId_tipo_de_servicio() + "','" + servicio.getId_reparacion() + "');";
				
					conn.open(); // abre la conexion
					conn.execute(query);
					conn.close(); // cierra la conexión
					
					return 1;
			}
			return 2; // el tipo de servicio ya existe
		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int eliminar(Servicio servicio) throws Exception {
		String queryEliminar = "DELETE FROM SERVICIO WHERE ID_SERVICIO = '" + servicio.getId_servicio() + "'";
		try{
			if (esValidoParaEliminar(servicio)){ 
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

	public List<Servicio> listaServicios(Reparacion reparacion) throws Exception{
	// este método retorna una lista con todos los servicios de una reparacion dada
	String query = "SELECT * FROM SERVICIO WHERE ID_REPARACION =  '" + reparacion.getId_reparacion() + "'";
	List<Servicio> listaServicios = new ArrayList<Servicio>();
	ResultSet resultado = null;
	Servicio servicio = null;

	try{
		conn.open(); // abre la conexion
		resultado = conn.query(query);
		
		while(resultado.next()){
			servicio = new Servicio(resultado.getInt("id_servicio"), resultado.getInt("id_tipo_de_servicio"), resultado.getInt("id_reparacion"));
			listaServicios.add(servicio);
		}
		conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
	return listaServicios;
	}

	public List<Servicio> listaServicios() throws Exception{
	// este método retorna una lista con todos los servicios de la BD
	// se lo utiliza para exportar la BD
	String query = "SELECT * FROM SERVICIO";
	List<Servicio> listaServicios = new ArrayList<Servicio>();
	ResultSet resultado = null;
	Servicio servicio = null;

	try{
		conn.open(); // abre la conexion
		resultado = conn.query(query);
		
		while(resultado.next()){
			servicio = new Servicio(resultado.getInt("id_servicio"), resultado.getInt("id_tipo_de_servicio"), resultado.getInt("id_reparacion"));
			listaServicios.add(servicio);
		}
		conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
	return listaServicios;
	}
	
	public int nextID() throws Exception {
		// este método retorna el siguiente ID del servicio que se quiera agregar
		String query = "SELECT nextval('servicio_id_servicio_seq')";
		ResultSet rs = null;
		int next_id_servicio;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_servicio = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return next_id_servicio;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM servicio_id_servicio_seq";
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

	
	private boolean esValidoParaEliminar(Servicio servicio) throws Exception{
		// este método retorna true si el servicio a eliminar existe realmente
		return existeServicio(servicio);
		
	}
	
	private boolean existeServicio(Servicio servicio) throws Exception{
		// este método retorna true si existe el servicio pasado como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM SERVICIO WHERE ID_SERVICIO = " + servicio.getId_servicio() + ") THEN true ELSE false END";
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
	
	private boolean esValidoParaAgregar(Servicio servicio) throws Exception{
		// este método false si ya existe el tipo servicio agregado en la reparacion
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM SERVICIO WHERE ID_TIPO_DE_SERVICIO = '"+ servicio.getId_tipo_de_servicio() +"' AND ID_REPARACION = '" + servicio.getId_reparacion() + "') THEN true ELSE false END";
		ResultSet rs = null;
		boolean esValido;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			esValido = !rs.getBoolean(1); // si existe entonces NO es valido
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return esValido;
	}

	public double importe(Servicio servicio) throws Exception {
		// este método devuelve la suma de los importes de las manos de obra y de los respuestos del servicio pasado como parametro
		String query = "SELECT SUM(SUMA_MANO_DE_OBRA +  SUMA_REPUESTO) AS SUMA_SERVICIO FROM( " +
				"SELECT COALESCE(SUM((PRECIO_MANO_DE_OBRA)), 0) AS SUMA_MANO_DE_OBRA " +
				"FROM SERVICIO NATURAL JOIN MANO_DE_OBRA " +
				"WHERE ID_SERVICIO = '" + servicio.getId_servicio() + "') SUMA_MANO_DE_OBRA, " +
				"(SELECT COALESCE(SUM((PRECIO_UNITARIO * CANTIDAD)), 0) AS SUMA_REPUESTO " +
				"FROM SERVICIO NATURAL JOIN REPUESTO " +
				"WHERE ID_SERVICIO = '" + servicio.getId_servicio() + "') SUMA_REPUESTO";
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

}
