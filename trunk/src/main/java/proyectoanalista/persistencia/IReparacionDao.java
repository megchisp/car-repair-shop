package proyectoanalista.persistencia;

import java.util.List;

public interface IReparacionDao {
	
	public int agregar ( Reparacion reparacion) throws Exception;
	public int modificar( Reparacion reparacion ) throws Exception;
	public int eliminar ( Reparacion reparacion ) throws Exception;
	public List<Reparacion> listaReparaciones(Automovil automovil) throws Exception;
	public double importe(Reparacion reparacion) throws Exception;
	public int tiempoMinimo(Reparacion reparacion) throws Exception;
	public int tiempoMaximo(Reparacion reparacion) throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
