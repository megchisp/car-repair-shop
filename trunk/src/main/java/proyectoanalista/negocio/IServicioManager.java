package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.Reparacion;
import proyectoanalista.persistencia.Servicio;

public interface IServicioManager {
	public int agregar ( Servicio servicio ) throws Exception;
	public int eliminar ( Servicio servicio ) throws Exception;
	public List<Servicio> listaServicios(Reparacion reparacion) throws Exception;
	public List<Servicio> listaServicios() throws Exception;
	public double importe(Servicio servicio) throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
