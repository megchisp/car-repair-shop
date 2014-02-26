package persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import persistencia.BD.ConexionDB;
import persistencia.BD.PostgresConexionDB;


public class ClienteDao implements IClienteDao {
	
	ConexionDB conn = null;

	// en esta clase se escriben las sentencias SQL literalmente

	public ClienteDao() {
		this.conn = new PostgresConexionDB();
	}	

	public int agregar(Cliente cliente) throws Exception {
		try{
			if (esValidoParaAgregar(cliente)){ 
				String query = "INSERT INTO cliente (id_cliente, cuit,fecha_nacimiento,fallecido,nombre,apellido,telefono,celular,domicilio,localidad,codigo_postal,mail,observaciones) VALUES ('"+ cliente.getId_cliente() + "','"+ cliente.getCUIT() + "','" + new java.sql.Date(cliente.getFechaDeNacimiento().getTimeInMillis()) + "','" + cliente.isFallecido()  +"','" + cliente.getNombre().replace("'", "''") + "','" + cliente.getApellido().replace("'", "''") + "','" + cliente.getTelefono() + "','" + cliente.getCelular() + "','" + cliente.getDomicilio().replace("'", "''") + "','" + cliente.getLocalidad().replace("'", "''") + "','" + cliente.getCodigoPostal().replace("'", "''") + "','" + cliente.getEmail() + "','" + cliente.getObservaciones().replace("'", "''") + "');";
				
					conn.open(); // abre la conexion
					conn.execute(query);
					conn.close(); // cierra la conexión
					
					return 1;
			}
			return 2; // el CUIT ingresado pertenece a un cliente existente
		} 
		catch(Exception e) 
		{ 
			throw e;
		}
	}

	public int modificar(Cliente clienteModificado) throws Exception {
		String queryModificar = "UPDATE Cliente SET cuit = '" + clienteModificado.getCUIT() + "', fecha_nacimiento = '" + new java.sql.Date(clienteModificado.getFechaDeNacimiento().getTimeInMillis()) + "', fallecido = '" + clienteModificado.isFallecido() +  "', nombre = '" + clienteModificado.getNombre().replace("'", "''") + "', apellido = '" + clienteModificado.getApellido().replace("'", "''") + "', telefono = '" + clienteModificado.getTelefono() + "', celular = '" + clienteModificado.getCelular() + "', domicilio = '" + clienteModificado.getDomicilio().replace("'", "''") + "', localidad = '" + clienteModificado.getLocalidad().replace("'", "''") + "', codigo_postal = '" + clienteModificado.getCodigoPostal().replace("'", "''") + "', mail = '" + clienteModificado.getEmail() + "', observaciones = '" + clienteModificado.getObservaciones().replace("'", "''") + "' WHERE id_cliente = '" + clienteModificado.getId_cliente() + "';";
		try{
			if(!existeCliente(clienteModificado)) // se verifica que el cliente que se quiere modificar exista en la BD
				return 7;
			if (esValidoParaModificar(clienteModificado)){ 
				
				conn.open(); // abre la conexion
				conn.execute(queryModificar);
				conn.close(); // cierra la conexión
				
				return 1; // éxito
			}
			return 2; // el CUIT ingresado pertenece a un cliente existente
			
		}catch (Exception e){
			throw e;
		}
	}

