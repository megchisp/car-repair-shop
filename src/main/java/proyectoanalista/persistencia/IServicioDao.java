package proyectoanalista.persistencia;

import java.util.List;

public interface IServicioDao {
	public int agregar ( Servicio servicio ) throws Exception;
	public int eliminar ( Servicio servicio ) throws Exception;
	public List<Servicio> listaServicios(Reparacion reparacion) throws Exception;
	public List<Servicio> listaServicios() throws Exception;
	public double importe(Servicio servicio) throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
