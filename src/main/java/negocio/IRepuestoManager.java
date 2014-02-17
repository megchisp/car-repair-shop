package negocio;

import java.util.List;

import persistencia.Repuesto;
import persistencia.Servicio;

public interface IRepuestoManager {
	public int agregar ( Repuesto repuesto ) throws Exception;
	public int modificar( Repuesto repuesto ) throws Exception;
	public int eliminar ( Repuesto repuesto ) throws Exception;
	public List<Repuesto> listaRepuestos(Servicio servicio) throws Exception;
	public List<Repuesto> listaRepuestos() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;

}
