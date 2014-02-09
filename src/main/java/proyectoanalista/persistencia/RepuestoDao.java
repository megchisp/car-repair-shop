package proyectoanalista.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proyectoanalista.persistencia.BD.ConexionDB;
import proyectoanalista.persistencia.BD.PostgresConexionDB;

public class RepuestoDao  implements IRepuestoDao{
	
	ConexionDB conn = null;
	
	public RepuestoDao(){
		this.conn = new PostgresConexionDB();
	}

	public int agregar(Repuesto repuesto) throws Exception {
		try{
			String query = "INSERT INTO repuesto (id_repuesto, id_proveedor, id_servicio, nombre, precio_unitario, cantidad, observaciones) VALUES ('" + repuesto.getId_repuesto() + "'," + (repuesto.getId_proveedor() == 0 ? "null" : ("'" + repuesto.getId_proveedor() + "'"))  + ",'" + repuesto.getId_servicio() + "','" + repuesto.getNombre().replace("'", "''") + "','" + repuesto.getPrecioUnitario() + "','" + repuesto.getCantidad() + "','" + repuesto.getObservaciones().replace("'", "''") + "');";
			conn.open(); // abre la conexion
			conn.execute(query);
			conn.close(); // cierra la conexión
			return 1;

		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
			throw e;
			
		}
	}

	public int modificar(Repuesto repuestoModificado) throws Exception {
		String queryModificar = "UPDATE repuesto SET id_proveedor = " + (repuestoModificado.getId_proveedor() == 0 ? "null" : ("'" + repuestoModificado.getId_proveedor() + "'")) + ", nombre = '" + repuestoModificado.getNombre().replace("'", "''") + "', precio_unitario = '" + repuestoModificado.getPrecioUnitario() + "', cantidad = '" + repuestoModificado.getCantidad() + "', observaciones = '" + repuestoModificado.getObservaciones().replace("'", "''") + "' WHERE id_repuesto = '" + repuestoModificado.getId_repuesto() + "';";
		try{
			if(!existeRepuesto(repuestoModificado)) // se verifica que la mano de obra que se quiere modificar exista en la BD
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

	public int eliminar(Repuesto repuesto) throws Exception {
		String queryEliminar = "DELETE FROM repuesto WHERE id_repuesto = '" + repuesto.getId_repuesto() + "'";
		try{
			if (esValidoParaEliminar(repuesto)){ 
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

	public List<Repuesto> listaRepuestos(Servicio servicio) throws Exception {
		// este método retorna una lista con todas los repuestos de un servicio dado
		String query = "SELECT * FROM repuesto WHERE id_servicio =  '" + servicio.getId_servicio() + "'";
		List<Repuesto> listaRepuestos = new ArrayList<Repuesto>();
		ResultSet resultado = null;
		Repuesto repuestos = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);

			while(resultado.next()){
				repuestos = new Repuesto(resultado.getInt("id_repuesto"), resultado.getInt("id_proveedor"), resultado.getInt("id_servicio"), resultado.getString("nombre"), resultado.getDouble("precio_unitario"), resultado.getInt("cantidad"), resultado.getString("observaciones"));
				listaRepuestos.add(repuestos);
			}
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
		return listaRepuestos;
	}

	public List<Repuesto> listaRepuestos() throws Exception {
		// este método retorna una lista con todos los repuestos de la BD
		// se lo utiliza unicamente para exportar la BD
		String query = "SELECT * FROM repuesto";
		List<Repuesto> listaRepuestos = new ArrayList<Repuesto>();
		ResultSet resultado = null;
		Repuesto repuesto = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);

			while(resultado.next()){
				repuesto = new Repuesto(resultado.getInt("id_repuesto"), resultado.getInt("id_proveedor"), resultado.getInt("id_servicio"),resultado.getString("nombre"), resultado.getDouble("precio_unitario"), resultado.getInt("cantidad"), resultado.getString("observaciones"));
				listaRepuestos.add(repuesto);
			}
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
		return listaRepuestos;
	}

	public int nextID() throws Exception {
		// este método retorna el siguiente ID del repuesto que se quiera agregar
		String query = "SELECT nextval('repuesto_id_repuesto_seq')";
		ResultSet rs = null;
		int next_id_repuesto;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_repuesto = rs.getInt(1);
			conn.close(); // cierra la conexión
		} 
		catch(Exception e) 
		{
			throw e;
		}
		return next_id_repuesto;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM repuesto_id_repuesto_seq";
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
	
	private boolean esValidoParaEliminar(Repuesto repuesto) throws Exception{
		// este método retorna true si el repuesto a eliminar existe realmente
		return existeRepuesto(repuesto);
		
	}
	
	private boolean existeRepuesto(Repuesto repuesto) throws Exception{
		// este método retorna true si existe el repuesto pasada como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM repuesto WHERE id_repuesto = " + repuesto.getId_repuesto() + ") THEN true ELSE false END";
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
