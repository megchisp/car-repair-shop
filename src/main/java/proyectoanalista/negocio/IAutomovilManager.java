package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.Automovil;
import proyectoanalista.persistencia.Cliente;
import proyectoanalista.persistencia.Reparacion;

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