	public int eliminar(Cliente cliente) throws Exception{ 
		String queryEliminar = "DELETE FROM Cliente WHERE id_cliente = '" + cliente.getId_cliente() + "'";
		try{
			if (esValidoParaEliminar(cliente)){ 
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
	
	public List<Cliente> listaClientes() throws Exception{
		// este método retorna una lista con todos los clientes de la BD
		String query = "SELECT * FROM cliente ORDER BY apellido, nombre ASC";
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		ResultSet resultado = null;
		Cliente cliente = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				cliente = new Cliente(resultado.getInt("id_cliente"), resultado.getString("cuit"), (resultado.getDate ("fecha_nacimiento")), resultado.getBoolean("fallecido"), resultado.getString("nombre"), resultado.getString("apellido"), resultado.getString("telefono"), resultado.getString("celular"), resultado.getString("domicilio"), resultado.getString("localidad"), resultado.getString("codigo_postal"), resultado.getString("mail"), resultado.getString("observaciones"));
				listaClientes.add(cliente);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaClientes;
	}

	public List<Cliente> listaClientesNoFallecidos() throws Exception{
		// este método retorna una lista con todos los clientes no fallecidos de la BD
		// se utiliza para mostrar los cumpleaños
		String query = "SELECT * FROM cliente WHERE fallecido = false ORDER BY apellido, nombre ASC";
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		ResultSet resultado = null;
		Cliente cliente = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			
			while(resultado.next()){
				cliente = new Cliente(resultado.getInt("id_cliente"), resultado.getString("cuit"), (resultado.getDate ("fecha_nacimiento")), resultado.getBoolean("fallecido"), resultado.getString("nombre"), resultado.getString("apellido"), resultado.getString("telefono"), resultado.getString("celular"), resultado.getString("domicilio"), resultado.getString("localidad"), resultado.getString("codigo_postal"), resultado.getString("mail"), resultado.getString("observaciones"));
				listaClientes.add(cliente);
			}
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return listaClientes;
	}

	
	private boolean esValidoParaAgregar(Cliente cliente) throws Exception{
		// este método retorna true si existe un cliente con cierto CUIT. Se lo utiliza para agregar un nuevo cliente
		if(cliente.getCUIT().isEmpty())
			return true;
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM CLIENTE WHERE CUIT = '"+ cliente.getCUIT() +"') THEN true ELSE false END";
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
	
	private boolean esValidoParaModificar(Cliente cliente) throws Exception{
		// este método retorna true si existe un cliente con cierto CUIT que no sea él mismo. Se lo utiliza para modificar un cliente
		if(cliente.getCUIT().isEmpty())
			return true;
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM CLIENTE WHERE CUIT = '"+ cliente.getCUIT() +"' AND ID_CLIENTE != " + cliente.getId_cliente() + ") THEN true ELSE false END";
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
	
	private boolean esValidoParaEliminar(Cliente cliente) throws Exception{
		// este método retorna true si el cliente a eliminar existe realmente
		return existeCliente(cliente);
	}
	
	private boolean existeCliente(Cliente cliente) throws Exception{
		// este método retorna true si existe el cliente pasado como parametro
		String query = "SELECT CASE WHEN EXISTS (SELECT * FROM CLIENTE WHERE ID_CLIENTE = " + cliente.getId_cliente() + ") THEN true ELSE false END";
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
		// este método retorna el siguiente ID del cliente que se quiera agregar
		String query = "SELECT nextval('cliente_id_cliente_seq')";
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
		String query = "SELECT last_value FROM cliente_id_cliente_seq";
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

	public String[] getApellidoNombre(Automovil automovil) throws Exception {
		// este método retorna el apellido y nombre del propietario del automovil pasado como parámetro
		String query = "SELECT apellido, nombre FROM AUTOMOVIL NATURAL JOIN CLIENTE WHERE id_cliente = " + automovil.getId_cliente();
		ResultSet rs = null;
		String apellidoNombre[] = null;
		try{
			conn.open(); // abre la conexion
			rs = conn.query(query);
			rs.next();
			apellidoNombre = new String[] {rs.getString(1), rs.getString(2)};
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return apellidoNombre;
	}

	public Date ultimaVez(Cliente cliente) throws Exception {
		// este método retorna cuándo fué la última vez que el cliente pasado como parámetro visitó el taller
		// es decir, la ultima fecha de reparación registrada a su nombre
		String query = "SELECT MAX(FECHA) FROM AUTOMOVIL NATURAL JOIN REPARACION WHERE ID_CLIENTE = '" + cliente.getId_cliente()  + "'";
		ResultSet resultado = null;
		Date fecha = null;

		try{
			conn.open(); // abre la conexion
			resultado = conn.query(query);
			resultado.next();
			fecha = resultado.getDate (1);
			conn.close(); // cierra la conexión
			} 
			catch(Exception e) 
			{
				throw e;
			}
		return fecha;
	}
}


