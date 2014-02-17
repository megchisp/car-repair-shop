package negocio;

import java.util.List;

import persistencia.Automovil;
import persistencia.Cliente;
import persistencia.Reparacion;

public interface IAutomovilManager {
	
	public int agregar ( Automovil automovil) throws Exception;
	public int modificar( Automovil automovil ) throws Exception;
	public int eliminar ( Automovil automovil ) throws Exception;
	public List<Automovil> listaAutomoviles(Cliente cliente) throws Exception;
	public List<Automovil> listaAutomoviles() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
	public Automovil getAutomovil(Reparacion reparacion) throws Exception;
}




