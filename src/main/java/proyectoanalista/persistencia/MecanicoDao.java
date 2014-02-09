package proyectoanalista.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proyectoanalista.persistencia.BD.ConexionDB;
import proyectoanalista.persistencia.BD.PostgresConexionDB;

public class MecanicoDao implements IMecanicoDao{
	
	ConexionDB conn = null;
	
	public MecanicoDao(){
		this.conn = new PostgresConexionDB();
	}

	public int agregar(Mecanico mecanico) throws Exception {
		try{
			if (esValidoParaAgregar(mecanico)){ 
				String query = "INSERT INTO MECANICO (id_mecanico, nombre,apellido,telefono_fijo,telefono_celular,domicilio,localidad,codigo_postal) VALUES ('" + mecanico.getId_mecanico() + "','"+ mecanico.getNombre().replace("'", "''") + "','" + mecanico.getApellido().replace("'", "''") + "','" + mecanico.getTelefono() + "','" + mecanico.getCelular() + "','" + mecanico.getDomicilio() + "','" + mecanico.getLocalidad().replace("'", "''") + "','" + mecanico.getCodigoPostal().replace("'", "''") + "');";
					conn.open(); // abre la conexion
					conn.execute(query);
					conn.close(); // cierra la conexión
					return 1;
			}
			return 2; // el ID ingresado pertenece a un mecanico existente
		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(Mecanico mecanicoModificado) throws Exception {
		String queryEliminar = "UPDATE MECANICO SET id_mecanico = '" + mecanicoModificado.getId_mecanico() + "', nombre = '" + mecanicoModificado.getNombre().replace("'", "''") + "', apellido = '" + mecanicoModificado.getApellido().replace("'", "''") + "', telefono_fijo = '" + mecanicoModificado.getTelefono() + "', telefono_celular = '" + mecanicoModificado.getCelular() + "', domicilio = '" + mecanicoModificado.getDomicilio().replace("'", "''") + "', localidad = '" + mecanicoModificado.getLocalidad().replace("'", "''") + "', codigo_postal = '" + mecanicoModificado.getCodigoPostal().replace("'", "''") + "' WHERE id_mecanico = '" + mecanicoModificado.getId_mecanico() + "';";
		try{
			if(!existeMecanico(mecanicoModificado)) // se verifica que el mecanico que se quiere modificar exista en la BD
				return 2 ;
			 
			conn.open(); // abre la conexion
			conn.execute(queryEliminar);
			conn.close(); // cierra la conexión
			return 1;
		}
	
		catch(Exception e){
			throw e;
		}
	}

	public int eliminar(Mecanico mecanico) throws Exception {
		String queryEliminar = "DELETE FROM MECANICO WHERE id_mecanico = '" + mecanico.getId_mecanico() + "'";
		try{
			if (esValidoParaEliminar(mecanico)){ 
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

	public List<Mecanico> listaMecanicos() throws Exception {
		// este método retorna una lista con todos los mecanicos de la BD
		String query = "SELECT * FROM MECANICO ORDER BY ID_MECANICO ASC";
		List<Mecanico> listaMecanicos = new ArrayList<Mecanico>();
		ResultSet resultado = null;
		Mecanico mecanico = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				mecanico = new Mecanico(resultado.getInt("id_mecanico"), resultado.getString("nombre"), resultado.getString("apellido"), resultado.getString("telefono_fijo"), resultado.getString("telefono_celular"), resultado.getString("domicilio"), resultado.getString("localidad"), resultado.getString("codigo_postal"));
				listaMecanicos.add(mecanico);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaMecanicos;
	}
	
	private boolean esValidoParaAgregar(Mecanico mecanico) throws Exception{
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM MECANICO WHERE ID_MECANICO = '"+ mecanico.getId_mecanico() +"') THEN true ELSE false END";
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
	
	private boolean esValidoParaEliminar(Mecanico mecanico) throws Exception{
		// este método retorna true si el mecanico a eliminar existe realmente
		return existeMecanico(mecanico);
	}
	
	private boolean existeMecanico(Mecanico mecanico) throws Exception{
		// este método retorna true si existe el mecanico pasado como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM MECANICO WHERE ID_MECANICO = " + mecanico.getId_mecanico() + ") THEN true ELSE false END";
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

	public Mecanico getMecanico(int id_mecanico) throws Exception {
		// este método retorna el mecanico con el id pasado como parametro
		String query = "SELECT * FROM MECANICO WHERE ID_MECANICO = '" + id_mecanico + "'";
		ResultSet resultado = null;
		Mecanico mecanico = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			resultado.next();
			mecanico = new Mecanico(resultado.getInt("id_mecanico"), resultado.getString("nombre"), resultado.getString("apellido"), resultado.getString("telefono_fijo"), resultado.getString("telefono_celular"), resultado.getString("domicilio"), resultado.getString("localidad"), resultado.getString("codigo_postal"));
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return mecanico;
	}
	
}
