package persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import persistencia.BD.ConexionDB;
import persistencia.BD.PostgresConexionDB;

public class TipoDeServicioDao implements ITipoDeServicioDao {
	
	ConexionDB conn;
	int estadoFinal; // por el momento esta variable se utiliza para eliminar un tipo de servicio
	
	public TipoDeServicioDao(){
		this.conn = new PostgresConexionDB();
	}

	public int agregar(TipoDeServicio tipoDeServicio) throws Exception {
		try{
			if (esValidoParaAgregar(tipoDeServicio)){ 
				String query = "INSERT INTO tipo_de_servicio (id_tipo_de_servicio, nombre,tiempo_min_reparacion_estimado,tiempo_max_reparacion_estimado) VALUES ('"+ tipoDeServicio.getId_tipo_servicio() + "','"+ tipoDeServicio.getNombre() + "','" + tipoDeServicio.getTiempoMinimoReparacion() + "','" + tipoDeServicio.getTiempoMaximoReparacion() + "');";
				
					conn.open(); // abre la conexion
					conn.execute(query);
					conn.close(); // cierra la conexión
					
					return 1;
			}
			return 2; // el nombre del tipo de servicio ingresado pertenece a uno existente
		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(TipoDeServicio tipoDeServicio) throws Exception {
		String queryModificar = "UPDATE tipo_de_servicio SET nombre = '" + tipoDeServicio.getNombre() + "', tiempo_min_reparacion_estimado = '" + tipoDeServicio.getTiempoMinimoReparacion() + "', tiempo_max_reparacion_estimado = '" + tipoDeServicio.getTiempoMaximoReparacion() + "' WHERE id_tipo_de_servicio = '" + tipoDeServicio.getId_tipo_servicio() + "';";
		try{
			if(!existeTipoDeServicio(tipoDeServicio)) 
				return 4; // el tipo de servicio que se quiere modificar no existe en la BD
			if (esValidoParaModificar(tipoDeServicio)){ 
				
				conn.open(); // abre la conexion
				conn.execute(queryModificar);
				conn.close(); // cierra la conexión
				
				return 1;
			}
			return 2; // el nombre ingresado pertenece a un tipo de servicio existente
		}catch (Exception e){
			throw e;
		}
	}


	public int eliminar(TipoDeServicio tipoDeServicio) throws Exception {
		String queryEliminar = "DELETE FROM TIPO_DE_SERVICIO WHERE id_tipo_de_servicio = '" + tipoDeServicio.getId_tipo_servicio() + "'";
		try{
			if (esValidoParaEliminar(tipoDeServicio)){ 
				conn.open(); // abre la conexion
				conn.execute(queryEliminar);
				conn.close(); // cierra la conexión
				return 1;
			}
			return estadoFinal;
		}
	
		catch(Exception e){
			throw e;
		}
	}

	public List<TipoDeServicio> listaTiposDeServicio() throws Exception {
		// este método retorna una lista con todos los clientes de la BD
		String query = "SELECT * FROM tipo_de_servicio ORDER BY nombre ASC";
		List<TipoDeServicio> listaTiposDeServicio = new ArrayList<TipoDeServicio>();
		ResultSet resultado = null;
		TipoDeServicio tipoDeServicio = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				tipoDeServicio = new TipoDeServicio(resultado.getInt("id_tipo_de_servicio"), resultado.getString("nombre"), resultado.getInt("tiempo_min_reparacion_estimado"), resultado.getInt("tiempo_max_reparacion_estimado"));
				listaTiposDeServicio.add(tipoDeServicio);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaTiposDeServicio;
	}

	public int nextID() throws Exception {
		// este método retorna el siguiente ID del tipo de servicio que se quiera agregar
		String query = "SELECT nextval('tipo_de_servicio_id_tipo_de_servicio_seq')";
		ResultSet rs = null;
		int next_id_tipo_de_servicio;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_tipo_de_servicio = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return next_id_tipo_de_servicio;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM tipo_de_servicio_id_tipo_de_servicio_seq";
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
	
	private boolean esValidoParaAgregar(TipoDeServicio tipoDeServicio) throws Exception{
		// este método retorna false si ya existe un tipo de servicio con el mismo nombre

		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM TIPO_DE_SERVICIO WHERE NOMBRE = '"+ tipoDeServicio.getNombre() +"') THEN true ELSE false END";
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
	
	private boolean existeTipoDeServicio(TipoDeServicio tipoDeServicio) throws Exception{
		// este método retorna true si existe el tipo de servicio pasado como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM TIPO_DE_SERVICIO WHERE ID_TIPO_DE_SERVICIO = " + tipoDeServicio.getId_tipo_servicio() + ") THEN true ELSE false END";
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
	
	private boolean estaSiendoUtilizado(TipoDeServicio tipoDeServicio) throws Exception{
		// este método retorna true si el tipo de servicio está siendo utilizado en al menos una reparación
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM SERVICIO WHERE ID_TIPO_DE_SERVICIO = '" + tipoDeServicio.getId_tipo_servicio() + "') THEN true ELSE false END";
		ResultSet rs = null;
		boolean utilizado;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			utilizado = rs.getBoolean(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return utilizado;
	}
	
	private boolean esValidoParaEliminar(TipoDeServicio tipoDeServicio) throws Exception{
		// este método retorna true si el tipo de servicio a eliminar existe realmente
		
		if(!existeTipoDeServicio(tipoDeServicio)){ // si no existe...
			estadoFinal = 2;
			return false;
		}
		
		if(estaSiendoUtilizado(tipoDeServicio)){
			estadoFinal = 3;
			return false;
		}
			
		return true;
		
	}
	
	private boolean esValidoParaModificar(TipoDeServicio tipoDeServicio) throws Exception{
		// este método retorna true si existe un tipo de servicio con el mismo nombre que no sea él mismo. Se lo utiliza para modificar un nuevo cliente
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM TIPO_DE_SERVICIO WHERE NOMBRE = '"+ tipoDeServicio.getNombre() +"' AND ID_TIPO_DE_SERVICIO != " + tipoDeServicio.getId_tipo_servicio() + ") THEN true ELSE false END";
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

	public String getNombre(Servicio servicio) throws Exception {
		// este método retorna el nombre del tipo de servicio dado el servicio
		String query = "SELECT NOMBRE FROM TIPO_DE_SERVICIO WHERE ID_TIPO_DE_SERVICIO = '"+ servicio.getId_tipo_de_servicio() +"'";
		ResultSet rs = null;
		String nombre = null;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			nombre = rs.getString(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return nombre;
	}

}
