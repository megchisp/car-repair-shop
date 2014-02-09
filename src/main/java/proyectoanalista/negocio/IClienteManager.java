package proyectoanalista.negocio;

import java.sql.Date;
import java.util.List;

import proyectoanalista.persistencia.Automovil;
import proyectoanalista.persistencia.Cliente;

public interface IClienteManager {
	public int agregar (Cliente cliente) throws Exception;
	public int modificar(Cliente cliente) throws Exception;
	public int eliminar(Cliente cliente) throws Exception;
	public List<Cliente> listaClientes() throws Exception;
	public Date ultimaVez (Cliente cliente) throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
	public String[] getApellidoNombre(Automovil automovil) throws Exception;
}
