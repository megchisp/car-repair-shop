package persistencia;

import java.sql.Date;
import java.util.List;

public interface IClienteDao {
	
	public int agregar ( Cliente cliente ) throws Exception;
	public int modificar( Cliente cliente ) throws Exception;
	public int eliminar ( Cliente cliente ) throws Exception;
	public List<Cliente> listaClientesPorNombre() throws Exception;
	public List<Cliente> listaClientesPorID() throws Exception;
	public List<Cliente> listaClientesNoFallecidos() throws Exception;
	public Date ultimaVez (Cliente cliente) throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
	public String[] getApellidoNombre ( Automovil automovil ) throws Exception;
	public Cliente getCliente (Automovil automovil) throws Exception;
}

