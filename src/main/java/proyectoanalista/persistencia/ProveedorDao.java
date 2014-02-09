package proyectoanalista.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proyectoanalista.persistencia.BD.ConexionDB;
import proyectoanalista.persistencia.BD.PostgresConexionDB;

public class ProveedorDao implements IProveedorDao {
	
	ConexionDB conn = null;

	public ProveedorDao() {
		this.conn = new PostgresConexionDB();
	}	

	public int agregar(Proveedor proveedor) throws Exception {
		try{
			if (esValidoParaAgregar(proveedor)){ 
				String query = "INSERT INTO proveedor (id_proveedor, nombre, telefono, telefono_alternativo) VALUES ('"+ proveedor.getId_proveedor() + "','"+ proveedor.getNombre().replace("'", "''") + "','" + proveedor.getTelefono() + "','" + proveedor.getTelefonoAlternativo() + "');";

				conn.open(); // abre la conexion
				conn.execute(query);
				conn.close(); // cierra la conexión

				return 1;
			}
			return 2; // el nombre ingresado pertenece a un proveedor existente
		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(Proveedor proveedorModificado) throws Exception {
		String queryModificar = "UPDATE Proveedor SET nombre = '" + proveedorModificado.getNombre().replace("'", "''") + "', telefono = '" + proveedorModificado.getTelefono() + "', telefono_alternativo = '" + proveedorModificado.getTelefonoAlternativo() + "' WHERE id_proveedor = '" + proveedorModificado.getId_proveedor() + "';";
		try{
			if(!existeProveedor(proveedorModificado)) // se verifica que el proveedor que se quiere modificar exista en la BD
				return 7;
			if (esValidoParaModificar(proveedorModificado)){ 
				
				conn.open(); // abre la conexion
				conn.execute(queryModificar);
				conn.close(); // cierra la conexión
				
				return 1; // éxito
			}
			return 2; // el nombre ingresado pertenece a un proveedor existente
			
		}catch (Exception e){
			throw e;
		}
	}

	public int eliminar(Proveedor proveedor) throws Exception{ 
		String queryEliminar = "DELETE FROM Proveedor WHERE id_proveedor = '" + proveedor.getId_proveedor() + "'";
		try{
			if (esValidoParaEliminar(proveedor)){ 
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
	
	public List<Proveedor> listaProveedores() throws Exception{
		// este método retorna una lista con todos los clientes de la BD
		String query = "SELECT * FROM proveedor ORDER BY nombre ASC";
		List<Proveedor> listaProveedores = new ArrayList<Proveedor>();
		ResultSet resultado = null;
		Proveedor proveedor = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				proveedor = new Proveedor(resultado.getInt("id_proveedor"), resultado.getString("nombre"), (resultado.getString("telefono")), (resultado.getString("telefono_alternativo")));
				listaProveedores.add(proveedor);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaProveedores;
	}

	private boolean esValidoParaAgregar(Proveedor proveedor) throws Exception{
		// este método retorna true si existe un cliente con cierto Nombre. Se lo utiliza para agregar un nuevo proveedor
		if(proveedor.getNombre().isEmpty())
			return true;
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM proveedor WHERE nombre = '"+ proveedor.getNombre() +"') THEN true ELSE false END";
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
	
	private boolean esValidoParaModificar(Proveedor proveedor) throws Exception{
		// este método retorna true si existe un cliente con cierto CUIT que no sea él mismo. Se lo utiliza para modificar un cliente
		if(proveedor.getNombre().isEmpty())
			return true;
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM proveedor WHERE nombre = '"+ proveedor.getNombre() +"' AND id_proveedor != " + proveedor.getId_proveedor() + ") THEN true ELSE false END";
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
	
	private boolean esValidoParaEliminar(Proveedor proveedor) throws Exception{
		// este método retorna true si el proveedor a eliminar existe realmente
		return existeProveedor(proveedor);
	}
	
	private boolean existeProveedor(Proveedor proveedor) throws Exception{
		// este método retorna true si existe el proveedor pasado como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM proveedor WHERE id_proveedor = " + proveedor.getId_proveedor() + ") THEN true ELSE false END";
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
	
	public int nextID() throws Exception{
		// este método retorna el siguiente ID del proveedor que se quiera agregar
		String query = "SELECT nextval('proveedor_id_proveedor_seq')";
		ResultSet rs = null;
		int next_id_proveedor;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			next_id_proveedor = rs.getInt(1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return next_id_proveedor;
	}

	public int lastValue() throws Exception {
		// este método devuelve el último valor de secuencia registrado en la tabla
		String query = "SELECT last_value FROM proveedor_id_proveedor_seq";
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

	public Proveedor getProveedor(int id_proveedor) throws Exception {
		// este método retorna el proveedor con el id pasado como parametro
		String query = "SELECT * FROM proveedor WHERE id_proveedor = '" + id_proveedor + "'";
		ResultSet resultado = null;
		Proveedor proveedor = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			resultado.next();
			proveedor = new Proveedor(resultado.getInt("id_proveedor"), resultado.getString("nombre"), resultado.getString("telefono"), resultado.getString("telefono_alternativo")); 
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return proveedor;
	}

}
