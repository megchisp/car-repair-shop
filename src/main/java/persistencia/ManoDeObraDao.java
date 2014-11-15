package persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import persistencia.BD.ConexionDB;
import persistencia.BD.PostgresConexionDB;

public class ManoDeObraDao implements IManoDeObraDao {
	
	ConexionDB conn = null;
	
	public ManoDeObraDao(){
		this.conn = new PostgresConexionDB();
	}

	public int agregar(ManoDeObra manoDeObra) throws Exception {
		try{
			String query = "INSERT INTO MANO_DE_OBRA (id_mano_de_obra, id_servicio, id_mecanico, nombre,precio_mano_de_obra,observaciones) VALUES ('" + manoDeObra.getId_mano_de_obra() + "','"+ manoDeObra.getId_servicio() + "'," + (manoDeObra.getId_mecanico() == 0 ? "null" : ("'" + manoDeObra.getId_mecanico() + "'"))  + ",'" + manoDeObra.getNombre().replace("'", "''") + "','" + manoDeObra.getPrecio() + "','" + manoDeObra.getObservaciones().replace("'", "''") + "');";
			conn.open(); // abre la conexion
			conn.execute(query);
			conn.close(); // cierra la conexión
			return 1;

		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(ManoDeObra manoDeObraModificada) throws Exception {
		String queryModificar = "UPDATE MANO_DE_OBRA SET ID_MECANICO = " + (manoDeObraModificada.getId_mecanico() == 0 ? "null" : ("'" + manoDeObraModificada.getId_mecanico() + "'")) + ", nombre = '" + manoDeObraModificada.getNombre().replace("'", "''") + "', precio_mano_de_obra = '" + manoDeObraModificada.getPrecio() + "', observaciones = '" + manoDeObraModificada.getObservaciones().replace("'", "''") + "' WHERE id_mano_de_obra = '" + manoDeObraModificada.getId_mano_de_obra() + "';";
		try{
			if(!existeManoDeObra(manoDeObraModificada)) // se verifica que la mano de obra que se quiere modificar exista en la BD
				return 3;
			if (true){ 
				
				conn.open(); // abre la conexion
				conn.execute(queryModificar);
				conn.close(); // cierra la conexión
			}
			
		}catch (Exception e){
			throw e;
		}
		return 1; // éxito
	}

	public int eliminar(ManoDeObra manoDeObra) throws Exception {
		String queryEliminar = "DELETE FROM MANO_DE_OBRA WHERE ID_MANO_DE_OBRA = '" + manoDeObra.getId_mano_de_obra() + "'";
		try{
			if (esValidoParaEliminar(manoDeObra)){ 
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

	public List<ManoDeObra> listaManosDeObras(Servicio servicio) throws Exception {
		// este método retorna una lista con todas las manos de obras de un servicio dado
		String query = "SELECT * FROM MANO_DE_OBRA WHERE ID_SERVICIO =  '" + servicio.getId_servicio() + "'";
		List<ManoDeObra> listaManosDeObras = new ArrayList<ManoDeObra>();
		ResultSet resultado = null;
		ManoDeObra manoDeObra = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				manoDeObra = new ManoDeObra(resultado.getInt("id_mano_de_obra"), resultado.getInt("id_servicio"), resultado.getInt("id_mecanico"),resultado.getString("nombre"), resultado.getDouble("precio_mano_de_obra"), resultado.getString("observaciones"));
				listaManosDeObras.add(manoDeObra);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaManosDeObras;
	}
	

	public List<String> listaNombreManosDeObras() throws Exception {
		// este método retorna una lista con todos los nombres de manos de obras
		// se utiliza para autocompletar el jtextfield
		String query = "SELECT DISTINCT nombre FROM mano_de_obra";
		List<String> listaNombreManosDeObra = new ArrayList<String>();
		ResultSet resultado = null;
		String nombreManoDeObra = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);

			while(resultado.next()){
				nombreManoDeObra = new String(resultado.getString("nombre"));
				listaNombreManosDeObra.add(nombreManoDeObra);
			}
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
		return listaNombreManosDeObra;
	}
	
	public List<ManoDeObra> listaManosDeObras() throws Exception {
		// este método retorna una lista con todas las manos de obras de la BD
		// se lo utiliza unicamente para exportar la BD
		String query = "SELECT * FROM MANO_DE_OBRA";
		List<ManoDeObra> listaManosDeObras = new ArrayList<ManoDeObra>();
		ResultSet resultado = null;
		ManoDeObra manoDeObra = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				manoDeObra = new ManoDeObra(resultado.getInt("id_mano_de_obra"), resultado.getInt("id_servicio"), resultado.getInt("id_mecanico"),resultado.getString("nombre"), resultado.getDouble("precio_mano_de_obra"), resultado.getString("observaciones"));
				listaManosDeObras.add(manoDeObra);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaManosDeObras;
	}

	public int nextID() throws Exception {
		// este método retorna el siguiente ID de la mano de obra que se quiera agregar
		String query = "SELECT nextval('mano_de_obra_id_mano_de_obra_seq')";
		ResultSet rs = null;
		int next_id_mano_de_obra;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_mano_de_obra = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return next_id_mano_de_obra;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM mano_de_obra_id_mano_de_obra_seq";
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
	
	private boolean esValidoParaEliminar(ManoDeObra manoDeObra) throws Exception{
		// este método retorna true si la mano de obra a eliminar existe realmente
		return existeManoDeObra(manoDeObra);
		
	}
	
	private boolean existeManoDeObra(ManoDeObra manoDeObra) throws Exception{
		// este método retorna true si existe la mano de obra pasada como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM MANO_DE_OBRA WHERE ID_MANO_DE_OBRA = " + manoDeObra.getId_mano_de_obra() + ") THEN true ELSE false END";
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
}
